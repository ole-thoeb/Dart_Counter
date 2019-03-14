package com.example.eloem.dartCounter

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eloem.dartCounter.database.deleteAllGamesComplete
import com.example.eloem.dartCounter.database.insertCompleteGame
import com.example.eloem.dartCounter.games.DoubleOutGame
import com.example.eloem.dartCounter.games.MasterOutGame
import com.example.eloem.dartCounter.games.SingleOutGame
import com.example.eloem.dartCounter.games.Player
import com.example.eloem.dartCounter.helperClasses.BetterEditText
import com.example.eloem.dartCounter.helperClasses.EditListAdapter
import com.example.eloem.dartCounter.util.*
import kotlinx.android.synthetic.main.activity_new_dartgame.*
import org.jetbrains.anko.doAsync
import java.lang.Error

class NewDartGameActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_dartgame)
        
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //setUpToolbar()
        
        val modeArray = resources.getStringArray(R.array.modes)
        val mAdapterModes = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                modeArray)
    
        modeSpinner.adapter = mAdapterModes
    
        val pointsArray = resources.getIntArray(R.array.startingPoints).toTypedArray()
        val mAdapterPoints = ArrayAdapter<Int>(this,
                android.R.layout.simple_list_item_1,
                pointsArray)
    
        startPointsSpinner.adapter = mAdapterPoints
    
        val initPlayers = savedInstanceState?.getParcelableArray(KEY_LIST_DATA)?.let {
            it.toMutableList() as MutableList<Player>
        } ?: mutableListOf()
        
        val viewAdapter = PlayerListAdapter(initPlayers)
        val viewManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        playerList.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
    
        fab.setOnClickListener {
            val players = viewAdapter.values
            val mode = modeSpinner.selectedItemPosition
            val startingPoints = pointsArray[startPointsSpinner.selectedItemPosition]
            startGame(players, mode, startingPoints)
        }
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray(KEY_LIST_DATA, (playerList.adapter as PlayerListAdapter).values.toTypedArray())
    }
    
    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(this, currentFocus)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId){
        R.id.gameList -> {
            hideSoftKeyboard(this, this.currentFocus)
        
            val intent = Intent(this, ListOfGamesActivity::class.java)
            startActivity(intent)
        
            true
        }
        R.id.deleteAllGames -> {
            deleteAllGamesComplete(this)
            true
        }
        else -> false
    }
    
    private fun startGame(players: MutableList<Player>, pos: Int, startingPoints: Int){
        val cleanedPlayers = players.filter { it.name != "" }
        
        if (cleanedPlayers.isEmpty()){
            Toast.makeText(this,
                    resources.getString(R.string.noPlayer),
                    Toast.LENGTH_SHORT).show()
            return
        }
        
        val playerArray = Array(cleanedPlayers.size) {
            cleanedPlayers[it].copy(id = newPlayerID(this), points = startingPoints)
        }
        
        val dartGame = when(pos){
            0 -> SingleOutGame(newGameID(this), playerArray)
            1 -> DoubleOutGame(newGameID(this), playerArray)
            2 -> MasterOutGame(newGameID(this), playerArray)
            else -> SingleOutGame(newGameID(this), playerArray)
        }
        
        doAsync { insertCompleteGame(applicationContext, dartGame) }
        
        hideSoftKeyboard(this, this.currentFocus)
    
        startActivity(Intent(this, GameActivity::class.java)
                .putExtra(GameActivity.DART_GAME_EXTRA, dartGame.id))
    }
    
    class PlayerListAdapter(values: MutableList<Player>): EditListAdapter<Player>(values) {
        
        class EditVH(layout: View): EditRowVH(layout){
            override val itemNameET: BetterEditText = layout.findViewById(R.id.playerName)
            override val deleteButton: ImageButton = layout.findViewById(R.id.deleteButton)
        }
        
        class AddItemVH(layout: View): RecyclerView.ViewHolder(layout) {
            val root: LinearLayout = layout.findViewById(R.id.linLayout)
        }
        
        override fun writeEditContent(pos: Int, content: String) {
            values[pos].name = content
        }
    
        override fun readEditContent(pos: Int): String = values[pos].name
    
        /**id is added in [startGame]*/
        override fun newItem(pos: Int, s: String): Player = Player(0, s, 0, mutableListOf())
    
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
                = when(viewType) {
            
            VIEW_TYPE_EDIT_ROW -> EditVH(
                    LayoutInflater
                            .from(context)
                            .inflate(R.layout.item_new_dartgame_edit_player, parent, false)
            )
            1 -> AddItemVH(
                    LayoutInflater
                            .from(context)
                            .inflate(R.layout.item_new_dartgame_new_player, parent, false)
            )
            else -> throw Error("unknown viewType: $viewType")
        }
    
        override fun getItemViewType(position: Int): Int = when(position) {
            in 0 until values.size -> VIEW_TYPE_EDIT_ROW
            else -> 1
        }
    
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            super.onBindViewHolder(holder, position)
            
            if (holder.itemViewType == 1) {
                holder as AddItemVH
                holder.root.setOnClickListener { addNewItem(values.size) }
            }
        }
    
        override fun getItemCount(): Int = values.size + 1
    }
    
    companion object {
        const val KEY_LIST_DATA = "dataListKey"
    }
}

package com.example.eloem.dartCounter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.eloem.dartCounter.database.deleteAllGamesComplete
import com.example.eloem.dartCounter.database.insertCompleteGame
import com.example.eloem.dartCounter.games.DoubleOutGame
import com.example.eloem.dartCounter.games.MasterOutGame
import com.example.eloem.dartCounter.games.SingleOutGame
import com.example.eloem.dartCounter.games.Player
import com.example.eloem.dartCounter.util.*
import emil.beothy.adapter.AddListItemsAdapter
import emil.beothy.widget.BetterEditText
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync

class MainActivity : Activity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        setUpToolbar()
        
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
    
        val player = if (savedInstanceState != null){
                @Suppress ("UNCHECKED_CAST")
                //it´s save, trust me. you can´t check it
                savedInstanceState.getParcelableArray(KEY_LIST_DATA).toMutableList() as MutableList<Player>
            } else mutableListOf()
        
        val viewAdapter = MyListAdapter(this, player)
        val viewManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        playerList.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
    
        fab.setOnClickListener { _ ->
            val players = viewAdapter.values
            val mode = modeSpinner.selectedItemPosition
            val startingPoints = pointsArray[startPointsSpinner.selectedItemPosition]
            startGame(players, mode, startingPoints)
        }
    }
    
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArray(KEY_LIST_DATA, (playerList.adapter as MyListAdapter).values.toTypedArray())
    }
    
    override fun onPause() {
        super.onPause()
        hideSoftKeyboard(this, currentFocus)
    }
    
    private fun setUpToolbar(){
        //set up toolbar
        toolbar.inflateMenu(R.menu.activity_main_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
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
        }
    }
    
    private fun startGame(players: MutableList<Player>, pos: Int, startingPoints: Int){
        val cleanedPlayers = players.filter { it.name != "" }
        
        if (cleanedPlayers.isEmpty()){
            Toast.makeText(this,
                    resources.getString(R.string.noPlayer),
                    Toast.LENGTH_SHORT).show()
            return
        }
        
        val playerArray = Array(cleanedPlayers.size){
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
    
    class MyListAdapter(private val context: Context, values: MutableList<Player>): AddListItemsAdapter<Player>(values){
        
        class ViewHolder1(layout: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(layout){
            val playerName: BetterEditText = layout.findViewById(R.id.playerName)
            val deleteButton: ImageButton = layout.findViewById(R.id.deleteButton)
        }
        
        class ViewHolder2(layout: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(layout){
            val linLayout: LinearLayout = layout.findViewById(R.id.linLayout)
        }
    
        override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
            when (holder.itemViewType){
                0 ->{
                    val realHolder = holder as ViewHolder1
                    
                    with(realHolder.playerName){
                        setText(values[position].name, TextView.BufferType.EDITABLE)
                        
                        setSpecialKeyListener = {
                            if (it.action == KeyEvent.ACTION_UP) {
                                when (it.keyCode) {
                                    KeyEvent.KEYCODE_ENTER -> {
                                        addNewItem(realHolder.adapterPosition + 1)
                                    }
                                }
                            }
                        }
    
                        setOnKeyListener{_, _, keyEvent ->
                            //handel special actions on backspace
                            if (keyEvent.action == KeyEvent.ACTION_UP){
                                when(keyEvent.keyCode){
                                    KeyEvent.KEYCODE_DEL ->{
                                        removeItem(realHolder.adapterPosition)
                                        return@setOnKeyListener true
                                    }
                                    KeyEvent.KEYCODE_ENTER ->{
                                        addNewItem(realHolder.adapterPosition + 1)
                                        return@setOnKeyListener true
                                    }
                                }
                            }
                            false
                        }
    
                        setTextChangeListener = { charSequence, _ ->
                            val pos = realHolder.adapterPosition
                            if(pos < values.size) {
                                values[pos].name = charSequence.toString()
                            }
                        }
                    }
                    
                    //set Focus to newly added textViews
                    realHolder.playerName.requestFocus()
                    //show keyboard and not hide it if it is visible
                    val ipm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    ipm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
                    
                    realHolder.deleteButton.setOnClickListener{ removeItem(realHolder.adapterPosition) }
                }
                
                1 ->{
                    val realHolder = holder as ViewHolder2
                    realHolder.linLayout.setOnClickListener { addNewItem(values.size) }
                }
            }
        }
    
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
            return when(viewType){
                0 ->{
                    val layout = LayoutInflater.from(parent.context).inflate(R.layout.player_list_row, parent,false)
                    ViewHolder1(layout)
                }
                else ->{
                    val layout = LayoutInflater.from(parent.context).inflate(R.layout.new_player_row, parent,false)
                    ViewHolder2(layout)
                }
            }
        }
    
        override fun getItemViewType(position: Int): Int {
            return if (position < values.size) 0
            else 1
        }
        
        override fun getItemCount() = values.size + 1
    
        /**id is added in [startGame]*/
        override fun defaultItem() = Player(0, "", 0, mutableListOf())
    
        override fun removeItem(pos: Int) {
            val vH = mRecyclerView.findViewHolderForAdapterPosition(pos) as ViewHolder1
            if (vH.playerName.hasFocus()){
                if (pos > 0){
                    //if deleted textView had focus switch it to the one before
                    val vH2 = mRecyclerView.findViewHolderForAdapterPosition(pos -1) as ViewHolder1
                    vH2.playerName.requestFocus()
                }else{
                    //if it was the last text view don't set focus and hide keyboard
                    hideSoftKeyboard(context, vH.playerName)
                }
            }
            
            super.removeItem(pos)
        }
    }
    
    companion object {
        const val KEY_LIST_DATA = "dataListKey"
    }
}

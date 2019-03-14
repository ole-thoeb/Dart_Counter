package com.example.eloem.dartCounter

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eloem.dartCounter.database.deleteAllGamesComplete
import com.example.eloem.dartCounter.database.deleteCompleteGame
import com.example.eloem.dartCounter.database.getAllGames
import com.example.eloem.dartCounter.database.insertCompleteGame
import com.example.eloem.dartCounter.games.DartGame
import com.example.eloem.dartCounter.recyclerview.ContextAdapter
import com.example.eloem.dartCounter.recyclerview.GridSpacingItemDecoration
import com.example.eloem.dartCounter.recyclerview.PaddingAdapter
import com.example.eloem.dartCounter.util.dp
import com.example.eloem.dartCounter.util.newGameID
import com.example.eloem.dartCounter.util.newPlayerID
import com.google.android.material.card.MaterialCardView
import emil.beothy.utilFun.deepCopy
import kotlinx.android.synthetic.main.activity_list_of_games.*
import java.util.*


class ListOfGamesActivity : AppCompatActivity() {
    private lateinit var recyclerViewAdapter: MyListAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_games)
        setSupportActionBar(toolbar)
        
        //real data is set in onResume
        recyclerViewAdapter = MyListAdapter(mutableListOf())
        list.apply {
            layoutManager = GridLayoutManager(this@ListOfGamesActivity, 2)
            addItemDecoration(
                    GridSpacingItemDecoration(2,
                            resources.getDimensionPixelSize(R.dimen.cardGridSpacing),
                            true)
            )
            adapter = PaddingAdapter(recyclerViewAdapter, 10.dp)
            emptyView = empty
        }
        
        newGameFAB.setOnClickListener {
            startActivity(Intent(this, NewDartGameActivity::class.java))
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_list_of_games_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId){
        R.id.deleteAllGames -> {
            deleteAllGamesComplete(this)
            recyclerViewAdapter.data = mutableListOf()
            recyclerViewAdapter.notifyDataSetChanged()
            true
        }
        R.id.deleteAllFinishedGames -> {
            recyclerViewAdapter.data.forEachIndexed { index, dartGame ->
                if (dartGame.isFinished) {
                    recyclerViewAdapter.data.remove(dartGame)
                    deleteCompleteGame(this, dartGame)
                    recyclerViewAdapter.notifyItemRemoved(index)
                }
            }
            true
        }
        R.id.deleteAllRunningGames -> {
            recyclerViewAdapter.data.forEachIndexed { index, dartGame ->
                if (!dartGame.isFinished) {
                    recyclerViewAdapter.data.remove(dartGame)
                    deleteCompleteGame(this, dartGame)
                    recyclerViewAdapter.notifyItemRemoved(index)
                }
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    
    override fun onResume() {
        super.onResume()
        recyclerViewAdapter.data = getAllGames(this)
        recyclerViewAdapter.notifyDataSetChanged()
    }
    
    class MyListAdapter(var data: MutableList<DartGame>): ContextAdapter<MyListAdapter.GameViewHolder>(){
        
        class GameViewHolder(layout: View): RecyclerView.ViewHolder(layout) {
            val card: MaterialCardView = layout.findViewById(R.id.card)
            val players: TextView = layout.findViewById(R.id.players)
            val status: TextView = layout.findViewById(R.id.status)
            val date: TextView = layout.findViewById(R.id.date)
            val optionButton: ImageButton = layout.findViewById(R.id.menuButton)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
            return GameViewHolder(inflate(R.layout.card_list_of_games, parent))
        }
    
        override fun getItemCount(): Int = data.size
    
        override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
            val game = data[position]
    
            with(holder){
                players.text = context.getString(R.string.cardPlayers, game.players.size)
                date.text = DateFormat.getDateFormat(context).format(game.date)
                status.text = when (game.state){
                    DartGame.STATE_ON_GOING -> context.getString(R.string.onGoingGame)
                    DartGame.STATE_DRAW -> context.getString(R.string.drawMessage,
                            game.winners.joinToString(separator = " ${context.getString(R.string.and)} "){
                                it.name
                            })
                    DartGame.STATE_WINNER -> context.getString(R.string.cardWinner, game.winners[0].name)
            
                    else -> context.getString(R.string.error)
                }
                card.setOnClickListener {
                    if (game.isFinished){
                        startActivity(context,
                                Intent(context, ScoreScreen::class.java).putExtra(ScoreScreen.GAME_ID_ARG, game.id),
                                null
                        )
                    }else {
                        startActivity(context,
                                Intent(context, GameActivity::class.java).putExtra(GameActivity.DART_GAME_EXTRA, game.id),
                                null
                        )
                    }
                }
                optionButton.setOnClickListener { button ->
                    val popupMenu = PopupMenu(context, button)
                    with(popupMenu) {
                        menuInflater.inflate(R.menu.list_of_games_context_menu, popupMenu.menu)
                
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.delete -> {
                                    val pos = holder.adapterPosition
                                    deleteCompleteGame(context, data[pos])
                            
                                    data.removeAt(pos)
                                    notifyItemRemoved(pos)
                                    true
                                }
                                R.id.revenge -> {
                                    val pos = holder.adapterPosition
                                    val newGame = data[pos].copy(
                                            pId = newGameID(context),
                                            pPlayers = data[pos].players.deepCopy {
                                                it.deepCopy(pId = newPlayerID(context),
                                                        pPoints = it.startingPoints)
                                            },
                                            pDate = Calendar.getInstance().time)
                                    insertCompleteGame(context, newGame)
                            
                                    startActivity(context,
                                            Intent(context, GameActivity::class.java)
                                                    .putExtra(GameActivity.DART_GAME_EXTRA, newGame.id),
                                            null
                                    )
                                    true
                                }
                                else -> false
                            }
                        }
                        show()
                    }
                }
            }
        }
    
        override fun getItemId(position: Int): Long = position.toLong()
    }
}
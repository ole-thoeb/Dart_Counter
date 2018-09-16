package com.example.eloem.dartCounter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.*
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import com.example.eloem.dartCounter.helperClasses.games.DartGame
import com.example.eloem.dartCounter.util.*
import emil.beothy.utilFun.deepCopy
import kotlinx.android.synthetic.main.activity_list_of_games.*
import java.util.*

class ListOfGamesActivity : Activity() {
    lateinit var games: MutableList<DartGame>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_games)
        
        toolbar.inflateMenu(R.menu.activity_list_of_games_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.deleteAllGames -> {
                    deleteAllGamesComplete(this)
                    refreshList()
                    true
                }
                R.id.deleteAllFinishedGames -> {
                    for (game in games) {
                        if (game.isFinished) deleteCompleteGame(this, game)
                        refreshList()
                    }
                    true
                }
                R.id.deleteAllRunningGames -> {
                    for (game in games) {
                        if (!game.isFinished) deleteCompleteGame(this, game)
                        refreshList()
                    }
                    true
                }
                else -> false
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        
        refreshList()
    }
    
    private fun refreshList(){
        games = getAllGames(this)
        list.adapter = MyListAdapter(games)
    }
    
    inner class MyListAdapter(var data: MutableList<DartGame>): BaseAdapter(){
        
        override fun getCount(): Int = data.size
    
        override fun getItem(position: Int): DartGame = data[position]
    
        override fun getItemId(position: Int): Long = position.toLong()
    
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val game = getItem(position)
            
            val viewHolder = convertView?: layoutInflater.inflate(R.layout.game_card_list_item, parent, false)
            
            with(viewHolder){
                findViewById<TextView>(R.id.players).text = resources.getString(R.string.cardPlayers, game.players.size)
                findViewById<TextView>(R.id.date).text = android.text.format.DateFormat.getDateFormat(context).format(game.date)
                findViewById<TextView>(R.id.status).text = when (game.state ){
                    DartGame.STATE_ON_GOING -> resources.getString(R.string.onGoingGame)
                    DartGame.STATE_DRAW -> resources.getString(R.string.drawMessage,
                                game.winner.joinToString(separator = " ${resources.getString(R.string.and)} "){
                                    it.name
                                })
                    DartGame.STATE_WINNER -> resources.getString(R.string.cardWinner, game.winner[0].name)
                    
                    else -> resources.getString(R.string.error)
                }
                findViewById<CardView>(R.id.card).setOnClickListener {
                    if (game.isFinished){
                        val intent = Intent(context, ScoreScreen::class.java)
                        intent.putExtra(ScoreScreen.GAME_ID_ARG, game.id)
                        startActivity(intent)
                    }else {
                        val i = Intent(context, GameActivity::class.java)
                        i.putExtra(GameActivity.DART_GAME_EXTRA, game.id)
                        startActivity(i)
                    }
                }
                findViewById<ImageButton>(R.id.menuButton).setOnClickListener { button ->
                    val popupMenu = PopupMenu(context, button)
                    with(popupMenu) {
                        menuInflater.inflate(R.menu.list_of_games_context_menu, popupMenu.menu)
                        
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.delete -> {
                                    deleteCompleteGame(context, data[position])
                
                                    data.removeAt(position)
                                    notifyDataSetChanged()
                                    true
                                }
                                R.id.revenge ->{
                                    val newGame = data[position].copy(
                                            pId = newGameID(this@ListOfGamesActivity),
                                            pPlayers = data[position].players.deepCopy {
                                                it.deepCopy(pId = newPlayerID(this@ListOfGamesActivity),
                                                        pPoints = it.startingPoints)
                                            },
                                            pDate = Calendar.getInstance().time)
                                    insertCompleteGame(this@ListOfGamesActivity, newGame)
                                    
                                    startActivity(Intent(this@ListOfGamesActivity,
                                            GameActivity::class.java).apply {
                                        putExtra(GameActivity.DART_GAME_EXTRA, newGame.id)
                                    })
                                    true
                                }
                                else -> false
                            }
                        }
                        show()
                    }
                }
            }
            return viewHolder
        }
    }
}
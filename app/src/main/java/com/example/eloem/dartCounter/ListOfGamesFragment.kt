package com.example.eloem.dartCounter

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eloem.dartCounter.database.deleteAllGamesComplete
import com.example.eloem.dartCounter.database.deleteCompleteGame
import com.example.eloem.dartCounter.database.getAllGames
import com.example.eloem.dartCounter.database.insertCompleteGame
import com.example.eloem.dartCounter.games.DartGame
import com.example.eloem.dartCounter.recyclerview.BottomSpacingAdapter
import com.example.eloem.dartCounter.recyclerview.ContextAdapter
import com.example.eloem.dartCounter.recyclerview.GridSpacingItemDecoration
import com.example.eloem.dartCounter.util.*
import com.google.android.material.card.MaterialCardView
import emil.beothy.utilFun.deepCopy
import kotlinx.android.synthetic.main.fragment_list_of_games.*
import java.lang.IllegalArgumentException
import java.util.*

class ListOfGamesFragment : Fragment() {
    private lateinit var recyclerViewAdapter: MyListAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_of_games, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        recyclerViewAdapter = MyListAdapter(mutableListOf())
        
        list.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(
                    GridSpacingItemDecoration(2,
                            resources.getDimensionPixelSize(R.dimen.cardGridSpacing),
                            true)
            )
            adapter = BottomSpacingAdapter(recyclerViewAdapter,
                    30.dp + getDimenAttr(R.attr.actionBarSize), 2)
            //adapter = recyclerViewAdapter
            emptyView = empty
            emptyThreshold = 2
        }
        
        (activity as HostActivity?)?.apply {
            onMainFabPressed = {
                findNavController().navigate(R.id.action_listOfGamesFragment_to_newDartGameFragment)
            }
            onMenuItemSelected = { when(it.itemId) {
                R.id.deleteAllGames -> {
                    deleteAllGamesComplete(this)
                    recyclerViewAdapter.data = mutableListOf()
                    recyclerViewAdapter.notifyDataSetChanged()
                    true
                }
                R.id.deleteAllFinishedGames -> {
                    val toRemove = mutableMapOf<Int, DartGame>()
                    recyclerViewAdapter.data.forEachIndexed { index, dartGame ->
                        if (dartGame.isFinished) {
                            toRemove[index] = dartGame
                            deleteCompleteGame(this, dartGame)
                        }
                    }
                    if (toRemove.isNotEmpty()) {
                        recyclerViewAdapter.data.removeAll(toRemove.values)
        
                        if (toRemove.size == 1) {
                            recyclerViewAdapter.notifyItemRemoved(toRemove.keys.first())
                        } else {
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                    true
                }
                R.id.deleteAllRunningGames -> {
                    val toRemove = mutableMapOf<Int, DartGame>()
                    recyclerViewAdapter.data.forEachIndexed { index, dartGame ->
                        if (!dartGame.isFinished) {
                            toRemove[index] = dartGame
                            deleteCompleteGame(this, dartGame)
                        }
                    }
                    if (toRemove.isNotEmpty()) {
                        recyclerViewAdapter.data.removeAll(toRemove.values)
                        
                        if (toRemove.size == 1) {
                            recyclerViewAdapter.notifyItemRemoved(toRemove.keys.first())
                        } else {
                            recyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                    true
                }
                else -> false
            }}
        }
    }
    
    override fun onResume() {
        super.onResume()
        context?.let {
            recyclerViewAdapter.data = getAllGames(it)
            recyclerViewAdapter.notifyDataSetChanged()
        }
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
                    try {
                        if (game.isFinished) {
                            Navigation.findNavController(recyclerView).navigate(
                                    ListOfGamesFragmentDirections
                                            .actionListOfGamesFragmentToOverviewFragment(game.id)
                            )
                        } else {
                            Navigation.findNavController(recyclerView).navigate(
                                    ListOfGamesFragmentDirections
                                            .actionListOfGamesFragmentToGameFragment(game.id)
                            )
                        }
                    } catch (e: IllegalArgumentException) {
                        //happens when 2 cards are pressed at the same time
                        Log.e("ListOfGamesFragment", e.localizedMessage)
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
    
                                    Navigation.findNavController(recyclerView).navigate(
                                            ListOfGamesFragmentDirections
                                                    .actionListOfGamesFragmentToGameFragment(newGame.id)
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
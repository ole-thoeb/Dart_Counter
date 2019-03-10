package com.example.eloem.dartCounter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eloem.dartCounter.games.HistoryTurn
import com.example.eloem.dartCounter.games.Player
import com.example.eloem.dartCounter.games.DartGame
import com.example.eloem.dartCounter.games.Point
import com.example.eloem.dartCounter.database.getOutGame
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.list_item_overview.view.*
import kotlinx.android.synthetic.main.player_list_item_overview.view.*
import kotlinx.android.synthetic.main.statistic_card.*
import kotlinx.android.synthetic.main.statistic_card.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverviewFragment : androidx.fragment.app.Fragment() {
    private lateinit var game: DartGame
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        arguments?.let {
            val ctx = context
            if (ctx != null) game = getOutGame(ctx, it.getInt(GAME_ID_PARAM))
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val winner = game.winners
        
        winnerTV.text = if (winner.size == 1){
            resources.getString(R.string.winMessage, winner[0].name)
        } else {
            resources.getString(R.string.drawMessage,
                    game.winners.joinToString(separator = " ${resources.getString(R.string.and)} "){
                        it.name
                    })
        }
        
        val p = game.players.copyOf()
        p.sortBy { it.points }
        
        var lastPos = 0
        var samePosFor = 0
        p.forEachIndexed { index, player ->
            playerList.addView(layoutInflater.inflate(R.layout.player_list_item_overview,
                    playerList, false).apply {
                
                placeTV.text = if(index > 0 && p[index -1].points == player.points){
                    samePosFor ++
                    lastPos.toString()
                }else {
                    lastPos = index + 1 + samePosFor
                    samePosFor = 0
                    lastPos.toString()
                }
                nameTV.text = player.name
                pointsTV.text = player.points.toString()
           })
        }
        
        addData(resources.getString(R.string.average)) { it.averagePoints.toString() }
        addData(resources.getString(R.string.bustedPoints)) { it.pointsBusted.toString() }
        addData(resources.getString(R.string.timesBusted)) { it.timesBusted.toString() }
        addData(resources.getString(R.string.missedThrows)) { it.missedThrows.toString() }
        addData(resources.getString(R.string.mostHit)) { it.mostHitPoint.toString() }
    }
    
    private fun addData(title: String, data: (Player) -> String){
        val card = layoutInflater.inflate(R.layout.statistic_card, listStatistic, false)
        card.statisticTitle.text = title
        game.players.forEach {
            card.listStatistic.addView(layoutInflater.inflate(R.layout.list_item_overview, listStatistic,
                    false).apply {
                playerNameTV.text = it.name
                dataTV.text = data(it)
            })
        }
        twoDivLin.addView(card)
    }
    
    companion object {
        @JvmStatic
        fun newInstance(gameId: Int) = OverviewFragment().apply {
            arguments = Bundle().apply {
                putInt(GAME_ID_PARAM, gameId)
            }
        }
        
        private const val GAME_ID_PARAM = "paramGameId"
    }
}

val Player.averagePoints: Int
    get() = history.sumBy { turn -> turn.points.sumBy { it.value } }/ history.size

val Player.missedThrows: Int
    get() = history.sumBy { turn -> turn.points.count { it.value == 0 } }

val Player.bustedThrows: List<HistoryTurn>
    get() = history.filter { it.pointsBefore < it.pointsScored }

val Player.timesBusted: Int
    get() = bustedThrows.size

val Player.pointsBusted: Int
    get() = bustedThrows.sumBy { it.pointsScored }

val Player.mostHitPoint: Point?
    get() = Point.instanceById(
            history.flatMap { it.points.toList() }.toMutableList()
            .apply { removeAll { it.value == 0 } }
            .groupBy { it.id }
            .maxBy { it.value.size }
            ?.key
    )
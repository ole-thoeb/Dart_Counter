package com.example.eloem.dartCounter

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import android.view.*
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.eloem.dartCounter.games.*
import com.example.eloem.dartCounter.database.getOutGame
import com.example.eloem.dartCounter.database.updateNewTurn
import com.example.eloem.dartCounter.util.dp
import com.example.eloem.dartCounter.util.fragmentViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.item_game_player.view.*
import kotlinx.android.synthetic.main.turn_overview_list.view.*

class GameFragment : Fragment() {
    //für textViews die die Punkte anteigen
    private lateinit var throwTextView: Array<MaterialButton>
    private lateinit var currentTextView: MaterialButton
    
    private val vm: SharedViewModel by fragmentViewModel()
    
    private lateinit var game: OutGame
    private val arg: GameFragmentArgs by navArgs()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        /*vm = ViewModelProviders.of(this).get(SharedViewModel::class.java)*/
        throwTextView = arrayOf(throw1mtrButton, throw2mtrButton, throw3mtrButton)
        
        currentTextView = throwTextView[vm.curThrow]
        setCurrentButton(currentTextView, vm.curThrow)
        
        throwTextView.forEach { mtrButton -> mtrButton.setOnClickListener { onClick(it) } }
        Log.d("GameFragment", "now getting viewmodel")
        game = vm.getOutGame(arg.gameId)
        game.onGameFinish = {
            findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToOverviewFragment(game.id))
        }
        
        //set up the dartboard
        board.onPointListener = {
            vm.throwPoints.points[vm.curThrow] = it
            setThrowTVText()
        }
        board.onNextPointListener = { switchCurrentButton() }
        board.setScrollObserver(scrollView)
    
        (activity as HostActivity?)?.apply {
            onMainFabPressed = { confirmTurn() }
            onMenuItemSelected = { false }
        }
    
        setThrowTVText()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.fragment_game)
        setSupportActionBar(toolbar)*/
        
        /*list.apply {
            adapter = ListAdapter(listOf())
            adapter.registerDataSetObserver(object : DataSetObserver(){
                override fun onChanged() {
                    val closingMoves = (adapter as ListAdapter).data
    
                    val bSheetB = BottomSheetBehavior.from(bottomSheet)
                    
                    if (closingMoves.isEmpty()){
                        bSheetB.isHideable = true
                        bSheetB.state = BottomSheetBehavior.STATE_HIDDEN
                    }else {
                        bSheetB.isHideable = false
                        bSheetB.state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }
            })
        }*/
    
       
    }
    
    fun onClick(view: View) {
        if (view is MaterialButton) {
            when (view.id) {
                R.id.throw1mtrButton -> setCurrentButton(view, 0)
                R.id.throw2mtrButton -> setCurrentButton(view, 1)
                R.id.throw3mtrButton -> setCurrentButton(view, 2)
            }
        }
    }
    
    private fun setCurrentButton(tv: MaterialButton, pos: Int) {
        fun setActive(mtrButton: MaterialButton, pos: Int) {
            currentTextView = mtrButton
            vm.curThrow = pos
            mtrButton.isActivated = true
            //tv.background = resources.getDrawable(R.drawable.active_background, theme)
        }
        
        fun setInactive(mtrButton: MaterialButton) {
            //tv.background = resources.getDrawable(R.drawable.inactive_background, theme)
            mtrButton.isActivated = false
        }
        
        setInactive(currentTextView)
        setActive(tv, pos)
    }
    
    private fun switchCurrentButton(){
        val nextTVPos = vm.curThrow + 1
        if (nextTVPos < throwTextView.size) setCurrentButton(throwTextView[nextTVPos], nextTVPos)
    }
    
    private fun setThrowTVText(){
        throwTextView.forEachIndexed { index, tv ->
            tv.text = vm.throwPoints.points[index].toString()
        }
        
        val currentStanding = game.nextPlayerThrow(vm.throwPoints, false)
        updatePlayerList(currentStanding)
        updateClosingInfo()
    }
    
    fun confirmTurn(){
        game.nextPlayerThrow(vm.throwPoints.deepCopy(), true)
        
        updateNewTurn(requireContext(), game)
    
        // reset auf null
        vm.throwPoints.points.forEachIndexed { index, _ ->
            vm.throwPoints.points[index] = Point.instanceByPoints(1, 0)
        }
        setThrowTVText()
        setCurrentButton(throwTextView[0], 0)
        
        updateClosingInfo()
    }
    
    private fun updatePlayerList(players: Array<Player>){
        players.sortBy { it.points }
        
        allPlayerList.removeAllViews()
        
        for (player in players){
            val view = layoutInflater.inflate(R.layout.item_game_player, allPlayerList, false)
            view.apply {
                playerTV.text = player.name
                playerScoreTV.text = player.points.toString()
                root.setOnClickListener {
                    @SuppressLint("InflateParams")
                    val layout = layoutInflater.inflate(R.layout.turn_overview_list, null)
                    layout.turnList.adapter = HistoryTurnAdapter(player.history)
        
                    AlertDialog.Builder(context)
                            .setTitle(player.name)
                            .setView(layout)
                            .setNegativeButton(R.string.done) { _, _ -> }
                            .show()
                }
                if (player.id == game.currentPlayer.id) {
                    root.strokeWidth = 1.dp
                }
            }
            allPlayerList.addView(view)
        }
    }
    
    private fun updateClosingInfo(){
        /*doAsync {
            val closingMoves = game.calculateClosingThrows(throwPoints.deepCopy())
            
            uiThread {
                val adapter = list.adapter
                
                if (adapter is ListAdapter) {
                    adapter.apply {
                        data = closingMoves
                        notifyDataSetChanged()
                    }
                }
            }
        }*/
    }
    
    inner class ListAdapter(var data: List<Turn>): BaseAdapter(){
        override fun getCount() = data.size
        
        override fun getItem(position: Int) = data[position]
        
        override fun getItemId(position: Int) = position.toLong()
        
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val moves = getItem(position)
            val vH = convertView?: layoutInflater.inflate(R.layout.bottom_sheet_list_item, parent, false)
            
            vH.findViewById<TextView>(R.id.points1TV).text = moves.points[0].toString()
            vH.findViewById<TextView>(R.id.points2TV).text = moves.points[1].toString()
            vH.findViewById<TextView>(R.id.points3TV).text = moves.points[2].toString()
            
            return vH
        }
    }
    
    inner class HistoryTurnAdapter(private var data: List<HistoryTurn>): BaseAdapter(){
        override fun getCount() = data.size
    
        override fun getItem(position: Int) = data[position]
    
        override fun getItemId(position: Int) = position.toLong()
    
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val turn = getItem(position)
            val vH = convertView?: layoutInflater.inflate(R.layout.history_turn_list_item, parent, false)
            
            vH.apply {
                findViewById<TextView>(R.id.points1TV).text = turn.points[0].toString()
                findViewById<TextView>(R.id.points2TV).text = turn.points[1].toString()
                findViewById<TextView>(R.id.points3TV).text = turn.points[2].toString()
                findViewById<TextView>(R.id.pointsLeftTV).text = turn.pointsAfter.toString()
            }
        
            return vH
        }
    }
    
    private class SharedViewModel(application: Application): AndroidViewModel(application) {
        var game: OutGame? = null
        
        fun getOutGame(id: Int): OutGame {
            val g = game
            if (g != null && g.id == id) return g
            
            return getOutGame(getApplication(), id).let {
                 game = it
                it
            }
        }
        
        val throwPoints: Turn = Turn(Array(3) { Point.instanceByPoints(1, 0) })
        
        var curThrow: Int = 0
    }
    
    companion object {
        const val DART_GAME_EXTRA = "extraDartGame"
    }
}

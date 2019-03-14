package com.example.eloem.dartCounter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.*
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.eloem.dartCounter.games.*
import com.example.eloem.dartCounter.database.getOutGame
import com.example.eloem.dartCounter.database.updateNewTurn
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.item_game_player.view.*
import kotlinx.android.synthetic.main.turn_overview_list.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class GameActivity : AppCompatActivity() {
    //für textViews die die Punkte anteigen
    private lateinit var throwTextView: Array<MaterialButton>
    private lateinit var currentTextView: MaterialButton
    private var currentTextViewPos = 0
    
    private lateinit var throwPoints: Turn
    
    private lateinit var game: OutGame
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setSupportActionBar(toolbar)
        
        throwTextView = arrayOf(throw1mtrButton, throw2mtrButton, throw3mtrButton)
        currentTextView = throwTextView[0]
        setCurrentButton(currentTextView, 0)
        throwPoints = Turn(Array(throwTextView.size) { Point.instanceByPoints(1, 0) })
        
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
    
        doAsync {
            val g = getOutGame(applicationContext, intent.getIntExtra(DART_GAME_EXTRA, 0))
            uiThread {activity ->
                game = g
            
                game.onGameFinish = {
                    startActivity(Intent(activity, ScoreScreen::class.java)
                            .putExtra(ScoreScreen.GAME_ID_ARG, game.id))
                }
            
                setThrowTVText()
            }
        }
        //set up the dartboard
        board.onPointListener = {
            throwPoints.points[currentTextViewPos] = it
            setThrowTVText()
        }
        board.onNextPointListener = { switchFocusedTextView() }
        board.setScrollObserver(scrollView)
        
        confirmThrowFab.setOnClickListener { confirmTurn() }
    }
    
    /*override fun onPause() {
        super.onPause()
        if (!game.isFinished) doAsync { writeGameAndSetFlag(this@GameActivity, game) }
    }*/
    
    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_dart_game, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.confirmThrow -> confirmTurn()
            R.id.f -> updateClosingInfo()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }*/
    
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
            currentTextViewPos = pos
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
    
    private fun switchFocusedTextView(){
        val nextTVPos = currentTextViewPos + 1
        if (nextTVPos < throwTextView.size) setCurrentButton(throwTextView[nextTVPos], nextTVPos)
    }
    
    private fun setThrowTVText(){
        throwTextView.forEachIndexed { index, tv ->
            tv.text = throwPoints.points[index].toString()
        }
        
        val currentStanding = game.nextPlayerThrow(throwPoints, false)
        updateCurrentPlayerInfo(currentStanding)
        updatePlayerList(currentStanding)
        updateClosingInfo()
    }
    
    private fun confirmTurn(){
        game.nextPlayerThrow(throwPoints, true)
        updateNewTurn(this, game)
        
        //new object so the history doesn't get changed | alternative copy throw points then passed to nextPlayerThrow
        throwPoints = Turn(Array(throwTextView.size) { Point.instanceByPoints(1, 0) }) // reset auf null
        setThrowTVText()
        setCurrentButton(throwTextView[0], 0)
        
        updateClosingInfo()
    }
    
    private fun updateCurrentPlayerInfo(players: Array<Player>){
        val currPlayer = players[game.currentPlayerPos]
        
        currentPlayer.text = currPlayer.name
        currentPlayerScore.text = currPlayer.points.toString()
    }
    
    private fun updatePlayerList(players: Array<Player>){
        players.sortBy { it.points }
        
        allPlayerList.removeAllViews()
        
        for (player in players){
            val view = layoutInflater.inflate(R.layout.item_game_player, allPlayerList, false)
            view.apply {
                playerTV.text = player.name
                playerScoreTV.text = player.points.toString()
                linLayout.setOnClickListener {
                    @SuppressLint("InflateParams")
                    val layout = layoutInflater.inflate(R.layout.turn_overview_list, null)
                    layout.turnList.adapter = HistoryTurnAdapter(player.history)
        
                    AlertDialog.Builder(context)
                            .setTitle(player.name)
                            .setView(layout)
                            .setNegativeButton(R.string.done) { _, _ -> }
                            .show()
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
    
    companion object {
        const val DART_GAME_EXTRA = "extraDartGame"
    }
}

package com.example.eloem.dartCounter


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.eloem.dartCounter.games.DartGame
import com.example.eloem.dartCounter.util.differentShade
import com.example.eloem.dartCounter.database.getOutGame
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlinx.android.synthetic.main.fragment_statistic.*
import kotlinx.android.synthetic.main.select_player_item.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [DiagramFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DiagramFragment : androidx.fragment.app.Fragment() {
    private lateinit var game: DartGame
    private lateinit var selectedPlayer: BooleanArray
    private var currentDiagram = Diagram.Point
    
    enum class Diagram{
        Point, Turn, PointTurn
    }
    
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
        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        styleDiagram()
        selectedPlayer = BooleanArray(game.players.size){_ -> true}
        game.players.forEachIndexed {index, player ->
            val item = layoutInflater.inflate(R.layout.select_player_item, selectPlayerList, false)
            item.playerName.text = player.name
            item.radioButton.setOnClickListener {
                it as CheckBox
                selectedPlayer[index] = it.isChecked
                updateCurrentDiagram()
            }
            item.radioButton.isChecked = true
            selectPlayerList.addView(item)
        }
    }
    
    private fun updateCurrentDiagram(){
        when(currentDiagram){
            Diagram.Point -> setPointDiagram()
            Diagram.Turn -> setTurnDiagram()
            Diagram.PointTurn -> setPointTurnDiagram()
        }
    }
    
    private fun getPointData(enabledPlayers: BooleanArray): MutableList<ILineDataSet>{
        val colors = resources.getIntArray(R.array.lineColor)
        val dataSets = mutableListOf<ILineDataSet>()
        game.players.forEachIndexed { i, player ->
            if (enabledPlayers[i]) {
                val entries = mutableListOf<Entry>()
                entries.add(Entry(0f, (player.startingPoints).toFloat()))
                
                player.history.forEachIndexed {j, hTurn ->
                    entries.add(Entry((j + 1).toFloat(), hTurn.pointsAfter.toFloat()))
                }
                
                val dataSet = LineDataSet(entries, resources.getString(R.string.points, player.name))
                dataSet.color = colors[i % colors.size]
                dataSet.setCircleColor(colors[i % colors.size])
                dataSet.setCircleColorHole(colors[i % colors.size])
                dataSets.add(dataSet)
            }
        }
        return dataSets
    }
    
    private fun getTurnData(enabledPlayers: BooleanArray): MutableList<ILineDataSet>{
        val colors = resources.getIntArray(R.array.lineColor)
        val dataSets = mutableListOf<ILineDataSet>()
        game.players.forEachIndexed { i, player ->
            if (enabledPlayers[i]) {
                val entries = mutableListOf<Entry>()
                
                player.history.forEachIndexed {j, hTurn ->
                    entries.add(Entry((j + 1).toFloat(),
                            if (j > 0 && hTurn.pointsBefore == hTurn.pointsAfter) 0f
                            else hTurn.pointsScored.toFloat()))
                }
                
                val dataSet = LineDataSet(entries, resources.getString(R.string.turn, player.name))
                val graphColor = differentShade(colors[i % colors.size], 7.5f)
                dataSet.color = graphColor
                dataSet.setCircleColor(graphColor)
                dataSet.setCircleColorHole(graphColor)
                dataSet.valueFormatter = MyValueFormatter()
                dataSets.add(dataSet)
            }
        }
        return dataSets
    }
    
    fun setPointDiagram(){
        currentDiagram = Diagram.Point
        val lineData = LineData(getPointData(selectedPlayer))
        lineData.setValueTextSize(12f)
        lineChart.data = lineData
        hideRightY()
        lineChart.invalidate()
    }
    
    fun setTurnDiagram(){
        currentDiagram = Diagram.Turn
        val lineData = LineData(getTurnData(selectedPlayer))
        lineData.setValueTextSize(12f)
        lineChart.data = lineData
        hideRightY()
        lineChart.invalidate()
    }
    
    fun setPointTurnDiagram(){
        currentDiagram = Diagram.PointTurn
        val turnData = getTurnData(selectedPlayer)
        val pointData = getPointData(selectedPlayer)
        turnData.forEach { it.axisDependency = YAxis.AxisDependency.RIGHT }
        turnData.addAll(pointData)
        val lineData = LineData(turnData)
        lineData.setValueTextSize(12f)
        lineChart.data = lineData
        showRightY()
        lineChart.invalidate()
    }
    
    private fun hideRightY(){
        val rAxis = lineChart.axisRight
        rAxis.setDrawLabels(false)
        rAxis.setDrawAxisLine(false)
        rAxis.setDrawGridLines(false)
    }
    
    private fun showRightY(){
        val rAxis = lineChart.axisRight
        rAxis.setDrawLabels(true)
        rAxis.setDrawAxisLine(true)
        rAxis.setDrawGridLines(true)
    }
    
    private fun styleDiagram(){
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 14f
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        
        val lAxis = lineChart.axisLeft
        lAxis.textSize = 14f
        lAxis.axisMinimum = 0f
        lAxis.granularity = 1f
    
        val rAxis = lineChart.axisRight
        rAxis.textSize = 14f
        rAxis.axisMinimum = 0f
        rAxis.granularity = 1f
        
        lineChart.setPinchZoom(false)
        lineChart.isDoubleTapToZoomEnabled = false
        lineChart.isScaleYEnabled = false
        lineChart.isHighlightPerDragEnabled = false
        lineChart.isHighlightPerTapEnabled = false
        lineChart.description.isEnabled = false
        lineChart.legend.textSize = 14f
        lineChart.legend.isWordWrapEnabled = true
    }
    
    private class MyValueFormatter: IValueFormatter {
        override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int,
                                       viewPortHandler: ViewPortHandler?): String {
            return value.toInt().toString()
        }
    }
    
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param gameId Parameter 1.
         * @return A new instance of fragment DiagramFragment.
         */
        
        @JvmStatic
        fun newInstance(gameId: Int) =
                DiagramFragment().apply {
                    arguments = Bundle().apply {
                        putInt(GAME_ID_PARAM, gameId)
                    }
                }
        
        private const val GAME_ID_PARAM = "paramGameId"
    }
}

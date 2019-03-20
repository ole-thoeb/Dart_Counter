package com.example.eloem.dartCounter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eloem.dartCounter.games.HistoryTurn
import com.example.eloem.dartCounter.games.Player
import com.example.eloem.dartCounter.games.DartGame
import com.example.eloem.dartCounter.games.Point
import com.example.eloem.dartCounter.database.getOutGame
import com.example.eloem.dartCounter.recyclerview.ContextAdapter
import com.example.eloem.dartCounter.recyclerview.GridSpacingItemDecoration
import com.example.eloem.dartCounter.util.differentShade
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ViewPortHandler
import emil.beothy.utilFun.copyOf
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlinx.android.synthetic.main.item_overview_stats.view.*
import kotlinx.android.synthetic.main.item_overview_standing.view.*
import kotlinx.android.synthetic.main.item_overview_select_player.view.*

class OverviewFragment : Fragment() {
    private lateinit var game: DartGame
    
    private val arg: OverviewFragmentArgs by navArgs()
    
    private lateinit var selectedPlayer: BooleanArray
    private var currentDiagram = Diagram.Point
    
    enum class Diagram {
        Point, Turn, PointTurn
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        game = getOutGame(requireContext(), arg.gameId)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val stats = listOf(
                StatItem(getString(R.string.winner), game.winners.map { Player.empty() to it.name }),
                StatItem(getString(R.string.standing), game.players.map { it to it.points.toString() }, StatItem.TYPE_STANDING),
                StatItem(getString(R.string.averagePoints), game.players.map { it to it.averagePoints.toString() }),
                StatItem(getString(R.string.bustedPoints), game.players.map { it to it.pointsBusted.toString() }),
                StatItem(getString(R.string.timesBusted), game.players.map { it to it.timesBusted.toString() }),
                StatItem(getString(R.string.missedThrows), game.players.map { it to it.missedThrows.toString() }),
                StatItem(getString(R.string.mostHit), game.players.map { it to it.mostHitPoint.toString() })
        )
        
        (activity as HostActivity?)?.apply {
            onMainFabPressed = null
            onMenuItemSelected = null
        }
        
        recyclerStats.apply {
            adapter = StatisticAdapter(stats)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(GridSpacingItemDecoration(2,
                    resources.getDimensionPixelSize(R.dimen.cardGridSpacing),
                    true))
        }
        
        styleDiagram()
        selectedPlayer = BooleanArray(game.players.size) { true }
        game.players.forEachIndexed { index, player ->
            val item = layoutInflater.inflate(R.layout.item_overview_select_player, selectPlayerList, false)
            item.playerName.text = player.name
            item.radioButton.setOnClickListener {
                it as CheckBox
                selectedPlayer[index] = it.isChecked
                updateCurrentDiagram()
            }
            item.radioButton.isChecked = true
            selectPlayerList.addView(item)
        }
    
        
        diagramSpinner.adapter = ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_list_item_1,
                resources.getStringArray(R.array.diagrams)).apply {
            setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        }
        diagramSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> setPointDiagram()
                    1 -> setTurnDiagram()
                    2 -> setPointTurnDiagram()
                }
            }
        
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nothing
            }
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
        lineChart.axisRight.apply {
            setDrawLabels(false)
            setDrawAxisLine(false)
            setDrawGridLines(false)
        }
    }
    
    private fun showRightY(){
        lineChart.axisRight.apply {
            setDrawLabels(true)
            setDrawAxisLine(true)
            setDrawGridLines(true)
        }
    }
    
    private fun styleDiagram(){
        lineChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 14f
            setDrawAxisLine(true)
            setDrawGridLines(false)
            granularity = 1f
        }
        
        lineChart.axisLeft.apply {
            textSize = 14f
            axisMinimum = 0f
            granularity = 1f
        }
        
        lineChart.axisRight.apply {
            textSize = 14f
            axisMinimum = 0f
            granularity = 1f
        }
        
        lineChart.apply {
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            isScaleYEnabled = false
            isHighlightPerDragEnabled = false
            isHighlightPerTapEnabled = false
            description.isEnabled = false
            legend.textSize = 14f
            legend.isWordWrapEnabled = true
        }
    }
    
    private class MyValueFormatter: IValueFormatter {
        override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int,
                                       viewPortHandler: ViewPortHandler?): String {
            return value.toInt().toString()
        }
    }
    
    data class StatItem(val title: String,
                        val data: List<Pair<Player, String>>,
                        val type: Int = TYPE_STATS) {
        
        companion object {
            const val TYPE_STATS = 0
            const val TYPE_STANDING = 1
            const val TYPE_DIAGRAM = 2
        }
    }
    
    class StatisticAdapter(val statData: List<StatItem>): ContextAdapter<StatisticAdapter.StatVH>() {
        
        class StatVH(layout: View): RecyclerView.ViewHolder(layout) {
            val title: TextView = layout.findViewById(R.id.statisticTitle)
            val statList: LinearLayout = layout.findViewById(R.id.listStatistic)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatVH = when (viewType) {
            StatItem.TYPE_STATS -> StatVH(inflate(R.layout.card_statistic, parent))
            else -> StatVH(inflate(R.layout.card_statistic, parent))
        }
    
        override fun getItemCount(): Int = statData.size
    
        override fun onBindViewHolder(holder: StatVH, position: Int) {
            val statItem = statData[position]
            holder.title.text = statItem.title
            holder.statList.removeAllViews()
            if (holder.itemViewType == StatItem.TYPE_STATS) {
                statItem.data.forEach {
                    val listItem = inflate(R.layout.item_overview_stats, holder.statList)
                    listItem.dataTV.text = it.second
                    listItem.playerNameTV.text = it.first.name
                    holder.statList.addView(listItem)
                }
            } else {
                val p = statItem.data.copyOf().map { it.first }.sortedBy { it.points }
    
                var lastPos = 0
                var samePosFor = 0
                p.forEachIndexed { index, player ->
                    val listItem = inflate(R.layout.item_overview_standing, holder.statList)
            
                    listItem.placeTV.text = if(index > 0 &&
                            p[index -1].points == player.points){
                        samePosFor ++
                        lastPos.toString()
                    }else {
                        lastPos = index + 1 + samePosFor
                        samePosFor = 0
                        lastPos.toString()
                    }
                    listItem.nameTV.text = player.name
                    listItem.pointsTV.text = player.points.toString()
    
                    holder.statList.addView(listItem)
                }
            }
        }
    
        override fun getItemViewType(position: Int): Int {
            return statData[position].type
        }
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
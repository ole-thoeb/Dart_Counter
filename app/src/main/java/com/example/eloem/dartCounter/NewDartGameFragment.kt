package com.example.eloem.dartCounter

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eloem.dartCounter.database.insertCompleteGame
import com.example.eloem.dartCounter.games.DoubleOutGame
import com.example.eloem.dartCounter.games.MasterOutGame
import com.example.eloem.dartCounter.games.SingleOutGame
import com.example.eloem.dartCounter.games.Player
import com.example.eloem.dartCounter.helperClasses.BetterEditText
import com.example.eloem.dartCounter.recyclerview.EditListAdapter
import com.example.eloem.dartCounter.util.*
import kotlinx.android.synthetic.main.fragment_new_dartgame.*
import org.jetbrains.anko.doAsync
import java.lang.Error

class NewDartGameFragment : Fragment() {
    
    private lateinit var rvAdapter: PlayerEditAdapter
    private val vm: SharedViewModel by fragmentViewModel()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_dartgame, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val initPlayers = vm.players
        
        val initModePos = vm.modePos
        val initPointPos = vm.pointPos
        
        rvAdapter = PlayerEditAdapter(initPlayers, initModePos, initPointPos)
        playerList.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        
        if (initPlayers.isEmpty()) rvAdapter.addNewItem(0)
        
        (activity as HostActivity?)?.apply {
            onMainFabPressed = { startGame() }
            onMenuItemSelected = { false }
        }
    }
    
    override fun onPause() {
        super.onPause()
        vm.modePos = rvAdapter.modePos
        vm.pointPos = rvAdapter.pointPos
        hideSoftKeyboard(requireContext(), view?.findFocus())
    }
    
    fun startGame(){
        val players = rvAdapter.values
        val mode = rvAdapter.modePos
        val startingPoints = resources.getIntArray(R.array.startingPoints)[rvAdapter.pointPos]
        
        val cleanedPlayers = players.filter { it.name != "" }
        
        if (cleanedPlayers.isEmpty()){
            Toast.makeText(requireContext(),
                    resources.getString(R.string.noPlayer),
                    Toast.LENGTH_SHORT).show()
            return
        }
        
        val playerArray = Array(cleanedPlayers.size) {
            cleanedPlayers[it].copy(id = newPlayerID(requireContext()), points = startingPoints)
        }
        
        val dartGame = when(mode){
            0 -> SingleOutGame(newGameID(requireContext()), playerArray)
            1 -> DoubleOutGame(newGameID(requireContext()), playerArray)
            2 -> MasterOutGame(newGameID(requireContext()), playerArray)
            else -> SingleOutGame(newGameID(requireContext()), playerArray)
        }
        
        doAsync { insertCompleteGame(requireContext(), dartGame) }
        
        hideSoftKeyboard(requireContext(), view?.findFocus())
    
        findNavController().navigate(
                NewDartGameFragmentDirections
                        .actionNewDartGameFragmentToGameFragment(dartGame.id)
        )
    }
    
    class PlayerEditAdapter(values: MutableList<Player>,
                            var modePos: Int = 0,
                            var pointPos: Int = 0): EditListAdapter<Player>(values) {
    
        private val mAdapterPoints: ArrayAdapter<Int> by lazy {
            ArrayAdapter<Int>(context,
                    android.R.layout.simple_list_item_1,
                    context.resources.getIntArray(R.array.startingPoints).toTypedArray())
        }
    
        private val mAdapterModes: ArrayAdapter<String> by lazy {
            ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1,
                    context.resources.getStringArray(R.array.modes))
        }
        
        class EditVH(layout: View): EditRowVH(layout){
            override val itemNameET: BetterEditText = layout.findViewById(R.id.playerName)
            override val deleteButton: ImageButton = layout.findViewById(R.id.deleteButton)
        }
        
        class AddItemVH(layout: View): RecyclerView.ViewHolder(layout) {
            val root: LinearLayout = layout.findViewById(R.id.root)
        }
        
        class SpinnerVH(layout: View): RecyclerView.ViewHolder(layout) {
            val modeSpinner: Spinner = layout.findViewById(R.id.modeSpinner)
            val startPointSpinner: Spinner = layout.findViewById(R.id.startPointsSpinner)
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
            2 -> SpinnerVH(
                    LayoutInflater
                            .from(context)
                            .inflate(R.layout.item_new_dartgame_spinner, parent, false)
            )
            else -> throw Error("unknown viewType: $viewType")
        }
    
        override fun getItemViewType(position: Int): Int = when(position) {
            0 -> 2
            in 1..values.size -> VIEW_TYPE_EDIT_ROW
            else -> 1
        }
    
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            super.onBindViewHolder(holder, position)
            
            when (holder.itemViewType) {
                1 -> {
                    holder as AddItemVH
                    holder.root.setOnClickListener { addNewItem(values.size) }
                }
                2 -> {
                    holder as SpinnerVH
                    holder.modeSpinner.apply {
                        adapter = mAdapterModes
                        onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
    
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                modePos = position
                            }
                        }
                        setSelection(modePos)
                    }
                    holder.startPointSpinner.apply {
                        adapter = mAdapterPoints
                        onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
        
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                pointPos = position
                            }
                        }
                        setSelection(pointPos)
                    }
                }
            }
        }
    
        override var valuesOffsetStart: Int = 1
    
        override fun getItemCount(): Int = values.size + 2
    }
    
    private class SharedViewModel: ViewModel(){
        var modePos: Int = 0
        var pointPos: Int = 0
        val players: MutableList<Player> = mutableListOf()
    }
}

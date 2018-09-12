package com.example.eloem.dartCounter

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.*
import android.support.v4.view.ViewPager
import android.util.SparseArray
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.eloem.dartCounter.helperClasses.games.DartGame
import com.example.eloem.dartCounter.util.getOutGame
import kotlinx.android.synthetic.main.activity_score_screen.*
import kotlinx.android.synthetic.main.diagram_spinner.*

class ScoreScreen : AppCompatActivity() {
    
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var gameId: Int? = null
    private lateinit var game: DartGame
    
    private var spinnerHasAdapter = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_screen)
        
        intent.let {
            gameId = it.getIntExtra(GAME_ID_ARG, 0)
        }
        
        game = getOutGame(this, gameId!!)
        
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setCustomView(R.layout.diagram_spinner)
        setDateInActionBar()
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        
        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        
        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                //nothing
            }
    
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //nothing
            }
    
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> {
                        supportActionBar?.setDisplayShowCustomEnabled(false)
                        supportActionBar?.setDisplayShowTitleEnabled(true)
                    }
                    1-> {
                        supportActionBar?.setDisplayShowCustomEnabled(true)
                        supportActionBar?.setDisplayShowTitleEnabled(false)
                        
                        if (!spinnerHasAdapter) {
                            diagramSpinner?.adapter = ArrayAdapter<String>(this@ScoreScreen,
                                    R.layout.spinner_text_view,
                                    resources.getStringArray(R.array.diagrams)).apply {
                                    setDropDownViewResource(
                                            R.layout.support_simple_spinner_dropdown_item)
                            }
                            diagramSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    val fragment = mSectionsPagerAdapter?.getFragmentAt(1) as DiagramFragment
                                    when(position){
                                        0 -> fragment.setPointDiagram()
                                        1 -> fragment.setTurnDiagram()
                                        2 -> fragment.setPointTurnDiagram()
                                    }
                                }
    
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    //nothing
                                }
                            }
                            
                            spinnerHasAdapter = true
                        }
                    }
                }
            }
        })
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        R.id.home -> true
        
        else -> super.onOptionsItemSelected(item)
    }
    
    private fun setDateInActionBar(){
        supportActionBar?.title = resources.getString(R.string.gameDate, android.text.format.DateFormat
                .getDateFormat(this@ScoreScreen)
                .format(game.date))
    }
    
    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private var firstFragment: OverviewFragment? = null
    private var secondFragment: DiagramFragment? = null
    
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val registeredFragments = SparseArray<Fragment>()
        
        override fun getItem(position: Int): Fragment = when(position){
            0 -> {
                firstFragment = OverviewFragment.newInstance(gameId!!)
                firstFragment!!
            }
            1 -> {
                secondFragment = DiagramFragment.newInstance(gameId!!)
                secondFragment!!
            }
            
            else -> OverviewFragment.newInstance(gameId!!)
        }
        
        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }
    
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position) as Fragment
            registeredFragments.put(position, fragment)
            return fragment
        }
    
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            registeredFragments.remove(position)
            super.destroyItem(container, position, `object`)
        }
        
        fun getFragmentAt(position: Int): Fragment? = registeredFragments[position]
    }
    
    companion object {
        const val GAME_ID_ARG = "argGameId"
    }
}

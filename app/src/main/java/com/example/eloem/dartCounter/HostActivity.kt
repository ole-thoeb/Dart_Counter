package com.example.eloem.dartCounter

import android.graphics.drawable.Animatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity() {
    
    var onMainFabPressed: (() -> Unit)? = null
    var onMenuItemSelected: ((MenuItem) -> Boolean)? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
    
        val navController = findNavController(R.id.navHostFragment)
        var curNavId = navController.currentDestination?.id ?: 0
        mainFAB.setImageDrawable(getDrawable(
                if (curNavId == R.id.listOfGamesFragment) R.drawable.add_to_check
                else R.drawable.check_to_add
        ))
       
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (curNavId == R.id.listOfGamesFragment &&
                    (destination.id == R.id.newDartGameFragment ||
                            destination.id == R.id.gameFragment)) {
                val drawable = getDrawable(R.drawable.add_to_check)
                mainFAB.setImageDrawable(drawable)
                (drawable as Animatable).start()
            } else if ((curNavId == R.id.newDartGameFragment ||
                            curNavId == R.id.gameFragment) &&
                    destination.id == R.id.listOfGamesFragment) {
                val drawable = getDrawable(R.drawable.check_to_add)
                mainFAB.setImageDrawable(drawable)
                (drawable as Animatable).start()
            }
            
            /*Log.d(TAG, "listOfGameFragmentsId: ${R.id.listOfGamesFragment}")
            Log.d(TAG, "gameFragmentId: ${R.id.gameFragment}")
            Log.d(TAG, "newDartGameFragmentId: ${R.id.newDartGameFragment}")
            Log.d(TAG, "destination: ${destination.id}, ${destination.label}")*/
            curNavId = destination.id
            when(destination.id) {
                R.id.listOfGamesFragment -> {
                    bottomAppBar.replaceMenu(R.menu.activity_list_of_games_menu)
                    bottomAppBar.navigationIcon = null
                    mainFAB.show()
                }
                R.id.newDartGameFragment -> {
                    bottomAppBar.menu.clear()
                    bottomAppBar.navigationIcon = getDrawable(R.drawable.ic_arrow_back)
                    bottomAppBar.setNavigationOnClickListener {
                        navController.navigateUp()
                    }
                    mainFAB.show()
                }
                R.id.gameFragment -> {
                    bottomAppBar.menu.clear()
                    bottomAppBar.navigationIcon = null
                    mainFAB.show()
                }
                R.id.overviewFragment -> {
                    bottomAppBar.menu.clear()
                    bottomAppBar.navigationIcon = getDrawable(R.drawable.ic_arrow_back)
                    bottomAppBar.setNavigationOnClickListener {
                        navController.navigateUp()
                    }
                    mainFAB.hide()
                }
            }
        }
        
        bottomAppBar.setOnMenuItemClickListener {
            onMenuItemSelected?.invoke(it) == true
        }
        mainFAB.setOnClickListener {
            /*when(navController.currentDestination?.id) {
                R.id.listOfGamesFragment -> {
                    //curFragment as ListOfGamesFragment
                    navController
                            .navigate(R.id.action_listOfGamesFragment_to_newDartGameFragment)
                }
                R.id.gameFragment -> {
                    ViewModelProviders.of(this).get(DartGameSharedModel::class.java).onMainFabPressed?.invoke()
                }
                R.id.newDartGameFragment -> {
                    ViewModelProviders.of(this).get(NewGameSharedModel::class.java).onMainFabPressed?.invoke()
                }
            }*/
            
                onMainFabPressed?.invoke()
            }
        }
    
    companion object {
        private const val TAG = "HostActivity"
    }
}

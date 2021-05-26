package com.example.radioy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.radioy.local.SharedPreferenceUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Dashboard : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     *ViewModels is taken for storing the values through out the lifecycle
     * like at the time orintation basically improved the onSavedInstace state
     */
    lateinit var dashVm:DashboardViewModel
    lateinit var pref:SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init(savedInstanceState)
        initialisePlayPauseAsStatus()
        playPauseClickListener()
    }

    fun init(savedInstanceState: Bundle?){
        dashVm = ViewModelProvider(this).get(DashboardViewModel::class.java)
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        pref = SharedPreferenceUtil(this)
        if (savedInstanceState == null) {
            Utils(this).addFragment(frame.id,HomeList(),HomeList::class.java.name)
        }
    }

    fun initialisePlayPauseAsStatus(){
        if(pref.getBoolean(Utils.togglePlay,false)){
            toggle_play.setImageResource(R.drawable.ic_pause)
        }else {
            toggle_play.setImageResource(R.drawable.ic_play)
        }
    }

    fun playPauseClickListener(){
        toggle_play.setOnClickListener {
            managePlayOnCick()
        }
    }


    fun managePlayOnCick(){
        if(pref.getBoolean(Utils.togglePlay,false)){
//            pause
            pref.setBoolean(Utils.togglePlay,false)
            pref.save()
            toggle_play.setImageResource(R.drawable.ic_play)
            CustomPlay.getInstance().pause()
        } else {
//             play
            pref.setBoolean(Utils.togglePlay,true)
            pref.save()
            toggle_play.setImageResource(R.drawable.ic_pause)

            /**
             * Here its been managed when it needs to start
             * the service or
             * the media player
             */

            if (CustomPlay.instance!=null){
                CustomPlay.getInstance().start()
            } else {
                /**
                 * Here coroutine is use to avoid the freeze of ui View
                 */
                CoroutineScope(Dispatchers.IO).launch {
                    val serviceIntent = Intent(this@Dashboard, RadioService::class.java)
                    ContextCompat.startForegroundService(this@Dashboard, serviceIntent)
                }
            }
        }
    }

    var backPressed : Boolean = false
    override fun onBackPressed() {
        super.onBackPressed()
        backPressed = true
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!backPressed){
            pref.clearAll()
            val serviceIntent = Intent(this, RadioService::class.java)
            stopService(serviceIntent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.navigation_home ->{
                Utils(this@Dashboard).addFragment(frame.id,HomeList(),HomeList::class.java.name)
                return true
            }
            R.id.navigation_recent ->{
                Utils(this@Dashboard).addFragment(frame.id, RecentList(),RecentList::class.java.name)
                return true

            }
            else ->{
                return false

            }
        }
    }
}
package com.timife.a_n_nursery_app

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.login.ui.auth.LoginActivity

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if(resources.getBoolean(R.bool.portrait_only)){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
//        val userPreferences = UserPreferences(this)
//        userPreferences.authToken.asLiveData().observe(this,  {
//            val activity = if (it == null) LoginActivity::class.java else MainActivity::class.java
//            startNewActivity(activity)
//            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
//
//        })
    }

}
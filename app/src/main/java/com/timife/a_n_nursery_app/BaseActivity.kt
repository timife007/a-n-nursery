package com.timife.a_n_nursery_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.timife.a_n_nursery_app.login.UserPreferences
import com.timife.a_n_nursery_app.login.ui.auth.LoginActivity
import com.timife.a_n_nursery_app.login.ui.startNewActivity

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this,  {
            val activity = if (it == null) LoginActivity::class.java else MainActivity::class.java
            startNewActivity(activity)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        })
    }
}
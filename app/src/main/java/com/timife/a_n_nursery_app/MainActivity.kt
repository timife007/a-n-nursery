package com.timife.a_n_nursery_app

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.ravikoradiya.library.CenterTitle
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.base.BaseViewModel
import com.timife.a_n_nursery_app.dashboard.DashBoardRepository
import com.timife.a_n_nursery_app.dashboard.DashboardViewModel
import com.timife.a_n_nursery_app.dashboard.network.DashboardApi
import com.timife.a_n_nursery_app.databinding.ActivityMainBinding
import com.timife.a_n_nursery_app.inventory.categories.database.CategoryDatabase
import com.timife.a_n_nursery_app.inventory.categories.network.CategoryApi
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryRepository
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryViewModel
import com.timife.a_n_nursery_app.inventory.classifications.network.ClassificationApi
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationRepository
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationViewModel
import com.timife.a_n_nursery_app.inventory.locations.network.LocationApi
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationRepository
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationViewModel
import com.timife.a_n_nursery_app.inventory.lots.network.LotApi
import com.timife.a_n_nursery_app.inventory.lots.ui.LotRepository
import com.timife.a_n_nursery_app.inventory.lots.ui.LotViewModel
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.inventory.ui.InventoryViewModel
import com.timife.a_n_nursery_app.login.network.RetrofitClient
import com.timife.a_n_nursery_app.login.ui.auth.LoginRepository
import com.timife.a_n_nursery_app.login.ui.auth.LoginViewModel
import com.timife.a_n_nursery_app.settings.SettingsRepository
import com.timife.a_n_nursery_app.settings.SettingsViewModel
import com.timife.a_n_nursery_app.settings.access_control.AccessControlRepository
import com.timife.a_n_nursery_app.settings.access_control.AccessControlViewModel
import com.timife.a_n_nursery_app.settings.access_control.network.AccessControlApi
import com.timife.a_n_nursery_app.settings.profile.network.ProfileApi
import com.timife.a_n_nursery_app.settings.profile.ui.ProfileRepository
import com.timife.a_n_nursery_app.settings.profile.ui.ProfileViewModel
import com.timife.a_n_nursery_app.vendor.VendorRepository
import com.timife.a_n_nursery_app.vendor.VendorViewModel
import com.timife.a_n_nursery_app.vendor.network.VendorsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    protected val retrofitClient = RetrofitClient()
    protected lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        if(resources.getBoolean(R.bool.portrait_only)){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences(this)
        val binding =
                DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
                val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController,appBarConfiguration)
        binding.bottomNavView.setupWithNavController(navController)
        CenterTitle.centerTitle(binding.toolbar,true)
        CoroutineScope(Dispatchers.IO).launch {
            instantiateViewModels()
       }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private suspend fun instantiateViewModels(){
        val token = userPreferences.authToken.first()
        val profileApi = retrofitClient.buildApi(ProfileApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(ProfileRepository(profileApi))).get(ProfileViewModel::class.java)
        val inventoryApi = retrofitClient.buildApi(InventoryApi::class.java, token)
        val database = CategoryDatabase.invoke(this)
        ViewModelProvider(this, ViewModelFactory(InventoryRepository(inventoryApi,database))).get(InventoryViewModel::class.java)
        val dashboardApi = retrofitClient.buildApi(DashboardApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(DashBoardRepository(dashboardApi))).get(DashboardViewModel::class.java)
        ViewModelProvider(this, ViewModelFactory(SettingsRepository())).get(SettingsViewModel::class.java)
        val vendorApi = retrofitClient.buildApi(VendorsApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(VendorRepository(vendorApi))).get(VendorViewModel::class.java)
        val accessControlApi = retrofitClient.buildApi(AccessControlApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(AccessControlRepository(accessControlApi))).get(AccessControlViewModel::class.java)
        val categoryApi = retrofitClient.buildApi(CategoryApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(CategoryRepository(categoryApi))).get(CategoryViewModel::class.java)
        val locationApi = retrofitClient.buildApi(LocationApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(LocationRepository(locationApi))).get(LocationViewModel::class.java)
        val lotApi = retrofitClient.buildApi(LotApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(LotRepository(lotApi))).get(LotViewModel::class.java)
        val classificationApi = retrofitClient.buildApi(ClassificationApi::class.java, token)
        ViewModelProvider(this, ViewModelFactory(ClassificationRepository(classificationApi))).get(ClassificationViewModel::class.java)
    }
}
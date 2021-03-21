package com.timife.a_n_nursery_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a_n_nursery_app.inventory.ui.InventoryViewModel
import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.dashboard.DashBoardRepository
import com.timife.a_n_nursery_app.dashboard.DashboardViewModel
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryRepository
import com.timife.a_n_nursery_app.inventory.categories.ui.CategoryViewModel
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationRepository
import com.timife.a_n_nursery_app.inventory.classifications.ui.ClassificationViewModel
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationRepository
import com.timife.a_n_nursery_app.inventory.locations.ui.LocationViewModel
import com.timife.a_n_nursery_app.inventory.lots.ui.LotRepository
import com.timife.a_n_nursery_app.inventory.lots.ui.LotViewModel
import com.timife.a_n_nursery_app.inventory.ui.InventoryRepository
import com.timife.a_n_nursery_app.login.ui.auth.LoginRepository
import com.timife.a_n_nursery_app.login.ui.auth.LoginViewModel
import com.timife.a_n_nursery_app.settings.SettingsRepository
import com.timife.a_n_nursery_app.settings.SettingsViewModel
import com.timife.a_n_nursery_app.settings.access_control.AccessControlRepository
import com.timife.a_n_nursery_app.settings.access_control.AccessControlViewModel
import com.timife.a_n_nursery_app.settings.profile.ui.ProfileRepository
import com.timife.a_n_nursery_app.settings.profile.ui.ProfileViewModel
import com.timife.a_n_nursery_app.vendor.VendorRepository
import com.timife.a_n_nursery_app.vendor.VendorViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
        private val repository: BaseRepository? = null
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(repository as LoginRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) ->
                ProfileViewModel(repository as ProfileRepository) as T
            modelClass.isAssignableFrom(InventoryViewModel::class.java) ->
                InventoryViewModel(repository as InventoryRepository) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) ->
                DashboardViewModel(repository as DashBoardRepository) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(repository as SettingsRepository) as T
            modelClass.isAssignableFrom(VendorViewModel::class.java) ->
                VendorViewModel(repository as VendorRepository) as T
            modelClass.isAssignableFrom(AccessControlViewModel::class.java) ->
                AccessControlViewModel(repository as AccessControlRepository) as T
            modelClass.isAssignableFrom(CategoryViewModel::class.java) ->
                CategoryViewModel(repository as CategoryRepository) as T
            modelClass.isAssignableFrom(LocationViewModel::class.java) ->
                LocationViewModel(repository as LocationRepository) as T
            modelClass.isAssignableFrom(LotViewModel::class.java) ->
                LotViewModel(repository as LotRepository) as T
            modelClass.isAssignableFrom(ClassificationViewModel::class.java) ->
                ClassificationViewModel(repository as ClassificationRepository) as T
            else -> throw  IllegalArgumentException("ViewModelClass not found")
        }
    }
}
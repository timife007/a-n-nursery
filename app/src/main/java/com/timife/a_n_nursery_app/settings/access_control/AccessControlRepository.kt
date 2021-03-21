package com.timife.a_n_nursery_app.settings.access_control

import com.timife.a_n_nursery_app.base.BaseRepository
import com.timife.a_n_nursery_app.settings.access_control.network.AccessControlApi

class AccessControlRepository(
    private val accessControlApi: AccessControlApi
) : BaseRepository() {
}
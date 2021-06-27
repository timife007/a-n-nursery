package com.timife.a_n_nursery_app.settings.access_control.network

import androidx.paging.PagingSource
import com.timife.a_n_nursery_app.settings.access_control.response.Invitee
import retrofit2.HttpException
import java.io.IOException


private const val INVITED_STARTING_PAGE_INDEX = 1

class InviteePagingSource(
    private val accessControlApi: AccessControlApi,
) : PagingSource<Int, Invitee>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Invitee> {
        val pageNumber = params.key ?: INVITED_STARTING_PAGE_INDEX

        return try {
            val response = accessControlApi.getInvitees(pageNumber)
            val invitee = response.results

            LoadResult.Page(
                data = invitee,
                prevKey = if (pageNumber == INVITED_STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (invitee.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            if (exception.code() == 404) {
                return LoadResult.Page(emptyList(), pageNumber - 1, null)
            }
            LoadResult.Error(exception)
        }
    }
}
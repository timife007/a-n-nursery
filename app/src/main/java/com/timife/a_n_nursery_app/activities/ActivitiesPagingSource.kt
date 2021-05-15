package com.timife.a_n_nursery_app.activities

import androidx.paging.PagingSource
import com.timife.a_n_nursery_app.activities.network.ActivitiesApi
import com.timife.a_n_nursery_app.activities.response.Activity
import retrofit2.HttpException
import java.io.IOException

private const val ACTIVITIES_STARTING_PAGE_INDEX = 1


class ActivitiesPagingSource (
    private val activitiesApi: ActivitiesApi,
) : PagingSource<Int, Activity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Activity> {
        val pageNumber = params.key ?: ACTIVITIES_STARTING_PAGE_INDEX

        return try {
            val response = activitiesApi.getActivities(pageNumber)
            val activity = response.results

            LoadResult.Page(
                data = activity,
                prevKey = if (pageNumber == ACTIVITIES_STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (activity.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            if(
                exception.code() == 404
            ){
                return LoadResult.Page(emptyList(), pageNumber-1 ,null)
            }
            LoadResult.Error(exception)
        }
    }
}

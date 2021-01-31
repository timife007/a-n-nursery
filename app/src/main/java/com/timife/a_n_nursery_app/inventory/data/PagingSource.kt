package com.timife.a_n_nursery_app.inventory.data

import androidx.paging.PagingSource
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.response.Result
import retrofit2.HttpException
import java.io.IOException

private const val INVENTORY_STARTING_PAGE_INDEX = 1

class InventoryPagingSource(
    private val inventoryApi: InventoryApi,
    private val query: String
) : PagingSource<Int, Result>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: INVENTORY_STARTING_PAGE_INDEX

        return try {
            val response = inventoryApi.getSearchInventory(query, position)
            val products = response.results

            LoadResult.Page(
                data = products,
                prevKey = if (position == INVENTORY_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (products.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
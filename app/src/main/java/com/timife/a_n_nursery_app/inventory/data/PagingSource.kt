package com.timife.a_n_nursery_app.inventory.data

import androidx.paging.PagingSource
import com.timife.a_n_nursery_app.inventory.network.InventoryApi
import com.timife.a_n_nursery_app.inventory.response.Inventory
import retrofit2.HttpException
import java.io.IOException

private const val INVENTORY_STARTING_PAGE_INDEX = 1

class InventoryPagingSource(
    private val inventoryApi: InventoryApi,
    private val query: String
) : PagingSource<Int, Inventory>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Inventory> {
        val pageNumber = params.key ?: INVENTORY_STARTING_PAGE_INDEX

        return try {
            val response = inventoryApi.getSearchInventory(query, pageNumber)
            val products = response.results

            LoadResult.Page(
                data = products,
                prevKey = if (pageNumber == INVENTORY_STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (products.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            if (
                exception.code() == 404
            ) {
                return LoadResult.Page(emptyList(), pageNumber - 1, null)

            }
            LoadResult.Error(exception)
        }
    }
}

class FilterPagingSource(
    private val inventoryApi: InventoryApi,
    private val category: String
) : PagingSource<Int, Inventory>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Inventory> {
        val pageNumber = params.key ?: INVENTORY_STARTING_PAGE_INDEX

        return try {
            val response = inventoryApi.getFilterByCategoryInventory(category, pageNumber)
            val products = response.results

            LoadResult.Page(
                data = products,
                prevKey = if (pageNumber == INVENTORY_STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (products.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
package com.timife.a_n_nursery_app.vendor.network

import androidx.paging.PagingSource
import com.timife.a_n_nursery_app.vendor.response.VendorItem
import retrofit2.HttpException
import java.io.IOException

private const val VENDOR_STARTING_PAGE_INDEX = 1

class VendorPagingSource(
    private val vendorsApi: VendorsApi,
    private val firstName: String
) : PagingSource<Int, VendorItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VendorItem> {
        val pageNumber = params.key ?: VENDOR_STARTING_PAGE_INDEX

        return try {
            val response = vendorsApi.getSearchVendors(firstName, pageNumber)
            val vendors = response.results

            LoadResult.Page(
                data = vendors,
                prevKey = if (pageNumber == VENDOR_STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (vendors.isEmpty()) null else pageNumber + 1
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
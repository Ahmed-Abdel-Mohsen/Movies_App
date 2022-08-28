package com.example.base.data

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState


/**
 * Base Paging Source for loading data as pages
 */
abstract class BasePagingSource<T : Any> : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {

        val currentLoadingPageKey = params.key ?: DEFAULT_PAGE_INDEX

        return try {

            val list = getData(currentLoadingPageKey)

            val nextKey =
                if (currentLoadingPageKey < MAX_PAGE_INDEX) currentLoadingPageKey.plus(1) else null

            LoadResult.Page(
                data = list.orEmpty(),
                prevKey = null,  // Only paging forward
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    abstract suspend fun getData(currentLoadingPageKey: Int): List<T>?

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val DEFAULT_PAGE_INDEX = 1
        private const val DEFAULT_PAGE_SIZE = 20
        private const val MAX_PAGE_INDEX = 500

        fun getDefaultPagingConfig(): PagingConfig {
            return PagingConfig(pageSize = DEFAULT_PAGE_SIZE)
        }
    }
}
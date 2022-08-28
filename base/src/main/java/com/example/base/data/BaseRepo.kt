package com.example.base.data

import com.example.base.utils.NetworkConnectivityHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepo(
    private val networkConnectivityHelper: NetworkConnectivityHelper,
    val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun <T> networkOnlyFlow(remoteCall: suspend () -> T): Flow<Resource<T>> {
        suspend fun fetchFromNetwork(): T {
            if (networkConnectivityHelper.isConnected()) return remoteCall()
            else throw NoInternetException()
        }

        return flow<Resource<T>> {
            emit(Resource.Loading)

            try {
                emit(Resource.Success(fetchFromNetwork()))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }.flowOn(ioDispatcher)
    }


    fun <T> networkWithCacheFlow(
        remoteCall: suspend () -> T,
        localCall: suspend () -> T?,
        cacheCall: suspend (T) -> Unit
    ): Flow<Resource<T>> {
        suspend fun fetchFromNetwork(): T {
            return remoteCall().also {
                cacheCall(it)
            }
        }


        return flow {
            emit(Resource.Loading)

            try {
                val localResult = localCall()
                val isLocalResultAvailable = (localResult != null)
                if (localResult != null) emit(Resource.Success(localResult))

                if (networkConnectivityHelper.isConnected()) {
                    emit(Resource.Success(fetchFromNetwork()))
                } else if (!isLocalResultAvailable) {
                    emit(Resource.Error(NoInternetException()))
                }

            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }.flowOn(ioDispatcher)
    }

}
package com.ddenfi.expertcapstone.core.data.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.ddenfi.expertcapstone.core.data.source.local.LocalDataSource
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import com.ddenfi.expertcapstone.core.data.source.local.entity.RemoteKeys
import com.ddenfi.expertcapstone.core.data.source.remote.RemoteDataSource
import com.ddenfi.expertcapstone.core.data.source.remote.network.ApiResponse
import com.ddenfi.expertcapstone.core.utils.DataMapper
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, GameEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d("State Refresh", remoteKeys?.nextKey.toString())
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                Log.d("State Prepend", prevKey.toString())
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                Log.d("State Append", nextKey.toString())
                nextKey
            }
        }

        return when (val responseData =
            remoteDataSource.getAllGames(page, state.config.pageSize).first()) {
            is ApiResponse.Success -> {
                Log.d("API Call", "success")
                if (loadType == LoadType.REFRESH) {
                    localDataSource.deleteAllGame()
                    localDataSource.deleteRemoteKeys()
                }
                val prefKey = if (page == 1) null else page - 1
                val nextKey = page + 1

                val keys = responseData.data.map {
                    RemoteKeys(
                        id = it.id,
                        nextKey = nextKey,
                        prevKey = prefKey
                    )

                }
                localDataSource.insertAllRemoteKey(keys)
                localDataSource.insertGame(responseData.data.map {
                    DataMapper.mapResponseToEntity(
                        it,page
                    )
                })
                MediatorResult.Success(false)
            }
            is ApiResponse.Empty -> {
                Log.d("API Call", "empty")
                MediatorResult.Success(true)
            }
            is ApiResponse.Error -> {
                Log.d("API Call", "empty")
                MediatorResult.Error(responseData.error)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeysId(id)
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
package com.ddenfi.expertcapstone.core.data.repository

import android.provider.ContactsContract
import androidx.paging.*
import com.ddenfi.expertcapstone.core.data.NetworkBoundResource
import com.ddenfi.expertcapstone.core.data.source.GameRemoteMediator
import com.ddenfi.expertcapstone.core.data.source.local.LocalDataSource
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import com.ddenfi.expertcapstone.core.data.source.remote.RemoteDataSource
import com.ddenfi.expertcapstone.core.data.source.remote.network.ApiResponse
import com.ddenfi.expertcapstone.core.data.source.remote.response.GameDetailResponse
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.domain.model.GameDetail
import com.ddenfi.expertcapstone.core.domain.repository.IGameRepository
import com.ddenfi.expertcapstone.core.utils.DataMapper
import com.ddenfi.expertcapstone.core.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IGameRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllGame(): Flow<PagingData<Game>> = Pager(

        config = PagingConfig(pageSize = 10),
        remoteMediator = GameRemoteMediator(localDataSource, remoteDataSource),
        pagingSourceFactory = {
            localDataSource.getAllGames()
        }
    ).flow.map { value: PagingData<GameEntity> ->
        value.map { DataMapper.mapGameEntityToGame(it) }
    }

    override fun getGameDetailByID(gameId: Int): Flow<Resource<GameDetail>> =
        object: NetworkBoundResource<GameDetail, GameDetailResponse>(){
            override fun loadFromDB(): Flow<GameDetail> {
                return localDataSource.getGameDetailById(gameId).map { DataMapper.mapGameDetailEntityToGameDetail(it) }
            }

            override fun shouldFetch(data: GameDetail?): Boolean = data == null

            override suspend fun createCall(): Flow<ApiResponse<GameDetailResponse>> {
                return remoteDataSource.getGameById(gameId)
            }

            override suspend fun saveCallResult(data: GameDetailResponse) {
                localDataSource.insertGameDetail(DataMapper.mapGameDetailResponseToGameDetailEntity(data))
            }
        }.asFlow()

    override fun setFavoriteGame(gameId: Int, isFavorite: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.setFavoriteGame(
                gameId,
                isFavorite
            )
        }
    }


    override fun getAllFavoriteGame(): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading())
        val localDataSource = localDataSource.getFavoriteGames()
        localDataSource.onEach { list: List<GameEntity> ->
            try {
                emit(Resource.Success(list.map { DataMapper.mapGameEntityToGame(it) }))
            } catch (e: IOException) {
                emit((Resource.Error(e.toString())))
            }
        }.collect()
    }
}
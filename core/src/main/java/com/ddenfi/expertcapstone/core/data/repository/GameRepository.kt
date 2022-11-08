package com.ddenfi.expertcapstone.core.data.repository

import com.ddenfi.expertcapstone.core.data.NetworkBoundResource
import com.ddenfi.expertcapstone.core.data.source.local.LocalDataSource
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import com.ddenfi.expertcapstone.core.data.source.remote.RemoteDataSource
import com.ddenfi.expertcapstone.core.data.source.remote.network.ApiResponse
import com.ddenfi.expertcapstone.core.data.source.remote.response.GamesResultsItem
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.domain.model.GameDetail
import com.ddenfi.expertcapstone.core.domain.repository.IGameRepository
import com.ddenfi.expertcapstone.core.utils.AppExecutors
import com.ddenfi.expertcapstone.core.utils.DataMapper
import com.ddenfi.expertcapstone.core.utils.Resource
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {
    override fun getAllGame(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GamesResultsItem>>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getAllGames().map { DataMapper.mapEntitiesToGames(it) }
            }

            override fun shouldFetch(data: List<Game>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GamesResultsItem>>> =
                remoteDataSource.getAllGames()


            override suspend fun saveCallResult(data: List<GamesResultsItem>) {
                val listGame = data.map { DataMapper.mapResponseToEntity(it) }
                localDataSource.insertGame(listGame)
            }
        }.asFlow()

    override fun getGameByID(gameId: Int): Flow<Resource<GameDetail>> = flow {
        val localDataStore = localDataSource.getGameById(gameId)
        val remoteDataSource = remoteDataSource.getGameById(gameId)
        emit(
            Resource.Loading(
                data = DataMapper.mapRemoteAndLocalToGameDetail(
                    null,
                    localDataStore.first()
                )
            )
        )

        localDataStore.combine(remoteDataSource) { local, remote ->
            when (remote) {
                is ApiResponse.Success -> emit(
                    Resource.Success(
                        DataMapper.mapRemoteAndLocalToGameDetail(
                            remote.data,
                            local
                        )
                    )
                )
                is ApiResponse.Error -> emit(
                    Resource.Error(
                        remote.errorMessage,
                        DataMapper.mapRemoteAndLocalToGameDetail(null, local)
                    )
                )
                is ApiResponse.Empty -> emit(Resource.Error("Unexpected error occurred"))
            }
        }.collect()
    }

    override fun setFavoriteGame(gameId: Int, isFavorite: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteGame(gameId, isFavorite) }
    }


    override fun getAllFavoriteGame(): Flow<Resource<List<Game>>> = flow {
        emit(Resource.Loading())
        val localDataSource = localDataSource.getFavoriteGames()
        localDataSource.onEach { list: List<GameEntity> ->
            try {
                emit(Resource.Success(list.map { DataMapper.mapGameEntityToGame(it) }))
            } catch (e:IOException){
                emit((Resource.Error(e.toString())))
            }
        }.collect()
    }
}
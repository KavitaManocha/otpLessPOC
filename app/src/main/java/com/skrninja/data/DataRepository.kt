package com.skrninja.data



import com.skrninja.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DataRepository @Inject constructor(
    private val remoteRepository: RemoteData,
    private val ioDispatcher: CoroutineContext
) : DataRepositorySource ,BaseRepository(){



   /* override suspend fun doLogIn(
        logInRequest: LogInRequest
    ): Flow<Resource<LogInResponse>> {
        return flow { emit(remoteRepository.doRemoteLogIn(logInRequest)) }.flowOn(ioDispatcher)
    }*/


}

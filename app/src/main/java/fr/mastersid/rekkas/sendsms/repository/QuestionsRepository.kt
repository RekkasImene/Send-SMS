package fr.mastersid.rekkas.sendsms.repository

import fr.mastersid.rekkas.sendsms.models.RequestState
import fr.mastersid.rekkas.sendsms.room.QuestionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
    private val stackOverFlowWebServices : QuestionsWebService,
    private val questionDao: QuestionDao
) {
    val questionList = questionDao.getQuestionList()
    private val _requestState = MutableStateFlow(RequestState.NONE_OR_DONE)
    val requestState: Flow<RequestState>
        get() = _requestState

    suspend fun updateQuestionsList(
        order: String,
        sort: String
    ) {
        try {
            _requestState.emit(RequestState.PENDING)
            val list = stackOverFlowWebServices
                .getQuestionList(order = order, sort = sort)

            questionDao.insertAll(list)


        } catch (exception: HttpException){
            _requestState.emit(RequestState.REQUEST_ERROR)

        } catch (exception : IOException) {
            _requestState.emit(RequestState.NETWORK_ERROR)
        }


        finally {
            _requestState.emit(RequestState.NONE_OR_DONE)
        }
    }
}
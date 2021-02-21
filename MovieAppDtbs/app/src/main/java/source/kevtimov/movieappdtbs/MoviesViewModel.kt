package source.kevtimov.movieappdtbs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MoviesViewModel : ViewModel() {


    private val viewModelJob = Job()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private var _movies = MutableLiveData<List<Movie>>()
    val movies : LiveData<List<Movie>>
    get() = _movies


    fun getMovies() {
//        scope.launch {
//            try {
//                val movies = MovieApi.retrofitService.getMovies().await()
//                _movies.postValue(movies)
//            } catch (t : Throwable) {
//                //Log.e("MOVIES CALL", t.message)
//            }
//        }
    }

    fun getRequestToken() {
//        scope.launch {
//            try {
//                val token = MovieApi.retrofitService.getRequestToken(REQUEST_TOKEN).await()
//                val o = token
//            } catch (t : Throwable) {
//                //Log.e("MOVIES CALL", t.message)
//                val p = 9
//            }
//        }
        MovieApi.retrofitService.getRequestToken(auth = REQUEST_TOKEN).enqueue(object :
            retrofit2.Callback<RequestTokenResponse> {
            override fun onFailure(call: Call<RequestTokenResponse>, t: Throwable) {
                val o = 0
            }

            override fun onResponse(
                call: Call<RequestTokenResponse>,
                response: Response<RequestTokenResponse>
            ) {
                val o = response.body()
                val p = o
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
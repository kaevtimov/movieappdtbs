package source.kevtimov.movieappdtbs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {


    private val viewModelJob = Job()
    private val scope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private var _movies = MutableLiveData<List<Movie>>()
    val movies : LiveData<List<Movie>>
    get() = _movies


    fun getMovies() {
        scope.launch {
            try {
                val movies = MovieApi.retrofitService.getMovies().await()
                _movies.postValue(movies)
            } catch (t : Throwable) {
                Log.e("MOVIES CALL", t.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
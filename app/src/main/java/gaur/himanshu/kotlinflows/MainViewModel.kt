package gaur.himanshu.kotlinflows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val channel = Channel<Int>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        onUndeliveredElement = {
            Log.d("TAG", "onUndeliveredElement: $it")
        }
    )

    val mChannel = channel.consumeAsFlow()
   // val mChannel = channel.receiveAsFlow()

    val stateFlow = MutableStateFlow(0)


    init {
        sharedFlowTesting()
      //  stateFlowTesting()
    }

    fun sharedFlowTesting() {
        viewModelScope.launch {
            repeat(10){
                //delay(1000L)
                channel.send(it)
            }
        }
    }

    fun stateFlowTesting() {
        viewModelScope.launch {
            repeat(10){
                delay(1000L)
                stateFlow.emit(it)
            }
            stateFlow.emit(10)
        }
    }

}
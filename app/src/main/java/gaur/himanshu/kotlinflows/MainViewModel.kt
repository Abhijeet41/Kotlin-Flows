package gaur.himanshu.kotlinflows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val sharedFlow = MutableSharedFlow<Int>(
        replay = 4,
        extraBufferCapacity = 4,
        onBufferOverflow = BufferOverflow.SUSPEND
    )


    val stateFlow = MutableStateFlow(0)

    val channel = Channel<Int>(
        capacity = 2,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
        onUndeliveredElement = {
            Log.d("TAGGGGG", "onUndeliveredElement: ${it} ")
        }
    )

    val mChannel = channel.receiveAsFlow()

    init {
//        sharedFLowTesting()
//        stateFlowTesting()
        channelFlowTesting()
    }

    fun sharedFLowTesting() {
        viewModelScope.launch {
            repeat(10) {
                delay(1000)
                sharedFlow.emit(it)
            }
        }
    }

    fun stateFlowTesting() {
        viewModelScope.launch {
            repeat(10) {
                delay(1000)
                stateFlow.emit(it)
            }
        }
    }

    fun channelFlowTesting() {
        viewModelScope.launch {
            repeat(10) {
                channel.send(it)
            }
        }
    }

    fun firebaseAuth() = callbackFlow<String> {
        FirebaseAuth.getInstance().signInWithEmailAndPassword("", "")
            .addOnSuccessListener {
                trySend("success")
            }
            .addOnFailureListener {
                trySend("failure")
            }
            .addOnCanceledListener {
                trySend("cancel")
            }
        awaitClose {

        }
    }


}
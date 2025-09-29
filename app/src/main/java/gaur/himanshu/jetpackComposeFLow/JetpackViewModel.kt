package gaur.himanshu.jetpackComposeFLow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class JetpackViewModel : ViewModel() {
    val countDownflow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        //collectFlow()
        //collectFlowWithOperator()
        collectFlowWithOperator2()
    }
    private fun collectFlow(){
        viewModelScope.launch {
            countDownflow.collectLatest{time->
                delay(1500)
                println("The current time is $time")
            }
        }
    }

    private fun collectFlowWithOperator(){
        viewModelScope.launch {
            delay(1000)
            countDownflow
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                    time * time
                }.onEach {time ->
                    println("$time")
                }
                .collect { time ->
                    println("The current time is $time")
                }
        }
    }
    private fun collectFlowWithOperator2(){
       viewModelScope.launch {
            delay(1000)
           val count = countDownflow
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                    time * time
                }.onEach {time ->
                    println("The current time is $time")
                }
                .count { time ->
                    time % 2 == 0
                }
           println("The count is $count")

       }
    }
}
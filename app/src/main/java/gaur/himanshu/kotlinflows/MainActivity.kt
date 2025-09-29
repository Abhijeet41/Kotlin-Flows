package gaur.himanshu.kotlinflows

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class  MainActivity : AppCompatActivity() {
    private  val TAG = "MainActivity"
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //collect flow here
       /* lifecycleScope.launch {
            viewModel.sharedFlow.collectLatest {
                findViewById<TextView>(R.id.tvCounter).text = it.toString()
                Log.d(TAG, "onCreate: $it")
            }
        }*/

        /*lifecycleScope.launch {
            viewModel.sharedFlow.collectLatest {
                Log.d(TAG, "onCreate: $it")
            }
        }*/

        /*lifecycleScope.launch {
            viewModel.channel.receiveAsFlow().collectLatest {
                Log.d(TAG, "channel: $it")
                findViewById<TextView>(R.id.tvCounter).text = it.toString()

            }
        }*/

        lifecycleScope.launch {
            viewModel.mChannel.collectLatest {
                Log.d(TAG, "channel: $it")
                findViewById<TextView>(R.id.tvCounter).text = it.toString()

            }

        }

    }

  /*  override fun onStart() {
        super.onStart()
       lifecycleScope.launch {
           viewModel.mChannel.collectLatest {
               Log.d(TAG, "channel2: $it")
               findViewById<TextView>(R.id.tvCounter).text = it.toString()

           }
       }
    }*/
}
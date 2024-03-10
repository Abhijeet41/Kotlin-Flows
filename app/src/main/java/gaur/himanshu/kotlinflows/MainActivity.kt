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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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

//        lifecycleScope.launch {
//            viewModel.sharedFlow.collectLatest {
//                Log.d("TAGGGGGGG-Shared", "onCreate: ${it}")
//                val textView = findViewById<TextView>(R.id.main_text)
//                textView.setText(it.toString())
//            }
//        }

//        lifecycleScope.launch {
//            delay(4000)
//            viewModel.sharedFlow.collectLatest {
//                Log.d("TAGGGGGGG-Shared 2", "onCreate: ${it}")
//            }
//        }

//
//        lifecycleScope.launch {
//            viewModel.stateFlow.collectLatest {
//                Log.d("TAGGGGGGG-stateflow", "onCreate: ${it}")
//                val textView = findViewById<TextView>(R.id.main_text)
//                textView.setText(it.toString())
//            }
//        }


        lifecycleScope.launch {
            viewModel.mChannel.collectLatest {
                Log.d("TAGGGGGGG-mChannel", "onCreate: ${it}")
                val textView = findViewById<TextView>(R.id.main_text)
                textView.setText(it.toString())
            }
        }

        lifecycleScope.launch {
            viewModel.firebaseAuth().collectLatest {

            }
        }

        val flow = flow<String> {
            emit("fklaskdfjalf")

        }

        val flowOf = flowOf("", "jfklajdf", "djfkla")


    }
}
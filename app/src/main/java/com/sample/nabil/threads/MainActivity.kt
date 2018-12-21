package com.sample.nabil.threads

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ProgressBar
import android.os.AsyncTask
import android.widget.Toast
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)

    }

    fun ankoStart(view: View) {
        doAsync {
            //Execute all the lon running tasks here
            for (i in 0..100) {
                SystemClock.sleep(50)
                progressBar?.progress = i
            }
            uiThread {
                //Update the UI thread here
                alert("Downloaded data is ", "Hi I'm an alert") {
                    yesButton { toast("Yay !") }
                    noButton { toast(":( !") }
                }.show()
            }
        }
    }
    fun handlerStart(view: View) {

        // do something long
        val runnable = Runnable {
            for (i in 0..100) {
                SystemClock.sleep(50)
                progressBar?.progress = i
            }
        }
        Thread(runnable).start()

    }

    fun asyncStart(view: View) {
        val myTask = MyTask(this)
        myTask.execute()
    }

    class MyTask(var activity: MainActivity?) : AsyncTask<Void, String, String>() {
        override fun doInBackground(vararg params: Void?): String {

            for (i in 0..100) {
                SystemClock.sleep(50)
                activity?.progressBar?.progress = i
            }
            return "done"
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            // do something with result
            Toast.makeText(activity, "AsyncTaskFinished!", Toast.LENGTH_LONG).show()
        }
    }
}




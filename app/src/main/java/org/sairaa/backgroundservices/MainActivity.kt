package org.sairaa.backgroundservices

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.start).setOnClickListener {
            startS()
        }
        findViewById<Button>(R.id.stop).setOnClickListener {
            stopS()
        }
    }

    private fun stopS() {
//        BackgroundService.stopServicees()
        Log.e("-----","cancel")
//        App().cancelWork(applicationContext)
        GeneralServices.shouldIStop = true
        val serviceIntent = Intent(this, GeneralServices::class.java)
        stopService(serviceIntent)
    }

    private fun startS() {
//        App().downLoadSyncProcess(applicationContext)
        Log.e("-----","start")
        GeneralServices.shouldIStop = false
        val serviceIntent = Intent(this, GeneralServices::class.java)
//        serviceIntent.putExtra("inputExtra", "binding.editText.getText().toString().trim()")
        startService(serviceIntent)
//        val serviceI = Intent(this, BackgroundService::class.java)
//        serviceI.putExtra("inputString","Hello Hi")
//        BackgroundService.shouldIStop = false
//        BackgroundService.enqueWork(applicationContext,serviceI)
    }
}
package com.example.batterystate

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    var batteryState: TextView? = null
    var batterycahrg:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        batteryState=findViewById(R.id.batteryhealth)
        batterycahrg = findViewById(R.id.textView)
        val intentFilter =IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryBroadcastReceiver , intentFilter)
    }
    private var batteryBroadcastReceiver:BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "android.intent.action.BATTERY_CHANGED"){
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL , -1)
                when(level){
                   in 1..20 -> batteryState?.text = "BATTERY LOW"
                    in  21..99 -> batteryState?.text = "BATTERY NORMAL"
                    else -> batteryState?.text = "BATTERY FULL"
                }
            }
            val batterychanger = intent?.getIntExtra(BatteryManager.EXTRA_STATUS,-1 )?:-1
            val charge:Boolean = batterychanger == BatteryManager.BATTERY_STATUS_CHARGING
            if (charge == true){
                batterycahrg?.text = "CHARGING"


            }else
                batterycahrg?.text = "NOTCHARGING"
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryBroadcastReceiver)
    }
}
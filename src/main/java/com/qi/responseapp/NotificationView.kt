package com.qi.responseapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NotificationView : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val notificationId = findViewById(R.id.notification) as TextView

//        notificationId?.setOnClickListener {
//            startActivity(Intent(this,MainActivity :: class.java))
//        }
    }
}
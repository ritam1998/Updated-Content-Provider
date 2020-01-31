package com.qi.contentprovider

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.qi.contentprovider.StudentProvider.Companion.CONTENT_URI


class AddStudent : AppCompatActivity(){

    private var addusername : EditText ?= null
    private var addemail : EditText ?= null
    private var addphone : EditText ?= null
    private var password : EditText ?= null

    private var addbutton : Button?= null

    var studentProvider = StudentProvider()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addstudent)

        var action = supportActionBar
        action?.setDisplayHomeAsUpEnabled(true)
        action?.setDisplayShowHomeEnabled(true)

        addusername = findViewById(R.id.username)
        addemail = findViewById(R.id.useremail)
        addphone = findViewById(R.id.userphone)
        password = findViewById(R.id.userpassword)

        addbutton = findViewById(R.id.savebutton)

        addbutton?.setOnClickListener {
            insertStudent()
        }
    }
    fun insertStudent(){

        var usernameToString = addusername?.text?.toString()
        var usermailToString = addemail?.text?.toString()

        var phoneToString = addphone?.text?.toString()
        var password = password?.text?.toString()

        if(usernameToString.equals("") || usermailToString.equals("") || phoneToString.equals("") || password.equals("")){
            Toast.makeText(this,"Please Fill Up",Toast.LENGTH_SHORT).show()
        }else{

            var contentvalues = ContentValues()

            contentvalues.put(studentProvider.USERNAME,usernameToString)
            contentvalues.put(studentProvider.USERMAIL,usermailToString)
            contentvalues.put(studentProvider.USERPHONE,phoneToString)
            contentvalues.put(studentProvider.USERPASSWORD,password)

            contentResolver?.insert(CONTENT_URI,contentvalues)

            Toast.makeText(this,"Sucess",Toast.LENGTH_LONG).show()

            finish()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
package com.qi.responseapp

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateStudent : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        var updatestudenID = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        var updateName = findViewById(R.id.updateusername) as EditText
        var updatePhone =findViewById(R.id.updatephone) as EditText
        var updateEmail = findViewById(R.id.updateemail) as EditText
        var updatePassword = findViewById(R.id.updateaddress) as EditText

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        if(intent.hasExtra("StudentModel")){

            var studentModel = intent.getParcelableExtra<StudentModel>("StudentModel")

            Log.d("Student Id","ID == ${studentModel.studentname}")

            updateName?.setText(studentModel.studentname)
            updatePhone?.setText(studentModel.studentphone)
            updateEmail?.setText(studentModel.studentEmail)

            updatestudenID = studentModel?.studentId?:0

        }

        val updateButton : Button = findViewById(R.id.updatebutton)

        updateButton?.setOnClickListener {
            update(updatestudenID,updateusername = updateName.text.toString(),updateuserphone = updatePhone.text.toString(),useremail = updateEmail.text.toString(),updateuserpassword = updatePassword.text.toString())
        }
    }
    fun update(updateuserId : Int?,updateusername : String?,updateuserphone : String?,useremail : String?,updateuserpassword : String){

        if(updateusername.equals("") || updateuserphone.equals("") || useremail.equals("") || updateuserpassword.equals("")){
            Toast.makeText(this,"All Fill Up",Toast.LENGTH_LONG).show()
        }else{

            var values = ContentValues()

            values.put("studentname",updateusername)
            values.put("studentphone",updateuserphone)
            values.put("studentemail",useremail)
            values.put("studentpassword",updateuserpassword)

            var status = contentResolver.update(Uri.parse("content://com.qi.contentprovider.StudentsProvider/students"),values,null,
                arrayOf(updateuserId.toString()))

            if(status != 0){
                Toast.makeText(this,"update SucessFull",Toast.LENGTH_LONG).show()
                finish()
            }
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
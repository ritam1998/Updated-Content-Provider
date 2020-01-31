package com.qi.contentprovider


import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.qi.contentprovider.StudentProvider.Companion.CONTENT_URI

class MainActivity : AppCompatActivity() {

    private var addstudentButton : Button?= null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val action = supportActionBar
        action?.setDisplayHomeAsUpEnabled(true)
        action?.setDisplayShowHomeEnabled(true)

        addstudentButton = findViewById(R.id.addStudentbutton)

        addstudentButton?.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity,AddStudent :: class.java),1)
        }

        val recyclerview = findViewById(R.id.recycleviewer) as RecyclerView

        val studentlist = ArrayList<StudentModel>()



        val cursor : Cursor? = contentResolver.query(CONTENT_URI,null,null,null,null)

        while(cursor?.moveToNext() == true){

            var studentnamelist = StudentModel(cursor.getString(cursor.getColumnIndex("studentname")),cursor.getString(cursor.getColumnIndex("studentphone")))

            //studentModel.userphone = cursor.getString(cursor.getColumnIndex("studentphone"))

            //Log.v("list","listname is === "+cursor.getString(cursor.getColumnIndex("studentname")))

            studentlist.add(studentnamelist)
        }

        Log.v("list","list is === $studentlist")

        val studentadapter = StudentAdapter(studentlist)

        recyclerview.adapter = studentadapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 1){
            view()
            Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun view(){

        val recyclerview = findViewById(R.id.recycleviewer) as RecyclerView

        val studentlist = ArrayList<StudentModel>()



        val cursor : Cursor? = contentResolver.query(CONTENT_URI,null,null,null,null)

        while(cursor?.moveToNext() == true){

            var studentnamelist = StudentModel(cursor.getString(cursor.getColumnIndex("studentname")),cursor.getString(cursor.getColumnIndex("studentphone")))

            //studentModel.userphone = cursor.getString(cursor.getColumnIndex("studentphone"))

            //Log.v("list","listname is === "+cursor.getString(cursor.getColumnIndex("studentname")))

            studentlist.add(studentnamelist)
        }

        Log.v("list","list is === $studentlist")

        val studentadapter = StudentAdapter(studentlist)

        recyclerview.adapter = studentadapter
    }
}

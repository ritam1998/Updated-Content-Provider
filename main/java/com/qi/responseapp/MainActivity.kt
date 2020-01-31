package com.qi.responseapp

import android.app.NotificationChannel
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.content.Context
import android.app.PendingIntent
import android.content.Intent
import android.database.ContentObserver
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat


class MainActivity : AppCompatActivity() {

    private var name : String ?= null

    var studentlist =  ArrayList<StudentModel>()
    private var studentadapter:StudentAdapter? = null

    var studentId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var action = supportActionBar
        action?.setDisplayHomeAsUpEnabled(true)
        action?.setDisplayShowHomeEnabled(true)

        fetchstudent()

        registerObserver()

    }


//    override fun onResume() {
//
//        registerObserver()
//        fetchstudent()
//        super.onResume()
//    }

    fun fetchstudent(){

        var studentName : String = ""
        studentlist.clear()

        val cursor : Cursor? = contentResolver.query(Uri.parse("content://com.qi.contentprovider.StudentsProvider/students"),null,null,null,null)


        while(cursor?.moveToNext() == true){

            val studentnamelist = StudentModel(cursor.getInt(cursor.getColumnIndex("studentId")),
                                               cursor.getString(cursor.getColumnIndex("studentname")),
                                               cursor.getString(cursor.getColumnIndex("studentphone")),
                                               cursor.getString(cursor.getColumnIndex("studentemail")),
                                               cursor.getString(cursor.getColumnIndex("studentpassword")))
            studentlist.add(studentnamelist)

//            studentModel.studentId = cursor.getInt(cursor.getColumnIndex("studentId"))
//            studentModel.studentname = cursor.getString(cursor.getColumnIndex("studentname"))
//            studentModel.studentphone = cursor.getString(cursor.getColumnIndex("studentphone"))

//            studentlist.add(studentModel)
        }

        cursor?.close()
//        Log.e("Students:",""+studentlist[0].studentId+studentlist[1].studentId+studentlist[2].studentId)
        loadAdapter(studentlist)

//        Log.v("studentname","list === "+studentlist.size)
//        Log.v("studentsize","studentsize ===== $studentSize")

//        val recyclerview = findViewById(R.id.recycleviewer) as RecyclerView
//
//        val studentadapter =  StudentAdapter(studentlist)
//
//        recyclerview.loadAdapter = studentadapter
//
//        studentadapter?.notifyDataSetChanged()

    }

    fun loadAdapter(studentlist : ArrayList<StudentModel>){

        val recyclerview = findViewById(R.id.recycleviewer) as RecyclerView
        studentadapter =  StudentAdapter(studentlist, object : StudentCallback{

            override fun onUpdateStudent(studentModel: StudentModel) {

                startActivityForResult(Intent(this@MainActivity,UpdateStudent :: class.java).putExtra("StudentModel",studentModel),1)
            }

            override fun onclickstudent(studentModel: StudentModel, position: Int) {

                Toast.makeText(this@MainActivity,"Id: ${studentModel.studentId}"+ " Pos: $position",Toast.LENGTH_LONG).show()
                studentId = studentModel?.studentId
                delete()
                studentadapter?.studentList?.removeAt(position)
                studentadapter?.notifyItemRemoved(position)
                studentadapter?.notifyItemRangeRemoved(position,studentadapter?.studentList?.size?:0)
            }

        })

        recyclerview.adapter = studentadapter


        //studentadapter.notifyDataSetChanged()
    }
    fun delete(){

        val cursor = contentResolver.delete(Uri.parse("content://com.qi.contentprovider.StudentsProvider/students"),null,
            arrayOf(studentId.toString()))
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerObserver() {
        contentResolver?.registerContentObserver(Uri.parse("content://com.qi.contentprovider.StudentsProvider/students"), true, observer)
    }

    private fun unregisterObserver() {
        contentResolver?.unregisterContentObserver(observer)
    }

     val observer: ContentObserver = object : ContentObserver(Handler()) {

         override fun onChange(selfChange: Boolean, uri: Uri?) {

             if (uri?.toString()?.contains("content://com.qi.contentprovider.StudentsProvider/students/") == true) {

                 //Toast.makeText(this@MainActivity,"new added",Toast.LENGTH_SHORT).show()
                 Log.d("MainActivity", "selfChange >> ${uri}")
//               super.onChange(selfChange, uri)

                 addNotification()

                 fetchstudent()
             }

         }

         override fun deliverSelfNotifications(): Boolean {

             return true
             //return super.deliverSelfNotifications()
         }

     }

    fun addNotification(){

        var builder = NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle("New Message")
            .setContentText("Add a new member")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel()

        val notificationIntent = Intent(this, MainActivity::class.java)

        val contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(contentIntent)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
        builder.setAutoCancel(false)

        //builder.setDeleteIntent(contentIntent)
    }
    private fun createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = getString(R.string.channel_name)

            val descriptionText = getString(R.string.channel_description)

            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterObserver()
    }

}

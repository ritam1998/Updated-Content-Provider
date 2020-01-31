package com.qi.contentprovider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.net.Uri
import android.util.Log
import android.widget.Toast
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.TextUtils
import androidx.loader.content.CursorLoader


class StudentProvider() : ContentProvider() {



//    constructor() : this() {
////        this.myContext = context
//    }



    private val URI_CODE = 1
//    private val CONTACT_ID = 2

    var USERNAME = "studentname"
    var USERMAIL = "studentemail"
    var USERPHONE = "studentphone"
    var USERPASSWORD = "studentpassword"

//    static
//    {
//        uriMatcher.addURI(AUTHORITY, BASE_PATH, CONTACTS);
//        uriMatcher.addURI(AUTHORITY, "$BASE_PATH/#", CONTACT_ID);
//    }
    companion object{

        internal var DATABASE_NAME = "studentInfo.db"
        internal var DATABASE_VERSION = 1

        internal  var CREATE_TABLE_STUDENT = "create table student_details(studentId integer primary key autoincrement,studentname text,studentemail text,studentphone text,studentpassword text)"

        lateinit var studentdbHandle : DataBaseHandle

        lateinit var db : SQLiteDatabase

        private var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        val AUTHORITY = "com.qi.contentprovider.StudentsProvider"

        private val BASE_PATH = "students"

        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$BASE_PATH")

    }
    init {

        uriMatcher.addURI(AUTHORITY,BASE_PATH,URI_CODE)

//        studentdbHandle = DataBaseHandle(myContext, DATABASE_NAME,null, DATABASE_VERSION)
    }
//    constructor(context: Context) : this(){
//
//        Log.d("TAG","ANOTHER CONSTRUCTOR")
//    }
    override fun insert(uri: Uri, values: ContentValues?): Uri? {

       /* TODO("not implemented") //To change body of created functions use File | Settings | File Templates. */

        var uri_name : Uri ?= null

        val status = db.insert("student_details",null,values)

        if(status > 0){

            uri_name = ContentUris.withAppendedId(CONTENT_URI,status)   /* a new URI with the given ID appended to the end of the path */

            context?.contentResolver?.notifyChange(uri_name,null)

            Log.v("success","status == $uri_name")
            return uri_name
        }
        throw SQLiteException("Failed to add a record into " + uri_name);
    }

    override fun query(uri: Uri,projection: Array<out String>?,selection: String?,selectionArgs: Array<out String>?,sortOrder: String?): Cursor? {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/

        val cursor: Cursor

        db = studentdbHandle.readableDatabase

        when(uriMatcher.match(uri)){

            URI_CODE -> cursor = db.query("student_details",projection,selection,selectionArgs,null,null,sortOrder)
            else -> throw IllegalArgumentException("This is an Unknown URI " + uri);
        }

        cursor.setNotificationUri(context?.contentResolver,uri)

        context?.contentResolver?.notifyChange(uri,null)

//        Log.v("cursor","notify == > ${cursor.setNotificationUri(context?.contentResolver,uri)}")
        return cursor
    }

    override fun onCreate(): Boolean {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/
        val context = context

        studentdbHandle = DataBaseHandle(context, DATABASE_NAME,null, DATABASE_VERSION)

        db = studentdbHandle.writableDatabase

        return true
    }

    override fun update(uri: Uri,values: ContentValues?,selection: String?,selectionArgs: Array<out String>?): Int {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/

        db = studentdbHandle.writableDatabase

        var status = 0

        when(uriMatcher.match(uri)){

            URI_CODE -> status = db.update("student_details",values,"studentId = ?",selectionArgs)
            else -> throw java.lang.IllegalArgumentException("unsupported : $uri")
        }
        return status
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/

        db =  studentdbHandle.writableDatabase

        var studentID : String ?= null

        var count = 0

        when(uriMatcher.match(uri)){

            URI_CODE -> count = db.delete("student_details","studentId = ?",selectionArgs)
            else -> throw java.lang.IllegalArgumentException("Unsupported URI : $uri")
        }

        return count
    }


    override fun getType(uri: Uri): String? {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/

        when (uriMatcher.match(uri)) {

            URI_CODE -> return "com.qi.contentprovider.StudentsProvider/students"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")

        }
    }

}
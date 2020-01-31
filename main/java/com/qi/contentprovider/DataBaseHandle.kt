package com.qi.contentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.qi.contentprovider.StudentProvider.Companion.CREATE_TABLE_STUDENT

class DataBaseHandle(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int) : SQLiteOpenHelper(context,name,factory,version){

    override fun onCreate(db: SQLiteDatabase?) {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/
        try {

            db?.execSQL(CREATE_TABLE_STUDENT)

        }catch (ex : Exception){
            Log.e("Exception","$ex")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/

        db?.execSQL("DROP TABLE IF EXISTS student_details")
        onCreate(db)
    }


}
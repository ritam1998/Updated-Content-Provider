package com.qi.responseapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_listview_student.view.*

class StudentAdapter(val studentList: ArrayList<StudentModel>,val studentcallback : StudentCallback) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.StudentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_listview_student, parent, false)

        return StudentViewHolder(view,studentcallback)
    }

    override fun getItemCount(): Int {

        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentAdapter.StudentViewHolder, position: Int) {

        holder.bind(studentList[position])
        Log.e("Id:",""+studentList[position].studentId)

        holder.deleteButton?.setOnClickListener {
            studentcallback.onclickstudent(studentModel = studentList[position],position = position)
        }
    }

    class StudentViewHolder(val itview: View,var studentcallback: StudentCallback) : RecyclerView.ViewHolder(itview) {

        private val studentName = itview.studentname
        private val studentPhone = itview.studentphone

        val deleteButton = itview.deleteMember

        val updateButton = itview.updateStudent

        fun bind(studentModel: StudentModel) {

            studentName.text = studentModel.studentname
            studentPhone.text = studentModel.studentphone

            //studentModel.userphone = studentPhone.text.toString()

            updateButton.setOnClickListener {
                studentcallback.onUpdateStudent(studentModel)
            }
        }
    }

}

interface StudentCallback {
    fun onclickstudent(studentModel: StudentModel,position : Int)
    fun onUpdateStudent(studentModel: StudentModel)
}

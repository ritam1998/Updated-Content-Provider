package com.qi.contentprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(val studentList: List<StudentModel>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentAdapter.StudentViewHolder {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_listview_student, parent, false)

        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/

        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentAdapter.StudentViewHolder, position: Int) {

        /*TODO("not implemented") //To change body of created functions use File | Settings | File Templates.*/


        holder.bind(studentList[position])
    }

    class StudentViewHolder(val itview: View) : RecyclerView.ViewHolder(itview) {

        private val studentName = itview.findViewById(R.id.studentname) as TextView
        private val studentPhone = itview.findViewById(R.id.studentphone) as TextView

        fun bind(studentModel: StudentModel) {

            studentName.text = studentModel.username
            studentPhone.text = studentModel.userphone
            //studentModel.userphone = studentPhone.text.toString()
        }
    }

}
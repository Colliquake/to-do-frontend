package com.example.to_do_frontend.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_frontend.R
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.viewmodel.TaskListViewModel

class TaskListAdapter(val viewModel: TaskListViewModel, val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {
    
    private var tasksList: ArrayList<TaskModel> = arrayListOf<TaskModel>(
        TaskModel(
            _id = "_id",
            id = "id",
            taskDescription = "taskDesc",
            createdDate = "date",
            dueDate = "due date",
            completed = false
        ),
    )
    
    init {
        val tasksObserver = Observer<ArrayList<TaskModel>> { newTasks ->
            tasksList = newTasks
        }
        viewModel.tasksLiveData.observe(lifecycleOwner, tasksObserver)
    }
    
    class TaskListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var task_edit_button = view.findViewById<ImageButton>(R.id.task_edit_button)
        var task_description = view.findViewById<TextView>(R.id.task_description)
        var task_due_date = view.findViewById<TextView>(R.id.task_due_date)
        var task_created_date = view.findViewById<TextView>(R.id.task_created_date)
        var task_check_box = view.findViewById<CheckBox>(R.id.task_check_box)
        var task_delete_button = view.findViewById<ImageButton>(R.id.task_delete_button)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_task_list, parent, false)
        return TaskListViewHolder(layout)
    }
    
    override fun getItemCount(): Int {
        return tasksList.size
    }
    
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val tasksItem = tasksList[position]
        holder.task_description.text = tasksItem.taskDescription
        holder.task_due_date.text = tasksItem.dueDate
        holder.task_created_date.text = tasksItem.createdDate
    }
}
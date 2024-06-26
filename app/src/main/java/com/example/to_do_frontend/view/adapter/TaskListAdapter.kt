package com.example.to_do_frontend.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_frontend.R
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.view.OnClickedChangeListener
import java.time.Instant


class TaskListAdapter(var newTasks: ArrayList<TaskModel>, val listener: OnClickedChangeListener) :
    RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder>() {
    
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
        return newTasks.size
    }
    
    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val tasksItem = newTasks[position]
        
        holder.task_description.text = tasksItem.taskDescription
        
        val dueString = FormatDueDate(Instant.parse(tasksItem.dueDate))
        holder.task_due_date.text = dueString
        
        val createdString = FormatCreatedDate(Instant.parse(tasksItem.createdDate))
        holder.task_created_date.text = createdString
        
        if(tasksItem.completed){
            holder.task_check_box.isChecked = true
        }
        
        holder.task_check_box.setOnClickListener(){
            listener.onItemCheckedChange(tasksItem)
        }
        
        holder.task_delete_button.setOnClickListener(){
            listener.onDeleteClickedChange(tasksItem.id)
            newTasks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, newTasks.size)
        }
        
        holder.task_edit_button.setOnClickListener(){
            listener.onEditClickedChange(tasksItem.id)
        }
    }
}
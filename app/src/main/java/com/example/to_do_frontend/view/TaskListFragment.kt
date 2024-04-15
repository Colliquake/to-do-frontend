package com.example.to_do_frontend.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_frontend.R
import com.example.to_do_frontend.databinding.FragmentTaskListBinding
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.model.TaskParameters
import com.example.to_do_frontend.model.TaskParametersRepository
import com.example.to_do_frontend.model.dataStore
import com.example.to_do_frontend.view.adapter.TaskListAdapter
import com.example.to_do_frontend.viewmodel.TaskListViewModel
import com.example.to_do_frontend.viewmodel.TaskListViewModelFactory

//private val Context.dataStore by preferencesDataStore(
//    name = "settings"
//)

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TaskListViewModel by lazy {
        ViewModelProvider(
        this,
        TaskListViewModelFactory(
            requireActivity().application, TaskParametersRepository(requireActivity().application.dataStore)
        )
    ).get(TaskListViewModel::class.java)
    }
    
    
    private var tasksList: ArrayList<TaskModel> = arrayListOf<TaskModel>(
        TaskModel(
            _id = "_id",
            id = "id",
            taskDescription = "taskDesc",
            createdDate = "date",
            dueDate = "due date",
            completed = false
        )
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val tasksObserver = Observer<ArrayList<TaskModel>> { newTasks ->
            val adapter = TaskListAdapter(newTasks)
            val recyclerView = binding.tasksListRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
        
        viewModel.tasksLiveData.observe(this, tasksObserver)
        
        val tasksParamsObserver = Observer<TaskParameters>{
            Log.v("task params changed", it.toString())
            viewModel.changeTasks(it)
        }
        
        viewModel.tasksParams.observe(this, tasksParamsObserver)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.headerSettingsButton.setOnClickListener { _ ->
            view.findNavController().navigate(R.id.action_taskListFragment_to_settingsFragment)
        }
        binding.headerAddButton.setOnClickListener {        //todo: get rid of this (this was just for "testing")
            viewModel.changeFilter("false")
        }
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
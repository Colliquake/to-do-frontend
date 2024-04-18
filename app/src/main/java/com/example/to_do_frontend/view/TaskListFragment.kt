package com.example.to_do_frontend.view

import android.os.Bundle
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

class TaskListFragment : Fragment(), OnClickedChangeListener {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TaskListViewModel by lazy {
        ViewModelProvider(
            this,
            TaskListViewModelFactory(
                requireActivity().application,
                TaskParametersRepository(requireActivity().application.dataStore)
            )
        ).get(TaskListViewModel::class.java)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tasksObserver = Observer<ArrayList<TaskModel>> { newTasks ->
            val adapter = TaskListAdapter(newTasks, this)
            val recyclerView = binding.tasksListRecyclerView
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
        viewModel.tasksLiveData.observe(this, tasksObserver)
        
        val tasksParamsObserver = Observer<TaskParameters> {
            viewModel.changeTasks(it)
        }
        viewModel.tasksParams.observe(this, tasksParamsObserver)
        
        binding.headerSettingsButton.setOnClickListener { _ ->
            view.findNavController().navigate(R.id.action_taskListFragment_to_settingsFragment)
        }
        binding.headerAddButton.setOnClickListener { _ ->
            view.findNavController().navigate(R.id.action_taskListFragment_to_createFragment)
        }
        
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    override fun onItemCheckedChange(task: TaskModel) {
        task.completed = !task.completed
        viewModel.updateTask(task)
    }
    
    override fun onDeleteClickedChange(taskId: String) {
        viewModel.deleteTask(taskId)
    }
    
    override fun onEditClickedChange(taskId: String) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToEditFragment(taskId)
        view?.findNavController()?.navigate(action)
    }
}
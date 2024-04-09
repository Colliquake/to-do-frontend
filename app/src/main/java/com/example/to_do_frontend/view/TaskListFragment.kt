package com.example.to_do_frontend.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.to_do_frontend.databinding.FragmentTaskListBinding
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.viewmodel.TaskListViewModel

class TaskListFragment : Fragment() {
    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TaskListViewModel by lazy {
        ViewModelProvider(this).get(TaskListViewModel::class.java)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val tasksObserver = Observer<ArrayList<TaskModel>> {newTasks ->
            if(newTasks.size != 0){
                binding.mainTextView.text = newTasks[0].id
            }
        }
        viewModel.tasksLiveData.observe(this, tasksObserver)
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
    
    //todo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.changeTextButton.setOnClickListener {
             viewModel.changeText()
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
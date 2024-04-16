package com.example.to_do_frontend.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.to_do_frontend.R
import com.example.to_do_frontend.databinding.FragmentCreateBinding
import com.example.to_do_frontend.viewmodel.CreateViewModel
import com.example.to_do_frontend.viewmodel.CreateViewModelFactory

class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: CreateViewModel by lazy {
        ViewModelProvider(
            this,
            CreateViewModelFactory(
                requireActivity().application
            )
        ).get(CreateViewModel::class.java)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener { _ ->
            val taskDescription = binding.toDoItemNameInput.text.toString()
            val dueDate = viewModel.formatDueDate(binding.selectDueDateInput.text.toString())
            viewModel.createTask(taskDescription, dueDate, false)
            view.findNavController().navigate(R.id.action_createFragment_to_taskListFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}
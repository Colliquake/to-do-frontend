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
import com.example.to_do_frontend.R
import com.example.to_do_frontend.databinding.FragmentSettingsBinding
import com.example.to_do_frontend.model.TaskParameters
import com.example.to_do_frontend.model.TaskParametersRepository
import com.example.to_do_frontend.model.dataStore
import com.example.to_do_frontend.viewmodel.SettingsViewModel
import com.example.to_do_frontend.viewmodel.SettingsViewModelFactory

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(
            this,
            SettingsViewModelFactory(
                TaskParametersRepository(requireActivity().application.dataStore)
            )
        ).get(SettingsViewModel::class.java)
    }
    
    private lateinit var filtersRadioGroup: String
    private lateinit var sortByRadioGroup: String
    private lateinit var sortDateDirectionRadioGroup: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val paramsObserver = Observer<TaskParameters> {
            filtersRadioGroup = it.filters
            sortByRadioGroup = it.sort_by
            sortDateDirectionRadioGroup = it.sort_date_direction
            
            when(filtersRadioGroup){
                "all" -> binding.filtersRadioGroup.check(R.id.all_button)
                "true" -> binding.filtersRadioGroup.check(R.id.complete_button)
                "false" -> binding.filtersRadioGroup.check(R.id.incomplete_button)
                else -> {
                    binding.filtersRadioGroup.check(R.id.all_button)
                    Log.w("filtersRadioGroup", "no value found")
                }
            }
            when(sortByRadioGroup){
                "dueDate" -> binding.sortByRadioGroup.check(R.id.due_button)
                "createdDate" -> binding.sortByRadioGroup.check(R.id.created_button)
                else -> {
                    binding.sortByRadioGroup.check(R.id.due_button)
                    Log.v("sortByRadioGroup", "no value found")
                }
            }
            when(sortDateDirectionRadioGroup){
                "+" -> binding.sortDateDirectionRadioGroup.check(R.id.ascending_button)
                "-" -> binding.sortDateDirectionRadioGroup.check(R.id.descending_button)
                else -> {
                    binding.sortDateDirectionRadioGroup.check(R.id.ascending_button)
                    Log.v("directionRadioGroup", "no value found")
                }
            }
        }
        viewModel.tasksParams.observe(this, paramsObserver)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.saveButton.setOnClickListener { _ ->
            val filtersVal = when(binding.filtersRadioGroup.checkedRadioButtonId){
                R.id.all_button -> "all"
                R.id.complete_button -> "true"
                R.id.incomplete_button -> "false"
                else -> "all"
            }
            viewModel.changeFilter(filtersVal)
            
            val sortByVal = when(binding.sortByRadioGroup.checkedRadioButtonId){
                R.id.due_button -> "dueDate"
                R.id.created_button -> "createdDate"
                else -> ""
            }
            viewModel.changeSortBy(sortByVal)
            
            val sortDateDirectionVal = when(binding.sortDateDirectionRadioGroup.checkedRadioButtonId){
                R.id.ascending_button -> "+"
                R.id.descending_button -> "-"
                else -> "+"
            }
            viewModel.changeSortDateDirection(sortDateDirectionVal)
            
            view.findNavController().navigate(R.id.action_settingsFragment_to_taskListFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
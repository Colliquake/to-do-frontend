package com.example.to_do_frontend.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.to_do_frontend.R
import com.example.to_do_frontend.databinding.FragmentCreateBinding
import com.example.to_do_frontend.viewmodel.CreateViewModel
import com.example.to_do_frontend.viewmodel.CreateViewModelFactory
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

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
    
    //todo: initialize due date selected
    //todo: implement calendar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.isNotEmpty() == true) {
                    binding.toDoItemNameInput.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.bar_background_with_outline,
                        null
                    )
                } else {
                    binding.toDoItemNameInput.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.bar_background, null)
                }
            }
        }
        binding.toDoItemNameInput.addTextChangedListener(textWatcher)
        
        binding.toDoItemNameInput.setOnKeyListener { v, keyCode, event ->
            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.toDoItemNameInput.clearFocus()
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                false
            }
        }
        
        binding.saveButton.setOnClickListener { _ ->
            val taskDescription = binding.toDoItemNameInput.text.toString()
            val dueDate = viewModel.formatDueDate(binding.selectDueDateInput.text.toString())
            viewModel.createTask(taskDescription, dueDate, false)
            view.findNavController().navigate(R.id.action_createFragment_to_taskListFragment)
        }
        
        binding.selectDueDateInput.setOnClickListener {
            showCalendarDialog(binding.selectDueDateInput.text)
        }
        
        
        super.onViewCreated(view, savedInstanceState)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun showCalendarDialog(date: CharSequence) {
        val builder = AlertDialog.Builder(context)
        val dialogView = layoutInflater.inflate(R.layout.dialog_calendar_view, null)
        val calendarView = dialogView.findViewById<CalendarView>(R.id.calendar_view)
        
        if (date != "") {
            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss")
            val zdt = ZonedDateTime.parse(
                date.toString() + " 00:00:00",
                formatter.withZone(ZoneId.systemDefault())
            )
            calendarView.setDate(zdt.toInstant().toEpochMilli())
        } else {
            val currentDay = Instant.now().toEpochMilli()
            calendarView.setDate(currentDay)
        }
        
        calendarView.setOnDateChangeListener { view, year, month, day ->
            val cal = Calendar.getInstance()
            cal.set(year, month, day)
            
            val monthName = DateFormat.format("MMMM", cal)
            binding.selectDueDateInput.text = "$monthName $day, $year"
        }
        
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.show()
    }
    
}
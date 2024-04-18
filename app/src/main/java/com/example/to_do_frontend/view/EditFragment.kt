package com.example.to_do_frontend.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.to_do_frontend.R
import com.example.to_do_frontend.databinding.FragmentEditBinding
import com.example.to_do_frontend.model.TaskModel
import com.example.to_do_frontend.viewmodel.EditViewModel
import com.example.to_do_frontend.viewmodel.EditViewModelFactory
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: EditViewModel by lazy {
        ViewModelProvider(
            this,
            EditViewModelFactory(
                requireActivity().application
            )
        ).get(EditViewModel::class.java)
    }
    
    private lateinit var taskId: String
    
    private lateinit var task: TaskModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            taskId = it.getString("taskId").toString()
        }
        
//        binding.toDoItemNameInput.text =
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val taskObserver = Observer<ArrayList<TaskModel>> {newTask ->
            task = newTask[0]
            view.findViewById<EditText>(R.id.to_do_item_name_input).setText(task.taskDescription)
            
            val date = viewModel.getDateFromInstant(Instant.parse(task.dueDate))
            view.findViewById<TextView>(R.id.select_due_date_input).setText(date)
        }
        viewModel.task.observe(this, taskObserver)
        viewModel.getTask(taskId)
        
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
        
        binding.selectDueDateInput.setOnClickListener{
            showCalendarDialog(binding.selectDueDateInput.text)
        }
        
        binding.saveButton.setOnClickListener { _ ->
            val taskDescription = binding.toDoItemNameInput.text.toString()
            val dueDate = viewModel.formatDueDate(binding.selectDueDateInput.text.toString())
            viewModel.updateTask(TaskModel(
                task._id,
                task.id,
                taskDescription,
                task.createdDate,
                dueDate,
                task.completed
            ))
            view.findNavController().navigate(R.id.action_editFragment_to_taskListFragment)
        }
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
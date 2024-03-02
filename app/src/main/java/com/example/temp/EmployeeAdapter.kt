package com.example.temp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.temp.databinding.ActivityDashboardBinding
import com.example.temp.databinding.ActivityEmployeeInfoBinding

class EmployeesAdapter(private var employees: List<EmployeeModel>) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    private lateinit var binding: ActivityEmployeeInfoBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityEmployeeInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(employees[position], position)
    }

    fun updateEmployeesList(newList: List<EmployeeModel>) {
        employees = newList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = employees.size

    inner class ViewHolder(private val binding: ActivityEmployeeInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: EmployeeModel, position: Int) {
            binding.apply {
               binding.textView20.text = employee.empId.toString()
                binding.textView21.text = employee.name
                textView22.text = employee.dob
                textView24.text = employee.role
                binding.textView23.text=employee.empId.toString()

                // Adjust layout constraints based on position
                val layoutParams = viewCard.layoutParams as ConstraintLayout.LayoutParams
                if (position % 2 == 0) {
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.endToEnd = -1
                } else {
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    layoutParams.startToStart = -1
                }
                viewCard.layoutParams = layoutParams
            }
        }
    }
}
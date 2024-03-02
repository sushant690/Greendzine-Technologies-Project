package com.example.temp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.temp.databinding.ActivityDashboardBinding
import com.example.temp.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONException

class DashboardActivity : AppCompatActivity() {
   private lateinit var binding: ActivityDashboardBinding
    lateinit var adapter: EmployeesAdapter
    private var originalEmployeeList = listOf<EmployeeModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding=ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView4.setOnClickListener {
            binding.imageView4.setBackgroundResource(R.drawable.box_shadow)
            binding.imageView5.background=null
           binding.productivityDashboard.visibility=View.VISIBLE
            binding.empInfo.visibility=View.GONE
        }
        binding.imageView5.setOnClickListener {
            binding.imageView5.setBackgroundResource(R.drawable.box_shadow)
            binding.imageView4.background=null
            binding.productivityDashboard.visibility=View.GONE
            binding.empInfo.visibility=View.VISIBLE
        }
        val jsonString = JsonHelper.loadJsonFromAsset(this, "employees.json")
        val employees = jsonString?.let { JsonHelper.parseEmployeesJson(it) }?: emptyList()


        originalEmployeeList = employees.toList()
         adapter = EmployeesAdapter(originalEmployeeList)
        binding.viewRecycler.layoutManager = LinearLayoutManager(this)
        binding.viewRecycler.adapter = adapter
        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    searchEmployees(s.toString())
                } else {
                    adapter.updateEmployeesList(originalEmployeeList)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        }
    override fun onBackPressed() {
        // Display a toast message
        Toast.makeText(this, "App closed", Toast.LENGTH_SHORT).show()

        // Close the app
        finishAffinity()
    }
    private fun searchEmployees(query: String) {
        val filteredList = originalEmployeeList.filter { employee:EmployeeModel->
            employee.name.startsWith(query, ignoreCase = true)
        }
        adapter.updateEmployeesList(filteredList)
    }

    }
    object JsonHelper {
        fun loadJsonFromAsset(context: Context, fileName: String): String? {
            val json: String?
            try {
                val inputStream = context.assets.open("employee.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charsets.UTF_8)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            return json
        }

        fun parseEmployeesJson(jsonString: String): ArrayList<EmployeeModel> {
            val employees = ArrayList<EmployeeModel>()
            try {
                val jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val empId = jsonObject.getInt("EMP Id")
                    val name = jsonObject.getString("Name")
                    val dob = jsonObject.getString("DOB")
                    val role = jsonObject.getString("Role")
                    val employee = EmployeeModel(empId, name, dob, role)
                    employees.add(employee)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return employees
        }

    }


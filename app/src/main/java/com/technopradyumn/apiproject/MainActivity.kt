package com.technopradyumn.apiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.technopradyumn.apiproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val url = "https://api.github.com/users"

    private val userInfoItemList = arrayListOf<userInfoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.recyclerView

        val adapter = Adapter(this, userInfoItemList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            val gson = Gson()
            val userInfoArray = gson.fromJson(response, Array<userInfoItem>::class.java)
            userInfoItemList.addAll(userInfoArray)

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged()
        }, { error ->
            // Handle error if needed
        })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}

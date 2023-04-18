package com.example.demott.TASK

import android.app.appsearch.observer.ObserverCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demott.Modal.TimelineInfo
import com.example.demott.R
import com.example.demott.Utils.CommonUtils
import com.example.demott.ViewModel.DemoViewModal
import com.example.demott.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {
    lateinit var binding : ActivityTaskBinding
    lateinit var adapter : DataAdapter
    lateinit var listItemInfo : List<TimelineInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DemoViewModal().demoapi(this).observe(this) {
            if (it.status==200){
                listItemInfo = it.data.timelineInfo
                adapter = DataAdapter(listItemInfo)
                binding.recycler.adapter = adapter
                CommonUtils.xToast(this,"done")
            }
            else{
                CommonUtils.xToast(this," not done")
            }
        }

    }
}
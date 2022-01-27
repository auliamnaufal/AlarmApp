package com.auliamnaufal.smartalarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.auliamnaufal.smartalarm.adapter.AlarmAdapter
import com.auliamnaufal.smartalarm.data.Alarm
import com.auliamnaufal.smartalarm.data.local.AlarmDB
import com.auliamnaufal.smartalarm.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var alarmAdapter: AlarmAdapter? = null

    private val db by lazy { AlarmDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        initTimeToday()
//        initDateToday()
        initView()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            val alarm = db.alarmDao().getAlarm()
            alarmAdapter?.setData(alarm)
            Log.i("GetAlarm", "SetupRecylerView: With this data $alarm")
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            alarmAdapter = AlarmAdapter()
            rvReminderAlarm.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = alarmAdapter
            }
        }
    }

    private fun initView() {
        binding.apply {
            cvSetOneTimeAlarm.setOnClickListener {
                startActivity(Intent(this@MainActivity, OneTimeAlarmActivity::class.java))
            }

            cvSetRepeatingAlarm.setOnClickListener {
                startActivity(Intent(this@MainActivity, RepeatingAlarmActivity::class.java))
            }
        }
    }

    // Di comment karena pakai textclock
    /*private fun initDateToday() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        binding.tvDateToday.text = formattedDate
    }

    private fun initTimeToday() {
        // calendar untuk mendapatkan segala hal yang berhubungan dengan waktu di android
        val calendar = Calendar.getInstance()

        // menentukan format jam yang akan digunakan, e.g. 13.36 atau 01.36 pm
        val timeFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        val formattedTime = timeFormat.format(calendar.time)
        binding.tvTimeToday.text = formattedTime
    }*/


}
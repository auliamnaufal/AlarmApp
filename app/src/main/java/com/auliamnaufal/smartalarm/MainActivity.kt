package com.auliamnaufal.smartalarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auliamnaufal.smartalarm.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        initTimeToday()
//        initDateToday()
        initView()
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
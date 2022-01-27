package com.auliamnaufal.smartalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auliamnaufal.smartalarm.databinding.ActivityRepeatingAlarmBinding
import com.auliamnaufal.smartalarm.fragment.TimeDialogFragment
import com.auliamnaufal.smartalarm.helper.timeFormatter

class RepeatingAlarmActivity : AppCompatActivity(), TimeDialogFragment.TimeDialogListener {

    private var _binding: ActivityRepeatingAlarmBinding? = null
    private val binding get() = _binding as ActivityRepeatingAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRepeatingAlarmBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            btnSetTimeRepeating.setOnClickListener {
                val timePickerFragment = TimeDialogFragment()
                timePickerFragment.show(supportFragmentManager, "TimePickerDialog")
            }
        }
    }

    override fun onTimeSetListener(tag: String?, hour: Int, minute: Int) {
        binding.tvRepeating.text = timeFormatter(hour, minute)
    }
}
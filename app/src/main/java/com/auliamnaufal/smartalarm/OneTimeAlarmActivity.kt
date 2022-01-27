package com.auliamnaufal.smartalarm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auliamnaufal.smartalarm.data.Alarm
import com.auliamnaufal.smartalarm.data.local.AlarmDB
import com.auliamnaufal.smartalarm.databinding.ActivityOneTimeAlarmBinding
import com.auliamnaufal.smartalarm.fragment.DateDialogFragment
import com.auliamnaufal.smartalarm.fragment.TimeDialogFragment
import com.auliamnaufal.smartalarm.helper.timeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OneTimeAlarmActivity : AppCompatActivity(), DateDialogFragment.DialogDateSetListener,
    TimeDialogFragment.TimeDialogListener {

    private var _binding: ActivityOneTimeAlarmBinding? = null
    private val binding get() = _binding as ActivityOneTimeAlarmBinding

    private val db by lazy { AlarmDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityOneTimeAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            btnSetDateOneTime.setOnClickListener {
                val datePickerFragment = DateDialogFragment()
                datePickerFragment.show(supportFragmentManager, "DatePickerDialog")
            }

            btnSetTimeOneTime.setOnClickListener {
                val timePickerFragment = TimeDialogFragment()
                timePickerFragment.show(supportFragmentManager, "TimePickerDialog")
            }

            btnAdd.setOnClickListener {
                val date = tvOnceDate.text.toString()
                val time = tvRepeating.text.toString()
                val message = edtNoteOneTime.text.toString()

                if (date == "Date" && time == "Time") {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.txt_toast_add_alarm),
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    CoroutineScope(Dispatchers.IO).launch {
                        db.alarmDao().addAlarm(
                            Alarm(
                                0,
                                date,
                                time,
                                message
                            )
                        )
                        Log.i("AddAlarm", "Alarm set on: $date $time with message $message")
                        finish()
                    }
                }


            }
        }

    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()

        // untuk menyetal/menentukan calendar menjadi waktu yang ditentukan
        calendar.set(year, month, dayOfMonth)
        val dateFormatted = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        binding.tvOnceDate.text = dateFormatted.format(calendar.time)
    }

    override fun onTimeSetListener(tag: String?, hour: Int, minute: Int) {

        binding.tvRepeating.text = timeFormatter(hour, minute)
    }
}
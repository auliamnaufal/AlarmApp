package com.auliamnaufal.smartalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.auliamnaufal.smartalarm.data.Alarm
import com.auliamnaufal.smartalarm.data.local.AlarmDB
import com.auliamnaufal.smartalarm.databinding.ActivityRepeatingAlarmBinding
import com.auliamnaufal.smartalarm.fragment.TimeDialogFragment
import com.auliamnaufal.smartalarm.helper.timeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepeatingAlarmActivity : AppCompatActivity(), TimeDialogFragment.TimeDialogListener {

    private var _binding: ActivityRepeatingAlarmBinding? = null
    private val binding get() = _binding as ActivityRepeatingAlarmBinding

    private val db by lazy { AlarmDB(this) }

    private var alarmService: AlarmReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRepeatingAlarmBinding.inflate(layoutInflater)
        alarmService = AlarmReceiver()

        setContentView(binding.root)

        binding.apply {
            btnSetTimeRepeating.setOnClickListener {
                val timePickerFragment = TimeDialogFragment()
                timePickerFragment.show(supportFragmentManager, "TimePickerDialog")
            }

            btnAdd.setOnClickListener {
                val time = tvRepeating.text.toString()
                val message = edtNoteRepeating.text.toString()

                if (time == "Time") {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.txt_toast_add_alarm),
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    alarmService?.setRepeatingAlarm(
                        applicationContext,
                        AlarmReceiver.TYPE_REPEATING,
                        time,
                        message
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        db.alarmDao().addAlarm(
                            Alarm(
                                0,
                                "Repeating Alarm",
                                time,
                                message,
                                AlarmReceiver.TYPE_REPEATING
                            )
                        )
                        Log.i("AddAlarm", "Alarm set on: $time with message $message")
                        finish()
                    }
                }
            }

            btnCancel.setOnClickListener {
                finish()
            }
        }
    }

    override fun onTimeSetListener(tag: String?, hour: Int, minute: Int) {
        binding.tvRepeating.text = timeFormatter(hour, minute)
    }
}
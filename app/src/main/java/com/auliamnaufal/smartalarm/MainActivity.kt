package com.auliamnaufal.smartalarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        db.alarmDao().getAlarm().observe(this) {
            alarmAdapter?.setData(it)
            Log.i("GetAlarm", "SetupRecylerView: With this data $it")
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            val alarm = db.alarmDao().getAlarm()
//            withContext(Dispatchers.Main) {
//                alarmAdapter?.setData(alarm)
//            }
//        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            alarmAdapter = AlarmAdapter()
            rvReminderAlarm.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = alarmAdapter
            }

            swipeToDelete(rvReminderAlarm)
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

    private fun swipeToDelete(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = alarmAdapter?.listAlarm?.get(viewHolder.adapterPosition)

                CoroutineScope(Dispatchers.IO).launch {
                    deletedItem?.let { db.alarmDao().deleteAlarm(it) }
                    Log.i("onSwipe", "OnSwipe: Alarm item successfully deleted")
                }
                Toast.makeText(applicationContext, "Item Deleted", Toast.LENGTH_SHORT).show()

//                alarmAdapter?.notifyItemRemoved(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(recyclerView)
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
package com.example.a7minutesworkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbHistoryActivity)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding.tbHistoryActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompleteDates(dao)
    }


    private fun getAllCompleteDates(historyDao: HistoryDao) {

        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { getAllCompleteDatesList ->
                if ( getAllCompleteDatesList.isNotEmpty()){

                    binding.tvHistory.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNoDataAvailable.visibility =View.INVISIBLE

                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = ArrayList<String>()
                    for (date in getAllCompleteDatesList){
                        dates.add(date.date)
                    }

                    val historyAdapter = HistoryAdapter(dates)

                    binding.rvHistory.adapter = historyAdapter


                }else {
                    binding.tvHistory.visibility = View.GONE
                    binding.rvHistory.visibility = View.GONE
                    binding.tvNoDataAvailable.visibility =View.VISIBLE

                }

            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
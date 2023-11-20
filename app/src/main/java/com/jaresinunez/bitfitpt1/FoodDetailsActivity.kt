package com.jaresinunez.bitfitpt1

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.jaresinunez.bitfitpt1.Constants.itemAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FoodDetailsActivity"

class FoodDetailsActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        val foodNameEditText: EditText = findViewById(R.id.food_name)
        val caloriesEditText: EditText = findViewById(R.id.calories)
        val recordButton: Button = findViewById(R.id.record_food)

        recordButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val foodDao = AppDatabase.getInstance(applicationContext).foodItemDao()

                val foodItem = FoodEntity(
                    name = foodNameEditText.text.toString(),
                    calories = caloriesEditText.text.toString()
                )
                foodDao.insertAll(foodItem)
                Log.d("DB ADDITION", (application as ItemApplication).db.toString())
            }
            itemAdapter.notifyDataSetChanged()

            this.finish()
        }
    }
}
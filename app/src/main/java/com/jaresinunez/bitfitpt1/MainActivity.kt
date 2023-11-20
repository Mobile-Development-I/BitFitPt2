package com.jaresinunez.bitfitpt1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaresinunez.bitfitpt1.Constants.itemAdapter
import com.jaresinunez.bitfitpt1.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private lateinit var foodItemsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val foods = mutableListOf<DisplayItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val addFoodButton = findViewById<Button>(R.id.add_food_button)

        foodItemsRecyclerView = findViewById(R.id.items)
        itemAdapter = ItemAdapter(this, foods)
        foodItemsRecyclerView.adapter = itemAdapter

        lifecycleScope.launch {
            (application as ItemApplication).db.foodItemDao().getAll().collect { databaseList ->
                val mappedList = databaseList.map { entity ->
                    DisplayItem(
                        entity.name,
                        entity.calories
                    )
                }

                withContext(Dispatchers.Main) {
                    foods.clear()
                    foods.addAll(mappedList)
                    itemAdapter.notifyDataSetChanged()
                }
            }
        }

        foodItemsRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            foodItemsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        addFoodButton.setOnClickListener {
            val intent = Intent(this, FoodDetailsActivity::class.java)
            this.startActivity(intent)
        }
    }
}
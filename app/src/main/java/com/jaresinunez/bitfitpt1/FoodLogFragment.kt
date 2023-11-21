package com.jaresinunez.bitfitpt1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaresinunez.bitfitpt1.Constants.itemAdapter
import com.jaresinunez.bitfitpt1.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FoodLogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodLogFragment : Fragment() {
    private lateinit var foodItemsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val foods = mutableListOf<DisplayItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_food_log, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        foodItemsRecyclerView = view.findViewById(R.id.food_recycler_view)
        foodItemsRecyclerView.layoutManager = layoutManager
        foodItemsRecyclerView.setHasFixedSize(true)
        itemAdapter = ItemAdapter(
            view.context,
            foods
        )
        foodItemsRecyclerView.adapter = itemAdapter

        lifecycleScope.launch {
            (context?.applicationContext as ItemApplication).db.foodItemDao().getAll()
                .collect { databaseList ->
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

        foodItemsRecyclerView.layoutManager = LinearLayoutManager(activity).also {
            val dividerItemDecoration = DividerItemDecoration(activity, it.orientation)
            foodItemsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val addFoodButton = view.findViewById<Button>(R.id.add_food_button)

        addFoodButton.setOnClickListener {
            val intent = Intent(activity, FoodDetailsActivity::class.java)
            startActivity(intent)
        }

        // Update the return statement to return the inflated view from above
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
    }
    companion object {
        fun newInstance() : FoodLogFragment {
            return FoodLogFragment()
        }
    }
}
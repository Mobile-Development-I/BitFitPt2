package com.jaresinunez.bitfitpt1

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    var averageCals by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val addFoodButton = view.findViewById<Button>(R.id.add_food_button)
        val avgCals = view.findViewById<TextView>(R.id.average_calories)
        val minCals = view.findViewById<TextView>(R.id.minimum_serving)
        val maxCals = view.findViewById<TextView>(R.id.maximum_serving)

        addFoodButton.setOnClickListener {
            val intent = Intent(activity, FoodDetailsActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val avgCalsFlow = (context?.applicationContext as ItemApplication).db.foodItemDao().getAverageCals()
            val minCalsFlow = (context?.applicationContext as ItemApplication).db.foodItemDao().getMinCals()
            val maxCalsFlow = (context?.applicationContext as ItemApplication).db.foodItemDao().getMaxCals()

            launch {
                avgCalsFlow.collect { avg ->
                    avgCals.text = avg.toString()
                }
            }

            launch {
                minCalsFlow.collect { min ->
                    minCals.text = min.toString()
                }
            }

            launch {
                maxCalsFlow.collect { max ->
                    maxCals.text = max.toString()
                }
            }
        }

        return view
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}
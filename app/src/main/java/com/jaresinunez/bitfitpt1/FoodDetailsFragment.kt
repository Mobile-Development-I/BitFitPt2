package com.jaresinunez.bitfitpt1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [FoodDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_details, container, false)

        val foodNameEditText: EditText = view.findViewById(R.id.food_name)
        val caloriesEditText: EditText = view.findViewById(R.id.calories)
        val recordButton: Button = view.findViewById(R.id.record_food)

        recordButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                val foodItem = FoodEntity(
                    name = foodNameEditText.text.toString(),
                    calories = caloriesEditText.text.toString()
                )
                context?.let { it1 -> AppDatabase.getInstance(it1).foodItemDao() }
                    ?.insertAll(foodItem)
                Log.d("DB ADDITION", (context as ItemApplication).db.toString())
            }
            Constants.itemAdapter.notifyDataSetChanged()

            getActivity()?.getFragmentManager()?.popBackStack();
        }

        return view
    }

    companion object {
        fun newInstance(): FoodDetailsFragment {
            return FoodDetailsFragment()
        }
    }
}
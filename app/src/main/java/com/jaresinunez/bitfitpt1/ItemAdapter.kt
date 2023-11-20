package com.jaresinunez.bitfitpt1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val ITEM_EXTRA = "ITEM_EXTRA"
private const val TAG = "ItemAdapter"

class ItemAdapter(private val context: Context, private val items: List<DisplayItem>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val article = items[position]
        holder.bind(article)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val foodNameTextView = itemView.findViewById<TextView>(R.id.food_name)
        private val caloriesTextView = itemView.findViewById<TextView>(R.id.calories)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(article: DisplayItem) {
            foodNameTextView.text = article.foodName
            caloriesTextView.text = article.calories

            /*
            Glide.with(context)
                .load(article.mediaImageUrl)
                .into(mediaImageView)
             */
        }


        override fun onClick(v: View?) {
            /*
            // TODO: Get selected article
            val foodItem = items[absoluteAdapterPosition]

            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA, article)
            context.startActivity(intent)
            */
        }
    }
}
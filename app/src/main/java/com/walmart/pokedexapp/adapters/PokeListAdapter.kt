package com.walmart.pokedexapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walmart.pokedexapp.activities.DetailActivity
import com.walmart.pokedexapp.activities.DetailActivity.Companion.POKE_NAME
import com.walmart.pokedexapp.databinding.PokeItemBinding
import com.walmart.pokedexapp.helper.capitalizeFirst
import com.walmart.pokedexapp.helper.prettyPrintNo
import com.walmart.pokedexapp.repository.entities.Response

class PokeListAdapter: ListAdapter<Response.PokeItem, PokeListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback: DiffUtil.ItemCallback<Response.PokeItem>() {
        override fun areItemsTheSame(
            oldItem: Response.PokeItem, newItem: Response.PokeItem
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: Response.PokeItem, newItem: Response.PokeItem
        ): Boolean {
            return oldItem.name == newItem.name && oldItem.number == newItem.number
        }
    }

    class ViewHolder(private val binding: PokeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: Response.PokeItem) {
            binding.number.text = item.number.prettyPrintNo()
            binding.name.text = item.name.capitalizeFirst()

            binding.root.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(POKE_NAME, item.name)
                }
                context.startActivity(intent)
            }
        }
    }
}
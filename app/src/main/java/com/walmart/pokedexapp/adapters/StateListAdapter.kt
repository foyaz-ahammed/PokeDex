package com.walmart.pokedexapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.walmart.pokedexapp.databinding.StateItemBinding
import com.walmart.pokedexapp.helper.getString
import com.walmart.pokedexapp.repository.entities.StateItem

/**
 * [ListAdapter] to show poke types
 */
class StateListAdapter: ListAdapter<StateItem, StateListAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(private val binding: StateItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StateItem) {
            binding.name.text = item.short_name
            binding.progressBar.max = item.max.toFloat()
            binding.progressBar.labelText = item.getString()
            binding.progressBar.progress = item.base_stat.toFloat()
            binding.progressBar.colorBackground = binding.root.context.getColor(item.color)
        }
    }

    class DiffCallback: DiffUtil.ItemCallback<StateItem>() {
        override fun areItemsTheSame(oldItem: StateItem, newItem: StateItem)
            = oldItem.short_name == newItem.short_name

        override fun areContentsTheSame(oldItem: StateItem, newItem: StateItem)
            = oldItem.base_stat == newItem.base_stat
    }
}
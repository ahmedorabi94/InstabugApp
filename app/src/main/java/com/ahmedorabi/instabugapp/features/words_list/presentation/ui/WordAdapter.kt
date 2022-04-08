package com.ahmedorabi.instabugapp.features.words_list.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedorabi.instabugapp.data.domain.Word
import com.ahmedorabi.instabugapp.databinding.WordItemBinding

class WordAdapter :
    ListAdapter<Word, WordAdapter.MyViewHolder>(DiffCallback) {


    companion object DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.name == newItem.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }


    class MyViewHolder(private var binding: WordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Word) {
            binding.wordTitle.text = item.name
            binding.wordQuantity.text = "${item.total}"

        }

    }


}
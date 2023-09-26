package com.example.rick_morty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.rick_morty.databinding.SampleLayoutBinding

class MyAdapter :
    PagingDataAdapter<Result, MyAdapter.MyHolder>(MyDiffUtil()) {
    val diffCallback = MyDiffUtil()
    val differ = AsyncListDiffer(this, diffCallback)

    class MyHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = SampleLayoutBinding.bind(itemView)
        fun bind(character: Result) = with(binding) {
            binding.gender.text = character.gender
            binding.name.text = character.name
            binding.type.text = character.type
            binding.status.text = character.status
            binding.species.text = character.species
            Glide.with(itemView)
                .load(character.image)
                .into(binding.avatar)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_layout, parent, false)
        return MyHolder(view)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(differ.currentList[position])


    }
    fun submitData(list: MutableList<Result>){

        differ.submitList(list)
    }


}
package com.example.rick_morty

import androidx.recyclerview.widget.DiffUtil

class MyDiffUtil : DiffUtil.ItemCallback<Result>() {


    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        val (_, status, gender, type, species, image) = oldItem
        val (_, status1, gender1, type1, species1, image1) = newItem
        return (status == status1|| gender == gender1 || type == type1|| species == species1|| image == image1)
    }
}
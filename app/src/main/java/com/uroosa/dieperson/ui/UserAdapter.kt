package com.uroosa.dieperson.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uroosa.dieperson.databinding.UserItemBinding
import com.uroosa.dieperson.models.response.UserResponse

class UserAdapter(private val onUserClicked: (UserResponse) -> Unit) :
    ListAdapter<UserResponse, UserAdapter.UserViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let {
            holder.bind(it)
        }
    }

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserResponse) {
            binding.userName.text = user.name
            binding.userEmail.text = user.email
            binding.userGender.text = user.gender

            binding.root.setOnClickListener {
                Log.e("helllo user" , user.id.toString())
                onUserClicked(user)
            }
        }

    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem == newItem
        }
    }
}
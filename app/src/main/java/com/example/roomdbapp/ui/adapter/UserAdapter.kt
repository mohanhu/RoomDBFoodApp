package com.example.roomdbapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.roomdbapp.R
import com.example.roomdbapp.databinding.ActivityMainBinding
import com.example.roomdbapp.databinding.CarddbrecycleBinding
import com.example.roomdbapp.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolderOne>() {
    private lateinit var passpos: ((User) -> Unit)

    inner class ViewHolderOne(val binding: CarddbrecycleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(data: User) {
            binding.firstname.text = data.first
            binding.lastname.text = data.last
            binding.age.text = data.age.toString()
            binding.address.text=data.address.streetName
            binding.image.load(data.image)
            Glide.with(itemView.context).apply {
                load(R.drawable.doll).apply(RequestOptions.fitCenterTransform() ).into(binding.image)
            }
            binding.addressno.text=data.address.streetNumber.toString()
            binding.card.setOnClickListener {
                val bundle = bundleOf("key" to data)
                Navigation.findNavController(it)
                    .navigate(R.id.action_listFragments3_to_updateFragment2, bundle)
            }
            binding.card.setOnLongClickListener {
                passpos.invoke(data)
                true
            }
        }
    }

    val differcallback = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differcallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {
        return ViewHolderOne(
            binding = CarddbrecycleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {
        holder.bindItem(differ.currentList[position])
    }
    fun delepos(listen:((User)-> Unit)){
        passpos=listen
    }
}
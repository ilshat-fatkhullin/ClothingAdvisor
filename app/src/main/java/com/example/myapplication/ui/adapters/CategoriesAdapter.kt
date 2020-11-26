package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.CategoryItemBinding
import com.example.myapplication.network.CategoryResponseItem

class CategoriesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<CategoryResponseItem, CategoriesAdapter.CategoryViewHolder>(
        DiffCallback
    ) {

    class CategoryViewHolder(private var binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryResponseItem: CategoryResponseItem) {
            binding.property = categoryResponseItem
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CategoryResponseItem>() {
        override fun areItemsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holderCategory: CategoryViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holderCategory.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holderCategory.bind(marsProperty)
    }

    class OnClickListener(val clickListener: (theCatCategoryResponseItem: CategoryResponseItem) -> Unit) {
        fun onClick(theCatCategoryResponseItem: CategoryResponseItem) =
            clickListener(theCatCategoryResponseItem)
    }
}

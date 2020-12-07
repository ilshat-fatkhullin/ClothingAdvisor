package com.group5.clothing_advisor.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.group5.clothing_advisor.databinding.ClothItemBinding
import com.group5.clothing_advisor.data.ClothesResponseItem

class ClothesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ClothesResponseItem, ClothesAdapter.ClothesResponseViewHolder>(
        DiffCallback
    ) {

    class ClothesResponseViewHolder(private var binding: ClothItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theCatSearchResponseItem: ClothesResponseItem) {
            binding.cloth = theCatSearchResponseItem
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ClothesResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ClothesResponseItem,
            newItem: ClothesResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ClothesResponseItem,
            newItem: ClothesResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClothesResponseViewHolder {
        return ClothesResponseViewHolder(
            ClothItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holderSearchResponse: ClothesResponseViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holderSearchResponse.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holderSearchResponse.bind(marsProperty)
    }

    class OnClickListener(val clickListener: (theCatSearchResponseItem: ClothesResponseItem) -> Unit) {
        fun onClick(theCatSearchResponseItem: ClothesResponseItem) =
            clickListener(theCatSearchResponseItem)
    }
}

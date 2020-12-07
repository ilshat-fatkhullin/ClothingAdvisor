package com.group5.clothing_advisor.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.module.AppGlideModule
import com.google.firebase.storage.FirebaseStorage
import com.group5.clothing_advisor.databinding.ClothItemBinding
import com.group5.clothing_advisor.data.ClothResponseItem

class ClothesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ClothResponseItem, ClothesAdapter.ClothesResponseViewHolder>(
        DiffCallback
    ) {

    class ClothesResponseViewHolder(private var binding: ClothItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clothResponseItem: ClothResponseItem) {
            Glide.with(binding.root)
                .load(FirebaseStorage.getInstance().reference.child(clothResponseItem.imgSrcUrl))
                .into(binding.gridImageView)
            binding.cloth = clothResponseItem
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ClothResponseItem>() {
        override fun areItemsTheSame(
            oldItem: ClothResponseItem,
            newItem: ClothResponseItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ClothResponseItem,
            newItem: ClothResponseItem
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

    class OnClickListener(val clickListener: (theCatSearchResponseItem: ClothResponseItem) -> Unit) {
        fun onClick(theCatSearchResponseItem: ClothResponseItem) =
            clickListener(theCatSearchResponseItem)
    }
}

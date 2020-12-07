package com.group5.clothing_advisor.ui

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.group5.clothing_advisor.R
import com.group5.clothing_advisor.data.ApiStatus
import com.group5.clothing_advisor.data.CategoryResponseItem
import com.group5.clothing_advisor.data.ClothesResponseItem
import com.group5.clothing_advisor.ui.adapters.CategoriesAdapter
import com.group5.clothing_advisor.ui.adapters.ClothesAdapter

@BindingAdapter("categoryListData")
fun bindCategoryRecyclerView(recyclerView: RecyclerView, data: List<CategoryResponseItem>?) {
    val adapter = recyclerView.adapter as CategoriesAdapter
    adapter.submitList(data)
}

@BindingAdapter("clothesListData")
fun bindClothesRecyclerView(recyclerView: RecyclerView, data: List<ClothesResponseItem>?) {
    val adapter = recyclerView.adapter as ClothesAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

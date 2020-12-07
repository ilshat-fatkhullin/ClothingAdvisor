package com.group5.clothing_advisor.ui.wardrobe.cloth_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.group5.clothing_advisor.databinding.FragmentClothDetailsBinding

class ClothDetailsFragment : Fragment() {

    private lateinit var viewModel: ClothDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentClothDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val clothResponseItem =
            ClothDetailsFragmentArgs.fromBundle(requireArguments()).selectedCloth
        val viewModelFactory = ClothDetailsViewModelFactory(clothResponseItem)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(ClothDetailsViewModel::class.java)
        binding.viewModel = viewModel
        Glide.with(this)
            .load(FirebaseStorage.getInstance().reference.child(clothResponseItem.imgSrcUrl))
            .into(binding.photo)
        binding.remove.setOnClickListener {
            viewModel.removeCloth()
        }
        viewModel.navigateBack.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
                viewModel.navigatedBack()
            }
        })
        viewModel.showError.observe(this.viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_LONG)
                viewModel.errorShown()
            }
        })
        return binding.root
    }
}
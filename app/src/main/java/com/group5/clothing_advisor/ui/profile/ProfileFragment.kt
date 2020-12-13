package com.group5.clothing_advisor.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.group5.clothing_advisor.LoginActivity
import com.group5.clothing_advisor.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.signOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        viewModel.userName.observe(viewLifecycleOwner){ name ->
            // Do not show log out button if the user is (somehow) not logged in
            if(name == null){
                binding.signOut.visibility = View.GONE
            }
            else {
                binding.nameText.text = name
                binding.signOut.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}
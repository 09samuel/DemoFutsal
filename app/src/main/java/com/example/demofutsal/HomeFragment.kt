package com.example.demofutsal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.demofutsal.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogOut.setOnClickListener{
            firebaseAuth.signOut()
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_loginFragment)
        }

        return binding.root
    }

}
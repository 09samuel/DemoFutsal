package com.example.demofutsal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.demofutsal.databinding.FragmentGroundAdminDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class GroundAdminDashboardFragment : Fragment() {
    private lateinit var binding : FragmentGroundAdminDashboardBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        firebaseAuth = FirebaseAuth.getInstance()
        binding = FragmentGroundAdminDashboardBinding.inflate(inflater,container,false)


        binding.btnLogOut.setOnClickListener{
            //disable back option-it goes back to logout page
            firebaseAuth.signOut()
            view?.findNavController()?.navigate(R.id.action_groundAdminDashboardFragment_to_loginFragment)
        }
        return binding.root
    }


}
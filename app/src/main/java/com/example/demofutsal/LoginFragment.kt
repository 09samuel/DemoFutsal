package com.example.demofutsal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.example.demofutsal.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth : FirebaseAuth
    //val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            // logic when backPress is clicked
            override fun handleOnBackPressed() {
                //close app on double back click
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        /*also add in override on start method too
                        store uid/email in db and retrieve it to check here
                        if(email=="b21634@fragnelcollege.edu.in"){
                            view?.findNavController()?.navigate(R.id.action_loginFragment_to_groundAdminDashboardFragment)
                        }else{*/
                            if(firebaseAuth.currentUser!!.isEmailVerified){
                                view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
                             }else{
                                Toast.makeText(activity, "Please verify your email", Toast.LENGTH_SHORT).show()
                             }
                        //}
                    } else {
                        Toast.makeText(activity, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(activity, "Enter necessary fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.tvForgot.setOnClickListener{
            val email = binding.etLoginEmail.text.toString()
            if(email.isNotEmpty()){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(activity, "Reset password link sent to your email", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, it.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(activity, "Enter your email", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    //remember login
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser!=null){
            if(firebaseAuth.currentUser!!.isEmailVerified){
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
            }else{
                Toast.makeText(activity, "Please verify your email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


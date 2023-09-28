package com.example.demofutsal

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.demofutsal.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationFragment : Fragment() {
    private lateinit var binding : FragmentRegistrationBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var db :  FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnRegister.setOnClickListener{
            val name = binding.etName.text.toString()
            val email = binding.etRegEmail.text.toString()
            val password = binding.etRegPassword.text.toString()
            val confirmPassword = binding.etRegConfirmPassword.text.toString()


            if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                if(it.isSuccessful){
                                    val cid=FirebaseAuth.getInstance().currentUser?.uid
                                    // Store customer data in db
                                    val user = hashMapOf(
                                        "customerName" to name,
                                        "email" to email,
                                    )

                                    if (cid != null) {
                                        db.collection("Customers")
                                            .document(cid)
                                            .set(user)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "DocumentSnapshot added")
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w(TAG, "Error adding document", e)
                                            }
                                    }

                                    Toast.makeText(activity ,"Link sent to your email. Please verify email", Toast.LENGTH_SHORT ).show()
                                    view?.findNavController()?.navigate(R.id.action_registrationFragment_to_loginFragment)
                                }else{
                                    Toast.makeText(activity ,"Registration Fail", Toast.LENGTH_SHORT ).show()
                                }
                            }
                        }else{
                            Toast.makeText(activity ,it.exception?.message.toString(), Toast.LENGTH_SHORT ).show()
                        }
                    }
                }else{
                    Toast.makeText(activity ,"Password is not Matching", Toast.LENGTH_SHORT ).show()
                }
            }else{
                Toast.makeText(activity ,"Empty", Toast.LENGTH_SHORT ).show()
            }
        }

        binding.tvSignIn.setOnClickListener {
            it.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        return binding.root
    }
}
package com.example.login_page

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.login_page.databinding.FragmentAuthBinding
import com.example.login_page.databinding.FragmentParkDetailBinding
import com.example.login_page.databinding.FragmentSignupBinding
import com.example.login_page.entity.User
import com.example.login_page.viewmodel.UserModel

class signupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    lateinit var usermodel: UserModel

    lateinit var sharedPreferences: SharedPreferences
    var isConnected = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignupBinding.inflate(layoutInflater)
        val view = binding.root
        usermodel= ViewModelProvider(requireActivity()).get(UserModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView9.setOnClickListener {
            view.findNavController().navigate(R.id.action_signupFragment_to_auth2)
        }

        binding.imageButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_signupFragment_to_auth2)
        }

        // set on click listener for the button
        binding.button6.setOnClickListener {

            // get the text from the edit text
            val name = binding.editTextTextPersonName9.text.toString()
            val last_name = binding.editTextTextPersonName2.text.toString()
            val tel = binding.editTextTextPersonName10.text.toString()
            val email = binding.editTextTextPersonName12.text.toString()
            val password = binding.editTextTextPersonName11.text.toString()
            // check if the text is empty
            if (name.isEmpty() || last_name.isEmpty() || tel.isEmpty() || email.isEmpty() || password.isEmpty()) {
                // if it is empty, show a toast
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else {
                usermodel.loadUser2(email)
            }

            // List users observer
            usermodel.loading.observe(requireActivity(), Observer {  loading ->
                if(loading == false && usermodel.existe.value == true){
                    // Toast pour dire que l'utilisateur existe deja
                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_signupFragment_to_auth2)
                }
                else if(usermodel.existe.value == false){
                    Toast.makeText(context, "Création d'un utilisateur", Toast.LENGTH_SHORT).show()
                    val user = User(0, name, last_name, tel, email, password)
                    usermodel.addUser(user)
                    Toast.makeText(context, "User created", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_signupFragment_to_auth2)
                }
            })

//            usermodel.creation.observe((requireActivity()), Observer { creation ->
//                if(creation == true){
//                    Toast.makeText(context, "User created", Toast.LENGTH_LONG).show()
//                    view.findNavController().navigate(R.id.action_signupFragment_to_auth2)
//                }
//                else{
//                    Toast.makeText(context, "User not created", Toast.LENGTH_LONG).show()
//                    // vider les champs
//                    binding.editTextTextPersonName9.text.clear()
//                    binding.editTextTextPersonName2.text.clear()
//                    binding.editTextTextPersonName10.text.clear()
//                    binding.editTextTextPersonName12.text.clear()
//                    binding.editTextTextPersonName11.text.clear()
//                }
//            })

        }
    }

}
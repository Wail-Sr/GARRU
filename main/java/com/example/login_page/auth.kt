package com.example.login_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.login_page.databinding.FragmentAuthBinding
import com.example.login_page.viewmodel.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class auth : Fragment() {

    lateinit var gso:GoogleSignInOptions
    lateinit var gsc:GoogleSignInClient
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var binding: FragmentAuthBinding
    lateinit var usermodel: UserModel

    lateinit var sharedPreferences: SharedPreferences
    var isConnected = false

    private val RC_SIGN_IN = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAuthBinding.inflate(layoutInflater)
        val view = binding.root

        usermodel = ViewModelProvider(this).get(UserModel::class.java)
        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putBoolean("CONNECTED",false)
        }
        isConnected = sharedPreferences.getBoolean("CONNECTED", false)


        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc=GoogleSignIn.getClient(requireActivity(),gso)

        binding.button3.setOnClickListener {

            // récuperer
            val email = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPersonName3.text.toString()

            // verifier les entrées
            if(email == "" || password == ""){
                // Toast pour dire qu'il faut remplir tout les champs
                Toast.makeText(requireContext(), "Remplir tout les champs", Toast.LENGTH_LONG).show()
            }
            else{
                usermodel.gettuser(email, password)
            }
        }

        binding.textView9.setOnClickListener {
            view.findNavController().navigate(R.id.action_auth2_to_signupFragment)
        }

        binding.button2.setOnClickListener {
            signIn()
        }

        // Error message observer
        usermodel.errorMessage.observe(requireActivity(), Observer {  message ->
            //Toast.makeText(requireContext(),"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()

        })


        // List users observer
        usermodel.user.observe(requireActivity(), Observer {  user ->
            // toast pour dire que l'utilisateur est connecté

            if(user != null){
                // Toast pour dire que l'utilisateur existe
                Toast.makeText(requireContext(), "Utilisateur existe", Toast.LENGTH_SHORT).show()
                // changer le boolean de connexion
                sharedPreferences.edit {
                    putBoolean("CONNECTED",true)
                    putInt("USERID",usermodel.user.value!!.user_id)
                }

                // new intent pour changer de page
                val intent = Intent(requireContext(), MainActivity3::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            else{
                // Toast pour dire que l'utilisateur n'existe pas
                Toast.makeText(requireContext(), "Utilisateur n'existe pas", Toast.LENGTH_LONG).show()
                // vider tous les champs
                binding.editTextTextPersonName.text.clear()
                binding.editTextTextPersonName3.text.clear()
            }
        })

    }

    private fun signIn() {
        val intent = gsc.signInIntent
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task);
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            var email:String=""
            var nom:String=""
            var prenom:String=""
            if (account != null) {
                Toast.makeText(requireContext(),"tres bien",Toast.LENGTH_SHORT).show()
                if(account.email!=null){
                    email= account.email!!
                    nom=account.familyName!!
                    prenom=account.givenName!!

                }
            }
            else{
                Toast.makeText(requireContext(),"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
            }

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
            val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
            if (acct != null) {

                email = acct.email.toString()
                nom=acct.familyName.toString()
                prenom=acct.givenName.toString()

            }
            //toast pour dire que l'utilisateur est connecté
            Toast.makeText(requireContext(), email, Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            e.printStackTrace()
            // print sign in status code
            Toast.makeText(requireContext(), e.statusCode.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
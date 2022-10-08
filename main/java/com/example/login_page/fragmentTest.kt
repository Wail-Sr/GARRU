//package com.example.login_page
//
//import android.content.Context
//import android.content.SharedPreferences
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.ViewModelProvider
//import com.example.login_page.databinding.FragmentAuthBinding
//import com.example.login_page.viewmodel.MyModel
//import com.example.login_page.viewmodel.UserModel
//
//class ReservationFragment : Fragment() {
//
//    private lateinit var binding: FragmentAuthBinding
//    lateinit var parksmodel: MyModel
//    lateinit var sharedPreferences: SharedPreferences
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//
//    ): View? {
//        binding = FragmentAuthBinding.inflate(layoutInflater)
//        val view = binding.root
//        parksmodel = ViewModelProvider(this).get(MyModel::class.java)
//        sharedPreferences = requireContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//
//        // Inflate the layout for this fragment
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//}
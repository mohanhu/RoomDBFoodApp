package com.example.roomdbapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.roomdbapp.R
import com.example.roomdbapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = activity?.getSharedPreferences("Key", Context.MODE_PRIVATE)

        val isLogin: Boolean? = sharedPreference?.getBoolean("Check", false)
        val userName= sharedPreference?.getString("nameshared","")
        val bool =false
        if (isLogin == true && userName?.isNotEmpty() == true) {
            //  onBackPressed()
            findNavController().navigate(R.id.action_loginFragment_to_listFragments3)
        }

        binding.login.setOnClickListener {
            val edit = sharedPreference?.edit()
            val name = binding.name.text
            val password = binding.password.text
            if (name.toString().isEmpty() || password.toString().isEmpty() || !binding.check.isChecked
            ) {
                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                //
            } else {
                // Toast.makeText(this, "$name", Toast.LENGTH_SHORT).show()
                if (edit != null) {
                    edit.putString("nameshared", name.toString())
                    edit.putString("passwordshared", password.toString())
                    edit.putBoolean("Check", true)
                    edit.apply()
                }

                findNavController().navigate(R.id.action_loginFragment_to_listFragments3)
            }

        }
    }


}
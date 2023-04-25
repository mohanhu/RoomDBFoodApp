package com.example.roomdbapp.ui.view.fragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.roomdbapp.R
import com.example.roomdbapp.databinding.FragmentAddBinding
import com.example.roomdbapp.model.Address
import com.example.roomdbapp.model.User
import com.example.roomdbapp.ui.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private lateinit var newViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        binding.add.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                addDataBase()
            }
        }
    }

    private suspend fun getImageBitMap():Bitmap{
        val loading:ImageLoader=ImageLoader(requireContext())
        val request:ImageRequest=ImageRequest.Builder(requireContext())
            .data(R.drawable.doll).build()

        val result=(loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun addDataBase() {
        val firstname = binding.firstname.text
        val lastname = binding.lastname.text
        val age = binding.age.text
        val streetname= binding.streetname.text
        val streetno= binding.streetnumber.text
        lifecycleScope.launch(Dispatchers.Main) {
        if (firstname.toString().isEmpty() || lastname.toString().isEmpty() || age.toString().isEmpty() || streetname.isEmpty() || streetno.isEmpty()) {
            Toast.makeText(requireContext(), "Input Failed", Toast.LENGTH_SHORT).show()
        } else {
                val ageChecked = getNumber(age.toString())
                val address = Address(streetname.toString(), streetno.toString().toInt())
                val user = User(
                    0,
                    firstname.trim().toString(),
                    lastname.trim().toString(),
                    ageChecked.toString().toInt(),
                    getImageBitMap(),
                    address
                )
                newViewModel.addData(user)
              //  Toast.makeText(requireContext(), "SuccessFully Added", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment2_to_listFragments3)
            }
        }
    }

    private fun getNumber(s: String): Any {
        return try {
            Integer.parseInt(s)
        } catch (e: NumberFormatException) {
            0
        }
    }
}
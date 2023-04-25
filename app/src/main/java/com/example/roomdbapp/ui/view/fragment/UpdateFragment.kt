package com.example.roomdbapp.ui.view.fragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.roomdbapp.R
import com.example.roomdbapp.databinding.FragmentUpdateBinding
import com.example.roomdbapp.model.Address
import com.example.roomdbapp.model.User
import com.example.roomdbapp.ui.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable


class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewModel: UserViewModel
    private var userupdatelist = ArrayList<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        var bundle: Serializable? = arguments?.getSerializable("key")

        if (bundle.toString() == "null") {
            Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show()
        } else {
            userupdatelist.addAll(listOf(bundle as User))
            binding.upfirstname.setText(bundle.first)
            binding.uplastname.setText(bundle.last)
            binding.upage.setText(bundle.age.toString())
            binding.upstreetname.setText(bundle.address.streetName)
            binding.upstreetnumber.setText(bundle.address.streetNumber.toString())

            binding.update.setOnClickListener{
                lifecycleScope.launch(Dispatchers.IO) {
                updataDatase(bundle)
                }
            }
        }
    }

    private fun updataDatase(bundle:User) {
        val first = binding.upfirstname.text
        val last = binding.uplastname.text
        val age = binding.upage.text
        val addressname= binding.upstreetname.text
        val addressnum = binding.upstreetnumber.text
        lifecycleScope.launch(Dispatchers.Main){
            if (first.toString().isEmpty() || last.toString().isEmpty() || age.toString().isEmpty() || addressname.isEmpty() || addressnum.isEmpty()) {
                Toast.makeText(requireContext(), "Input Failed", Toast.LENGTH_SHORT).show()
            }
            else{
                val user = User(bundle.id,first.toString().trim(),last.toString().trim(),age.toString().toInt(), getImageBitMap(),
                    Address(addressname.toString(),addressnum.toString().toInt())
                )
                viewModel.update(user)
                findNavController().navigate(R.id.action_updateFragment2_to_listFragments3)
            }

        }
    }

    private suspend fun getImageBitMap(): Bitmap {
        val loading: ImageLoader = ImageLoader(requireContext())
        val request: ImageRequest = ImageRequest.Builder(requireContext())
            .data(R.drawable.doll).build()

        val result=(loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}
package com.example.roomdbapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbapp.R
import com.example.roomdbapp.databinding.ActivityMainBinding.inflate
import com.example.roomdbapp.databinding.BottomAlertBinding.inflate
import com.example.roomdbapp.databinding.FragmentListBinding
import com.example.roomdbapp.model.User
import com.example.roomdbapp.ui.adapter.UserAdapter
import com.example.roomdbapp.ui.viewmodel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ListFragments : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var newModel:UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        // Inflate the layout for this fragment
        binding=FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

     fun onBackPressed(): Boolean {
        findNavController().navigate(R.id.action_listFragments3_to_splashFragment)
        return false
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFloats.setOnClickListener {
            findNavController().navigate(R.id.action_listFragments3_to_addFragment2)
        }

        newModel=ViewModelProvider(this).get(UserViewModel::class.java)
        setadapter()
        displayList()

        val sharedPreference = activity?.getSharedPreferences("Key", Context.MODE_PRIVATE)

        val edit =sharedPreference?.edit()
        val check=sharedPreference?.getBoolean("Check",false)
        val userName= sharedPreference?.getString("nameshared","")
        binding.username.text=userName.toString()

/*        activity?.onBackPressedDispatcher?.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (check==true){
                    try{
                        onBackPressed()
                    }
                    catch (e:IllegalStateException){
                        throw (e)
                    }
                }

            }
        })*/
        binding.logout.setOnClickListener {
            if (edit != null) {
                edit.clear()
                edit.apply()
                findNavController().navigate(R.id.action_listFragments3_to_loginFragment)
            }
        }
        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filtertext(newText)
                return false
            }

        })
    }

    private  fun filtertext(newText: String?) {

            if (newText?.trim().toString().isNotEmpty()){
                if (newText != null) {
                    newModel.search("%$newText%")
                }
                lifecycleScope.launch(Dispatchers.Main){
                    newModel.searchlivedata.observe(viewLifecycleOwner, Observer {search ->
                      //  Toast.makeText(requireContext(), "$search", Toast.LENGTH_SHORT).show()
                        userAdapter.differ.submitList(search)
                        // userAdapter.notifyDataSetChanged()
                    })
                }

        }
        else{
                newModel.userLiveData.observe(viewLifecycleOwner, Observer {
                    userAdapter.differ.submitList(it)
                })
            }
    }

    private fun displayList() {
        userAdapter.delepos {user ->
            val viewBottom= layoutInflater.inflate(R.layout.bottom_alert,null)
            val bottomDialog=BottomSheetDialog(requireContext())
            bottomDialog.setContentView(viewBottom)
            bottomDialog.create()
            bottomDialog.setCanceledOnTouchOutside(true)
            bottomDialog.setCancelable(true)
            bottomDialog.show()
            val delete=viewBottom.findViewById<TextView>(R.id.delete)
            delete.setOnClickListener {
                newModel.delete(user)
                bottomDialog.dismiss()
            }
            val deleteAll=viewBottom.findViewById<TextView>(R.id.deleteall)
            deleteAll.setOnClickListener {
                newModel.deleteAll()
                bottomDialog.dismiss()
            }
        }
    }

    private fun setadapter() {
        userAdapter= UserAdapter()

            newModel.userLiveData.observe(viewLifecycleOwner, Observer {
                userAdapter.differ.submitList(it)
            })

        binding.recycle.layoutManager=LinearLayoutManager(requireContext())
        binding.recycle.adapter=userAdapter
    }


}
package com.uroosa.dieperson.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.uroosa.dieperson.R
import com.uroosa.dieperson.databinding.FragmentUserListBinding
import com.uroosa.dieperson.models.response.UserResponse
import com.uroosa.dieperson.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class userListFragment : Fragment() {
    //Create Binding Object
    private var _binding: FragmentUserListBinding? = null

    //Null safe
    private val binding get() = _binding!!

    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Binding Object Initialize in OnCreateView
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        adapter = UserAdapter(::onUserClicked)
        //Access view
        //binding.noteList
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.getAllUsers()
        binding.userList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.userList.adapter = adapter
        binding.addUser.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_createUserFragment)
        }
        bindObservers()
    }

    private fun bindObservers() {
        userViewModel.usersLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    adapter.submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onUserClicked(user: UserResponse){
        val bundle = Bundle()
        bundle.putString("user", Gson().toJson(user))
        findNavController().navigate(R.id.action_userListFragment_to_createUserFragment, bundle)
    }


    //OVERRIDE ONDESTROYVIEW & SET BINDING TO NULL
    //IT IS A PERFORMANCE BENEFIT
    //If your view destroy, Binding object should destroy
    //making memory free by making binding null
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
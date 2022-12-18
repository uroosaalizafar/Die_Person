package com.uroosa.dieperson.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.uroosa.dieperson.R
import com.uroosa.dieperson.databinding.FragmentCreateUserBinding
import com.uroosa.dieperson.models.request.UserRequest
import com.uroosa.dieperson.models.response.UserResponse
import com.uroosa.dieperson.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class createUserFragment : Fragment() {

    private var _binding: FragmentCreateUserBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()
    private var user: UserResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        userViewModel.statusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        })
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            user?.let { userViewModel.deleteUser(it!!.id.toString()) }
        }
        binding.apply {
            btnSubmit.setOnClickListener {
                val name = txtName.text.toString()
                val email = txtEmail.text.toString()
                val gender = txtGender.text.toString()
                val userRequest = UserRequest(email = email, gender = gender, name = name, status = "active")
                if (user == null) {
                    userViewModel.createUser(userRequest)
                } else {
                    Log.e("User ID" , user!!.id.toString())
                    userViewModel.updateUser(user!!.id.toString(), userRequest)
                }
            }
        }
    }

    private fun setInitialData() {
        val jsonUser = arguments?.getString("user")
        Log.e("JsonUser" , jsonUser.toString())
        if (jsonUser != null) {
            user = Gson().fromJson<UserResponse>(jsonUser, UserResponse::class.java)
            user?.let {
                binding.txtName.setText(it.name)
                binding.txtEmail.setText(it.email)
                binding.txtGender.setText(it.gender)
            }
        }
        else{
            binding.btnDelete.isVisible=false
            binding.addEditText.text = resources.getString(R.string.add_user)
        }
    }
}
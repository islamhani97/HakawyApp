package com.islam.hakawyapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.islam.hakawyapp.R
import com.islam.hakawyapp.callbacks.SignInCallback
import com.islam.hakawyapp.databinding.FragmentProfileInfoBinding
import com.islam.hakawyapp.pojo.User
import com.islam.hakawyapp.ui.activities.signin.SignInActivity


class ProfileInfoFragment : Fragment() {
    private lateinit var signInCallback: SignInCallback
    private lateinit var binding: FragmentProfileInfoBinding

    private val args :ProfileInfoFragmentArgs by navArgs()

            override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_info, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInCallback = context as SignInActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentProfileInfoName.setText(args.username)

        binding.fragmentProfileInfoSubmit.setOnClickListener {
            val name = binding.fragmentProfileInfoName.text.toString()
            if (name.isNotEmpty()) {
                val user = User()
                user.name = name
                signInCallback.submitProfileInfo(user)
            } else {
                binding.fragmentProfileInfoName.error = getString(R.string.username_required)
            }
        }
    }
}
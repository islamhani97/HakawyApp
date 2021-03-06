package com.islam.hakawyapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.islam.hakawyapp.R
import com.islam.hakawyapp.callbacks.SignInCallback
import com.islam.hakawyapp.databinding.FragmentAuthCodeBinding
import com.islam.hakawyapp.ui.activities.signin.SignInActivity


class AuthCodeFragment : Fragment() {
    private lateinit var signInCallback: SignInCallback
    private lateinit var binding: FragmentAuthCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth_code, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signInCallback = context as SignInActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fragmentAuthCodeAuthCode.setOnCodeCompleteListener { code ->
            if (code.isNotEmpty()) {
                signInCallback.onCodeSubmit(code)
            }
        }


    }



}
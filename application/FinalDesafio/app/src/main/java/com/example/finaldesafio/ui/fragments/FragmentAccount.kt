package com.example.finaldesafio.ui.fragments

import android.util.Log
import android.widget.TextView

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.model.AnnouncementSearch
import com.example.finaldesafio.ui.BaseFragment
import com.example.finaldesafio.ui.features.MainActivity
import com.google.android.material.button.MaterialButton

class FragmentAccount : BaseFragment() {
    private var textNameView: TextView? = null
    private var textEmailView: TextView? = null

    override fun getLayoutId(): Int = R.layout.fragment_account
    override fun initViews() {
        activity?.let {
            textNameView  = it.findViewById(R.id.textNameView)
            textEmailView = it.findViewById(R.id.textEmailView)
            it.findViewById<MaterialButton>(R.id.account_button_edit).apply {
                setOnClickListener { goEditAccount() }
            }
        }
    }

    override fun initViewModel() {
        (activity as? MainActivity)?.also {
            it.viewModel.userSession?.let { session ->
                textNameView?.text  = session.userName
                textEmailView?.text = session.userEmail
            }
        }
        setLoading(false)
    }

    private fun goEditAccount() { (activity as? MainActivity)?.goEditAccount() }
    override fun action() {}
    override fun search(search: AnnouncementSearch) {}

    companion object {
        fun newInstance() : FragmentAccount = FragmentAccount()
    }
}
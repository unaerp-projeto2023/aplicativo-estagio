package com.example.finaldesafio.ui.fragments

import com.example.finaldesafio.R
import com.example.finaldesafio.ui.BaseFragment

class FragmentAnnounce : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_announce
    override fun initViews() {}
    override fun initViewModel() {}

    companion object {
        fun newInstance() : FragmentAnnounce = FragmentAnnounce()
    }

}
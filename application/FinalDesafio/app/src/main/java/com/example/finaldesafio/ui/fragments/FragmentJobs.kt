package com.example.finaldesafio.ui.fragments

import com.example.finaldesafio.R
import com.example.finaldesafio.ui.BaseFragment

class FragmentJobs : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_jobs
    override fun initViews() {}
    override fun initViewModel() {}

    companion object {
        fun newInstance() : FragmentJobs = FragmentJobs()
    }

}
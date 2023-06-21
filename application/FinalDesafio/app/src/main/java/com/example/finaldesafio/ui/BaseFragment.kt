package com.example.finaldesafio.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.finaldesafio.repository.model.AnnouncementSearch

abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startInitViews()
        startViewModel()
    }

    private fun startInitViews() {
        Handler(Looper.getMainLooper()).postDelayed({ initViews() }, 60)
    }

    private fun startViewModel() {
        Handler(Looper.getMainLooper()).postDelayed({ initViewModel() }, 60)
    }

    fun setLoading(hasLoading: Boolean) { (activity as? BaseActivity)?.setLoading(hasLoading) }
    fun errorMsg(@StringRes msg: Int)   { (activity as? BaseActivity)?.errorMsg(msg) }
    fun errorMsg(msg: String)           { (activity as? BaseActivity)?.errorMsg(msg) }

    fun boxMsg(@StringRes msg: Int, callback: () -> Unit) {
        (activity as? BaseActivity)?.boxMsg(msg) { callback.invoke() }
    }
    fun boxMsg(msg: String, callback: () -> Unit) {
        (activity as? BaseActivity)?.boxMsg(msg) { callback.invoke() }
    }

    fun boxConfirm(@StringRes msg: Int, callback: (Boolean) -> Unit) {
        (activity as? BaseActivity)?.boxConfirm(msg) { callback.invoke(it) }
    }
    fun boxConfirm(msg: String, callback: (Boolean) -> Unit) {
        (activity as? BaseActivity)?.boxConfirm(msg) { callback.invoke(it) }
    }

    @LayoutRes
    abstract fun getLayoutId() : Int
    abstract fun initViews()
    abstract fun initViewModel()
    abstract fun action()
    abstract fun search(search: AnnouncementSearch)
}
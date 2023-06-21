package com.example.finaldesafio.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.extensions.leftToRight
import com.example.finaldesafio.repository.extensions.rightToLeft

abstract class BaseActivity : AppCompatActivity() {
    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(getLayoutId())
        startInitViews()
        startInitViewModel()
    }

    private fun startInitViews() {
        Handler(Looper.getMainLooper()).postDelayed({ initViews() }, 60)
    }

    private fun startInitViewModel() {
        Handler(Looper.getMainLooper()).postDelayed({ initViewModel() }, 60)
    }

    fun setupToolBar(toolbar: Toolbar?) {
        toolbar?.let {
            this.setSupportActionBar(it)
            it.setTitleTextColor(ContextCompat.getColor(baseContext, R.color.white))
        }
        actionBar = supportActionBar
    }

    fun setActionBarHome()       { actionBar?.setHomeButtonEnabled(true) }
    fun setActionBarHomeButton() { actionBar?.setDisplayHomeAsUpEnabled(true) }

    fun setActionBarTitle(title: String)         { actionBar?.title = title }
    fun setActionBarTitle(@StringRes title: Int) { actionBar?.title = getString(title) }

    fun setActionBarSubTitle(title: String)         { actionBar?.subtitle = title }
    fun setActionBarSubTitle(@StringRes title: Int) { actionBar?.subtitle = getString(title) }


    fun setTheme(isNight: Boolean) {
        val night = AppCompatDelegate.getDefaultNightMode()
        AppCompatDelegate.setDefaultNightMode(
            if (isNight) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun errorMsg(@StringRes msg: Int) { errorMsg(getString(msg)) }
    fun errorMsg(message: String) {
        AlertDialog.Builder(this, R.style.AlertDialogTheme).apply {
            setCancelable(false)
            setMessage(message)
            setPositiveButton(getString(R.string.btn_ok)) { dialog, _ -> dialog.dismiss() }
            create().show()
        }
    }

    fun boxMsg(@StringRes msg: Int, callback: () -> Unit) { boxMsg(getString(msg)) { callback.invoke() } }
    fun boxMsg(message: String, callback: () -> Unit) {
        AlertDialog.Builder(this, R.style.AlertDialogTheme).apply {
            setCancelable(false)
            setMessage(message)
            setPositiveButton(getString(R.string.btn_ok)) { dialog, _ ->
                dialog.dismiss()
                callback.invoke()
            }
            create().show()
        }
    }
    fun boxConfirm(@StringRes msg: Int, callback: (Boolean) -> Unit) { boxConfirm(getString(msg)) { callback.invoke(it) }  }
    fun boxConfirm(message: String, callback: (Boolean) -> Unit) {
        AlertDialog.Builder(this, R.style.AlertDialogTheme).apply {
            setCancelable(false)
            setMessage(message)
            setNegativeButton(getString(R.string.btn_no)) { dialog, _ ->
                dialog.dismiss()
                callback.invoke(false)
            }
            setPositiveButton(getString(R.string.btn_yes)) { dialog, _ ->
                dialog.dismiss()
                callback.invoke(true)
            }
            create().show()
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            back()
            true
        }
        else { super.onKeyDown(keyCode, event) }
    }

    override fun onSupportNavigateUp(): Boolean {
        back()
        return true
    }

    fun replaceFragment(@IdRes resourceId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().also {
            it.replace(resourceId, fragment, TAG)
            it.disallowAddToBackStack()
            it.commit()
        }
    }

    fun animLeftToRight() { this.leftToRight() }
    fun animRightToLeft() { this.rightToLeft() }

    @LayoutRes
    abstract fun getLayoutId() : Int
    abstract fun initViews()
    abstract fun initViewModel()
    abstract fun setLoading(hasLoading: Boolean)
    abstract fun back()

    companion object {
        private const val TAG = "BaseActivity"
    }
}
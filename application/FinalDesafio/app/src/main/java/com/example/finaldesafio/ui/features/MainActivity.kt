package com.example.finaldesafio.ui.features

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.extensions.editTableText
import com.example.finaldesafio.repository.extensions.editText
import com.example.finaldesafio.repository.extensions.visibility
import com.example.finaldesafio.repository.model.AnnouncementSearch
import com.example.finaldesafio.ui.BaseActivity
import com.example.finaldesafio.ui.BaseFragment
import com.example.finaldesafio.ui.components.Progress
import com.example.finaldesafio.ui.fragments.FragmentAccount
import com.example.finaldesafio.ui.fragments.FragmentAccountEdit
import com.example.finaldesafio.ui.fragments.FragmentAnnounce
import com.example.finaldesafio.ui.fragments.FragmentJobs
import com.example.finaldesafio.ui.fragments.FragmentMyAnnounces
import com.example.finaldesafio.ui.fragments.FragmentMyJobs
import com.example.finaldesafio.viewmodel.MainViewModel

class MainActivity : BaseActivity() {
    private var bottomNav : BottomNavigationView? = null
    private var fabAction : FloatingActionButton? = null
    private var collapsing : CollapsingToolbarLayout? = null
    private var progress: Progress? = null
    private var fragment: BaseFragment? = null
    @IdRes private var itemId : Int? = null

    val viewModel : MainViewModel by viewModels()

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun setLoading(hasLoading: Boolean) { progress?.visibility(hasLoading) }
    override fun initViews() {
        findViewById<Toolbar>(R.id.toolbarApp).also { setupToolBar(it) }
        progress   = findViewById(R.id.progressMain)
        collapsing = findViewById(R.id.collapsing)
        bottomNav  = findViewById<BottomNavigationView>(R.id.bottomNav).also {
            it.setOnItemSelectedListener { item ->
                itemId = item.itemId
                when (itemId) {
                    R.id.action_account      -> goAction(FragmentAccount.newInstance())
                    R.id.action_jobs         -> goAction(FragmentJobs.newInstance())
                    R.id.action_my_jobs      -> goAction(FragmentMyJobs.newInstance())
                    R.id.action_my_announces -> goAction(FragmentMyAnnounces.newInstance())
                    R.id.action_announce     -> goAction(FragmentAnnounce.newInstance())
                    else                     -> false
                }
            }
        }
        fabAction = findViewById<FloatingActionButton?>(R.id.fabAction).also { action ->
            action.setOnClickListener {
                when (it.contentDescription) {
                    R.string.content_description_search.toString() -> search()
                    R.string.content_description_save.toString()   -> fragment?.action()
                }
            }
        }
    }

    override fun initViewModel() {
        viewModel.also {
            it.initViewModel(baseContext) {
                findViewById<TextView>(R.id.textNavName).text  = it.userSession?.userName
                findViewById<TextView>(R.id.textNavEmail).text = it.userSession?.userEmail
                when(it.userSession?.userType) {
                    "A" -> showCompanyBar()
                    "U" -> showUserBar()
                }
            }
        }
    }

    private fun showUserBar() {
        itemId = R.id.action_jobs
        bottomNav?.menu?.apply {
            findItem(R.id.action_announce)?.isVisible = false
            findItem(R.id.action_my_announces)?.isVisible = false
            findItem(R.id.action_jobs)?.isChecked = true
        }
        goAction(FragmentJobs.newInstance())
    }

    private fun showCompanyBar() {
        itemId = R.id.action_my_announces
        bottomNav?.menu?.apply {
            findItem(R.id.action_jobs)?.isVisible = false
            findItem(R.id.action_my_jobs)?.isVisible = false
            findItem(R.id.action_my_announces)?.isChecked = true
        }
        goAction(FragmentMyAnnounces.newInstance())
    }

    private fun goAction(fragment : BaseFragment) : Boolean {
        var hasShow = false
        @DrawableRes var iconResource = R.drawable.ic_search
        @StringRes   var description  = R.string.content_description_search
        collapsing?.title = when(itemId) {
            R.id.action_account      -> getString(R.string.title01)
            R.id.action_my_jobs      -> getString(R.string.title03)
            R.id.action_my_announces -> getString(R.string.title10)
            R.id.action_announce     -> {
                hasShow      = true
                iconResource = R.drawable.ic_save
                description  = R.string.content_description_save
                getString(R.string.title04)
            }
            R.id.action_jobs -> {
                hasShow = true
                getString(R.string.title02)
            }
            else -> null
        }
        fabAction?.apply {
            contentDescription = description.toString()
            setImageResource(iconResource)
            visibility(hasShow)
        }

        return goFragment(fragment)
    }

    private fun goFragment(fragment : BaseFragment) : Boolean {
        this.fragment = fragment
        setLoading(true)
        replaceFragment(R.id.container, fragment)
        return true
    }

    fun goEditAccount() {
        collapsing?.title = getString(R.string.bt_account_edit)
        goFragment(FragmentAccountEdit.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_logout -> {
                back()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("InflateParams")
    private fun search() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        AlertDialog.Builder(this, R.style.AlertDialogTheme).apply {
            setCancelable(false)
            val customLayout = inflater.inflate(R.layout.search_dialog, null)
            setView(customLayout)
            setNegativeButton(getString(R.string.btn_cancel)) { dialog, _ -> dialog.dismiss() }
            setPositiveButton(getString(R.string.btn_ok)) { dialog, _ ->
                customLayout.let { view ->
                    fragment?.search(
                        AnnouncementSearch().also {
                            it.description     = view.editText(R.id.search_filed1).editTableText()
                            it.areaDescription = view.editText(R.id.search_filed2).editTableText()
                            it.locality        = view.editText(R.id.search_filed3).editTableText()
                        }
                    )
                }
                dialog.dismiss()
            }
            create().show()
        }
    }

    override fun back() {
        Intent(baseContext, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
            animLeftToRight()
        }
    }
}
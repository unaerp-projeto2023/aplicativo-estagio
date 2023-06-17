package com.example.finaldesafio

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.example.finaldesafio.ui.BaseActivity
import com.example.finaldesafio.ui.BaseFragment
import com.example.finaldesafio.ui.features.LoginActivity
import com.example.finaldesafio.ui.fragments.FragmentAccount
import com.example.finaldesafio.ui.fragments.FragmentAnnounce
import com.example.finaldesafio.ui.fragments.FragmentJobs
import com.example.finaldesafio.ui.fragments.FragmentMyJobs
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : BaseActivity() {
    private var bottomNav : BottomNavigationView? = null
    private var fabAction : FloatingActionButton? = null
    private var collapsing : CollapsingToolbarLayout? = null
    private var itemId : Int = 2

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun initViewModel() {}
    override fun initViews() {
        findViewById<Toolbar>(R.id.toolbarApp).also { setupToolBar(it) }
        collapsing = findViewById(R.id.collapsing)
        bottomNav  = findViewById<BottomNavigationView>(R.id.bottomNav).also {
            it.setOnItemSelectedListener { item ->
                itemId = item.itemId
                when (itemId) {
                    R.id.action_account  -> goFragment(FragmentAccount.newInstance())
                    R.id.action_jobs     -> goFragment(FragmentJobs.newInstance())
                    R.id.action_my_jobs  -> goFragment(FragmentMyJobs.newInstance())
                    R.id.action_announce -> goFragment(FragmentAnnounce.newInstance())
                    else                 -> false
                }
            }
        }
        fabAction = findViewById<FloatingActionButton?>(R.id.fabAction).also { action ->
            action.setOnClickListener {
                when (action.contentDescription) {
                    getString(R.string.content_description_search) -> {}
                }



            }


        }
        goFragment(FragmentJobs.newInstance())
    }

    private fun showHideItem(@IdRes item: Int, hasVisible: Boolean = false) {
        bottomNav?.menu?.findItem(item)?.isVisible = hasVisible
    }

    private fun goFragment(fragment : BaseFragment) : Boolean {
        collapsing?.title = when(itemId) {
            R.id.action_account  -> getString(R.string.title01)
            R.id.action_jobs     -> getString(R.string.title02)
            R.id.action_my_jobs  -> getString(R.string.title03)
            R.id.action_announce -> getString(R.string.title04)
            else                 -> getString(R.string.title02)
        }
        replaceFragment(R.id.container, fragment)
        return true
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

    override fun back() {
        Intent(baseContext, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
            animLeftToRight()
        }
    }
}
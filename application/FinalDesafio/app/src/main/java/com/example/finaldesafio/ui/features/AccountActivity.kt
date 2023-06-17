package com.example.finaldesafio.ui.features

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.example.finaldesafio.MainActivity
import com.example.finaldesafio.R

import com.example.finaldesafio.repository.core.Constants
import com.example.finaldesafio.repository.core.Constants.AccountAction
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.ui.BaseActivity
import com.example.finaldesafio.viewmodel.AddUserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class AccountActivity: BaseActivity() {
    private var action: AccountAction? = null
    private val viewModel: AddUserViewModel by viewModels()
    private var accountType: String = "U"

    // fileds
    private var name : TextInputEditText? = null
    private var email : TextInputEditText? = null
    private var password : TextInputEditText? = null
    private var rePassword : TextInputEditText? = null

    override fun getLayoutId(): Int = R.layout.activity_account
    override fun initViews() {
        intent?.extras?.let {
            action = it.getSerializable(Constants.TYPE) as AccountAction
        }
        findViewById<Toolbar>(R.id.toolbarOther).also { setupToolBar(it) }
        setActionBarTitle(when(action) {
            AccountAction.ADD_USER -> {
                accountType = "U"
                getString(R.string.title05)
            }
            AccountAction.ADD_COMPANY -> {
                accountType = "A"
                getString(R.string.title06)
            }
            AccountAction.EDIT_USER -> {
                accountType = "U"
                getString(R.string.title07)
            }
            AccountAction.EDIT_COMPANY -> {
                accountType = "A"
                getString(R.string.title08)
            }
            else -> ""
        })
        findViewById<FloatingActionButton>(R.id.fab_save_account).also {
            it.setOnClickListener { save() }
        }

        // get fields
        name       = findViewById(R.id.account_filed1)
        email      = findViewById(R.id.account_filed2)
        password   = findViewById(R.id.account_filed3)
        rePassword = findViewById<TextInputEditText>(R.id.account_filed4).also {
            it.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> save()
                }
                false
            }
        }
    }

    override fun initViewModel() {
        viewModel.apply {
            initViewModel(baseContext) {
                response.observe(this@AccountActivity) {
                    if (it.codeError!! > 0) { errorMsg(it.message.toString()) }
                    else                    { goLogin() }
                }
            }
        }
    }

    private fun save() {
        val txtName       = name?.editableText.toString()
        val txtEmail      = email?.editableText.toString()
        val txtPassword   = password?.editableText.toString()
        val txtRePassword = rePassword?.editableText.toString()

        if (txtRePassword != txtName) {
            errorMsg(R.string.account_error1)
        }
        else {
            viewModel.saveUser(User().also {
                it.name     = txtName
                it.password = txtPassword
                it.email    = txtEmail
                it.type     = accountType
            })
        }
    }

    private fun goLogin() {
        boxMsg(when(accountType) {
            "U"  -> getString(R.string.title05a)
            "A"  -> getString(R.string.title06a)
            else -> ""
        }) {
            back()
        }
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
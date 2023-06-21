package com.example.finaldesafio.ui.features

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.core.Constants
import com.example.finaldesafio.repository.core.Constants.AccountAction
import com.example.finaldesafio.repository.extensions.editTableText
import com.example.finaldesafio.repository.extensions.visibility
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult
import com.example.finaldesafio.ui.BaseActivity
import com.example.finaldesafio.ui.components.Progress
import com.example.finaldesafio.viewmodel.AddUserViewModel

class AccountActivity: BaseActivity() {
    private var action: AccountAction? = null
    private val viewModel: AddUserViewModel by viewModels()
    private var accountType: String = "U"
    private var progress: Progress? = null

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
            else -> ""
        })
        findViewById<FloatingActionButton>(R.id.fab_save_account).also {
            it.setOnClickListener { save() }
        }

        progress   = findViewById(R.id.progressAccount)
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
        setLoading(false)
    }

    override fun initViewModel() {
        viewModel.apply {
            initViewModel(baseContext) {
                response.observe(this@AccountActivity) { accountAction(it) }
            }
        }
    }

    override fun setLoading(hasLoading: Boolean) { progress?.visibility(hasLoading) }

    private fun save() {
        val userName       = name?.editTableText()
        val userEmail      = email?.editTableText()
        val userPassword   = password?.editTableText()
        val txtRePassword = rePassword?.editTableText()

        if (txtRePassword != userPassword) {
            errorMsg(R.string.account_error1)
        }
        else {
            setLoading(true)
            viewModel.saveUser(User().also {
                it.userName     = userName
                it.userEmail    = userEmail
                it.userPassword = userPassword
                it.userType     = accountType
            })
        }
    }

    private fun accountAction(response: WsResult<User>?) {
        setLoading(false)
        if (response?.codeError == 0) {
            viewModel.apply {
                userClear()
                setUserLogin(response.result)
                goLogin()
            }
        }
        else { errorMsg(response?.message.toString()) }
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
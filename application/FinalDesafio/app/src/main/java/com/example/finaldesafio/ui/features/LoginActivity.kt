package com.example.finaldesafio.ui.features

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.core.Constants
import com.example.finaldesafio.repository.extensions.editTableText
import com.example.finaldesafio.repository.extensions.visibility
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult
import com.example.finaldesafio.ui.BaseActivity
import com.example.finaldesafio.ui.components.Progress
import com.example.finaldesafio.viewmodel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : BaseActivity() {
    private var progress: Progress? = null
    private val viewModel : LoginViewModel by viewModels()

    // fileds
    private var email : TextInputEditText? = null
    private var password : TextInputEditText? = null

    override fun getLayoutId(): Int = R.layout.activity_login
    override fun initViews() {
        findViewById<Toolbar>(R.id.toolbarOther).also { setupToolBar(it) }
        setActionBarTitle(R.string.title09)
        progress = findViewById(R.id.progressLogin)
        email    = findViewById(R.id.login_filed1)
        password = findViewById<TextInputEditText>(R.id.login_filed2).also {
            it.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> { enter() }
                }
                false
            }
        }
        findViewById<MaterialButton>(R.id.login_button).apply {
            setOnClickListener { enter() }
        }
        findViewById<MaterialButton>(R.id.bt_account_user).apply {
            setOnClickListener { goAccount(Constants.AccountAction.ADD_USER) }
        }
        findViewById<MaterialButton>(R.id.bt_account_comp).apply {
            setOnClickListener { goAccount(Constants.AccountAction.ADD_COMPANY) }
        }
        setLoading(false)
    }

    override fun initViewModel() {
        viewModel.apply {
            initViewModel(baseContext) {
                email?.setText(userSession?.userEmail)
                response.observe(this@LoginActivity) { loginAction(it) }
            }
        }
    }

    override fun setLoading(hasLoading: Boolean) { progress?.visibility(hasLoading) }

    private fun enter() {
        val userLogin = email?.editTableText()
        val passLogin = password?.editTableText()
        when {
            userLogin?.isEmpty() == true -> errorMsg(getString(R.string.login_field1_error))
            passLogin?.isEmpty() == true -> errorMsg(getString(R.string.login_field2_error))
            else -> {
                setLoading(true)
                userLogin?.let { passLogin?.let { it1 -> viewModel.getLogin(it, it1) } }
            }
        }
    }

    private fun goAccount(type: Constants.AccountAction) {
        Bundle().let { bundle ->
            bundle.putSerializable(Constants.TYPE, type)
            Intent(this, AccountActivity::class.java).apply {
                putExtras(bundle)
                startActivity(this)
                finish()
                animRightToLeft()
            }
        }
    }

    private fun loginAction(response: WsResult<User>?) {
        setLoading(false)
        if (response?.codeError == 0) {
            boxMsg(response.message.toString()) {
                viewModel.apply {
                    userClear()
                    setUserLogin(response.result)
                    Intent(baseContext, MainActivity::class.java).apply {
                        startActivity(this)
                        finish()
                        animRightToLeft()
                    }
                }
            }
        }
        else { errorMsg(response?.message.toString()) }
    }

    override fun back() { finish() }
}
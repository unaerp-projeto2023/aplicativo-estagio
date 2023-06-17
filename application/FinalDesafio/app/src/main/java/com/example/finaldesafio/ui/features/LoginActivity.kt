package com.example.finaldesafio.ui.features

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.example.finaldesafio.MainActivity

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.core.Constants
import com.example.finaldesafio.ui.BaseActivity
import com.example.finaldesafio.viewmodel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : BaseActivity() {
    private val viewModel : LoginViewModel by viewModels()

    // fileds
    private var email : TextInputEditText? = null
    private var password : TextInputEditText? = null

    override fun getLayoutId(): Int = R.layout.activity_login
    override fun initViews() {
        findViewById<Toolbar>(R.id.toolbarOther).also { setupToolBar(it) }
        setActionBarTitle(R.string.title09)
        // get fields
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
    }

    override fun initViewModel() {
        viewModel.apply {
            viewModel.apply {
                initViewModel(baseContext) {
                    response.observe(this@LoginActivity) {
                        it?.let {
                            if (it.codeError!! > 0) { errorMsg(it.message.toString()) }
                            else                    { goMainActivity(it.message.toString()) }
                        }
                    }
                }
            }
        }
    }

    private fun enter() {
        val txtEmail    = email?.editableText.toString()
        val txtPassword = password?.editableText.toString()

        viewModel.getLogin(txtEmail, txtPassword)
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

    private fun goMainActivity(message: String) {
        boxMsg(message) {
            Intent(baseContext, MainActivity::class.java).apply {
                startActivity(this)
                finish()
                animRightToLeft()
            }
        }
    }

    override fun back() { finish() }
}
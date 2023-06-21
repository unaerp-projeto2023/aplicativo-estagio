package com.example.finaldesafio.ui.fragments

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.finaldesafio.R
import com.example.finaldesafio.repository.extensions.editTableText
import com.example.finaldesafio.repository.model.AnnouncementSearch
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult
import com.example.finaldesafio.ui.BaseActivity
import com.example.finaldesafio.ui.BaseFragment
import com.example.finaldesafio.ui.features.LoginActivity
import com.example.finaldesafio.ui.features.MainActivity
import com.example.finaldesafio.viewmodel.EditUserViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class FragmentAccountEdit : BaseFragment() {
    private val viewModel : EditUserViewModel by viewModels()
    private var userName: TextInputEditText? = null
    private var userEmail: TextInputEditText? = null

    override fun getLayoutId(): Int = R.layout.fragment_account_edit
    override fun initViews() {
        activity?.let {
            userName  = it.findViewById<TextInputEditText?>(R.id.account_edit_filed1).apply { requestFocus() }
            userEmail = it.findViewById(R.id.account_edit_filed2)
            it.findViewById<MaterialButton>(R.id.account_button_edit_save).apply {
                setOnClickListener { action() }
            }
        }
        setLoading(false)
    }
    override fun initViewModel() {
        activity?.let {
            (it as? MainActivity)?.let { main ->
                main.viewModel.userSession?.let { session ->
                    userName?.setText(session.userName)
                    userEmail?.setText(session.userEmail)
                }
            }
        }
        viewModel.apply {
            initViewModel(context) {
                response.observe(viewLifecycleOwner) { loginAction(it) }
            }
        }
    }

    override fun action() {
        val userEditName  = userName?.editTableText()
        val userEditLogin = userEmail?.editTableText()

        if (userEditName?.isEmpty() == false && userEditLogin?.isEmpty() == false) {
            setLoading(true)
            viewModel.saveUser(User().also {
                it.userId    = viewModel.userSession?.userId!!
                it.userName  = userEditName.toString()
                it.userEmail = userEditLogin.toString()
            })
        }
        else {
            errorMsg(R.string.account_error2)
        }
    }

    private fun loginAction(response: WsResult<User>?) {
        setLoading(false)
        if (response?.codeError == 0) {
            boxMsg(response.message.toString()) {
                viewModel.apply {
                    clear()
                    setLogin(userEmail?.editTableText().toString())
                    Intent(context, LoginActivity::class.java).apply {
                        startActivity(this)
                        (activity as? BaseActivity)?.apply {
                            finish()
                            animLeftToRight()
                        }
                    }
                }
            }
        }
        else { errorMsg(response?.message.toString()) }
    }

    override fun search(search: AnnouncementSearch) {}

    companion object {
        fun newInstance() : FragmentAccountEdit = FragmentAccountEdit()
    }
}
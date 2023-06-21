package com.example.finaldesafio.ui.fragments

import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.extensions.addChangeTextDecimal
import com.example.finaldesafio.repository.extensions.getDateSave
import com.example.finaldesafio.repository.extensions.getDoubleValue
import com.example.finaldesafio.repository.extensions.getNow
import com.example.finaldesafio.repository.model.Announcement
import com.example.finaldesafio.repository.model.AnnouncementSearch
import com.example.finaldesafio.repository.model.WsResult
import com.example.finaldesafio.ui.BaseFragment
import com.example.finaldesafio.ui.components.DateDialog
import com.example.finaldesafio.viewmodel.AddAnnouncementViewModel

class FragmentAnnounce : BaseFragment() {
    private val viewModel: AddAnnouncementViewModel by viewModels()
    private var announcementDescription: TextInputEditText? = null
    private var announcementContactName : TextInputEditText? = null
    private var announcementContactEmail : TextInputEditText? = null
    private var announcementContactPhone : TextInputEditText? = null
    private var announcementCompanyShow : AppCompatCheckBox? = null
    private var announcementAreaDescription : TextInputEditText? = null
    private var announcementLocality : TextInputEditText? = null
    private var announcementStartDate : TextInputEditText? = null
    private var announcementFinalDate : TextInputEditText? = null
    private var announcementRemuneration : TextInputEditText? = null

    override fun getLayoutId(): Int = R.layout.fragment_announce
    override fun initViews() {
        activity?.let {
            announcementDescription     = it.findViewById<TextInputEditText?>(R.id.announcement_filed1).apply { requestFocus() }
            announcementContactName     = it.findViewById(R.id.announcement_filed2)
            announcementContactEmail    = it.findViewById(R.id.announcement_filed3)
            announcementContactPhone    = it.findViewById(R.id.announcement_filed4)
            announcementAreaDescription = it.findViewById(R.id.announcement_filed5)
            announcementLocality        = it.findViewById(R.id.announcement_filed6)
            announcementCompanyShow     = it.findViewById(R.id.announcement_filed11)
            announcementStartDate       = it.findViewById<TextInputEditText?>(R.id.announcement_filed7).also { date1 ->
                date1.setText("dma".getNow())
            }
            announcementFinalDate = it.findViewById<TextInputEditText?>(R.id.announcement_filed8).also { date2 ->
                date2.setText("dma".getNow())
            }
            announcementRemuneration = it.findViewById<TextInputEditText?>(R.id.announcement_filed9).also { currency ->
                currency.addChangeTextDecimal()
            }
            it.findViewById<TextInputLayout>(R.id.announcement_filed7_input).setEndIconOnClickListener {
                announcementStartDate.setDatePicker("Date Start")
            }
            it.findViewById<TextInputLayout>(R.id.announcement_filed8_input).setEndIconOnClickListener {
                announcementFinalDate.setDatePicker("Date Final")
            }
        }
        setLoading(false)
    }
    override fun initViewModel() {
        viewModel.apply {
            initViewModel(context) {
                response.observe(viewLifecycleOwner) { addAction(it) }
            }
        }
    }

    override fun action() {
        val description     = announcementDescription?.editableText.toString()
        val contactName     = announcementContactName?.editableText.toString()
        val contactEmail    = announcementContactEmail?.editableText.toString()
        val contactPhone    = announcementContactPhone?.editableText.toString()
        val areaDescription = announcementAreaDescription?.editableText.toString()
        val locality        = announcementLocality?.editableText.toString()
        val startDate       = announcementStartDate?.editableText.toString()
        val finalDate       = announcementFinalDate?.editableText.toString()
        val remuneration    = announcementRemuneration?.editableText.toString()
        val companyShow     = announcementCompanyShow?.isChecked

        setLoading(true)
        viewModel.saveAnnouncement(Announcement().also {
            it.announcementUserId          = viewModel.userSession?.userId!!
            it.announcementDescription     = description
            it.announcementContactName     = contactName
            it.announcementContactEmail    = contactEmail
            it.announcementContactPhone    = contactPhone
            it.announcementCompanyShow     = if (companyShow == true) "S" else "N"
            it.announcementAreaDescription = areaDescription
            it.announcementLocality        = locality
            it.announcementStartDate       = startDate.getDateSave()
            it.announcementFinalDate       = finalDate.getDateSave()
            it.announcementRemuneration    = remuneration.getDoubleValue()
        })
    }

    private fun TextInputEditText?.setDatePicker(tag: String?) {
        val dialog = DateDialog.newInstance(this).also {
            it.set(this?.editableText.toString())
        }
        dialog.show(parentFragmentManager, tag)
    }

    private fun addAction(response: WsResult<Nothing>?) {
        setLoading(false)
        if (response?.codeError == 0) {
            boxMsg(response.message.toString()) { clearFields() }
        }
        else { errorMsg(response?.message.toString()) }
    }

    private fun clearFields() {
        announcementDescription?.apply {
            setText("")
            requestFocus()
        }
        announcementContactName?.setText("")
        announcementContactEmail?.setText("")
        announcementContactPhone?.setText("")
        announcementAreaDescription?.setText("")
        announcementLocality?.setText("")
        announcementStartDate?.setText("dma".getNow())
        announcementFinalDate?.setText("dma".getNow())
        announcementRemuneration?.setText("0,00")
        announcementCompanyShow?.isChecked = false
    }

    override fun search(search: AnnouncementSearch) {}

    companion object {
        fun newInstance() : FragmentAnnounce = FragmentAnnounce()
    }

}
package com.example.finaldesafio.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.extensions.getDate
import com.example.finaldesafio.repository.extensions.setFormatDecimal
import com.example.finaldesafio.repository.extensions.visibility
import com.example.finaldesafio.repository.model.Announcement

class ItemAnnouncementAdapter: RecyclerView.Adapter<ItemAnnouncementAdapter.ViewHolder>() {
    private var list    = emptyList<Announcement>().toMutableList()
    private var hasUser = true

    var onItemClicked: ((Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_announcement, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(items: MutableList<Announcement>?, hasUser: Boolean = true) {
        this.hasUser = hasUser
        items?.let {
            if (it.isNotEmpty()) {
                list.clear()
                list.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = list.size
    override fun getItemId(position: Int): Long = list[position].announcementId

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { item ->
            holder.apply {
                var label1 = ""
                var label2 = ""
                var label3 = ""
                var label4 = ""
                var label5 = ""
                var label6 = ""
                var label7 = ""
                itemView.let {
                    label1 = it.context.getString(R.string.list_label1)
                    label2 = it.context.getString(R.string.list_label2)
                    label3 = it.context.getString(R.string.list_label3)
                    label4 = it.context.getString(R.string.list_label4)
                    label5 = it.context.getString(R.string.list_label5)
                    label6 = it.context.getString(R.string.list_label6)
                    label7 = it.context.getString(R.string.list_label7)
                }
                txtJob.text          = item.announcementDescription
                txtLocality.text     = "$label1 ${item.announcementLocality}"
                txtRemuneration.text = "$label2 R$ ${item.announcementRemuneration.setFormatDecimal()}"
                txtContactEmail.text = "$label4 ${item.announcementContactEmail}"
                txtContactPhone.text = "$label5 ${item.announcementContactPhone}"
                txtDataIni.text      = "$label6 ${item.announcementStartDate?.getDate("ext")}"
                txtTotal.text        = "$label7 ${item.announcementTotal}"

                if (item.announcementCompanyShow == "S") {
                    txtCompany.text = "$label3 ${item.announcementCompany}"
                }
                else {
                    txtCompany.visibility(false)
                }
                txtTotal.visibility(!(hasUser || (item.myJobs == 1)))
                if (hasUser) {
                    btnConfirmation.let { btn ->
                        btn.setOnClickListener { onItemClicked?.invoke(item.announcementId) }
                    }
                }
                else {
                    btnConfirmation.visibility(false)
                }
            }
        }
    }

    private fun getItem(position: Int): Announcement = list[position]

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtJob         : TextView       = itemView.findViewById(R.id.titleJob)
        val txtLocality    : TextView       = itemView.findViewById(R.id.labelLocality)
        val txtRemuneration: TextView       = itemView.findViewById(R.id.labelRemuneration)
        val txtCompany     : TextView       = itemView.findViewById(R.id.labelCompany)
        val txtContactEmail: TextView       = itemView.findViewById(R.id.labelContactEmail)
        val txtContactPhone: TextView       = itemView.findViewById(R.id.labelContactPhone)
        val txtDataIni     : TextView       = itemView.findViewById(R.id.labelDataIni)
        val txtTotal       : TextView       = itemView.findViewById(R.id.labelTotal)
        val btnConfirmation: MaterialButton = itemView.findViewById(R.id.btnConfirmation)
    }
}
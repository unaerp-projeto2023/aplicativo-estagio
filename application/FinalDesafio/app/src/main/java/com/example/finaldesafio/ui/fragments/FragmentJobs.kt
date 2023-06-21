package com.example.finaldesafio.ui.fragments

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.finaldesafio.R
import com.example.finaldesafio.repository.model.AnnouncementList
import com.example.finaldesafio.repository.model.AnnouncementSearch
import com.example.finaldesafio.repository.model.WsResult
import com.example.finaldesafio.ui.BaseFragment
import com.example.finaldesafio.ui.adapter.ItemAnnouncementAdapter
import com.example.finaldesafio.viewmodel.MyAnnouncementViewModel

class FragmentJobs : BaseFragment() {
    private val viewModel: MyAnnouncementViewModel by viewModels()
    private var itemAdapter: ItemAnnouncementAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_jobs
    override fun initViews() {
        ItemAnnouncementAdapter().apply {
            onItemClicked = { setSelectionAnnouncement(it) }
        }.also {
            itemAdapter = it
        }
        activity?.findViewById<RecyclerView>(R.id.rv_jobs)?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1)
            adapter       = itemAdapter
        }
    }
    override fun initViewModel() {
        viewModel.apply {
            initViewModel(context) {
                updateList()
                response.observe(viewLifecycleOwner) { loadList(it) }
                saved.observe(viewLifecycleOwner)    { savedSelection(it) }
            }
        }
    }

    private fun loadList(response: WsResult<AnnouncementList>?) {
        setLoading(false)
        if (response?.codeError == 0) {
            response.result?.items.let { itemAdapter?.updateList(it, true) }
        }
        else { errorMsg(response?.message.toString()) }
    }

    override fun action() {}
    override fun search(search: AnnouncementSearch) {
        setLoading(true)
        viewModel.getList(search.also {
            it.companyId  = 0
            it.userId     = viewModel.userSession?.userId!!
            it.myJobs     = 0
        })
    }

    private fun setSelectionAnnouncement(announcementId: Long) {
        boxConfirm(R.string.text_confirm) {
            setLoading(true)
            if (it) { viewModel.getSelectAnnouncement(announcementId) }
        }
    }

    private fun updateList() {
        viewModel.getList(AnnouncementSearch().also {
            it.companyId  = 0
            it.userId     = viewModel.userSession?.userId!!
            it.myJobs     = 0
        })
    }

    private fun savedSelection(response: WsResult<Nothing>?) {
        setLoading(false)
        if (response?.codeError == 0) {
            boxMsg(response.message.toString()) { updateList() }
        }
        else  { errorMsg(response?.message.toString()) }
    }

    companion object {
        fun newInstance() : FragmentJobs = FragmentJobs()
    }
}
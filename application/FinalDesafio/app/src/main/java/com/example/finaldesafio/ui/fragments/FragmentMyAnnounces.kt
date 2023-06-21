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

class FragmentMyAnnounces : BaseFragment() {
    private val viewModel: MyAnnouncementViewModel by viewModels()
    private var itemAdapter: ItemAnnouncementAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_my_announces
    override fun initViews() {
        ItemAnnouncementAdapter().also {itemAdapter = it }
        activity?.findViewById<RecyclerView>(R.id.rv_my_announcement)?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 1)
            adapter       = itemAdapter
        }
    }
    override fun initViewModel() {
        viewModel.apply {
            initViewModel(context) {
                getList(AnnouncementSearch().also {
                    it.companyId = userSession?.userId!!
                    it.userId    = 0
                    it.myJobs    = 0
                })
                response.observe(viewLifecycleOwner) { loadList(it) }
            }
        }
    }

    private fun loadList(response: WsResult<AnnouncementList>?) {
        setLoading(false)
        if (response?.codeError == 0) {
            response.result?.items.let { itemAdapter?.updateList(it, false) }
        }
        else { errorMsg(response?.message.toString()) }
    }

    override fun action() {}
    override fun search(search: AnnouncementSearch) {}

    companion object {
        fun newInstance() : FragmentMyAnnounces = FragmentMyAnnounces()
    }

}
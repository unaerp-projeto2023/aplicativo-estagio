package com.example.finaldesafio.repository.core

import com.example.finaldesafio.repository.extensions.serverWrapper
import com.example.finaldesafio.repository.model.Announcement
import com.example.finaldesafio.repository.model.AnnouncementList
import com.example.finaldesafio.repository.model.AnnouncementSearch
import com.example.finaldesafio.repository.model.User
import com.example.finaldesafio.repository.model.WsResult

class RepositoryServer {
    private var service = RetrofitMobile().apiService

    fun addUser(user: User, callback: (WsResult<User>) -> Unit) {
        service.addUser(
            0,
            user.userName,
            user.userPassword,
            user.userEmail,
            user.userType
        ).serverWrapper { callback.invoke(it) }
    }

    fun editUser(user: User, callback: (WsResult<User>) -> Unit) {
        service.editUser(
            user.userId,
            user.userName,
            user.userEmail
        ).serverWrapper { callback.invoke(it) }
    }

    fun addAnnouncement(data: Announcement, callback: (WsResult<Nothing>) -> Unit) {
        service.addAnnouncement(
            0,
            data.announcementUserId,
            data.announcementDescription,
            data.announcementContactName,
            data.announcementContactEmail,
            data.announcementContactPhone,
            data.announcementCompanyShow,
            data.announcementAreaDescription,
            data.announcementLocality,
            data.announcementStartDate,
            data.announcementFinalDate,
            data.announcementRemuneration.toString()
        ).serverWrapper { callback.invoke(it) }
    }

    fun announcementList(data: AnnouncementSearch, callback: (WsResult<AnnouncementList>) -> Unit) {
        service.announcementList(
            data.companyId,
            data.userId,
            data.description,
            data.areaDescription,
            data.locality,
            data.myJobs
        ).serverWrapper { callback.invoke(it) }
    }

    fun getLogin(userLogin: String, userPassword: String, callback: (WsResult<User>) -> Unit) {
        service.getLogin(userLogin, userPassword).serverWrapper {
            callback.invoke(it)
        }
    }

    fun selection(id_announcement: Long, id_user: Long, callback: (WsResult<Nothing>) -> Unit) {
        service.selection(id_announcement, id_user).serverWrapper {
            callback.invoke(it)
        }
    }
}
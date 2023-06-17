package com.example.finaldesafio.repository.model

import java.io.Serializable

/**
 * Tabela de Anúncio (Vaga)
 */
data class Announcement(
    val id: Int? = 0,
    val idCompany: Int? = 0,
    val idArea: Int? = 0,
    val idLocality: Int? = 0,
    val description: String? = null,
    val contactName: String? = null,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val areaDescription: String? = null,
    val locality: String? = null,
    val startDate: String? = null,
    val finalDate: String? = null,
    val remuneration: Double? = 0.00
) : Serializable

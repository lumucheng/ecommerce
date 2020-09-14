package com.ecommerce.repository

import com.ecommerce.model.Data
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository

interface DataRepository : JpaRepository<Data, Long> {
    fun findByCountry(country: String, pageable: Pageable?): Page<Data?>?
}


package com.codingschool.deskbooking.ui.administration

import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse

interface AdminActionListener {
    fun onFixDeskRequestConfirm(fixDeskRequest: FixDeskResponse)
    fun onFixDeskRequestDecline(fixDeskRequest: FixDeskResponse)
}
package com.example.meetapp.data.repositories

import com.example.meetapp.data.services.DataEventService

class PresentersRepository(private val dataEventService: DataEventService) {
    //key da api sendo armazenada e controlada pelo repository
    private val doityServicesKey = "59c654f003e03cb1f34fb921af330a24cb619c99"

    fun getAllPresenters() = dataEventService.getAllPresenters(doityServicesKey)

    fun getAllSchedules() = dataEventService.getAllPresenters(doityServicesKey)

    fun getAllColaborators() = dataEventService.getColaborators(doityServicesKey)

    fun getAllNotifications() = dataEventService.getNotifications(doityServicesKey)
}
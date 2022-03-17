package com.example.exammodul06.database.repository

import android.app.Application
import com.example.exammodul06.database.AppDatabase
import com.example.exammodul06.database.dao.RoomDao
import com.example.exammodul06.database.entity.CardRoom

class RoomRepository(application: Application) {
    private var roomDao:RoomDao

    init {
        val db: AppDatabase = AppDatabase.getInstance(application)
        roomDao = db.roomDao
    }

    fun saveExamModel(cardRoom: CardRoom) {
        roomDao.insertCardRoom(cardRoom)
    }

    fun getCardRoomList(): List<CardRoom> {
        return roomDao.getCardRoomList()
    }

    fun updateCardRoom(id: Int, online: String){
        roomDao.updateCardRoom(id, online)
    }

}
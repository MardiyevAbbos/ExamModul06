package com.example.exammodul06.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exammodul06.database.entity.CardRoom

@Dao
interface RoomDao {

    @Insert()
    fun insertCardRoom(cardRoom: CardRoom)

    @Query("SELECT * FROM table_cards")
    fun getCardRoomList(): List<CardRoom>

    @Query("UPDATE table_cards SET isOnline =:online WHERE id=:id")
    fun updateCardRoom(id: Int, online: String)

}
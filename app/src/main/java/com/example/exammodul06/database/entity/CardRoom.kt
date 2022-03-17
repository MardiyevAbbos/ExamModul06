package com.example.exammodul06.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exammodul06.model.CardResp

@Entity(tableName = "table_cards")
data class CardRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val card_number: String,
    val month: String,
    val year: String,
    val cvv: String,
    val holder_name: String,
    val isOnline: Boolean
)

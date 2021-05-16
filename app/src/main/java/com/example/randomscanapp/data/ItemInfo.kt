package com.example.randomscanapp.data

import com.example.randomscanapp.data.enums.Barcode
import com.example.randomscanapp.data.enums.Color
import com.example.randomscanapp.data.enums.Type
import java.util.*

data class ItemInfo(
    val barcode: Barcode,
    val type: Type,
    val color: Color,
    val generationTimestamp: Long
) {
    companion object {
        fun factory(): ItemInfo {
            return ItemInfo(
                getRandomEnumMember(Barcode.values()),
                getRandomEnumMember(Type.values()),
                getRandomEnumMember(Color.values()),
                Date().time
            )
        }

        private fun <T> getRandomEnumMember(array: Array<T>): T {
            val rangeEnd = array.size - 1
            val random = (0..rangeEnd).random()
            return array[random]
        }
    }
}

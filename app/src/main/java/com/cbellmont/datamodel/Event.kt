package com.cbellmont.datamodel

import kotlin.random.Random


enum class EventType {
    BIRTHDAY, ANNIVERSARY, PROMOTION, WEDDING, OTHERS
}

object EventFactory {

    fun getRandom(): MutableList<Event> {
        return when(Random.nextInt(4)) {
            0 ->
                mutableListOf(
                Event(EventType.BIRTHDAY, mutableListOf("Móvil Android")),
                Event(EventType.ANNIVERSARY, mutableListOf("Tarjeta de felicitación")))
            1 ->
                mutableListOf(
                Event(EventType.PROMOTION, mutableListOf("Corbata nueva")))
            2 ->
                mutableListOf(
                Event(EventType.WEDDING, mutableListOf("Albún de fotos")),
                Event(EventType.ANNIVERSARY, mutableListOf("Actualización del albúm de fotos")))
            3 ->
                mutableListOf(
                Event(EventType.OTHERS, mutableListOf("Cámara de fotos")))
            else ->
                mutableListOf()
        }
    }
}


data class Event(
    var name: EventType,
    var giftIdea: MutableList<String>
)


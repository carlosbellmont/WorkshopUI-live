package com.cbellmont.datamodel

import com.google.gson.annotations.SerializedName

data class User(
    var name: Name,
    var gender: String,
    var email: String,
    var picture: Picture,
    var events: MutableList<Event>
) {
    fun getCompleteName() : String {
        return String.format("%s %s", name.name, name.surname)
    }

    fun getSmallPhoto() : String {
        return picture.medium
    }

    fun getLargePhoto() : String {
        return picture.large
    }

    fun getEmailEnString(): String {
        return email
    }
}

data class Name (
    @SerializedName("first") var name: String,
    @SerializedName("last") var surname: String
)

data class Picture (
    var large : String,
    var medium : String,
    var thumbnail : String
)
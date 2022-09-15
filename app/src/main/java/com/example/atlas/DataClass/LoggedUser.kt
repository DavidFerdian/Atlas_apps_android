package com.example.atlas.DataClass

data class LoggedUser(
    var isLoggedIn: Boolean? = null,
    var loggedInMemberId: String? = null,
    var loggedInUserName: String? = null,
    var loginToken: String? = null
)
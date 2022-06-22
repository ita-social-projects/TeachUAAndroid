package com.softserve.teachua.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

var baseImageUrl = "https://speak-ukrainian.org.ua/dev/"
const val baseMailImage = "/static/images/contacts/mail.svg"
var baseUrl = "https://speak-ukrainian.org.ua/dev/api/"
const val baseAboutUsImg = "static/images/service/banerAboutUs.jpg"
const val profileBackground = "static/media/user-background.a64a2ede.png"
val roles = listOf("ВІДВІДУВАЧ", "АДМІНІСТРАТОР", "КЕРІВНИК")


object TeachUaLinksConstants {
    const val Facebook = "https://www.facebook.com/teach.in.ukrainian"
    const val Youtube = "https://www.youtube.com/channel/UCP38C0jxC8aNbW34eBoQKJw"
    const val Instagram = "https://www.instagram.com/teach.in.ukrainian/"
    const val Mail = "mailto:teach.in.ukrainian@gmail.com"
}

@HiltAndroidApp
class App : Application()
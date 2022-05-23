package com.softserve.teachua.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

const val baseImageUrl = "https://speak-ukrainian.org.ua/dev/"
const val baseMailImage = "/static/images/contacts/mail.svg"
const val baseUrl = "https://speak-ukrainian.org.ua/dev/api/"
val roles = listOf("ВІДВІДУВАЧ", "АДМІНІСТРАТОР", "КЕРІВНИК")


object TeachUaLinksConstants {
    const val Facebook = "https://www.facebook.com/teach.in.ukrainian"
    const val Youtube = "https://www.youtube.com/channel/UCP38C0jxC8aNbW34eBoQKJw"
    const val Instagram = "https://www.instagram.com/teach.in.ukrainian/"
    const val Mail = "mailto:teach.in.ukrainian@gmail.com"
}

@HiltAndroidApp
class App : Application()
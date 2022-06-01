package com.softserve.teachua.app.tools

import org.jsoup.Jsoup

class CategoryToUrlTransformer {

    fun toUrlEncode(categoryName: String): String {

        return if (categoryName.contains(", ")) {
            categoryName.replace(", ", ";+")
        } else
            return categoryName
    }

    fun parseHtml(html: String?): String {
        return html?.let { Jsoup.parse(it).text() }.toString()


    }
}
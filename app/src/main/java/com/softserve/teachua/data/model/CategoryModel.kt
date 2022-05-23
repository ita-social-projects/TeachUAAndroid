package com.softserve.teachua.data.model

data class CategoryModel(
    var categoryId: Int,
    var categorySortby: Int,
    var categoryName: String,
    var categoryDescription: String,
    var categoryUrlLogo: String,
    var categoryBackgroundColor: String,
    var categoryTagBackgroundColor: String,
    var categoryTagTextColor: String
) {
}
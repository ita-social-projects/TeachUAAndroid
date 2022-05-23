package com.softserve.teachua.domain.interfaces

import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.CategoryModel

interface CategoriesUseCasesInterface {
    suspend fun getAllCategories(): Resource<List<CategoryModel>>
}

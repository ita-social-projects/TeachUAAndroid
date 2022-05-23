package com.softserve.teachua.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denzcoskun.imageslider.models.SlideModel
import com.softserve.teachua.app.baseImageUrl
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.model.BannerModel
import com.softserve.teachua.data.model.CategoryModel
import com.softserve.teachua.domain.interfaces.BannerUseCasesInterface
import com.softserve.teachua.domain.interfaces.CategoriesUseCasesInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoriesUseCases: CategoriesUseCasesInterface,
    private val bannerUseCases: BannerUseCasesInterface,
) : ViewModel() {

    //might be some problems due to implementing "by lazy". It changes only once.
    val bansList : ArrayList<SlideModel> by lazy { loadBansList() }

    private var _banners = MutableStateFlow<Resource<List<BannerModel>>>(Resource.loading())

    val banners: StateFlow<Resource<List<BannerModel>>>
        get() = _banners

    private var _categories = MutableStateFlow<Resource<List<CategoryModel>>>(Resource.loading())

    val categories: StateFlow<Resource<List<CategoryModel>>>
        get() = _categories


    val staticMainImg: String
        get() = baseImageUrl + "static/images/about/challenge_2.png"

    fun loadData() = viewModelScope.launch {
        loadBanners()
        loadCategories()
    }

    private fun loadBansList(): ArrayList<SlideModel> {
        val bansList = ArrayList<SlideModel>()
        banners.value.data?.let {
            for (ban in it) {
                bansList.add(
                    SlideModel(
                        baseImageUrl + ban.bannerPicture,
                        ban.bannerTitle + "\n\n" + ban.bannerSubtitle,
                    )
                )
            }
        }
        return bansList
    }

    private suspend fun loadBanners() {
        _banners.value = bannerUseCases.getAllBanners()
    }

    private suspend fun loadCategories() {
        _categories.value = categoriesUseCases.getAllCategories()
    }

}
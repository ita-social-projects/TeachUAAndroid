package com.softserve.teachua.ui.clubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.data.dto.AdvancedSearchClubDto
import com.softserve.teachua.data.dto.SearchClubDto
import com.softserve.teachua.data.model.CategoryModel
import com.softserve.teachua.data.model.CityModel
import com.softserve.teachua.data.model.DistrictModel
import com.softserve.teachua.data.model.StationModel
import com.softserve.teachua.data.retrofit.Common
import com.softserve.teachua.domain.interfaces.CategoriesUseCasesInterface
import com.softserve.teachua.domain.interfaces.CitiesUseCasesInterface
import com.softserve.teachua.domain.interfaces.DistrictUseCasesInterface
import com.softserve.teachua.domain.interfaces.StationsUseCasesInterface
import com.softserve.teachua.domain.pagination.ClubsPageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubsViewModel @Inject constructor(
    private val districtUseCases: DistrictUseCasesInterface,
    private val stationsUseCases: StationsUseCasesInterface,
    private val citiesUseCases: CitiesUseCasesInterface,
    private val categoriesUseCases: CategoriesUseCasesInterface,

    ) : ViewModel() {

    private var _searchClubDto =
        MutableLiveData<SearchClubDto>(SearchClubDto("", "", false, "", 0))
    private var _advancedSearchClubDto = MutableLiveData<AdvancedSearchClubDto>(
        AdvancedSearchClubDto("",
            null,
            "",
            "",
            "",
            "name,asc",
            0,
            mutableListOf(),
            isOnline = false,
            isCenter = false,
            isAdvanced = false))

    var listOfSearchedCategories: MutableList<String> = mutableListOf()

    val searchClubDto: LiveData<SearchClubDto>
        get() = _searchClubDto

    val advancedSearchClubDto: LiveData<AdvancedSearchClubDto>
        get() = _advancedSearchClubDto

    private var _cities = MutableStateFlow<Resource<List<CityModel>>>(Resource.loading())

    val cities: StateFlow<Resource<List<CityModel>>>
        get() = _cities

    private var _districts = MutableStateFlow<Resource<List<DistrictModel>>>(
        Resource.loading())

    val districts: StateFlow<Resource<List<DistrictModel>>>
        get() = _districts

    private var _stations = MutableStateFlow<Resource<List<StationModel>>>(Resource.loading())

    val stations: StateFlow<Resource<List<StationModel>>>
        get() = _stations

    private var _categories = MutableStateFlow<Resource<List<CategoryModel>>>(Resource.loading())

    val categories: StateFlow<Resource<List<CategoryModel>>>
        get() = _categories

    fun loadCities() =
        viewModelScope.launch {
            println("Loaded Cities")
            _cities.value = citiesUseCases.getAllCities()
        }

    fun loadDistricts(cityName: String) =
        viewModelScope.launch {
            _districts.value = Resource.loading()
            _districts.value = districtUseCases.getAllDistricts(cityName)
        }

    fun loadStations(cityName: String) =
        viewModelScope.launch {
            _stations.value = Resource.loading()
            _stations.value = stationsUseCases.getAllStations(cityName)
        }

    fun loadCategories() =
        viewModelScope.launch {
            _categories.value = Resource.loading()
            _categories.value = categoriesUseCases.getAllCategories()
        }


    val clubs = Pager(config = PagingConfig(pageSize = 2), pagingSourceFactory = {
        ClubsPageSource(Common.retrofitService,
            searchClubDto.value!!,
            advancedSearchClubDto.value!!)
    }).flow.cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
    }

}
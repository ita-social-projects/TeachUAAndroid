package com.softserve.teachua.ui.clubs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softserve.teachua.ui.MainActivity
import com.softserve.teachua.R
import com.softserve.teachua.app.tools.CategoryToUrlTransformer
import com.softserve.teachua.app.enums.Resource
import com.softserve.teachua.databinding.FragmentClubsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.adv_search.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ClubsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentClubsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val clubsViewModel: ClubsViewModel by viewModels()
    private lateinit var clubsAdapter: ClubsAdapter
    private lateinit var dialog: Dialog

    private var cities = listOf<String>()
    private var districts = mutableListOf<String>()
    private var stations = mutableListOf<String>()
    private var categories = listOf<String>()
    //var listOfSearchedCategories = arrayListOf<String>()

    private var districtByCity = "Київ"
    private var checkboxCounter = 0
    private lateinit var toolbar: Toolbar

    private var query = ""
    private var layoutManager =
        LinearLayoutManager(activity?.baseContext, RecyclerView.VERTICAL, false)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        createViewModel()
        _binding = FragmentClubsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.searchEdit.setupClearButtonWithAction()
        checkboxCounter = 0
        createAdvancedSearchDialog()
        setAllClickListenersAndDefaultValues()
        initClubs()
        initDataForAllAdvSpinners()
        updateViewModel()
        updateToolbar()


        return root
    }

    private fun setAllClickListenersAndDefaultValues() {

        binding.searchAdvBtn.setOnClickListener(this)
        binding.searchBtn.setOnClickListener(this)
        binding.clearEdit.setOnClickListener(this)
        binding.openDrawer.setOnClickListener(this)
        dialog.club_adv_search_radioBtn.isChecked = true
        dialog.center_adv_search_radioBtn.isChecked = false
        dialog.isOnline.isChecked = false
    }

    private fun whenLoadingClubs() {
        binding.progressBarClubs.visibility = View.VISIBLE
        binding.errorText.isVisible = false
        binding.rcv.visibility = View.GONE

    }

    private fun whenErrorLoadingClubs() {

        binding.progressBarClubs.visibility = View.GONE
        binding.rcv.isInvisible = true
        binding.errorText.isVisible = true
    }

    private fun whenDataIsClear() {
        println("Data Is Clear")
        binding.progressBarClubs.visibility = View.GONE
        binding.rcv.isInvisible = true
        binding.errorText.text = "Cant load matching results for your search"
        binding.errorText.isVisible = true

    }

    private fun whenDataIsLoaded() {
        binding.progressBarClubs.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.rcv.visibility = View.VISIBLE

    }


    private fun createViewModel() {


        clubsViewModel.loadCities()
        clubsViewModel.loadCategories()
        updateDistricts()


    }

    private fun destroyToolbar() {
        (activity as MainActivity).showToolbar()
    }

    private fun updateToolbar() {
        (activity as MainActivity).hideToolbar()
    }

    private fun updateViewModel() {

        clubsViewModel.viewModelScope.launch {

            clubsViewModel.clubs.collectLatest { pagingData -> clubsAdapter.submitData(pagingData) }


        }
    }

    private fun updateDistricts() {

        lifecycleScope.launch {
            clubsViewModel.loadDistricts(districtByCity)

        }


    }

    private fun updateStations() {

        lifecycleScope.launch {
            clubsViewModel.loadStations(districtByCity)

        }
    }


    private fun initClubs() {

        binding.rcv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        clubsAdapter = ClubsAdapter(requireContext())

        binding.rcv.adapter = clubsAdapter.withLoadStateHeaderAndFooter(
            header = ClubsLoadStateAdapter { clubsAdapter.retry() },
            footer = ClubsLoadStateAdapter { clubsAdapter.retry() })


    }

    private fun initDataForAllAdvSpinners() {
        lifecycleScope.launch {

            getCitiesForSpinner()
            getDistrictsForSpinner()
            getStationsForSpinner()
            getCategoriesForCheckBox()


        }
    }

    private fun initAdapterState() {
        lifecycleScope.launch {
            clubsAdapter.loadStateFlow.collectLatest { loadStates ->


                when (loadStates.refresh) {

                    is LoadState.Loading -> {

                        println("Loading Case")
                        whenLoadingClubs()
                    }

                    is LoadState.Error -> {
                        println("Error Case")
                        whenErrorLoadingClubs()
                    }


                    else -> {
                        println("Else Case")
                        whenDataIsLoaded()

                        if (clubsAdapter.itemCount < 1) {
                            whenDataIsClear()
                            println("clubs ada" + clubsAdapter.itemCount)
                        }


                    }
                }
            }
        }


    }


    @SuppressLint("ClickableViewAccessibility")
    fun EditText.setupClearButtonWithAction() {

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable?.isNotEmpty() == true) binding.clearEdit.visibility =
                    View.VISIBLE else binding.clearEdit.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                Unit
        })

    }


    private fun citySpinnerPicker(cities: ArrayList<String>) {

        //println("cities" + cities.toString())
        val citySpinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
            R.layout.item_dropdown, cities)

        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCities.adapter = citySpinnerAdapter

        binding.spinnerCities.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    pos: Int,
                    id: Long,
                ) {
                    println(parent!!.getItemAtPosition(pos))
                    //iewModelStore.clear()

                    clubsViewModel.searchClubDto.value?.cityName =
                        parent.getItemAtPosition(pos).toString()
                    Toast.makeText(requireContext(),
                        parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show()
                    //clubsViewModel.advancedSearchClubDto.value?.isAdvanced = false
                    //createViewModel()
                    //addDataToVM()
                    clubsAdapter.refresh()


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }


            }

    }

    private fun showSuccess() {
        binding.contentClubs.visibility = View.VISIBLE
        binding.progressBarClubs.visibility = View.GONE
        binding.connectionProblemClubs.visibility = View.GONE
    }

    private fun showLoading() {
        binding.contentClubs.visibility = View.GONE
        binding.progressBarClubs.visibility = View.VISIBLE
        binding.connectionProblemClubs.visibility = View.GONE
    }

    private fun showError() {
        binding.contentClubs.visibility = View.GONE
        binding.progressBarClubs.visibility = View.GONE
        binding.connectionProblemClubs.visibility = View.VISIBLE
    }

    private fun getCitiesForSpinner() {

        lifecycleScope.launch {
            clubsViewModel.cities.collect { data ->

                when (data.status) {

                    Resource.Status.SUCCESS -> {
                        showSuccess()
                        cities = data.data?.map { it.cityName } as ArrayList
                        citySpinnerPicker(cities as ArrayList<String>)
                    }

                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.FAILED -> showError()


                }
            }


        }

    }

    private fun getDistrictsForSpinner() {


        lifecycleScope.launch {

            clubsViewModel.districts.collectLatest { data ->
                when (data.status) {
                    Resource.Status.SUCCESS -> {
                        districts = data.data?.map { it.districtName } as ArrayList
                        districts.add(0, "Виберіть район")
                        setUpDefaultSpinner(dialog, districts, R.id.spinner_city_district)
                    }
                }
            }
        }
    }


    private suspend fun getStationsForSpinner() {

        lifecycleScope.launch {

            clubsViewModel.stations.collect { data ->
                when (data.status) {

                    Resource.Status.SUCCESS -> {
                        stations = data.data?.map { it.stationName } as ArrayList
                        stations.add(0, "Виберіть станцію")
                        setUpDefaultSpinner(dialog, stations, R.id.spinner_metro_station)
                    }

                }

            }

        }

    }

    private suspend fun getCategoriesForCheckBox() {

        lifecycleScope.launch {

            clubsViewModel.categories.collect { data ->
                when (data.status) {

                    Resource.Status.SUCCESS -> {
                        categories =
                            data.data?.reversed()?.map { it.categoryName } as MutableList<String>
                    }

                }

            }

        }

    }
    private fun createAdvancedSearchDialog() {
        dialog = Dialog(requireContext(), R.style.CustomAlertDialog)
        dialog.setContentView(R.layout.adv_search)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

    }

    private fun createSearchCategoriesCheckboxes() {

        println("size" + checkboxCounter)

        if (checkboxCounter != clubsViewModel.categories.value.data?.size!!) {

            for (i in 0 until clubsViewModel.categories.value.data?.size!!) {
                val checkBox =
                    LayoutInflater.from(dialog.context)
                        .inflate(R.layout.category_checkbox, rootAdvView, false) as CheckBox
                checkBox.text = clubsViewModel.categories.value.data!!.reversed()[i].categoryName
                checkBox.setOnClickListener {
                    if (checkBox.isChecked) {
                        clubsViewModel.listOfSearchedCategories.add(CategoryToUrlTransformer().toUrlEncode(
                            checkBox.text.toString()))
                        Log.e("categoryAll", clubsViewModel.listOfSearchedCategories.toString())
                    } else
                        clubsViewModel.listOfSearchedCategories.remove(CategoryToUrlTransformer().toUrlEncode(
                            checkBox.text.toString()))
                }
                dialog.rootAdvView.addView(checkBox, 12)
                checkboxCounter++
            }

        }

    }

    private fun unCheckAllCategoriesCheckboxes() {

        for (i in categories.indices) {

            var checkBox = dialog.rootAdvView.getChildAt(12 + i) as CheckBox
            checkBox.isChecked = false
        }
    }


    private fun setUpDefaultSpinner(
        dialog: Dialog,
        arrayRes: List<String>,
        @IdRes spinner: Int,
    ) {
        var sp: Spinner = dialog.findViewById(spinner)

        var citySearchSpinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(),
                R.layout.item_dropdown, arrayRes)
        citySearchSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = citySearchSpinnerAdapter
        println("$spinner" + arrayRes)


    }

    private fun searchAdvDialog() {

        createSearchCategoriesCheckboxes()

        dialog.show()

        setUpDefaultSpinner(dialog, cities, R.id.spinner_search_city)
        dialog.spinner_search_city.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    pos: Int,
                    id: Long,
                ) {

                    println("pos" + parent?.getItemAtPosition(pos))
                    districtByCity = parent?.getItemAtPosition(pos).toString()
                    clubsViewModel.advancedSearchClubDto.value?.cityName = districtByCity
                    updateDistricts()
                    updateStations()

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        println("before spin" + districts)

        dialog.spinner_city_district.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    pos: Int,
                    id: Long,
                ) {
//
                    println("pos" + parent?.getItemAtPosition(pos))
                    var district = parent?.getItemAtPosition(pos).toString()
                    if (pos > 0) {
                        clubsViewModel.advancedSearchClubDto.value?.districtName = district
                    } else {
                        clubsViewModel.advancedSearchClubDto.value?.districtName = null


                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }


        setUpDefaultSpinner(dialog, stations, R.id.spinner_metro_station)

        dialog.spinner_metro_station.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    pos: Int,
                    id: Long,
                ) {

                    println("pos" + parent?.getItemAtPosition(pos))
                    var station = parent?.getItemAtPosition(pos).toString()
                    if (pos > 0) {
                        clubsViewModel.advancedSearchClubDto.value?.stationName = station
                    } else
                        clubsViewModel.advancedSearchClubDto.value?.stationName = null

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        dialog.apply_search.setOnClickListener {

            println("categories " + clubsViewModel.listOfSearchedCategories.toString())
            clubsViewModel.advancedSearchClubDto.value?.categoriesName =
                clubsViewModel.listOfSearchedCategories
            clubsViewModel.advancedSearchClubDto.value?.isAdvanced = true
            clubsViewModel.advancedSearchClubDto.value?.isCenter = false
            clubsViewModel.advancedSearchClubDto.value?.sort = "name,asc"
            dialog.hide()

            binding.rcv.smoothScrollToPosition(0)
            clubsAdapter.refresh()
            layoutManager.scrollToPositionWithOffset(0, 0)


        }

        dialog.clear_search.setOnClickListener {

            clubsViewModel.advancedSearchClubDto.value?.isAdvanced = false
            clubsViewModel.advancedSearchClubDto.value?.name = ""
            clubsViewModel.advancedSearchClubDto.value?.cityName =
                clubsViewModel.searchClubDto.value?.cityName.toString()
            clubsViewModel.advancedSearchClubDto.value?.districtName = null.toString()
            clubsViewModel.advancedSearchClubDto.value?.stationName = null.toString()
            unCheckAllCategoriesCheckboxes()
            dialog.club_adv_search_radioBtn.isChecked = true
            dialog.center_adv_search_radioBtn.isChecked = false
            dialog.isOnline.isChecked = false
            dialog.cancel()
            clubsAdapter.refresh()
            binding.rcv.smoothScrollToPosition(0)
        }

    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.openDrawer -> {
                (activity as MainActivity).openDrawer()
            }

            R.id.searchAdvBtn -> {
                searchAdvDialog()
            }

            R.id.searchBtn -> {
                //viewModelStore.clear()
                clubsViewModel.advancedSearchClubDto.value?.isAdvanced = true
                clubsViewModel.advancedSearchClubDto.value?.name =
                    binding.searchEdit.text.toString()
                clubsViewModel.searchClubDto.value?.clubName
                clubsViewModel.advancedSearchClubDto.value?.cityName =
                    clubsViewModel.searchClubDto.value?.cityName!!
                clubsViewModel.advancedSearchClubDto.value?.districtName = null
                clubsViewModel.advancedSearchClubDto.value?.stationName = null
                //createViewModel()
                // addDataToVM()
                clubsAdapter.refresh()
                binding.searchEdit.isActivated = false
                layoutManager.scrollToPositionWithOffset(0, 0)
                requireContext().hideKeyboard(binding.searchEdit)
                binding.searchEdit.clearFocus()

            }

            R.id.clearEdit -> {
                when (binding.searchEdit.text?.isNotEmpty()) {

                    true -> {


                        binding.searchEdit.text.clear()
                        binding.searchEdit.isActivated = false
                        binding.searchEdit.clearAnimation()
                        clubsViewModel.advancedSearchClubDto.value?.name = ""
                        clubsViewModel.advancedSearchClubDto.value?.isAdvanced = false
                        clubsViewModel.advancedSearchClubDto.value?.sort = ""
                        clubsAdapter.refresh()
                        binding.searchEdit.clearFocus()


                    }

                    false -> {

                        binding.searchEdit.requestFocus()
                        binding.searchEdit.isActivated = true

                        //showKeyboard()
                    }
                }

            }


        }
    }

    override fun onResume() {
        super.onResume()
        updateToolbar()
        initAdapterState()

    }

    override fun onPause() {
        super.onPause()
        destroyToolbar()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.rcv.visibility = View.GONE
        checkboxCounter = 0
    }
}
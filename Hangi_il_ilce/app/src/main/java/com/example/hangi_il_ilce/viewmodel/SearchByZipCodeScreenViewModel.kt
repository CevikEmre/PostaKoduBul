package com.example.hangi_il_ilce.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangi_il_ilce.models.ZipCode
import com.example.hangi_il_ilce.services.APIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchByZipCodeScreenViewModel : ViewModel() {


    private var _districtList = MutableStateFlow<List<ZipCode>>(emptyList())
    var districtList: StateFlow<List<ZipCode>> = _districtList

    private val _filterByPkList = MutableStateFlow<List<ZipCode>>(emptyList())
    val filterByPkList: StateFlow<List<ZipCode>> = _filterByPkList
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    fun loadZipCodes(cityKey: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = APIService.findByCityKey(cityKey)
                _districtList.value = result ?: emptyList()
                Log.e("zipcodes", _districtList.value.size.toString())
            } catch (e: Exception) {
                Log.e("MainScreenViewModel", "Fail", e)
            } finally {
                _loading.value = false
            }
        }
    }
    fun filterByPk(pk: String) {
        viewModelScope.launch {
            _filterByPkList.value = _districtList.value.filter {
                it.pk.contains(pk)
            }
        }
    }

}

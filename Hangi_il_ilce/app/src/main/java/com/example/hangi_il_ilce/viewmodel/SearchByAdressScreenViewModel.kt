package com.example.hangi_il_ilce.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangi_il_ilce.models.ZipCode
import com.example.hangi_il_ilce.services.APIService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchByAdressScreenViewModel: ViewModel(){

    private val _printedZipCode = MutableLiveData<String>()
    val printedZipCode: LiveData<String> = _printedZipCode

    private var _districtList = MutableStateFlow<List<ZipCode>>(emptyList())

    private var _selectedDistrict = MutableStateFlow<ZipCode?>(null)

    var districtList: StateFlow<List<ZipCode>> = _districtList

    val townList: StateFlow<List<ZipCode>> = _districtList
        .combine(_selectedDistrict) { zipCodes, selectedDistrict ->
            zipCodes.filter { it.district == selectedDistrict?.district }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


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

    fun setDistrictByZipCode(zipCode: ZipCode) {
        viewModelScope.launch {
            try {
                _loading.value = true
                delay(500)
                _selectedDistrict.value = zipCode
            }
            catch (e:Exception){
                Log.e("Error","Fail")
            }
            finally {
                _loading.value = false
            }
        }
    }
    fun printZipCode(printedZipCode: String){
        viewModelScope.launch {
            try {
                _selectedDistrict.value?.pk = printedZipCode
                _printedZipCode.value = printedZipCode
            }
            catch (e:Exception){
                Log.e("ErrorPrint","Fail")
            }

        }

    }

    fun clearPrintedZipCode(){
        _printedZipCode.value = ""
    }

    fun clearState() {
        _printedZipCode.value = ""
        _districtList.value = emptyList()
        _selectedDistrict.value = null

    }
}
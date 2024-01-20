package com.sumeyra.kotlinweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumeyra.kotlinweather.model.CurrentWeather
import com.sumeyra.kotlinweather.model.WeatherLocation
import com.sumeyra.kotlinweather.repository.WeatherDataRepository
import kotlinx.coroutines.launch

class ListViewModel(private val weatherDataRepository: WeatherDataRepository): ViewModel() {
    //region Location

    //View Modelde genel olarak amacımız ui bloklamadan main thread işlemlerini kullanıcı arayüzüne yansıtmadan çözmeek


    // _searchResult, SearchResultsDataState tipindeki durumu depolamak için kullanılan MutableLiveData'yi temsil eder.
    //O anki tipi depolarız ve sonra onu LiveData tipine çevirir ve sadece okumabilir immutable tipe çeviririz.
    // MutableLiveData, değerinin güncellenebilir olduğu bir LiveData türüdür.
    private val _searchResult = MutableLiveData<SearchResultDataState>()
    val searchResult : LiveData<SearchResultDataState> get() = _searchResult



    /*son aşama olarak;
    viewModelScope viewmodelin ömrü boyunca devam eder view modelin ömrü bittiğinde otomatik olarak başlatılan tüm coroutines iptal olur.
    böylece bellek sızıntıları önlenir
    Konum arama fonksiyonu burada konum arama işlemleri için coroutine kullanılmış

    Burada koşullar isloading üzerinden el alındı çünkü eğer yükleme varsa konum alınacak
    yükleme yoksa error vermesi gerekiyor
    emitSearchResultUiState içinde zaten initial değerleri atamıştık buna göre coroutine içinde
    isloading=true dersek iki aşamayı da ele alabiliyoruz
    apiden gelen veriyi weatherdatarepository de oluşturduğumuz searchLocationFromAPI den ele alıyoruz onu da
    */

    fun searchLocation(query: String){
        viewModelScope.launch {
            emitSearchResultUiState(isLoading = true)
            val searchResultComingFromAPI = weatherDataRepository.  searchLocationFromAPI(query) //burada weatherdatarepository de apiden gelen body i döndüryoruz.
            if (searchResultComingFromAPI.isNullOrEmpty()) {
                emitSearchResultUiState(error = "Location not found, please try again!")
            }else{
                emitSearchResultUiState(locations = searchResultComingFromAPI)
            }
        }
    }


    //3
    //emit=yayınla
    //bulduğum sonucun objelerini ui ye yayınlamak için kullanıyorum
    // eğer konum artık isloading aşamasında değilse (false) liste halinde konumlar gelmesi gerek eğer bir hata varsa da Error döndürecek
    private fun emitSearchResultUiState(
        isLoading: Boolean =false,
        locations: List<WeatherLocation>? =null,
        error: String? = null
    ){
        val searchResultDataState = SearchResultDataState(isLoading,locations, error)
        _searchResult.value = searchResultDataState


    }

    //1
    //ilk olarak arama durumumu kontrol objelerini oluşturuyorum
    //konum/konumlar şuan bulunma aşamasında mı bulundu mu yoksa bir error mu verdi
    // Main thread altında incelememiz gereken durumlar
    data class SearchResultDataState(
        val isLoading: Boolean,
        val locations: List<WeatherLocation>?,
        val error: String?
    )

    //endregion

}
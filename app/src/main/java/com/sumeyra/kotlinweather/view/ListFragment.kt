package com.sumeyra.kotlinweather.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sumeyra.kotlinweather.adapter.LocationAdapter
import com.sumeyra.kotlinweather.databinding.ListFragmentBinding
import com.sumeyra.kotlinweather.model.WeatherLocation
import com.sumeyra.kotlinweather.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment: Fragment() {

    private var _binding : ListFragmentBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by viewModel()

   private val locationAdapter = LocationAdapter(arrayListOf())




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setupLocationsRecyclerView()
        setObservers()
    }

    //1
    //Araylamaları dinlemek için eğer bir inpuut girdisi olursa kullanıcı tarafından onun için ele alınacak koşulları
    // yönetmek için.
    private fun setListener(){
        binding.inputSearch.editText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                hideSoftKeyboard()
                val query = binding.inputSearch.editText?.text
                if (query.isNullOrBlank()) return@setOnEditorActionListener true
                searchLocationUI(query.toString())
            }
            return@setOnEditorActionListener true
        }
    }
    private fun setupLocationsRecyclerView(){
        with(binding.locationRecyclerView){
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            adapter= locationAdapter
        }
    }

    private fun setLocation( weatherLocation: WeatherLocation){

    }

    //4
    //LiveData ile incelediğimiz searchResult için bir observer oluşturuyoruz
    private fun setObservers(){
        listViewModel.searchResult.observe(viewLifecycleOwner){
            val searchResultDataState = it ?: return@observe
            if (searchResultDataState.isLoading){
                binding.locationRecyclerView.visibility = View.GONE
                binding.countryListPrograssBar.visibility=  View.VISIBLE
                binding.countryListError.visibility = View.GONE
            }else{
                binding.countryListPrograssBar.visibility = View.GONE
                binding.countryListError.visibility = View.VISIBLE
            }
            searchResultDataState.locations?.let {  weatherLocations ->
                //Toast.makeText(requireContext(),"${weatherLocations.size} location(s) found." , Toast.LENGTH_SHORT).show()
                binding.locationRecyclerView.visibility = View.VISIBLE
                binding.countryListError.visibility = View.GONE
                locationAdapter.setData(weatherLocations)
            }
            searchResultDataState.error?.let { error->
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                binding.countryListError.visibility= View.VISIBLE
                binding.locationRecyclerView.visibility = View.GONE
            }

        }
    }



    //2
    //weatherRepository den gelen bilgiler boş değilse view modelde koşullarla kontrol edildi ve burada çağırdım
    //fragmentta konuma erişmek için artık arayüzde kullanılacak fonksiyonum
    private fun searchLocationUI(query: String){
        listViewModel.searchLocation(query)

    }

    //3
    // konuma arama ekranımda klavyenin görünümü ile ilgili
    private fun hideSoftKeyboard(){
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            binding.inputSearch.editText?.windowToken,0
        )
    }




}
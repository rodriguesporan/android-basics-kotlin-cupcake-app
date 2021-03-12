package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderViewModel: ViewModel() {

//    init {
//        resetOrder()
//    }

    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> get() = _quantity

    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> get() = _price

    private val _flavor = MutableLiveData<String>("")
    val flavor: LiveData<String> get() = _flavor

    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> get() = _date

    val dateOptions = getPickupOptions()

    private fun getPickupOptions(): List<String> {
        var options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }

    fun setQuantity(quantityCupcakes: Int) {
        _quantity.value = quantityCupcakes
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
    }

    fun hasNoFlavorSet(): Boolean = _flavor.value.isNullOrEmpty()

//    private fun resetOrder() {
//        _quantity.value = 0
//        _flavor.value = ""
//        _date.value = dateOptions[0]
//        _price.value = 0.0
//    }
}
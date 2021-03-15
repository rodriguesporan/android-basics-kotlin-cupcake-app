package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {

    private val _quantity = MutableLiveData<Int>(0)
    val quantity: LiveData<Int> get() = _quantity

    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _flavor = MutableLiveData<String>("")
    val flavor: LiveData<String> get() = _flavor

    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> get() = _date

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

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

    private fun updatePrice() {
        var calculatePrice = (_quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _date.value) {
            calculatePrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatePrice
    }

    fun setQuantity(quantityCupcakes: Int) {
        _quantity.value = quantityCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean = _flavor.value.isNullOrEmpty()

    fun resetOrder() {
        _quantity.value = 0
        _price.value = 0.0
        _flavor.value = ""
        _date.value = dateOptions[0]
    }
}
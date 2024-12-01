package com.dmribeiro87.cupcakeapp.ui.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Address
import com.dmribeiro87.model.Client
import kotlinx.coroutines.launch

class AddressViewModel(
    private val repository: CupcakeRepository
): ViewModel() {


    fun addClientInfoToOrder(orderId: String, clientName: String, address: Address) {
        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                existingOrder?.let { order ->
                    val updatedClient = Client(name = clientName, address = address)
                    val updatedOrder = order.copy(client = updatedClient)
                    repository.createOrUpdateOrder(updatedOrder)
                }
            } catch (e: Exception) {
                // Trate exceções
            }
        }
    }
}
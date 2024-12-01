package com.dmribeiro87.cupcakeapp.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.PaymentType
import kotlinx.coroutines.launch

class PixViewModel(
    private val repository: CupcakeRepository
): ViewModel() {

    fun updatePaymentType(orderId: String, paymentType: PaymentType) {
        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                existingOrder?.let { order ->
                    val updatedOrder = order.copy(paymentType = paymentType)
                    repository.createOrUpdateOrder(updatedOrder)
                }
            } catch (e: Exception) {
                // Trate exceções
            }
        }
    }

}
package com.dmribeiro87.cupcakeapp.ui.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import kotlinx.coroutines.launch

class SuccessViewModel(
    private val repository: CupcakeRepository
): ViewModel() {
    fun deleteOrder(orderId: String) {
        viewModelScope.launch {
            try {
                repository.deleteOrder(orderId)
            } catch (e: Exception) {
                // Trate a exceção, por exemplo, registrando um log
            }
        }
    }
}
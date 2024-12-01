package com.dmribeiro87.cupcakeapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Address
import com.dmribeiro87.model.Client
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import com.dmribeiro87.model.PaymentType
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch
import java.util.UUID

class CartViewModel(private val repository: CupcakeRepository) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>(emptyList())
    val orders: LiveData<List<Order>> = _orders
    private var _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress


    fun loadOrders() {
        _progress.value = true
        viewModelScope.launch {
            repository.getAllOrders { ordersList ->
                _orders.value = ordersList
                _progress.value = false
            }
        }
    }

    fun addCupcakeToOrder(cupcake: Cupcake) {
        _progress.value = true
        val orderId = "unique-order-of-the-galaxy"

        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                val orderCupcakes = existingOrder?.list?.toMutableList() ?: mutableListOf()
                orderCupcakes.add(cupcake)

                val newOrder = Order(
                    orderId = orderId,
                    list = orderCupcakes,
                    date = existingOrder?.date ?: Timestamp.now(),
                    client = existingOrder?.client ?: Client("", Address("", "", "", "", "", ""))
                )
                repository.createOrUpdateOrder(newOrder)
                loadOrders() // Recarrega os pedidos para refletir as mudanças
                _progress.value = false
            } catch (e: Exception) {
                // Trate a exceção
            }
        }
    }

    fun updateOrderTotal(orderId: String, newTotal: Double) {
        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                existingOrder?.let { order ->
                    val updatedOrder = order.copy(total = newTotal)
                    repository.createOrUpdateOrder(updatedOrder)
                }
            } catch (e: Exception) {
                // Trate exceções
            }
        }
    }


    fun removeCupcakeFromOrder(cupcake: Cupcake) {
        val orderId = "unique-order-of-the-galaxy"

        viewModelScope.launch {
            try {
                val existingOrder = repository.getOrderById(orderId)
                existingOrder?.let {
                    // Encontra o índice do primeiro cupcake que corresponde ao productId
                    val cupcakeIndex = it.list.indexOfFirst { it.productId == cupcake.productId }
                    if (cupcakeIndex != -1) {
                        // Cria uma nova lista removendo o cupcake encontrado
                        val updatedCupcakes = ArrayList(it.list)
                        updatedCupcakes.removeAt(cupcakeIndex)
                        val updatedOrder = it.copy(list = updatedCupcakes)
                        repository.createOrUpdateOrder(updatedOrder)
                        loadOrders() // Recarregar os pedidos para refletir as mudanças
                    }
                }
            } catch (e: Exception) {
                // Trate a exceção
            }
        }
    }
}
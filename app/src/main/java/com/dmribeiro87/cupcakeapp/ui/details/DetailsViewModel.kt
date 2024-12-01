package com.dmribeiro87.cupcakeapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Address
import com.dmribeiro87.model.Client
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import com.google.firebase.Timestamp
import kotlinx.coroutines.launch

import java.util.UUID

class DetailsViewModel(private val repository: CupcakeRepository): ViewModel() {

    private val selectedCupcakes = mutableListOf<Cupcake>()
    private val _currentSelection = MutableLiveData<List<Cupcake>>()
    val currentSelection: LiveData<List<Cupcake>> = _currentSelection

    // Este ID de pedido deve ser constante para acumular cupcakes no mesmo pedido
    private val fixedOrderId = "unique-order-of-the-galaxy"

    fun initializeOrAddCupcakeToSelection(cupcake: Cupcake) {
        selectedCupcakes.add(cupcake)
        _currentSelection.value = selectedCupcakes
    }

    // Função para criar um pedido com os cupcakes selecionados e um cliente vazio
    fun createOrderForCheckout() {
        if (selectedCupcakes.isNotEmpty()) {
            // Busca o pedido existente ou cria um novo
            viewModelScope.launch {
                val existingOrder = repository.getOrderById(fixedOrderId)
                val orderCupcakes = existingOrder?.list?.toMutableList() ?: mutableListOf()
                orderCupcakes.addAll(selectedCupcakes)

                val newOrder = Order(
                    orderId = fixedOrderId,
                    list = orderCupcakes,
                    date = null,
                    client = Client("", Address("", "", "", "", "", "")) // Cliente vazio por enquanto
                )
                repository.createOrUpdateOrder(newOrder)

                // Limpa apenas a seleção local, mantendo o pedido no Firebase
                selectedCupcakes.clear()
                _currentSelection.value = selectedCupcakes
            }
        }
    }

//    fun createOrUpdateOrder() {
//        if (selectedCupcakes.isNotEmpty()) {
//            val newOrder = Order(
//                orderId = "unique-order-of-the-galaxy",
//                list = selectedCupcakes.toList(),
//                date = null,
//                client = Client("", Address("", "", "", "", "", "")) // Cliente vazio por enquanto
//            )
//            repository.createOrUpdateOrder(newOrder)
//            // Não limpa a seleção após a criação do pedido
//        }
//    }

//    fun finalizeOrder(client: Client) {
//        if (selectedCupcakes.isNotEmpty()) {
//            val finalizedOrder = Order(
//                orderId = "unique-order-of-the-galaxy",
//                list = selectedCupcakes.toList(),
//                date = Timestamp.now(),
//                client = client // Cliente fornecido no momento do checkout
//            )
//            repository.createOrUpdateOrder(finalizedOrder)
//
//            // Resetar a seleção e o estado do pedido após finalização
//            selectedCupcakes.clear()
//            _currentSelection.value = selectedCupcakes
//            currentOrderId = null
//        }
//    }



}
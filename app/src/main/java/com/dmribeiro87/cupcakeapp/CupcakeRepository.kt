package com.dmribeiro87.cupcakeapp

import android.util.Log
import com.dmribeiro87.model.Address
import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CupcakeRepository(
    private val db: FirebaseFirestore = Firebase.firestore
) {

    fun deleteOrder(orderId: String) {
        db.collection("orders").document(orderId).delete()
            .addOnSuccessListener {
                Log.d("CupcakeRepository", "Pedido deletado com sucesso.")
            }
            .addOnFailureListener { e ->
                Log.e("CupcakeRepository", "Erro ao deletar pedido.", e)
            }
    }

    suspend fun addCupcakeMocked(cupcake: Cupcake) {
        try {
            db.collection("cupcakes")
                .add(cupcake)
                .await()
            println("Cupcake adicionado com sucesso!")
        } catch (e: Exception) {
            println("Erro ao adicionar cupcake: ${e.message}")
        }
    }

    suspend fun getCupcakes(): List<Cupcake> {
        return try {
            val snapshot = db.collection("cupcakes").get().await()
            val cupcakes = snapshot.toObjects(Cupcake::class.java)
            cupcakes
        } catch (e: Exception) {
            println("Erro ao recuperar cupcakes: ${e.message}")
            emptyList()
        }
    }

    suspend fun getOrderById(orderId: String): Order? {
        return try {
            val snapshot = db.collection("orders").document(orderId).get().await()
            snapshot.toObject(Order::class.java)
        } catch (e: Exception) {
            Log.e("CupcakeRepository", "Erro ao obter o pedido: ${e.message}")
            null
        }
    }


    fun createOrUpdateOrder(order: Order) {
        // Aqui você pode usar o ID do pedido como o documento ID
        db.collection("orders")
            .document(order.orderId)
            .set(order)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                // Tratar erros, por exemplo, falha de conexão
            }
    }

    fun getAllOrders(onComplete: (List<Order>) -> Unit) {
        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                val ordersList = result.mapNotNull { it.toObject(Order::class.java) }
                onComplete(ordersList)
            }
            .addOnFailureListener { e ->
                // Tratar exceção, por exemplo, falha de conexão ou query vazia
                onComplete(emptyList())
            }
    }
}
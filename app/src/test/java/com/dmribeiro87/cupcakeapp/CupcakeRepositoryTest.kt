package com.dmribeiro87.cupcakeapp

import com.dmribeiro87.model.Cupcake
import com.dmribeiro87.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CupcakeRepositoryTest {

    private val mockFirestore = mockk<FirebaseFirestore>()
    private val mockCollection = mockk<CollectionReference>()
    private val mockTask = mockk<Task<QuerySnapshot>>()
    private val mockSnapshot = mockk<QuerySnapshot>()
    private lateinit var cupcakeRepository: CupcakeRepository

    @Before
    fun setup() {
        // Inicializa os mocks
        every { mockFirestore.collection(any()) } returns mockCollection
        every { mockCollection.get() } returns mockTask
        coEvery { mockTask.await() } returns mockSnapshot

        // Cria uma instância do repositório com o Firestore mockado
        cupcakeRepository = CupcakeRepository(mockFirestore)
    }

    @Test
    fun `getCupcakes returns list of cupcakes`() = runTest {
        // Define o comportamento esperado do mock
        val expectedCupcakes = listOf(
            Cupcake("1", "Cupcake de Chocolate", "Chocolate", 2.50, "O cupcake de chocolate clássico é intenso e profundo, feito com cacau de alta qualidade que seduz imediatamente o paladar. A textura é úmida e rica, complementada por uma cobertura cremosa de ganache de chocolate. Perfeito para qualquer ocasião, é um favorito atemporal que promete satisfazer sua busca pelo prazer chocolate.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/chocolate_cupcake.jpeg?alt=media&token=ae5e73d6-b03d-4915-b7ff-e328afabed1f&_gl=1*pyefce*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg1NDguOS4wLjA.", weight = 120),
            Cupcake("2", "Cupcake de Baunilha", "Baunilha", 2.50, "Um clássico atemporal, o cupcake de baunilha é a definição de simplicidade elegante. A massa aerada e leve é perfumada com extrato de baunilha puro, criando um sabor suave e reconfortante. A cobertura de buttercream de baunilha, sedosa e rica, complementa a base perfeitamente, fazendo deste cupcake uma escolha perfeita para qualquer celebração.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/vanilla_cupcake.jpeg?alt=media&token=4c59ee19-45ff-49f8-8599-f986676bd90a&_gl=1*vjytr8*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg4MDkuMTMuMC4w", weight = 120),
        )
        every { mockSnapshot.toObjects(Cupcake::class.java) } returns expectedCupcakes

        // Chama a função a ser testada
        val cupcakes = cupcakeRepository.getCupcakes()

        // Verifica se o resultado é o esperado
        assertEquals(expectedCupcakes, cupcakes)
    }

    @Test
    fun `deleteOrder successfully deletes an order`() = runTest {
        val orderId = "order_id"
        val mockDocumentRef = mockk<DocumentReference>(relaxed = true)
        val mockDeleteTask = mockk<Task<Void>>(relaxed = true)

        every { mockFirestore.collection("orders").document(orderId) } returns mockDocumentRef
        every { mockDocumentRef.delete() } returns mockDeleteTask

        cupcakeRepository.deleteOrder(orderId)

        verify { mockDocumentRef.delete() }
    }

    @Test
    fun `createOrUpdateOrder successfully updates an order`() = runTest {
        val order = Order(orderId = "order_id")
        val mockDocumentRef = mockk<DocumentReference>(relaxed = true)
        val mockSetTask = mockk<Task<Void>>(relaxed = true)

        every { mockFirestore.collection("orders").document(order.orderId) } returns mockDocumentRef
        every { mockDocumentRef.set(order) } returns mockSetTask

        cupcakeRepository.createOrUpdateOrder(order)

        verify { mockDocumentRef.set(order) }
    }

    @Test
    fun `getOrderById returns an order`() = runTest {
        val orderId = "order_id"
        val mockDocumentRef = mockk<DocumentReference>()
        val mockGetTask = mockk<Task<DocumentSnapshot>>()
        val mockDocumentSnapshot = mockk<DocumentSnapshot>()

        every { mockFirestore.collection("orders").document(orderId) } returns mockDocumentRef
        every { mockDocumentRef.get() } returns mockGetTask
        coEvery { mockGetTask.await() } returns mockDocumentSnapshot
        every { mockDocumentSnapshot.toObject(Order::class.java) } returns Order(orderId = orderId)

        val result = cupcakeRepository.getOrderById(orderId)

        assertEquals(orderId, result?.orderId)
    }

    @Test
    fun `getAllOrders returns a list of orders`() = runTest {
        val mockCollectionRef = mockk<CollectionReference>()
        val mockGetTask = mockk<Task<QuerySnapshot>>()
        val mockQuerySnapshot = mockk<QuerySnapshot>()
        val expectedOrders = listOf(Order(orderId = "order1"), Order(orderId = "order2"))

        every { mockFirestore.collection("orders") } returns mockCollectionRef
        every { mockCollectionRef.get() } returns mockGetTask
        coEvery { mockGetTask.await() } returns mockQuerySnapshot
        every { mockQuerySnapshot.mapNotNull { it.toObject(Order::class.java) } } returns expectedOrders

        val result = mutableListOf<Order>()
        cupcakeRepository.getAllOrders { result.addAll(it) }

        assertEquals(expectedOrders, result)
    }
}
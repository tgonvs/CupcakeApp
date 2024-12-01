package com.dmribeiro87.cupcakeapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.model.Cupcake
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CupcakeRepository) : ViewModel(){

    private var _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private val _cupcakes = MutableLiveData<List<Cupcake>>()
    val cupcakes: LiveData<List<Cupcake>> = _cupcakes

    fun addCupcake(cupcake: Cupcake) {
        viewModelScope.launch {
            repository.addCupcakeMocked(cupcake)
        }
    }

    suspend fun populateFirebaseWithCupcakes() {
        Log.d("***Check", "Passou")
        val cupcakes = listOf(
            Cupcake("1", "Cupcake de Chocolate", "Chocolate", 2.50, "O cupcake de chocolate clássico é intenso e profundo, feito com cacau de alta qualidade que seduz imediatamente o paladar. A textura é úmida e rica, complementada por uma cobertura cremosa de ganache de chocolate. Perfeito para qualquer ocasião, é um favorito atemporal que promete satisfazer sua busca pelo prazer chocolate.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/chocolate_cupcake.jpeg?alt=media&token=ae5e73d6-b03d-4915-b7ff-e328afabed1f&_gl=1*pyefce*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg1NDguOS4wLjA.", weight = 120),
            Cupcake("2", "Cupcake de Baunilha", "Baunilha", 2.50, "Um clássico atemporal, o cupcake de baunilha é a definição de simplicidade elegante. A massa aerada e leve é perfumada com extrato de baunilha puro, criando um sabor suave e reconfortante. A cobertura de buttercream de baunilha, sedosa e rica, complementa a base perfeitamente, fazendo deste cupcake uma escolha perfeita para qualquer celebração.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/vanilla_cupcake.jpeg?alt=media&token=4c59ee19-45ff-49f8-8599-f986676bd90a&_gl=1*vjytr8*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg4MDkuMTMuMC4w", weight = 120),
            Cupcake("3", "Cupcake de Pistache", "Pistache", 3.00, "Este cupcake de pistache é uma obra-prima de sabores sutis e texturas requintadas. A massa é suavemente aromatizada com pistache moído, oferecendo um sabor delicado e terroso. Coberto com uma leve cobertura de manteiga de pistache e polvilhado com pistaches triturados, é um deleite sofisticado que encanta o paladar.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/pistache_cupcake.jpeg?alt=media&token=ec11b31c-fbe4-49fa-a4ee-50c36a2244cb&_gl=1*yawwnc*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3NjIuNjAuMC4w", weight = 120),
            Cupcake("4", "Cupcake de Brownie", "Brownie", 4.00, "Com a indulgência de um brownie e a delicadeza de um cupcake, esta tentação é feita para aqueles que adoram uma sobremesa bem rica. A base densa e úmida se assemelha ao clássico brownie, enquanto a cobertura suave traz um contraste perfeito. Pedaços de nozes crocantes adicionam a textura ideal a cada mordida.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/brownie_cupcake.jpeg?alt=media&token=b8c7665c-669b-482f-89dc-526689133874&_gl=1*1kwgo2m*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg1MTIuNDUuMC4w", weight = 130),
            Cupcake("5", "Cupcake de Nutella", "Nutella", 5.00, "Luxuoso e irresistível, este cupcake de Nutella é uma verdadeira indulgência. A massa fofa de chocolate é coroada com uma generosa camada de creme de avelã, oferecendo uma experiência única de sabor. Com um coração recheado de Nutella, cada mordida é uma fusão de texturas que derretem na boca, perfeita para os amantes do chocolate e avelã.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/nutella_cupcake.jpeg?alt=media&token=ceb36c6f-15f1-4692-a4eb-18a3881bfc44&_gl=1*1u3homq*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3NDQuMTQuMC4w", weight = 150),
            Cupcake("6", "Cupcake de Leite Ninho", "Leite Ninho", 4.00, "O cupcake de Leite Ninho é uma nostálgica viagem de volta à infância. A massa leve é enriquecida com o sabor doce e cremoso do Leite Ninho, criando um perfil de sabor reconfortante e familiar. A cobertura é um creme aveludado que remete ao leite em pó, finalizado com uma pitada adicional de Leite Ninho para uma experiência autêntica e deliciosa.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/milk_cupcake.jpeg?alt=media&token=531b050c-54be-4dfd-9d82-e5e329dc2f1d&_gl=1*1p24xy2*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3MTguNDAuMC4w", weight = 120),
            Cupcake("7", "Cupcake de Frutas Vermelhas", "Frutas Vermelhas", 5.00, "Este cupcake é um sonho para os amantes de sabores ácidos e doces. Uma base delicada de baunilha é infundida com pedaços de frutas vermelhas frescas, criando um equilíbrio perfeito entre doce e tangy. Coberto com uma cobertura leve de chantilly e decorado com um mix de frutas vermelhas, é tanto uma delícia visual quanto gustativa.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/red_fruit_cupcake.jpeg?alt=media&token=11b66f5c-ee5a-4a14-9797-d70f61582ed7&_gl=1*1cnj6gl*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg3NzQuNDguMC4w", weight = 140),
            Cupcake("8", "Cupcake de Cappuccino", "Cappuccino", 4.00, "Ideal para os amantes de café, o cupcake de cappuccino mistura a riqueza do café expresso com a suavidade do creme. A massa é infundida com café de verdade, e a cobertura é um irresistível buttercream que imita a espuma do cappuccino, polvilhado com um toque de cacau em pó para uma finalização elegante.", "https://firebasestorage.googleapis.com/v0/b/cupcakeapp-ec00f.appspot.com/o/capuccino_cupcake.jpeg?alt=media&token=73344abd-629c-423f-93c8-58862a73f7c1&_gl=1*1mjy953*_ga*MTA5NzM3ODg2NC4xNjg2OTYyNzEy*_ga_CW55HF8NVT*MTY5ODYyNzE3OC41NS4xLjE2OTg2Mjg1MzQuMjMuMC4w", weight = 140),
        )

        cupcakes.forEach { cupcake ->
            repository.addCupcakeMocked(cupcake)
        }
    }

    fun getCupcakes() {
        _progress.value = true
        viewModelScope.launch {
            val cupcakes = repository.getCupcakes()
            _cupcakes.value = cupcakes
            _progress.value = false
        }
    }
}
package com.dmribeiro87.cupcakeapp.di

import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.cupcakeapp.ui.address.AddressViewModel
import com.dmribeiro87.cupcakeapp.ui.cart.CartViewModel
import com.dmribeiro87.cupcakeapp.ui.details.DetailsViewModel
import com.dmribeiro87.cupcakeapp.ui.home.HomeViewModel
import com.dmribeiro87.cupcakeapp.ui.payment.CardViewModel
import com.dmribeiro87.cupcakeapp.ui.payment.PixViewModel
import com.dmribeiro87.cupcakeapp.ui.success.SuccessViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CupcakeRepository() }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { CartViewModel(get()) }
    viewModel { AddressViewModel(get()) }
    viewModel { CardViewModel(get()) }
    viewModel { PixViewModel(get()) }
    viewModel { SuccessViewModel(get()) }
}

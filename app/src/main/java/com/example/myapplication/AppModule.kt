package com.example.myapplication


import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

@JvmField
val koinModule = module {
   viewModel { InputViewModel() }
   viewModel { CreateViewViewModel() }
}
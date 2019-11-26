package io.github.rybalkinsd.kohttp.kohttp_dagger

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> AppCompatActivity.injectViewModel(
    factory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, factory)[T::class.java]
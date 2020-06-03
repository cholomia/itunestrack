package com.pocholomia.itunestrack.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

/**
 * This is an extension function that calls [ViewModelProviders.of]
 * Gets the view model (fragment scope)
 */
inline fun <reified VM : ViewModel> withViewModel(
    fragment: Fragment,
    factory: ViewModelProvider.Factory,
    function: VM.() -> Unit = {}
): VM {
    val vm = ViewModelProviders.of(fragment, factory)[VM::class.java]
    vm.function()
    return vm
}

fun <M : Any, L : LiveData<M>> Fragment.observe(liveData: L, observer: (M) -> Unit = {}) =
    liveData.observe(this.viewLifecycleOwner, Observer(observer))

fun Context.showErrorDialog(message: String) {
    AlertDialog.Builder(this)
        .setMessage(message)
        .show()
}
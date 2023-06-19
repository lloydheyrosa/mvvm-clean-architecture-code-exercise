package com.lloydheyrosa.contactlist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydheyrosa.commons.utils.ConnectivityFailure
import com.lloydheyrosa.commons.utils.Failure
import com.lloydheyrosa.commons.utils.GeneralFailure
import com.lloydheyrosa.commons.utils.Success
import com.lloydheyrosa.domain.model.Contact
import com.lloydheyrosa.domain.usecase.ContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: ContactsUseCase
) : ViewModel() {

    val initialPages = 2

    val errorRelay = MutableSharedFlow<Failure<*>>()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _contacts = MutableStateFlow(emptyList<Contact>())
    val contacts: StateFlow<List<Contact>> = _contacts

    fun initialize() {
        viewModelScope.launch {
            try {
                setIsLoading(true)
                _contacts.value = emptyList()
                for (i in 1..initialPages) {
                    when (val result = useCase.getContacts(i)) {
                        is Success -> {
                            _contacts.value += result.value
                        }

                        is Failure -> {
                            errorRelay.emit(result)
                        }
                    }
                }

            } catch (e: Exception) {
                Log.e("initialize", e.message.orEmpty())
                errorRelay.emit(GeneralFailure)
            } finally {
                setIsLoading(false)
            }
        }
    }

    private fun setIsLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}
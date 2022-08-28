package com.example.base.view

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.view.BaseViewModel.ErrorMessage.StringErrorMessage
import com.example.base.view.BaseViewModel.ErrorMessage.StringResErrorMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : UiState>(
    defaultUiState: STATE
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(defaultUiState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    val currentState
        get() = uiStateFlow.value

    private val _errorMsgEventFlow = MutableSharedFlow<ErrorMessage>()
    val errorMsgEventFlow = _errorMsgEventFlow.asSharedFlow()

    private val _singleEventFlow = Channel<SingleEvent>()
    val singleEventFlow = _singleEventFlow.receiveAsFlow()

    protected fun sendSingleEvent(event: SingleEvent) {
        viewModelScope.launch {
            _singleEventFlow.send(event)
        }
    }

    protected fun sendErrorMsgEvent(message: String?) {
        message?.let {
            viewModelScope.launch {
                _errorMsgEventFlow.emit(StringErrorMessage(message))
            }
        }
    }

    protected fun sendErrorMsgEvent(@StringRes messageResId: Int) {
        viewModelScope.launch {
            _errorMsgEventFlow.emit(StringResErrorMessage(messageResId))
        }
    }

    fun updateState(state: STATE) {
        _uiStateFlow.value = state
    }

    fun updateState(block: (STATE) -> STATE) {
        _uiStateFlow.update(block)
    }

    sealed class ErrorMessage {
        data class StringErrorMessage(val message: String) : ErrorMessage()
        data class StringResErrorMessage(@StringRes val messageResId: Int) : ErrorMessage()
    }

}

package com.luka.grcki_kino_mozzart

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<StateType, ScreenEventType> : ViewModel() {

    protected val _state = MutableStateFlow(this.initialState()) // main state
    val state = _state.asStateFlow()
    protected abstract fun initialState(): StateType // set initial state value

    /** for receiving events from UI **/
    abstract fun onEvent(event: ScreenEventType)

    /** for sending one time channel events to UI **/
    private val _uiEventChannel = Channel<EventForUi>()
    val uiEvents = _uiEventChannel.receiveAsFlow()
    protected fun sendUiEvent(event: EventForUi) {
        viewModelScope.launch {
            _uiEventChannel.send(event)
        }
    }
}

interface EventForUi {
    data class ShowToast(val message: String, val toastLength: Int = Toast.LENGTH_SHORT): EventForUi
//    data class Error(val message: String): UiEvent
//    data class ShowDialog(val message: String): EventForUI
//    data class Navigate(val route: String): UiEvent
}
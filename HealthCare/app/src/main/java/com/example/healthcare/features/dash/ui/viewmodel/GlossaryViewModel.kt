package com.example.healthcare.features.dash.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthcare.features.dash.data.model.Glossary
import com.example.healthcare.features.dash.domain.FetchGlossaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlossaryViewModel @Inject constructor(
    private val fetchGlossaryUseCase: FetchGlossaryUseCase
) : ViewModel() {

    private val state = MutableStateFlow<GlossaryUiState>(GlossaryUiState.Init)
    val mState: StateFlow<GlossaryUiState> get() = state

    private val _mGlossaryDetails = MutableStateFlow<Glossary?>(Glossary())
    val mGlossaryDetails: StateFlow<Glossary?> get() = _mGlossaryDetails

    init {
        onGlossaryFetched()
    }

    private fun onGlossaryFetched() {
        viewModelScope.launch {
            fetchGlossaryUseCase.getGlossary()
                .flowOn(Dispatchers.IO)
                .onStart { setLoading() }
                .catch { exception ->
                    hideLoading()
                    showError(exception.message.toString())
                    Log.e("GlossaryViewModel", exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    _mGlossaryDetails.value = result
                }
        }
    }

    private fun setLoading() {
        state.value = GlossaryUiState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = GlossaryUiState.IsLoading(false)
    }

    private fun showError(message: String) {
        state.value = GlossaryUiState.Error(message)
    }

    sealed class GlossaryUiState {
        data class IsLoading(val isLoading: Boolean) : GlossaryUiState()
        data class Error(val message: String) : GlossaryUiState()
        object Init : GlossaryUiState()
    }
}



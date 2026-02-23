package edu.ucne.apigoku.presentation.api.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.apigoku.domain.usecase.GetPlanetDetailUseCase
import edu.ucne.apigoku.domain.usecase.GetPlanetsUseCase
import edu.ucne.apigoku.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase,
    private val getPlanetDetailUseCase: GetPlanetDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlanetListUiState())
    val state = _state.asStateFlow()

    init {
        loadPlanets()
    }

    fun onEvent(event: PlanetListEvent) {
        when (event) {
            is PlanetListEvent.OnSearchQueryChange -> _state.update { it.copy(searchQuery = event.query) }
            is PlanetListEvent.UpdateFilterStatus -> _state.update { it.copy(filterStatus = event.isDestroyed) }
            PlanetListEvent.Search -> loadPlanets()
        }
    }

    private fun loadPlanets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val current = _state.value
            val query = current.searchQuery.trim()
            val idAsNumber = query.toIntOrNull()


            if (idAsNumber != null) {
                when (val result = getPlanetDetailUseCase(idAsNumber)) {
                    is Resource.Success -> {
                        val planet = result.data
                        val matchStatus = current.filterStatus == null || planet?.isDestroyed == current.filterStatus
                        val list = if (planet != null && matchStatus) listOf(planet) else emptyList()
                        _state.update { it.copy(isLoading = false, planets = list) }
                    }
                    is Resource.Error -> _state.update { it.copy(isLoading = false, error = "No se encontró el planeta", planets = emptyList()) }
                    is Resource.Loading -> Unit
                }
            }

            else {
                when (val result = getPlanetsUseCase(name = query.takeIf { it.isNotBlank() })) {
                    is Resource.Success -> {
                        var list = result.data ?: emptyList()


                        if (current.filterStatus != null) {
                            list = list.filter { it.isDestroyed == current.filterStatus }
                        }

                        _state.update { it.copy(isLoading = false, planets = list) }
                    }
                    is Resource.Error -> _state.update { it.copy(isLoading = false, error = result.message, planets = emptyList()) }
                    is Resource.Loading -> Unit
                }
            }
        }
    }
}
package edu.ucne.apigoku.presentation.api.list

import edu.ucne.apigoku.domain.model.Planet

data class PlanetListUiState(
    val isLoading: Boolean = false,
    val planets: List<Planet> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val filterStatus: Boolean? = null
)
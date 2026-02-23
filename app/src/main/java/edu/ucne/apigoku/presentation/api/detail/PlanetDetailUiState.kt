package edu.ucne.apigoku.presentation.api.detail

import edu.ucne.apigoku.domain.model.Planet

data class PlanetDetailUiState(
    val isLoading: Boolean = false,
    val planet: Planet? = null,
    val error: String? = null
)
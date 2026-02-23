package edu.ucne.apigoku.presentation.api.list

sealed interface PlanetListEvent {
    data class OnSearchQueryChange(val query: String) : PlanetListEvent
    data class UpdateFilterStatus(val isDestroyed: Boolean?) : PlanetListEvent
    data object Search : PlanetListEvent
}
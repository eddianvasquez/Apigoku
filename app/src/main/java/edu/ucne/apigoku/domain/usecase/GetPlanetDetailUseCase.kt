package edu.ucne.apigoku.domain.usecase

import edu.ucne.apigoku.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetDetailUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(id: Int) = repository.getPlanetDetail(id)
}
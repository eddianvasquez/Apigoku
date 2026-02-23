package edu.ucne.apigoku.presentation.api.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.apigoku.domain.model.Planet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListScreen(
    viewModel: PlanetListViewModel = hiltViewModel(),
    onPlanetClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Planetas Dragon Ball") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {


            ElevatedCard(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { viewModel.onEvent(PlanetListEvent.OnSearchQueryChange(it)) },
                        label = { Text("Buscar por Nombre o ID") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Text("Filtrar por estado:", style = MaterialTheme.typography.bodySmall)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = state.filterStatus == null,
                            onClick = { viewModel.onEvent(PlanetListEvent.UpdateFilterStatus(null)) },
                            label = { Text("Todos") }
                        )
                        FilterChip(
                            selected = state.filterStatus == false,
                            onClick = { viewModel.onEvent(PlanetListEvent.UpdateFilterStatus(false)) },
                            label = { Text("Activos") }
                        )
                        FilterChip(
                            selected = state.filterStatus == true,
                            onClick = { viewModel.onEvent(PlanetListEvent.UpdateFilterStatus(true)) },
                            label = { Text("Destruidos") }
                        )
                    }

                    Button(
                        onClick = { viewModel.onEvent(PlanetListEvent.Search) },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Buscar")
                    }
                }
            }

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }


            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(state.planets) { planet ->
                    PlanetItem(planet = planet, onClick = { onPlanetClick(planet.id) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun PlanetItem(planet: Planet, onClick: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = planet.image,
                contentDescription = planet.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(planet.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (planet.isDestroyed) "Destruido" else "Activo",
                    color = if (planet.isDestroyed) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
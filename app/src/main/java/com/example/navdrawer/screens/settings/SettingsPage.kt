package com.example.navdrawer.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navdrawer.model.Etiqueta
import com.example.navdrawer.viewModel.AppViewModel


val charitableOrganizationNames = listOf(
    Etiqueta(id = "ID_0", nombre = "Charity A"),
    Etiqueta(id = "ID_1", nombre = "Charity B"),
    Etiqueta(id = "ID_2", nombre = "Charity C"),
    Etiqueta(id = "ID_3", nombre = "Charity D"),
    Etiqueta(id = "ID_4", nombre = "Charity E"),
    Etiqueta(id = "ID_5", nombre = "Charity F"),
    Etiqueta(id = "ID_6", nombre = "Charity G"),
    Etiqueta(id = "ID_7", nombre = "Charity H"),
    Etiqueta(id = "ID_8", nombre = "Charity I"),
    Etiqueta(id = "ID_9", nombre = "Charity J")
)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsPage(
    appViewModel: AppViewModel
) {

    Column {

        Text(text = "My favorite Orgs")


        FlowRow {
            charitableOrganizationNames.forEach {
                OrgTagCard(it.id,it.nombre)
            }
        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun PreviewOrgList(){

    FlowRow {
        charitableOrganizationNames.forEach {
            OrgTagCard(it.id,it.nombre)
        }
    }
}

@Preview
@Composable
fun OrgTagCard(id: String="123", name: String = "Charity A") {

    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp) // Reduce padding to make cards smaller
            .shadow(8.dp, shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.padding(18.dp), // Reduce padding for content
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp) // Reduce icon size
                    .clip(CircleShape)
                    .clickable {

                        isFavorite = !isFavorite
                        // Agregar aqui codigo para actualizar tu BD correspondiente con el id del favorito.
                        // OrgViewModel.updateFavorites(id)
                    }
            )

            Spacer(modifier = Modifier.height(4.dp)) // Reduce spacing

            Text(
                text = name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp) // Reduce text size
            )
        }
    }
}


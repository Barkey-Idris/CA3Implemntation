package com.example.ca3_implemntation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ca3_implemntation.ui.theme.CA3ImplemntationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dao = SampleItemDao()
        setContent {
            CA3ImplemntationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        InventoryApp(dao = dao)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CA3ImplemntationTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryAppPreview() {
    CA3ImplemntationTheme {
        InventoryApp(dao = SampleItemDao())
    }
}

@Composable
fun InventoryApp(dao: ItemDao) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
    ) {
        composable("home") {
            ItemListScreen(dao) { itemId ->
                navController.navigate("details/$itemId")
            }
        }

        composable(
            route = "details/{itemId}",
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                },
            ),
        ) { entry ->
            val itemId = entry.arguments?.getInt("itemId") ?: 0
            ItemDetailScreen(dao, itemId)
        }
    }
}

@Composable
fun ItemListScreen(
    dao: ItemDao,
    onItemClick: (Int) -> Unit,
) {
    val items by dao.getAllItems()
        .collectAsState(initial = emptyList())

    LazyColumn {
        items(items) { (id, name, quantity, price) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        onItemClick(id)
                    },
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Name: $name")
                    Text("Quantity: $quantity")
                    Text("Price: $price")
                }
            }
        }
    }
}

@Composable
fun ItemDetailScreen(
    dao: ItemDao,
    itemId: Int,
) {
    val item by dao.getItemById(itemId)
        .collectAsState(initial = null)
    val currentItem = item

    Column(
        modifier = Modifier.padding(20.dp),
    ) {
        Text("Item Details")

        if (currentItem != null) {
            Text("ID: ${currentItem.id}")
            Text("Name: ${currentItem.name}")
            Text("Quantity: ${currentItem.quantity}")
            Text("Price: ${currentItem.price}")
        } else {
            Text("Item not found")
        }
    }
}

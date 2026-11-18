package com.example.ca3_implemntation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class Item(
    val id: Int,
    val name: String,
    val quantity: Int,
    val price: Double,
)

interface ItemDao {
    fun getAllItems(): Flow<List<Item>>
    fun getItemById(id: Int): Flow<Item?>
}

class SampleItemDao : ItemDao {
    private val sampleItems = listOf(
        Item(1, "Laptop", 5, 999.99),
        Item(2, "Mouse", 25, 29.99),
        Item(3, "Keyboard", 15, 79.99),
        Item(4, "Monitor", 8, 199.99),
    )

    override fun getAllItems(): Flow<List<Item>> = flowOf(sampleItems)

    override fun getItemById(id: Int): Flow<Item?> = flowOf(sampleItems.find { (itemId) -> itemId == id })
}

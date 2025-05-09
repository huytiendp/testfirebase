package com.example.testfirebase

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

@Composable
fun HomeScreen() {
    val database = Firebase.database
    val myRef = database.getReference("message")
    myRef.setValue("Hello, World!")
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { newtext: String -> text = newtext },
            label = { Text(text = "enter your data") }
        )
        Button(
            onClick = { myRef.setValue(text) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Submit")
        }
    }
}
@Preview (showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
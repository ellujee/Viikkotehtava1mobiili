package com.example.viikkotehtava1mobiili

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikkotehtava1mobiili.domain.*
import com.example.viikkotehtava1mobiili.ui.theme.Viikkotehtava1mobiiliTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikkotehtava1mobiiliTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var taskList by remember { mutableStateOf(mockTasks) }

    Column(modifier = modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
        Text(text = "Viikkotehtävä 1: Tehtävälista", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val newTask = Task(taskList.size + 1, "New", "description", 1, "2026-05-05", false)
                taskList = addTask(taskList, newTask)
            }) { Text("Add")
            }

            Button(onClick = { taskList = sortByDueDate(taskList) }) {
                Text("Sort")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
            Button(onClick = { taskList = filterByDone(taskList, true) }) {
                Text("Show only finished")
            }
            Button(onClick = { taskList = mockTasks }) {
                Text("Show all")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        taskList.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "${task.id}. ${task.title}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Pvm: ${task.dueDate} | Done: ${task.done}", style = MaterialTheme.typography.bodySmall)
                }

                Button(onClick = { taskList = toggleDone(taskList, task.id) }) {
                    Text("Done")
                }
            }

        }
    }
}
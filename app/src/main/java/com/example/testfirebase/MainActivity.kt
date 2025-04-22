package com.example.testfirebase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testfirebase.ui.theme.TestfirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestfirebaseTheme {

                   Surface(modifier = Modifier.fillMaxSize(),
                       color= MaterialTheme.colorScheme.background) {
                       Scaffold(
                           topBar = {
                               TopAppBar(backgroundColor = MaterialTheme.colorScheme.primary,
                               title={
                                   Text(text = "GFG",
                                       modifier = Modifier.fillMaxWidth(),
                                       textAlign = TextAlign.Center,
                                       color= Color.White

                                       )
                               })
                           }) { innerPadding ->
                           Text (
                               modifier = Modifier.padding(innerPadding),
                                 text = "Them du lieu",
                           )
                           FirebaseUI(LocalContext.current)
                       }

                   }

            }
        }
    }
}

@Composable
fun FirebaseUI(context: Context){
    var courseID= remember { mutableStateOf("") }
    var courseName= remember { mutableStateOf("") }
    var  courseDescription= remember { mutableStateOf("") }
    var courseDuration= remember { mutableStateOf("") }
Column(modifier= Modifier.fillMaxSize()
    .background(Color.White),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally

    ){
    TextField(value= courseName.value, onValueChange = {courseName.value=it},
        modifier=Modifier.padding(16.dp).fillMaxWidth(),
        textStyle = TextStyle(color= Color.Black, fontSize= 15.sp),
        singleLine = true,
        )
Spacer(modifier = Modifier.height(10.dp))
    TextField (
        value=courseDuration.value, onValueChange = {courseDuration.value=it},
        placeholder = {Text(text="Enter course duration")},
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        textStyle = TextStyle(color= Color.Black, fontSize= 15.sp),
        singleLine = true,
    )
    Spacer(modifier = Modifier.height(10.dp))
    TextField (
        value=courseDescription.value, onValueChange = {courseDescription.value=it},
        placeholder = {Text(text="Enter course description")},
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        textStyle = TextStyle(color= Color.Black, fontSize= 15.sp),
        singleLine = true,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Button(onClick = {
        if (TextUtils.isEmpty(courseName.value.toString())) {
            Toast.makeText(context,"Please eter course Name", Toast.LENGTH_SHORT).show()
        }else if(TextUtils.isEmpty(courseDuration.value.toString())) {
            Toast.makeText(context, "Please eter course Duration", Toast.LENGTH_SHORT).show()
        }else if(TextUtils.isEmpty(courseDescription.value.toString())) {
            Toast.makeText(context, "Please eter course Description", Toast.LENGTH_SHORT).show()
        }else {
            addDataToFirebase( courseID.value, courseName.value, courseDescription.value, courseDuration.value,context)
        }
    },
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
        Text(text = "Add data", modifier = Modifier.padding(8.dp))
    }
    Spacer(modifier = Modifier.height(10.dp))
    Button(
        onClick = {
            // on below line opening course details activity.
            context.startActivity(Intent(context, CourseDetailsActivity::class.java))
        },
        // on below line we are
        // adding modifier to our button.
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // on below line we are adding text for our button
        Text(text = "View Courses", modifier = Modifier.padding(8.dp))
    }

}
}
fun addDataToFirebase(
    courseID:String,courseName: String, courseDuration: String, courseDescription: String, context: Context
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    // db.collection("Courses").document(courseID.toString()).set(updatedCourse)
    // creating a collection reference for our Firebase Firestore database.
    val dbCourses: CollectionReference = db.collection("Courses")

    // adding our data to our courses object class.
    val courses = Course(courseID,courseName, courseDuration,courseDescription)

    // below method is use to add data to Firebase Firestore
    // after the data addition is successful
    dbCourses.add(courses).addOnSuccessListener {
        // we are displaying a success toast message.
        Toast.makeText(
            context, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        // this method is called when the data addition process is failed.
        // displaying a toast message when data addition is failed.
        Toast.makeText(context, "Fail to add course \n$e", Toast.LENGTH_SHORT).show()
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestfirebaseTheme {
        FirebaseUI(LocalContext.current)
    }
}
package com.example.testfirebase

import com.google.firebase.database.Exclude

data class Course (
    @Exclude var courseID : String? = "",
    var courseName : String? = "",
    var courseDescription : String? = "",
    var courseDuration : String? = ""


)
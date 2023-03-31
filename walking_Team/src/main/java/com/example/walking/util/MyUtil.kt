package com.example.walking.util

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.walking.MyApplication
import com.example.walking.RegisterActivity.Companion.imgUri
import java.io.File
import java.text.SimpleDateFormat
import java.util.*



fun dateToString(date: Date): String {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return format.format(date)
}

//파이어 스토어 수정, 삭제 등
fun deleteStore(docId: String){
    //delete............................
    MyApplication.db.collection("news")
        .document(docId)
        .delete()

}

fun deleteImage(docId: String) {
    //add............................
    val storage = MyApplication.storage
    val storageRef = storage.reference
    val imgRef = storageRef.child("images/${docId}.jpg")
    imgRef.delete()
}


fun uploadImage(activity: AppCompatActivity,docId: String,filePath:String){
    //add............................
    val storage = MyApplication.storage
    val storageRef = storage.reference
    val imgRef = storageRef.child("profiles/${docId}.jpg")

    val file = Uri.fromFile(File(filePath))
    Log.d("park","file $file")
    imgUri?.let {
        imgRef.putFile(it)
        .addOnSuccessListener {
            Toast.makeText(activity, "save ok..", Toast.LENGTH_SHORT).show()
            activity.finish()
        }
        .addOnFailureListener{
            Log.d("lsy", "file save error", it)
        }
    }

}


fun updateStore(docId: String){
    //delete............................
    MyApplication.db.collection("news")
        .document(docId)
        .delete()

}
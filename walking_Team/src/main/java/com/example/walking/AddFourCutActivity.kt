package com.example.walking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walking.databinding.ActivityAddFourCutBinding
import com.example.walking.databinding.FragmentPhotoBinding
import com.example.walking.recycler.ListViewAdapter
import com.example.walking.recycler.MultiImageAdapter
import java.text.SimpleDateFormat
import java.util.*

class AddFourCutActivity : AppCompatActivity() {
    companion object {
    var resultUri:ArrayList<String> = ArrayList()

    var uriList: MutableList<Uri> = ArrayList()

    var docId: String = ""
    }
    lateinit var binding:ActivityAddFourCutBinding
    lateinit var binding2: FragmentPhotoBinding
    var recyclerView: RecyclerView? = null  // 이미지를 보여줄 리사이클러뷰
    var adapter: MultiImageAdapter? = null // 리사이클러뷰에 적용시킬 어댑터
    var adapter2: ListViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFourCutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryBtn.setOnClickListener {
            openGallery()
        }
        binding.addFourCut.setOnClickListener {
            addFirebase()
        }

    }

    private fun addFirebase() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storage = MyApplication.storage
        val db = MyApplication.db
        val hashtag = binding.hashtag.text.toString()

        for (photoUri in uriList) {
            val splitUri = "${timeStamp}${photoUri.toString().substring(151)}"
            val imgFileName = "IMAGE.png"
            val storageRef = storage.reference.child("images")?.child("$splitUri$imgFileName")
                storageRef?.putFile(photoUri)?.addOnSuccessListener {
                    Toast.makeText(applicationContext,"image upload",Toast.LENGTH_SHORT).show()
                    resultUri.add("$splitUri$imgFileName")
                    if (resultUri.size == 4) {
                        val data = mapOf(
                            "img1" to resultUri[0],
                            "img2" to resultUri[1],
                            "img3" to resultUri[2],
                            "img4" to resultUri[3]
                        )
                        db?.collection("testFourCut")?.add(data)?.addOnSuccessListener {
                            Toast.makeText(this,"성공",Toast.LENGTH_SHORT).show()
                        }
//                        val mainUri = storage.reference.child("images").child(resultUri[0]).downloadUrl
//                        val photoDto = com.example.walking.model.Photo(hashtag,timeStamp,mainUri.toString())
////               photoDto.mainUri = mainUri.toString()
////               photoDto.photo_board_date = System.currentTimeMillis()
////               photoDto.hashtag = hashtag
//                        db?.collection("Fourcut")?.document()?.set(photoDto)

                    }

        }
    }

//        Log.d("park","resultUri[0] : ${resultUri.size}")
//        val mainUri = storage.reference.child("images").child(resultUri[0]).downloadUrl
        finish()
    }

    private fun openGallery() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.type = "image/*"
        requestGalleryLauncher.launch(intent)
    }
    private val requestGalleryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {

        val clipData = it.data?.clipData
        if (clipData == null) { // 이미지를 선택하지 않았을 경우
            Toast.makeText(applicationContext,"이미지를 선택하지 않았습니다.",Toast.LENGTH_LONG).show()
        }
        else { // 이미지를 하나라도 선택한 경우
            Log.d("park","$clipData")
            if (clipData != null) { // 이미지를 하나 이상 선택한 경우
                uriList.clear()
                val count = clipData!!.itemCount
                Log.d("park","클립데이터 아이템카운트 $count")
                if (count != 4) {
                    Toast.makeText(applicationContext,"4장의 사진을 선택해주세요",Toast.LENGTH_LONG).show()
                } else {
                    for (i in 0 until count) {
                        val imageUri = clipData!!.getItemAt(i).uri
                        Log.d("park","이미지 uri getItemAt 알아보기 $imageUri")
                        uriList.add(imageUri)

                    }
                    Log.d("park","이미지URI 리스트 $uriList")
                    adapter = MultiImageAdapter(uriList, applicationContext)
                    recyclerView = binding.addRecyclerView
                    recyclerView?.adapter = adapter
                    recyclerView?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
                }

            }

        }
    }


//    private fun calculateInSampleSize(fileUriList: List<Uri>, reqWidth: Int, reqHeight: Int): Int {
//        val options = BitmapFactory.Options()
//        options.inJustDecodeBounds = true
//        try {
//            for (fileUri in fileUriList) {
//
//            var inputStream = contentResolver.openInputStream(fileUri)
//
//            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
//            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
//            BitmapFactory.decodeStream(inputStream, null, options)
//            inputStream!!.close()
//            inputStream = null
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        //비율 계산........................
//        val (height: Int, width: Int) = options.run { outHeight to outWidth }
//        var inSampleSize = 1
//        //inSampleSize 비율 계산
//        if (height > reqHeight || width > reqWidth) {
//
//            val halfHeight: Int = height / 2
//            val halfWidth: Int = width / 2
//
//            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
//                inSampleSize *= 2
//            }
//        }
//        return inSampleSize
//    }
}


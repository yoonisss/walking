package com.example.walking

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walking.databinding.ActivityShowFourCutBinding
import com.example.walking.recycler.ShowViewAdapter

class ShowFourCutActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowFourCutBinding
    var resultUri: ArrayList<String> = ArrayList()
    var recyclerView: RecyclerView? = null
    var adapter: ShowViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowFourCutBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val storage = MyApplication.storage.getReferenceFromUrl("gs://walkingbusanproject.appspot.com")
//        val pathRef = storage.child("images")
//        var resultUri: ArrayList<Uri> = AddFourCutActivity.resultUri

        if (!resultUri.isEmpty()) {
            resultUri.clear()
            resultUri = AddFourCutActivity.resultUri
        }
        else {
            resultUri = AddFourCutActivity.resultUri
        }
        Log.d("park","resultUri에 리스트로 값이 담김? $resultUri")
        binding.listBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        val img1 = intent.getStringExtra("img1")
        val img2 = intent.getStringExtra("img2")
        val img3 = intent.getStringExtra("img3")
        val img4 = intent.getStringExtra("img4")
        var count = 1
        val imgArray: MutableList<String?> = mutableListOf(img1,img2,img3,img4)
        Log.d("park","imgArray : $imgArray")
        var imgList: MutableList<Uri> = ArrayList()
        for(fourImg in imgArray) {
            MyApplication.storage.reference
                .child("images")
                .child(fourImg.toString())
                .downloadUrl.addOnSuccessListener {
                    imgList.add(it)
                    Log.d("park","imgList 반복문 : $imgList")
                    if (imgList.size == 4) {
                        Log.d("park","imgList : $imgList")
                        Glide.with(this)
                            .load(imgList[0])
                            .into(binding.img1)
                        Glide.with(this)
                            .load(imgList[1])
                            .into(binding.img2)
                        Glide.with(this)
                            .load(imgList[2])
                            .into(binding.img3)
                        Glide.with(this)
                            .load(imgList[3])
                            .into(binding.img4)
                    }
                }
            Log.d("park","imgList.size : ${imgList.size}")

        }



//        MyApplication.db.collection("testFoutCut")
//            .get()
//            .addOnSuccessListener {result ->
//                val imgList = mutableListOf<FireStoreImg>()
//                for (document in result) {
//                    val item = document.toObject(FireStoreImg::class.java)
//                    item.docId = document.id
//                    imgList.add(item)
//                }
//                adapter = ShowViewAdapter(imgList, applicationContext,AddFourCutActivity.uriList)
//                recyclerView = binding.showRecyclerView
//                recyclerView?.adapter = adapter
//                recyclerView?.layoutManager = GridLayoutManager(this,2)
//            }
//
//
//        adapter!!.notifyDataSetChanged()
    }

    }



import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.walking.databinding.ItemMainBinding
import com.example.walking.model.Item
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MyViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root) {
    var username=""

    init {
        val database = Firebase.database
        val myRef = database.getReference("username")

        myRef.get().addOnSuccessListener {
            username=it.value.toString()
        }

        binding.likebtn.setOnClickListener{
            binding.likebtn.visibility= View.GONE
            binding.likefill.visibility=View.VISIBLE
        }
        binding.likefill.setOnClickListener{
            binding.likefill.visibility= View.GONE
            binding.likebtn.visibility=View.VISIBLE
        }
    }

}
class MyAdapter(val context: Context, val datas: List<Item>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    init {
        setHasStableIds(true)
    }

    override fun getItemCount(): Int{
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding

//        var item: Item = datas?[position] //배열처럼 쓰는걸 권장
//        //본문글씨중에 html 태그문이 포함되어 있는걸 안보이도록 제거 : HtmlCompat.FROM_HTML_MODE_COMPACT
//        var ITEMCNTNTS:String = HtmlCompat.fromHtml(item.ITEMCNTNTS, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
//        holder.binding.maincontent.text = ITEMCNTNTS


        //add......................................
        val user = datas?.get(position)
        binding.username.text = user?.MAIN_TITLE
        binding.maincontent.text = user?.ITEMCNTNTS

        //본문글씨중에 html 태그문이 포함되어 있는걸 안보이도록 제거 : HtmlCompat.FROM_HTML_MODE_COMPACT
        var ITEMCNTNTS:String = HtmlCompat.fromHtml(user!!.ITEMCNTNTS, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
        holder.binding.maincontent.text = ITEMCNTNTS



        val urlImg = user?.MAIN_IMG_THUMB
        Glide.with(context)
            .asBitmap()
            .load(urlImg)
            .into(object : CustomTarget<Bitmap>(400, 350) {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.mainImg.setImageBitmap(resource)
                    Log.d("chc", "width : ${resource.width}, height: ${resource.height}")
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })

    }



    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
}
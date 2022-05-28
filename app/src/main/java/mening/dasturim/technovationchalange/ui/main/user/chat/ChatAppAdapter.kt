package mening.dasturim.technovationchalange.ui.main.user.chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.data.module.User
import mening.dasturim.technovationchalange.databinding.ItemChatBinding
import mening.dasturim.technovationchalange.ui.main.user.chat.message.MessageActivity

class ChatAppAdapter(
    var context:Context,
    var arrayList: ArrayList<User>
) : RecyclerView.Adapter<ChatAppAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAppAdapter.VH {
        val v =LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: ChatAppAdapter.VH, position: Int) {
        val user = arrayList[position]
        holder.binding.tvName.text = user.nickname
        Glide.with(context) //1
            .load(user.profileImage)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.no_image)
            .skipMemoryCache(true) //2
            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
            .transform(CircleCrop()) //4
            .into(holder.binding.image)

//        Glide.with(context).load(user.profileImage)
//            .into(holder.binding.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,MessageActivity::class.java)
            intent.putExtra("nickname",user.nickname)
            intent.putExtra("image",user.profileImage)
            intent.putExtra("uid",user.uid)

            context.startActivity(intent)
        }

    }

    override fun getItemCount()= arrayList.size

    class VH(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding:ItemChatBinding = ItemChatBinding.bind(itemView)

    }
}
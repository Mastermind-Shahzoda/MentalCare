package mening.dasturim.technovationchalange.ui.main.user.chat.message

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.data.module.MessageItems
import mening.dasturim.technovationchalange.databinding.DelateLayoutBinding
import mening.dasturim.technovationchalange.databinding.ReceiveMsgBinding
import mening.dasturim.technovationchalange.databinding.SendMsgBinding

class MessageAdapter(
    var context: Context,
   var messageList: ArrayList<MessageItems>,
   var senderRomm: String,
   var receiverRoom: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
   // lateinit var message: ArrayList<MessageItems>
    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2
//    val senderRomm: String
//    var receiverRoom: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == ITEM_SENT){
            val view:View = LayoutInflater.from(context).inflate(R.layout.send_msg,parent,false)
            SentMsgHolder(view)
        }else {
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive_msg,parent,false)
            ReceiveMsgHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val messages = messageList[position]

        return if (FirebaseAuth.getInstance().uid == messages.senderId){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageViewHolder = messageList[position]
         if (holder.javaClass == SentMsgHolder :: class.java){
             val viewHolder = holder as SentMsgHolder
             if (messageViewHolder.message.equals("image")){
                 viewHolder.binding.ivSend.visibility = View.VISIBLE
                 viewHolder.binding.tvSend.visibility = View.GONE
                 viewHolder.binding.slinear.visibility = View.GONE

                 Glide.with(context)
                     .load(messageViewHolder.imageUrl)
                     .placeholder(R.drawable.doctor1)
                     .into(viewHolder.binding.ivSend)

             }
             viewHolder.binding.tvSend.text = messageViewHolder.message
             viewHolder.itemView.setOnLongClickListener {
                 val view = LayoutInflater.from(context).inflate(R.layout.delate_layout,null)

                 val deleteBinding:DelateLayoutBinding = DelateLayoutBinding.bind(view)

                 val dialog= AlertDialog.Builder(context)
                     .setTitle("Delete Message")
                     .setView(deleteBinding.root)
                     .create()



                 deleteBinding.tvEveryone.setOnClickListener {
                     messageViewHolder.message = "This message is removed"
                     messageViewHolder.messageId?.let { it1->
                         FirebaseDatabase.getInstance().reference.child("chats")
                             .child(senderRomm)
                             .child("message")
                             .child(it1).setValue(messageList)
                     }
                     messageViewHolder.messageId?.let { it1->
                         FirebaseDatabase.getInstance().reference.child("chats")
                             .child(receiverRoom)
                             .child("message")
                             .child(it1).setValue(messageList)
                     }
                     dialog.dismiss()
                 }
                 deleteBinding.tvDelete.setOnClickListener {
                     messageViewHolder.messageId.let { it1->
                         FirebaseDatabase.getInstance().reference
                             .child("chats")
                             .child(senderRomm)
                             .child("message")
                             .child(it1!!).setValue(null)
                     }
                     dialog.dismiss()

                 }
                 deleteBinding.cancel.setOnClickListener {
                     dialog.dismiss()
                 }
                 dialog.show()
                 false
             }
         }
        else{
            val viewHolder = holder as ReceiveMsgHolder
             if (messageViewHolder.message.equals("image")){
                 viewHolder.binding.ivReceive.visibility = View.VISIBLE
                 viewHolder.binding.tvReceive.visibility = View.GONE
                 viewHolder.binding.mlinear.visibility = View.GONE

                 Glide.with(context)
                     .load(messageViewHolder.imageUrl)
                     .placeholder(R.drawable.doctor1)
                     .into(viewHolder.binding.ivReceive)
             }
             viewHolder.binding.tvReceive.text = messageViewHolder.message
             viewHolder.itemView.setOnLongClickListener {
                 val view = LayoutInflater.from(context).inflate(R.layout.delate_layout, null)

                 val deleteBinding: DelateLayoutBinding = DelateLayoutBinding.bind(view)

                 val dialog = AlertDialog.Builder(context)
                     .setTitle("Delete Message")
                     .setView(deleteBinding.root)
                     .create()



                 deleteBinding.tvEveryone.setOnClickListener {
                     messageViewHolder.message = "This message is removed"
                     messageViewHolder.messageId?.let { it1 ->
                         FirebaseDatabase.getInstance().reference.child("chats")
                             .child(senderRomm)
                             .child("message")
                             .child(it1).setValue(messageList)
                     }
                     messageViewHolder.messageId?.let { it1 ->
                         FirebaseDatabase.getInstance().reference.child("chats")
                             .child(receiverRoom)
                             .child("message")
                             .child(it1).setValue(messageList)
                     }
                     dialog.dismiss()
                 }
                 deleteBinding.tvDelete.setOnClickListener {
                     messageViewHolder.messageId.let { it1 ->
                         FirebaseDatabase.getInstance().reference
                             .child("chats")
                             .child(senderRomm)
                             .child("message")
                             .child(it1!!).setValue(null)
                     }
                     dialog.dismiss()

                 }
                 deleteBinding.cancel.setOnClickListener {
                     dialog.dismiss()
                 }
                 dialog.show()

                 false
             }
         }
    }

    override fun getItemCount() = messageList.size

    inner class SentMsgHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: SendMsgBinding = SendMsgBinding.bind(itemView)
    }

    inner class ReceiveMsgHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ReceiveMsgBinding = ReceiveMsgBinding.bind(itemView)
    }

//    init {
////        if (messageList != null) {
////            this.messageList = messageList
////        }
//        this.senderRomm = senderRomm
//        this.receiverRoom = receiverRoom
//
//    }
}
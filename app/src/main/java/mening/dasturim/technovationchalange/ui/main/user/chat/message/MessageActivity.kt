package mening.dasturim.technovationchalange.ui.main.user.chat.message

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.data.module.MessageItems
import mening.dasturim.technovationchalange.databinding.ActivityMessageBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MessageActivity : AppCompatActivity() {
    var binding : ActivityMessageBinding? =null
    var adapter: MessageAdapter? = null
    var messageList: ArrayList<MessageItems>? = null
    var senderRoom :String? = null
    var receiverRoom :String? = null
    var database:FirebaseDatabase? =null
    var storage : FirebaseStorage? =null
    var dialog : ProgressDialog? = null
    var senderUid: String? = null
    var receiverUid : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        setSupportActionBar(binding!!.toolbar)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        dialog = ProgressDialog(this)
        dialog!!.setMessage("Uploading image...")
        dialog!!.setCancelable(false)

        messageList = ArrayList()
        val name = intent.getStringExtra("nickname")
        val profile = intent.getStringExtra("image")
        binding!!.tvProfile.text = name
        Glide.with(this).load(profile).into(binding!!.image2)
        binding!!.ivBack.setOnClickListener {
            finish()
        }
        receiverUid = intent.getStringExtra("uid")
        senderUid = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence").child(receiverUid!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val status = snapshot.getValue(String::class.java)
                        if(status == "offline"){
                            binding!!.tvOnlin.visibility = View.GONE

                        }else{
                            binding!!.tvOnlin.setText(status)
                            binding!!.tvOnlin.visibility = View.GONE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

        adapter = MessageAdapter(this,messageList!!,senderRoom!!,receiverRoom!!)
        binding!!.rv.layoutManager =LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding!!.rv.adapter = adapter
        database!!.reference.child("chats")
            .child(senderRoom!!)
            .child("message")
            .addValueEventListener(object :  ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                   messageList!!.clear()
                   for(snapshot1 in snapshot.children){
                       val message :MessageItems? = snapshot1.getValue(MessageItems ::class.java)
                       message!!.messageId = snapshot1.key
                       messageList!!.add(message)

                   }
                    adapter!!.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        binding!!.ivSend.setOnClickListener {

            val messageText:String = binding!!.etMessage.text.toString()
            val date = Date()
            val meassage = MessageItems(messageText,senderUid,date.time)

            binding!!.etMessage.setText("")
            val randomKey = database!!.reference.push().key
            val lastMsgObj = HashMap<String,Any>()
            lastMsgObj["lastMsg"] = meassage.message!!
            lastMsgObj["lastObjTime"] =date.time

            database!!.reference.child("chats").child(senderRoom!!)
                .updateChildren(lastMsgObj)
              database!!.reference.child("chats").child(receiverRoom!!)
                .updateChildren(lastMsgObj)


            database!!.reference.child("chats").child(senderRoom!!)
                .child("messages")
                .child(randomKey!!)
                .setValue(meassage).addOnSuccessListener {
                    database!!.reference.child("chats")
                        .child(receiverRoom!!)
                        .child("message")
                        .child(randomKey)
                        .setValue(meassage)
                        .addOnSuccessListener {

                        }
                }
            database!!.reference.child("chats").child(receiverRoom!!)
                .child("messages")
                .child(randomKey)
                .setValue(meassage).addOnSuccessListener {
                    database!!.reference.child("chats")
                        .child(senderRoom!!)
                        .child("message")
                        .child(randomKey)
                        .setValue(meassage)
                        .addOnSuccessListener {

                        }
                }
         }
        binding!!.ivMenu.setOnClickListener {
            val intent= Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,85)

        }

        val handler = Handler()
        binding!!.etMessage.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
               database!!.reference.child("Presence")
                   .child(senderUid!!)
                   .setValue("typing..,")
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed(userStopedTyping,1000)

            }
            var userStopedTyping = Runnable {
                database!!.reference.child("Presence")
                    .child(senderUid!!)
                    .setValue("OnLine")

            }

        })

        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence")
            .child(currentId!!)
            .setValue("offline")
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("Presence")
            .child(currentId!!)
            .setValue("Online")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 25){
            if (data != null ){
                if (data.data != null){
                    val selectedImage = data.data
                    val calendar = Calendar.getInstance()
                    val refence= storage!!.reference.child("chats")
                        .child(calendar.timeInMillis.toString() + "")

                    dialog!!.show()
                    refence.putFile(selectedImage!!)
                        .addOnCompleteListener{task->
                            dialog!!.dismiss()
                            if (task.isSuccessful){
                                refence.downloadUrl.addOnSuccessListener {
                                    val filePath = it.toString()
                                    val messageTxt :String = binding!!.etMessage.text.toString()
                                    val date = Date()
                                    val message = MessageItems(messageTxt,senderUid,date.time)
                                    message.message = "photo"
                                    message.imageUrl = filePath
                                    binding!!.etMessage.setText("")
                                    val randomKey = database!!.reference.push().key

                                    val lastMsgObj =HashMap<String,Any>()
                                    lastMsgObj["lastMsg"] = message.message!!
                                    lastMsgObj["lastMsgTime"] = date.time

                                    database!!.reference.child("chats")
                                        .updateChildren(lastMsgObj)

                                     database!!.reference.child("chats")
                                         .child(receiverRoom!!)
                                        .updateChildren(lastMsgObj)
                                       database!!.reference.child("chats")
                                         .child(senderRoom!!)
                                           .child("messages")
                                           .child(randomKey!!)
                                           .setValue(message).addOnSuccessListener {
                                               database!!.reference.child("chats")
                                                   .child(receiverRoom!!)
                                                   .child("messages")
                                                   .child(randomKey)
                                                   .setValue(message)
                                                   .addOnSuccessListener {  }
                                           }


                                }
                            }
                        }
                }
            }
        }
    }
}
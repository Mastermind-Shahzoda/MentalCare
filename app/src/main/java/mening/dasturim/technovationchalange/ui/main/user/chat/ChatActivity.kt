package mening.dasturim.technovationchalange.ui.main.user.chat

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mening.dasturim.technovationchalange.data.module.User
import mening.dasturim.technovationchalange.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    var binding : ActivityChatBinding? =null
    var database: FirebaseDatabase? =null
    var userList : ArrayList<User>? = null
    var usersAdapter:ChatAppAdapter? =null
    var dialog: ProgressDialog? = null
    var user :User? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        dialog = ProgressDialog(this)
        dialog!!.setMessage("Update Image..")
        dialog!!.setCancelable(false)

      //  user=User()

        database =FirebaseDatabase.getInstance()
        userList = ArrayList<User>()

        val layoutManager =LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding!!.rvChat.layoutManager = layoutManager



        database!!.reference.child("users")
            .child(FirebaseAuth.getInstance().uid!!)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user= snapshot.getValue(User::class.java)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        database!!.reference.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList!!.clear()
                for (snapshot1 in snapshot.children){
                    val user:User? = snapshot1.getValue(User::class.java)
                    if (!user!!.uid.equals(FirebaseAuth.getInstance().uid)) userList!!.add(user)

                }
                usersAdapter!!.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        usersAdapter = ChatAppAdapter(this,userList!!)
        binding!!.rvChat.adapter = usersAdapter
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("presence")
            .child(currentId!!).setValue("Online")

    }

    override fun onPause() {
        super.onPause()
        val currentId = FirebaseAuth.getInstance().uid
        database!!.reference.child("presence")
            .child(currentId!!).setValue("offline")



    }
}
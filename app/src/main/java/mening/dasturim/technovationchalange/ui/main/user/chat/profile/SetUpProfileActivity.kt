package mening.dasturim.technovationchalange.ui.main.user.chat.profile

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import mening.dasturim.technovationchalange.data.module.User
import mening.dasturim.technovationchalange.databinding.ActivitySetUpProfileBinding
import mening.dasturim.technovationchalange.ui.main.user.chat.ChatActivity
import java.util.*
import kotlin.collections.HashMap

class SetUpProfileActivity : AppCompatActivity() {
    var binding: ActivitySetUpProfileBinding? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage : Uri? = null
    var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUpProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        dialog = ProgressDialog(this)
        dialog!!.setMessage("Updating Profile...")
        dialog!!.setCancelable(false)

        database= FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        binding!!.circularImageView.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,45)

        }

        binding!!.mbActivation.setOnClickListener {
            val name:String = binding!!.etName.text.toString()
            if (name.isEmpty()){
                binding!!.etName.error = "Please type a name"
            }
             val nickName:String = binding!!.etNickname.text.toString()
            if (nickName.isEmpty()){
                binding!!.etNickname.error = "Please type a nickName"
            }

            dialog!!.show()
            if (selectedImage != null){
                val reference = storage!!.reference.child("Profile")
                    .child(auth!!.uid!!)
                reference.putFile(selectedImage!!).addOnCompleteListener{task->
                    if (task.isSuccessful){
                        reference.downloadUrl.addOnCompleteListener { uri ->
                         //   dialog!!.dismiss()
                            val imageUri = uri.toString()
                            val uid = auth!!.uid
                            val phone = auth!!.currentUser!!.phoneNumber
                            val name2 :String = binding!!.etName.text.toString()
                            val nickname :String = binding!!.etNickname.text.toString()
                            val user= User(uid,name2,nickname, phone,imageUri)

                            database!!.reference
                                .child("users")
                                .child(uid!!)
                                .setValue(user)
                                .addOnCompleteListener {
                                    dialog!!.dismiss()
                                    val intent = Intent(this,ChatActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                        }
                    }
                    else{
                        val uid = auth!!.uid
                        val phone = auth!!.currentUser!!.phoneNumber
                        val name2 :String = binding!!.etName.text.toString()
                        val nickname :String = binding!!.etNickname.text.toString()
                        val user =User(uid,name2,nickname,phone,"No Image")

                        database!!.reference
                            .child("users")
                            .child(uid!!)
                            .setValue(user)
                            .addOnCanceledListener {
                                dialog!!.dismiss()
                                val intent =Intent(this,ChatActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                    }
                }
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null){
            if (data.data != null){
                val uri = data.data  //filePath
                val storage = FirebaseStorage.getInstance()
                val time = Date().time
                val reference = storage.reference
                    .child("Profile")
                    .child(time.toString() + "")

                reference.putFile(uri!!).addOnCompleteListener{task->

                    if (task.isSuccessful){
                        reference.downloadUrl.addOnCompleteListener {uri->
                            val filePath = uri.toString()
                            val obj = HashMap<String,Any>()
                            obj["image"] = filePath
                            database!!.reference
                                .child("users")
                                .child(FirebaseAuth.getInstance().uid!!)
                                .updateChildren(obj).addOnSuccessListener {  }

                        }
                    }
                }
                binding!!.circularImageView.setImageURI(data.data)
                selectedImage = data.data
            }
        }
    }
}
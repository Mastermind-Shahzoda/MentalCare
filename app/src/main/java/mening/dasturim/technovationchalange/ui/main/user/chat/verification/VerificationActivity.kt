package mening.dasturim.technovationchalange.ui.main.user.chat.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.databinding.ActivityVerificationBinding
import mening.dasturim.technovationchalange.ui.main.user.chat.ChatActivity
import mening.dasturim.technovationchalange.ui.main.user.chat.activation.ActivationActivity

class VerificationActivity : AppCompatActivity() {
    var binding:ActivityVerificationBinding? = null
    var auth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser != null){
            val intent  = Intent(this,ChatActivity::class.java)
            startActivity(intent)
            finish()
        }
        supportActionBar?.hide()
        binding!!.etPhone.requestFocus()
        binding!!.mbVerification.setOnClickListener {
            val intent= Intent(this,ActivationActivity::class.java)
            intent.putExtra("phoneNumber",binding!!.etPhone.text.toString())
            startActivity(intent)

        }
    }
}
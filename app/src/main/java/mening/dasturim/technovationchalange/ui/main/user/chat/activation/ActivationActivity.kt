package mening.dasturim.technovationchalange.ui.main.user.chat.activation


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import mening.dasturim.technovationchalange.databinding.ActivityActivationBinding
import mening.dasturim.technovationchalange.ui.main.user.chat.profile.SetUpProfileActivity
import java.util.concurrent.TimeUnit

class ActivationActivity :  AppCompatActivity() {
    var binding: ActivityActivationBinding? = null
    var verifcationId: String? = null
    var auth : FirebaseAuth? = null
    var dialog : ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityActivationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        dialog = ProgressDialog(this)
        dialog!!.setMessage("Sending code..")
        dialog!!.setCancelable(false)
        dialog!!.show()
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        val phoneNumber = intent.getStringExtra("phoneNumber")
        binding!!.tvVerify.text = "Verify $phoneNumber code:"

        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object :  PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {

                }

                override fun onCodeSent(verifiyId: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verifiyId, forceResendingToken)
                    dialog!!.dismiss()
                    verifcationId = verifiyId
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
                    binding!!.otpView.requestFocus()

                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
        binding!!.otpView.setOtpCompletionListener { otp ->
            val credential = PhoneAuthProvider.getCredential(verifcationId!!, otp)
            auth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, SetUpProfileActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
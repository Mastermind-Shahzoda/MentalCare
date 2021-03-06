package mening.dasturim.technovationchalange.ui.main.user.consultation.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import mening.dasturim.technovationchalange.R

class DoctorDetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view =  inflater.inflate(R.layout.fragment_doctor_details, container, false)

        view.findViewById<MaterialButton>(R.id.btn).setOnClickListener {
            findNavController().navigate(R.id.acceptionFragment)
        }
        return view
    }
}
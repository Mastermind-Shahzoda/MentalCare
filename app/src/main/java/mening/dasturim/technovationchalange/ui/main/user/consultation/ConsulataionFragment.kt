package mening.dasturim.technovationchalange.ui.main.user.consultation

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.databinding.FragmentConsulataionBinding
import mening.dasturim.technovationchalange.ui.base.BaseFragment
import mening.dasturim.technovationchalange.ui.main.user.home.HomeVM

class ConsulataionFragment : BaseFragment<FragmentConsulataionBinding,HomeVM>() {
    override fun onBound() {
     setUp()
    }

    fun setUp(){
        binding.cvDoc.setOnClickListener {
            findNavController().navigate(R.id.doctorDetailsFragment)
        }
        binding.cvDoc2.setOnClickListener {
            findNavController().navigate(R.id.doctorDetailsFragment)
        }
         binding.cvIi.setOnClickListener {
            findNavController().navigate(R.id.irtificialFragment)
        }

    }

    override fun getLayoutResId()= R.layout.fragment_consulataion

    override val vm: HomeVM
        get() = ViewModelProvider(this).get(HomeVM::class.java)


}
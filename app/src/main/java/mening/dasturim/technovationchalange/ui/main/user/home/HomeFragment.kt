package mening.dasturim.technovationchalange.ui.main.user.home

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.databinding.FragmentHomeBinding
import mening.dasturim.technovationchalange.ui.base.BaseFragment
import mening.dasturim.technovationchalange.utils.ViewUtils

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeVM>() {

    override fun onBound() {
        setUp()
    }

    fun setUp() {
        binding.cvHappy.setOnClickListener {
            setHappy()
        }
        binding.cvGood.setOnClickListener {
            setGood()
        }
        binding.cvSad.setOnClickListener {
            setSad()
        }




        binding.cvQueues.setOnClickListener {
            setQueues()
        }
        binding.ivQueues.setOnClickListener {
            setQueues()
        }
        binding.cvCrying.setOnClickListener {
            setCansultation()
        }
        binding.cvAwful.setOnClickListener {
            setCansultation()
        }
    }

    fun setQueues() {
        findNavController().navigate(R.id.action_homeFragment_to_queuesFragment)
    }

    fun setCansultation() {
        findNavController().navigate(R.id.action_homeFragment_to_consulataionFragment)
    }

    fun setHappy() {
        ViewUtils.fadeIn(binding.cvMon)
        ViewUtils.fadeOut(binding.cv2)
        ViewUtils.fadeOut(binding.cv3)
    }

    fun setGood() {
        ViewUtils.fadeIn(binding.cv2)
        ViewUtils.fadeOut(binding.cvMon)
        ViewUtils.fadeOut(binding.cv3)
    }

    fun setSad() {
        ViewUtils.fadeIn(binding.cv3)
        ViewUtils.fadeOut(binding.cv2)
        ViewUtils.fadeOut(binding.cvMon)
    }

    override fun getLayoutResId() = R.layout.fragment_home
    override val vm: HomeVM
        get() = ViewModelProvider(this).get(HomeVM::class.java)

}
package mening.dasturim.technovationchalange.ui.main

import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.databinding.ActivityMainBinding
import mening.dasturim.technovationchalange.ui.base.BaseActivity
import mening.dasturim.technovationchalange.utils.ViewUtils

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onBound() {
        setUp()
    }

    fun setUp() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_cv)
        if (navHost != null) {
            navController = navHost.findNavController()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.homeFragment) {
                ViewUtils.fadeOut(binding.appBarLayout)
            } else {
                ViewUtils.fadeIn(binding.appBarLayout)
            }


            if (destination.id == R.id.queuesFragment) {
                binding.tvMain.text = getString(R.string.app_bar_txt)
                ViewUtils.fadeOut(binding.ivFavourite)
            }  else if (destination.id == R.id.consulataionFragment) {
                binding.tvMain.text = getString(R.string.psychologists)
                ViewUtils.fadeOut(binding.ivFavourite)
            } else if (destination.id == R.id.doctorDetailsFragment) {
                ViewUtils.fadeIn(binding.ivFavourite)
                binding.tvMain.text = getString(R.string.about_psychologists)
            } else if (destination.id == R.id.acceptionFragment) {
                ViewUtils.fadeOut(binding.ivFavourite)
                binding.tvMain.text = getString(R.string.sign_up)
            } else if (destination.id == R.id.irtificialFragment) {
                ViewUtils.fadeOut(binding.ivFavourite)
                binding.tvMain.text = getString(R.string.ai)
            }
        }
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.fab.setOnClickListener {
            navController.navigate(R.id.consulataionFragment)
        }
        binding.ibClock.setOnClickListener {
            navController.navigate(R.id.queuesFragment)

            binding.ibClock.imageTintList =
                ContextCompat.getColorStateList(this, R.color.black_light)
            binding.ibHome.imageTintList = ContextCompat.getColorStateList(this, R.color.icon_blue)
            binding.ibMessage.imageTintList =
                ContextCompat.getColorStateList(this, R.color.icon_blue)
            binding.ibProfile.imageTintList =
                ContextCompat.getColorStateList(this, R.color.icon_blue)
        }
        binding.ibHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)

            binding.ibHome.imageTintList =
                ContextCompat.getColorStateList(this, R.color.black_light)
            binding.ibClock.imageTintList = ContextCompat.getColorStateList(this, R.color.icon_blue)
            binding.ibMessage.imageTintList =
                ContextCompat.getColorStateList(this, R.color.icon_blue)
            binding.ibProfile.imageTintList =
                ContextCompat.getColorStateList(this, R.color.icon_blue)
        }
        binding.ibMessage.setOnClickListener {
            navController.navigate(R.id.verificationActivity)

            binding.ibMessage.imageTintList =
                ContextCompat.getColorStateList(this, R.color.black_light)
            binding.ibClock.imageTintList = ContextCompat.getColorStateList(this, R.color.icon_blue)
            binding.ibHome.imageTintList = ContextCompat.getColorStateList(this, R.color.icon_blue)
            binding.ibProfile.imageTintList =
                ContextCompat.getColorStateList(this, R.color.icon_blue)
        }
    }


    override fun getLayoutResId() = R.layout.activity_main

    override val vm: MainVM by viewModels()

    override fun setStatusBarBackgroundHeight(statusBarBackground: View) {

    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
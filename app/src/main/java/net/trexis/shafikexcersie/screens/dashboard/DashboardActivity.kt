package net.trexis.shafikexcersie.screens.dashboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import net.trexis.shafikexcersie.R
import net.trexis.shafikexcersie.commons.CustomLoadingDialog
import net.trexis.shafikexcersie.databinding.ActivityDashboardBinding

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding

    companion object {
        lateinit var pdDashboard: CustomLoadingDialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        try {
            val navHostFrag =
                supportFragmentManager.findFragmentById(R.id.dashboardFcv) as NavHostFragment
            binding.dashboardBnv.setupWithNavController(navHostFrag.navController)
        } catch (e: Exception) {
            Log.e("dashboard", "catch: ${e.message}")
        }
    }

    private fun init() {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdDashboard = CustomLoadingDialog(context = this@DashboardActivity)
    }

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this@DashboardActivity)
            .setMessage(getString(R.string.dialog_exit_msg))
            .setPositiveButton(getString(R.string.yes))
            { _, _ ->
                kotlin.run {
                    finish()
                }
            }
            .setNegativeButton(getString(R.string.no))
            { dialog, _ ->
                kotlin.run {
                    dialog.dismiss()
                }
            }
            .show()
    }
}
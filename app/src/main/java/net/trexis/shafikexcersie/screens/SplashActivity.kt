package net.trexis.shafikexcersie.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import net.trexis.shafikexcersie.R
import net.trexis.shafikexcersie.screens.login.LoginActivity
import net.trexis.shafikexcersie.screens.dashboard.DashboardActivity
import net.trexis.shafikexcersie.session.SessionRepo
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionRepo: SessionRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        lifecycleScope.launchWhenStarted {
            delay(2000)
            finish()
            val myIntent = if (sessionRepo.isLogin())
                Intent(this@SplashActivity, DashboardActivity::class.java)
            else
                Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(myIntent)
        }
    }
}
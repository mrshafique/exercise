package net.trexis.shafikexcersie.screens.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import net.trexis.shafikexcersie.R
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.commons.CustomLoadingDialog
import net.trexis.shafikexcersie.databinding.ActivityLoginBinding
import net.trexis.shafikexcersie.interfaces.IActivity
import net.trexis.shafikexcersie.screens.dashboard.DashboardActivity
import net.trexis.shafikexcersie.session.SessionRepo
import net.trexis.shafikexcersie.utils.Validations.isFiledEmpty
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), IActivity {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var pd: CustomLoadingDialog
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sessionRepo: SessionRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        observer()

        binding.loginBtnLogin.setOnClickListener {
            val strUsername = binding.loginEtUsername.text.toString().trim()
            if (isFiledEmpty(strData = strUsername)) {
                binding.loginLayoutUsername.error = getString(R.string.error_empty_field)
                return@setOnClickListener
            }
            binding.loginLayoutUsername.error = null

            val strPassword = binding.loginEtPass.text.toString().trim()
            if (isFiledEmpty(strData = strPassword)) {
                binding.loginLayoutPassword.error = getString(R.string.error_empty_field)
                return@setOnClickListener
            }
            binding.loginLayoutPassword.error = null

            viewModel.getLogin(strUsername = strUsername, strPassword = strPassword)
        }
    }

    override fun init() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pd = CustomLoadingDialog(context = this@LoginActivity)
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlowLoginResp.collectLatest {
                when (it) {
                    is ApiResponse.Loading -> pd.show()
                    is ApiResponse.Success -> {
                        pd.dismiss()
                        if (it.data == 200) {
                            Toast.makeText(
                                this@LoginActivity,
                                getString(R.string.login_success),
                                Toast.LENGTH_SHORT
                            ).show()

                            sessionRepo.isLogin(true)
                            finish()
                            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        } else {
                            MaterialAlertDialogBuilder(this@LoginActivity)
                                .setMessage(getString(R.string.error_invalid_credentials))
                                .setPositiveButton(getString(R.string.close))
                                { dialog, _ ->
                                    kotlin.run {
                                        dialog.dismiss()
                                    }
                                }
                                .show()
                        }
                    }
                    is ApiResponse.Error -> {
                        pd.dismiss()
                        MaterialAlertDialogBuilder(this@LoginActivity)
                            .setMessage(it.exception.message.toString())
                            .setPositiveButton(getString(R.string.close))
                            { dialog, _ ->
                                kotlin.run {
                                    dialog.dismiss()
                                }
                            }
                            .show()
                    }
                    else -> loge("observer: else")
                }
            }
        }
    }

    override fun loge(msg: String) {
        Log.e("login", msg)
    }
}
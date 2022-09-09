package net.trexis.shafikexcersie.screens.dashboard.two

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import net.trexis.shafikexcersie.R
import net.trexis.shafikexcersie.databinding.FragTwoBinding
import net.trexis.shafikexcersie.screens.login.LoginActivity
import net.trexis.shafikexcersie.session.SessionRepo
import javax.inject.Inject

@AndroidEntryPoint
class FragTwo : Fragment(R.layout.frag_two) {
    @Inject
    lateinit var sessionRepo: SessionRepo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragTwoBinding.bind(view)
        binding.fragTwoBtnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setMessage(getString(R.string.dialog_logout_msg))
                .setPositiveButton(getString(R.string.yes))
                { _, _ ->
                    kotlin.run {
                        sessionRepo.isLogin(isLogin = false)
                        requireActivity().finish()
                        startActivity(Intent(requireActivity(), LoginActivity::class.java))
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
}
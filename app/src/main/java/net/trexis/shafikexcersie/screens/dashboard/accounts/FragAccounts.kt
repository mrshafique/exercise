package net.trexis.shafikexcersie.screens.dashboard.accounts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import net.trexis.shafikexcersie.R
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.databinding.LayoutRvBinding
import net.trexis.shafikexcersie.databinding.SingleAccountsBinding
import net.trexis.shafikexcersie.model.Account
import net.trexis.shafikexcersie.screens.dashboard.DashboardActivity
import net.trexis.shafikexcersie.screens.transactions.TransactionsActivity

@AndroidEntryPoint
class FragAccounts : Fragment() {
    private lateinit var binding: LayoutRvBinding
    private val viewModel: FragAccountsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutRvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observer()
        viewModel.getAccounts()
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlowAccounts.collectLatest {
                when (it) {
                    is ApiResponse.Loading -> DashboardActivity.pdDashboard.show()
                    is ApiResponse.Success -> {
                        DashboardActivity.pdDashboard.dismiss()
                        binding.layoutRv.apply {
                            layoutManager =
                                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
                            val mainAdapter = AccountsAdapter(listAccount = it.data)
                            adapter = mainAdapter
                        }
                    }
                    is ApiResponse.Error -> {
                        DashboardActivity.pdDashboard.dismiss()
                        MaterialAlertDialogBuilder(requireActivity())
                            .setMessage(it.exception.message.toString())
                            .setPositiveButton(getString(R.string.retry))
                            { _, _ ->
                                kotlin.run {
                                    viewModel.getAccounts()
                                }
                            }
                            .show()
                    }
                    else -> Log.e("fragAccount", "observer: else")
                }
            }
        }
    }

    private inner class AccountsAdapter(val listAccount: List<Account>) :
        RecyclerView.Adapter<AccountsAdapter.AccountsHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsHolder {
            val singleBinding =
                SingleAccountsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AccountsHolder(singleBinding)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: AccountsHolder, position: Int) {
            val listItem = listAccount[position]
            holder.singleBinding.singleAccountsTvName.text = listItem.name
            holder.singleBinding.singleAccountsTvBalance.text = "$ ${listItem.balance}"

            holder.itemView.setOnClickListener {
                val myIntent = Intent(requireActivity(), TransactionsActivity::class.java).apply {
                    putExtra("account", Gson().toJson(listItem))
                }
                startActivity(myIntent)
            }
        }

        override fun getItemCount(): Int = listAccount.size

        inner class AccountsHolder(val singleBinding: SingleAccountsBinding) :
            RecyclerView.ViewHolder(singleBinding.root)
    }
}
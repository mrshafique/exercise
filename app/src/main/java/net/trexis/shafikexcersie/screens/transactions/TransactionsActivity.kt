package net.trexis.shafikexcersie.screens.transactions

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import net.trexis.shafikexcersie.R
import net.trexis.shafikexcersie.commons.ApiResponse
import net.trexis.shafikexcersie.commons.CustomLoadingDialog
import net.trexis.shafikexcersie.databinding.ActivityTransactionsBinding
import net.trexis.shafikexcersie.databinding.SingleTransactionsBinding
import net.trexis.shafikexcersie.interfaces.IActivity
import net.trexis.shafikexcersie.model.Account
import net.trexis.shafikexcersie.model.Transactions
import net.trexis.shafikexcersie.screens.dashboard.DashboardActivity

@AndroidEntryPoint
class TransactionsActivity : AppCompatActivity(), IActivity {
    private lateinit var binding: ActivityTransactionsBinding
    private lateinit var pd: CustomLoadingDialog
    private lateinit var account: Account
    private val viewModel: TransactionsViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        val strAccount = intent.extras!!.getString("account").toString()
        loge("strAccount: $strAccount")
        account = Gson().fromJson(strAccount, Account::class.java)
        binding.transactionsTvAccountName.text = "${account.name} $${account.balance}"

        observer()
        viewModel.getTransactionsByAccountId(strAccountId = account.id.toString())

        binding.transactionsFabBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlowTransactions.collectLatest {
                when (it) {
                    is ApiResponse.Loading -> DashboardActivity.pdDashboard.show()
                    is ApiResponse.Success -> {
                        DashboardActivity.pdDashboard.dismiss()
                        displayList(listTransaction = it.data)
                    }
                    is ApiResponse.Error -> {
                        DashboardActivity.pdDashboard.dismiss()
                        MaterialAlertDialogBuilder(this@TransactionsActivity)
                            .setMessage(it.exception.message.toString())
                            .setPositiveButton(getString(R.string.retry))
                            { _, _ ->
                                kotlin.run {
                                    viewModel.getTransactionsByAccountId(strAccountId = account.id.toString())
                                }
                            }
                            .show()
                    }
                    else -> Log.e("fragTxn", "observer: else")
                }
            }
        }
    }

    private fun displayList(listTransaction: List<Transactions>) {
        binding.transactionsLayout.layoutRv.apply {
            layoutManager =
                LinearLayoutManager(this@TransactionsActivity, RecyclerView.VERTICAL, false)
            val mainAdapter = TransactionsAdapter(listTransaction = listTransaction)
            adapter = mainAdapter
        }
    }

    private inner class TransactionsAdapter(private val listTransaction: List<Transactions>) :
        RecyclerView.Adapter<TransactionsAdapter.TransactionsHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsHolder {
            val singleBinding =
                SingleTransactionsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return TransactionsHolder(singleBinding)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: TransactionsHolder, position: Int) {
            val listItem = listTransaction[position]
            holder.singleBinding.singleTransactionsTvTitle.text = listItem.title
            holder.singleBinding.singleTransactionsTvBalance.text = "$ ${listItem.balance}"
        }

        override fun getItemCount(): Int = listTransaction.size

        inner class TransactionsHolder(val singleBinding: SingleTransactionsBinding) :
            RecyclerView.ViewHolder(singleBinding.root)
    }

    override fun init() {
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pd = CustomLoadingDialog(context = this@TransactionsActivity)
    }

    override fun loge(msg: String) {
        Log.e("login", msg)
    }
}
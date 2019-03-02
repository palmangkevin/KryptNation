package com.example.kevinkombate.kryptnation.adapter


import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.fragment.TransactionFragment.OnTransactionFragmentInteractionListener
import com.example.kevinkombate.kryptnation.model.Transaction
import com.example.kevinkombate.kryptnation.util.toEther
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionRecyclerViewAdapter( private val address: String,
                                      private val context: Context,
                                      private val mValues: List<Transaction>,
                                      private val mListener: OnTransactionFragmentInteractionListener?)
    : RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val etherStringFormat: String by lazy { context.getString(R.string.ether_value) }

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Transaction
            mListener?.onTransactionClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        if (item.from == address) {
            holder.transactionIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_to))
            holder.address.text = if (item.to.isNotEmpty()) item.to else item.contractAddress
        } else {
            holder.transactionIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_from))
            holder.address.text = item.from
        }

        holder.ether.text = String.format(etherStringFormat, item.value.toEther())

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val transactionIcon: AppCompatImageView = mView.transactionIcon
        val address: TextView = mView.address
        val ether: TextView = mView.ether
    }
}

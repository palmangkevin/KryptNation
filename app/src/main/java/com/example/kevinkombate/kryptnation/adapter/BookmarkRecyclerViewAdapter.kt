package com.example.kevinkombate.kryptnation.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kevinkombate.kryptnation.R
import com.example.kevinkombate.kryptnation.fragment.BookmarkFragment.OnBookmarkFragmentInteractionListener
import com.example.kevinkombate.kryptnation.model.Wallet
import kotlinx.android.synthetic.main.bookmark_item.view.*

class BookmarkRecyclerViewAdapter(
        private val mValues: List<Wallet>,
        private val mListener: OnBookmarkFragmentInteractionListener?)
    : RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Wallet
            mListener?.onBookmarkClick(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bookmark_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.name.text = item.name
        holder.address.text = item.address

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val name: TextView = mView.wallet_name
        val address: TextView = mView.wallet_address
    }
}

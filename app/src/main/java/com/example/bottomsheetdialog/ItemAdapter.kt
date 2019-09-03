package com.example.bottomsheetdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_item_list_dialog_item.view.*

class ItemAdapter(private val mAccounts: List<Account>, private val listener: (Account) -> Unit) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mAccounts[position])
    }

    override fun getItemCount(): Int {
        return mAccounts.size
    }

    inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_item_list_dialog_item,parent,false)) {

        fun bind(account: Account) {
            itemView.name.text = account.name
            itemView.email.text = account.email
            itemView.item_layout.setOnClickListener { listener(account) }
        }
    }
}
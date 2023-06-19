package com.lloydheyrosa.contactlist.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lloydheyrosa.contactlist.R
import com.lloydheyrosa.contactlist.databinding.ItemContactDetailsBinding
import com.lloydheyrosa.domain.model.Contact

class ContactsAdapter(
    private val onItemClicked: (contact: Contact) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private val list = mutableListOf<Contact>()

    inner class ViewHolder(val binding: ItemContactDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemContactDetailsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_contact_details,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.item = item
        holder.binding.constraintLayoutContactItem.setOnClickListener {
            onItemClicked.invoke(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Contact>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}
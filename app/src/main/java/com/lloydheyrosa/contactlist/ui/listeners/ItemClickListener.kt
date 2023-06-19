package com.lloydheyrosa.contactlist.ui.listeners

import com.lloydheyrosa.contactlist.databinding.ItemContactDetailsBinding
import com.lloydheyrosa.domain.model.Contact

interface ItemClickListener {
    fun onItemClickListener(item: Contact, binding: ItemContactDetailsBinding, pos: Int)
}
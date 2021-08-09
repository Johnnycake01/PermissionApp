package com.example.customcontact.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customcontact.R
import com.example.customcontact.`interface`.OnContactClicked
import com.example.customcontact.dataClass.ContactItem

class ContactListAdapter(val listItem:List<ContactItem>,var itemListener:OnContactClicked):
    RecyclerView.Adapter<ContactListAdapter.ContactHolder>(){

    inner class ContactHolder(contactView: View):RecyclerView.ViewHolder(contactView),
        View.OnClickListener {
        val textInCardView:TextView = contactView.findViewById(R.id.TextInBox)
        val contactViewName:TextView = contactView.findViewById(R.id.contactName)
        init {
            contactView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
           var position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                itemListener.clickEvent(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        var view = LayoutInflater.from(parent.context).
        inflate(R.layout.contact_list_item_layout,parent,false)
        return  ContactHolder(view)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val itemPosition = listItem[position]
        holder.contactViewName.text = itemPosition.contactName
        holder.textInCardView.text = itemPosition.contactSymbol.toString()
    }

    override fun getItemCount(): Int {
      return listItem.size
    }
}
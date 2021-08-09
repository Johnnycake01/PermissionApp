package com.example.contactapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.dataclasses.ContactItem

class ContactListAdapter(val listItem:List<ContactItem>):
    RecyclerView.Adapter<ContactListAdapter.ContactHolder>(){

    inner class ContactHolder(contactView: View):RecyclerView.ViewHolder(contactView){
        val textInCardView:TextView = contactView.findViewById(R.id.TextInBox)
        val contactViewName:TextView = contactView.findViewById(R.id.contactName)
        val image:ImageView = contactView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        var view = LayoutInflater.from(parent.context).
        inflate(R.layout.contact_list_item_layout,parent,false)
        return  ContactHolder(view)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val itemPosition = listItem[position]
        holder.contactViewName.text = itemPosition.contactName
        if (itemPosition.image != null){
            holder.image.setImageBitmap(itemPosition.image)
            holder.textInCardView.text = ""
        }else{
            holder.textInCardView.text = itemPosition.contactSymbol
        }

    }

    override fun getItemCount(): Int {
      return listItem.size
    }
}
package com.musnadil.challengechapter4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musnadil.challengechapter4.Item
import com.musnadil.challengechapter4.databinding.ItemListBinding

class ItemAdapter (private val listItem : List<Item>): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    class ViewHolder(val binding : ItemListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            tvNamaBarang.text = listItem[position].item_name
            tvHargaBeli.text = listItem[position].purchase_price.toString()
            tvHargaJual.text = listItem[position].selling_price.toString()
        }

    }

    override fun getItemCount(): Int =listItem.size
}
package com.musnadil.challengechapter4.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musnadil.challengechapter4.Item
import com.musnadil.challengechapter4.MainActivity
import com.musnadil.challengechapter4.databinding.ItemListBinding
import com.musnadil.challengechapter4.fragment.AddListFragment
import com.musnadil.challengechapter4.fragment.UpdateFragment

class ItemAdapter (private val listItem : List<Item>): RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    class ViewHolder(val binding : ItemListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding){
            tvNamaBarang.text = listItem[position].item_name
            tvHargaBeli.text = ": ${listItem[position].purchase_price.toString()}"
            tvHargaJual.text = ": ${listItem[position].selling_price.toString()}"

        itemRv.setOnClickListener{
            val activity = it.context as MainActivity
            val dialogFragment = UpdateFragment(listItem[position])
            dialogFragment.show(activity.supportFragmentManager, null)
        }
        }
    }
    override fun getItemCount(): Int =listItem.size
}
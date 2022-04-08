package com.musnadil.challengechapter4.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.musnadil.challengechapter4.Item
import com.musnadil.challengechapter4.MainActivity
import com.musnadil.challengechapter4.StoreDatabase
import com.musnadil.challengechapter4.databinding.ItemListBinding
import com.musnadil.challengechapter4.fragment.MainFragment
import com.musnadil.challengechapter4.fragment.UpdateFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private val listItem = mutableListOf<Item>()

    class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            tvNamaBarang.text = listItem[position].item_name
            tvHargaBeli.text = ": ${listItem[position].purchase_price.toString()}"
            tvHargaJual.text = ": ${listItem[position].selling_price.toString()}"

            itemRv.setOnClickListener {
                val activity = it.context as MainActivity
                val dialogFragment = UpdateFragment(listItem[position])
                dialogFragment.show(activity.supportFragmentManager, null)
            }
            btnDelete.setOnClickListener {
                AlertDialog.Builder(it.context)
                    .setPositiveButton("Ya"){p0,p1 ->
                        val mDb = StoreDatabase.getInstance(holder.itemView.context)
                        GlobalScope.async {
                            val result = mDb?.itemDao()?.deleteItem(listItem[position])
                            runBlocking(Dispatchers.Main) {
                                if (result != 0 ){
                                    Toast.makeText(it.context, "${listItem[position].item_name} berhasil dihapus", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(it.context, "${listItem[position].item_name} gagal dihapus", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    .setNegativeButton("Batal"){p0,p1->
                        p0.dismiss()
                    }
                    .setTitle("Konfirmasi Hapus")
                    .setMessage("Apakah anda yakin ingin menghapus data ${listItem[position].item_name}")
                    .create()
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = listItem.size
    fun setData(itemList: List<Item>) {

        listItem.clear()
        listItem.addAll(itemList)
        notifyDataSetChanged()
    }
}
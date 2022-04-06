package com.musnadil.challengechapter4.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.musnadil.challengechapter4.Item
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.StoreDatabase
import com.musnadil.challengechapter4.databinding.FragmentUpdateBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class UpdateFragment() : DialogFragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    lateinit var itemSelected : Item
    constructor(itemSelected:Item):this(){
        this.itemSelected = itemSelected
    }

    var mDb:StoreDatabase?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = StoreDatabase.getInstance(requireContext())

        if(this::itemSelected.isInitialized){
            binding.tvItemName.text = "Update Harga ${itemSelected.item_name}"
            binding.etPurchasePrice.setText(itemSelected.purchase_price.toString())
        }

        binding.btnUpdateBarang.setOnClickListener {
            when {
                binding.etPurchasePrice.text.isNullOrEmpty() -> {
                    binding.wrapPurchasePrice.error = getString(R.string.warning_purchase_price)
                }
                binding.etPrecentage.text.isNullOrEmpty() -> {
                    binding.wrapPrecentage.error = getString(R.string.warning_precent)
                }
                else -> {
                    val purchase: Int = binding.etPurchasePrice.text.toString().toInt()
                    val precentage: Int = binding.etPrecentage.text.toString().toInt()
                    val selling: Int = purchase + (purchase * precentage / 100)

                    val objectItem = itemSelected
                    objectItem.purchase_price = purchase
                    objectItem.selling_price = selling

                    GlobalScope.async {
                        val result = mDb?.itemDao()?.updateItem(objectItem)
                        runBlocking {
                            if (result != 0) {
                                Toast.makeText(
                                    it.context,
                                    "Harga ${itemSelected.item_name} sukses diubah",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(it.context, "Harga gagal diubah", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            activity?.finish()
                        }
                    }
                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
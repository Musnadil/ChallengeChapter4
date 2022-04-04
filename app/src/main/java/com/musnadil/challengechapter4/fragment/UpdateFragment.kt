package com.musnadil.challengechapter4.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.databinding.FragmentUpdateBinding

class UpdateFragment() : DialogFragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    lateinit var itemName: String
    lateinit var purcasePrice: String
    constructor(itemName:String,price : String):this(){
        this.itemName = itemName
        this.purcasePrice = price
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
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

        if(this::purcasePrice.isInitialized){
            binding.tvItemName.text = "Update Harga ${itemName}"
            binding.etPurchasePrice.setText(purcasePrice)
        }
        binding.btnUpdateBarang.setOnClickListener {
            dialog?.dismiss()
        }

    }

}
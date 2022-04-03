package com.musnadil.challengechapter4.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.databinding.FragmentAddListBinding

class AddListFragment : DialogFragment() {
    lateinit var binding:FragmentAddListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        binding = FragmentAddListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTambahBarang.setOnClickListener {
            dialog?.dismiss()
        }
    }
}
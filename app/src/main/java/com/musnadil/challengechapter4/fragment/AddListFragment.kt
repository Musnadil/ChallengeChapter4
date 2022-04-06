package com.musnadil.challengechapter4.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.musnadil.challengechapter4.Item
import com.musnadil.challengechapter4.R
import com.musnadil.challengechapter4.StoreDatabase
import com.musnadil.challengechapter4.databinding.FragmentAddListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class AddListFragment : DialogFragment() {
    private var _binding: FragmentAddListBinding? = null
    private val binding get() = _binding!!
    var mDb: StoreDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
        dialog?.window?.attributes?.windowAnimations = R.style.MyDialogAnimation
        _binding = FragmentAddListBinding.inflate(inflater, container, false)
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
        binding.btnTambahBarang.setOnClickListener {

            when {
                binding.etItemName.text.isNullOrEmpty() -> {
                    binding.wrapItemName.error = getString(R.string.warning_item_name)
                }
                binding.etPurchasePrice.text.isNullOrEmpty() -> {
                    binding.wrapPurchasePrice.error = getString(R.string.warning_purchase_price)
                }
                binding.etPrecentage.text.isNullOrEmpty() -> {
                    binding.etPrecentage.error = getString(R.string.warning_precent)
                }
                else -> {
                    val purchase: Int = binding.etPurchasePrice.text.toString().toInt()
                    val precentage: Int = binding.etPrecentage.text.toString().toInt()
                    val selling: Int = purchase + (purchase * precentage / 100)
                    val objectItem = Item(
                        null, binding.etItemName.text.toString(), purchase, selling
                    )
                    GlobalScope.async {
                        val result = mDb?.itemDao()?.insertItem(objectItem)
                        runBlocking {
                            if (result != 0.toLong()) {
                                Toast.makeText(requireContext(),"${objectItem.item_name} berhasil ditambahkan ke daftar",Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(requireContext(),"Gagal menambahkan item ke daftar",Toast.LENGTH_SHORT).show()
                            }
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
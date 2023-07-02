package com.Ibnuumar.makananringan_ibnuumar.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.Ibnuumar.makananringan_ibnuumar.R
import com.Ibnuumar.makananringan_ibnuumar.application.SnacksApp
import com.Ibnuumar.makananringan_ibnuumar.databinding.FragmentSecondBinding
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val snacksViewModel : SnacksViewModel by viewModels {
        SnacksViewModelFactory((applicationContext as SnacksApp).repository)
    }
    private val args : SecondFragmentArgs by navArgs()
    private var snacks: Snacks? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snacks = args.snacks
        if (snacks !=null) {
            binding.deleteButton.visibility = View.VISIBLE
            binding.saveButton.text = "Ubah"
            binding.nameEditText.setText(snacks?.name)
            binding.descEditText.setText(snacks?.desc)
            binding.flavorEditText.setText(snacks?.flavor)
        }
        val name= binding.nameEditText.text
        val desc= binding.descEditText.text
        val flavor = binding.flavorEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (desc.isEmpty()) {
                Toast.makeText(context, "Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (flavor.isEmpty()) {
                Toast.makeText(context,  "Rasa tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else{
                if (snacks==null) {
                val snacks = Snacks(0, name.toString(), desc.toString(), flavor.toString())
                snacksViewModel.insert(snacks)
            }else{
                val snacks = Snacks(snacks?.id!!,name.toString(), desc.toString(),flavor.toString())
                snacksViewModel.update(snacks)
            }
                findNavController().popBackStack()
            }

        }

        binding.deleteButton.setOnClickListener {
            snacks?.let {  snacksViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
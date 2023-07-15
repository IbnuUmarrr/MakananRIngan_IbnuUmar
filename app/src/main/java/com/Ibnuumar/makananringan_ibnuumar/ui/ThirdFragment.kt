package com.Ibnuumar.makananringan_ibnuumar.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.Ibnuumar.makananringan_ibnuumar.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.backButton.setOnClickListener {
           findNavController().popBackStack()
        }
        binding.contactFAB.setOnClickListener {
            val phoneNumber = "089513769493"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        }
        binding.instagramFAB.setOnClickListener {
            val instagramUrl = "https://www.instagram.com/ibnu_umarr/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
            intent.setPackage("com.instagram.android") // Jika ingin membuka aplikasi Instagram langsung
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
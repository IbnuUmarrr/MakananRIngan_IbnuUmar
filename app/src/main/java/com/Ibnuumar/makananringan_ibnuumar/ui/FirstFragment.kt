package com.Ibnuumar.makananringan_ibnuumar.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.Ibnuumar.makananringan_ibnuumar.R
import com.Ibnuumar.makananringan_ibnuumar.application.SnacksApp
import com.Ibnuumar.makananringan_ibnuumar.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val snacksViewModel : SnacksViewModel by viewModels {
        SnacksViewModelFactory((applicationContext as SnacksApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SnacksListAdapter{snacks ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(snacks)
            findNavController().navigate(action)
        }
        binding.dataRecyclerView.adapter = adapter
        binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
        snacksViewModel.allSnacks.observe(viewLifecycleOwner){ snacks ->
            snacks.let {
                if (snacks.isEmpty()) {
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.illustrationImageView.visibility = View.VISIBLE
                }else{
                    binding.emptyTextView.visibility = View.GONE
                    binding.illustrationImageView.visibility = View.GONE
                }
                adapter.submitList(snacks)
            }
        }

        binding.addFAB.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
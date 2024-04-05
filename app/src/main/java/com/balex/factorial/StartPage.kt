package com.balex.factorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.balex.factorial.databinding.StartPageBinding

class StartPage : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private var _binding: StartPageBinding? = null
    private val binding: StartPageBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
        binding.buttonFtag.setOnClickListener {
            findNavController().navigate(
                StartPageDirections.actionStartPageToDemoFragment()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(requireActivity()) {
            binding.progressBarLoading.visibility = View.GONE
            binding.buttonCalculate.isEnabled = true

            when (it) {
                is Error -> {
                    Toast.makeText(
                        requireActivity(),
                        "You did not entered value",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Progress -> {
                    binding.progressBarLoading.visibility = View.VISIBLE
                    binding.buttonCalculate.isEnabled = false
                }
                is Result -> {
                    binding.textViewFactorial.text = it.factorial
                }
            }
        }
    }
}
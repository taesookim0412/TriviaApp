package com.example.triviaapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.triviaapp.R
import com.example.triviaapp.databinding.TriviaEntityFragmentBinding
import com.example.triviaapp.viewModels.TriviaEntityViewModel

class TriviaEntity : Fragment() {

    companion object {
        fun newInstance() = TriviaEntity()
    }
    val viewModel: TriviaEntityViewModel by viewModels();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TriviaEntityFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.trivia_entity_fragment, container, false )
        binding.viewmodel = viewModel;
        binding.setLifecycleOwner(this);
        return binding.root;
    }
}
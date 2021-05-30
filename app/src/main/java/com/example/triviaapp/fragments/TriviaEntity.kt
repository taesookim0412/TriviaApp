package com.example.triviaapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.triviaapp.R
import com.example.triviaapp.api.TriviaResultsResponse
import com.example.triviaapp.api.TriviaViewModel
import com.example.triviaapp.databinding.TriviaEntityFragmentBinding
import com.example.triviaapp.viewModels.TriviaEntityViewModel

class TriviaEntity : Fragment() {
//    companion object {
//        fun newInstance() = TriviaEntity()
//    }
    val triviaViewModel:TriviaEntityViewModel by activityViewModels()
    val triviaAllViewModel: TriviaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TriviaEntityFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.trivia_entity_fragment, container, false )
        binding.viewmodel = triviaViewModel;
        binding.host = this
        binding.setLifecycleOwner(this);
        return binding.root;
    }

    fun onClickAnswer(view: View){
        val view = view as RadioButton
        val selection = view.text.toString()
        val res = triviaViewModel.onClickAnswer(selection)
        if (res){
            val toast = Toast.makeText(context, "Correct answer! +1", Toast.LENGTH_SHORT)
            toast.show()
        }
        else{
            val toast = Toast.makeText(context, "Incorrect answer! -1", Toast.LENGTH_SHORT)
            toast.show()
        }
        //delete trivia entry at position and go back
        triviaAllViewModel.trivias.remove(triviaViewModel.triviaEntityResponse)
        this.activity?.supportFragmentManager?.popBackStack();
    }
}
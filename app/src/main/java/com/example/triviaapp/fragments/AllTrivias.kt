package com.example.triviaapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triviaapp.R
import com.example.triviaapp.adapters.TriviaAdapter
import com.example.triviaapp.api.TriviaViewModel
import com.example.triviaapp.databinding.FragmentAllTriviasBinding
import com.example.triviaapp.viewModels.CategoryEntityViewModel
import com.example.triviaapp.viewModels.TriviaEntityViewModel

class AllTrivias(val categoryEntityViewModel: CategoryEntityViewModel = CategoryEntityViewModel(0,"",0) ) : Fragment() {

    private lateinit var fragmentBinding: FragmentAllTriviasBinding;
    private val triviaViewModel by activityViewModels<TriviaViewModel>()
    val triviaEntityViewModel: TriviaEntityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentAllTriviasBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_trivias, container, false)
        // Inflate the layout for this fragment
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()
        fragmentBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = TriviaAdapter(this)
        fragmentBinding.triviaRecyclerview.apply{
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }
        if (triviaViewModel.trivias.size == 0 || categoryEntityViewModel.category != triviaViewModel.trivias[0].category){
            triviaViewModel.get50CategoryQuestions(categoryEntityViewModel.apiIdStr).observe(viewLifecycleOwner) { trivias ->
                trivias?.let{ viewAdapter.setTrivias(it) }
            }
        }
        else{
            viewAdapter.setTrivias(triviaViewModel.trivias)
        }
//        alarmViewModel.allAlarmsSorted?.observe(viewLifecycleOwner, Observer { alarms ->
//            alarms?.let{ viewAdapter.setAlarms(it) }
//            Log.d("alarms", alarms.toString())
//        })

    }


}

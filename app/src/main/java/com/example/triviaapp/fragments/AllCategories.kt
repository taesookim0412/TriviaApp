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
import com.example.triviaapp.adapters.CategoryAdapter
import com.example.triviaapp.api.TriviaViewModel
import com.example.triviaapp.databinding.FragmentAllCategoriesBinding
import com.example.triviaapp.viewModels.CategoryEntityViewModel

class AllCategories : Fragment() {

    private lateinit var fragmentBinding: FragmentAllCategoriesBinding;
    private val triviaViewModel by activityViewModels<TriviaViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentAllCategoriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_categories, container, false)
        // Inflate the layout for this fragment
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.show()
        fragmentBinding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = CategoryAdapter(this)
        fragmentBinding.categoryRecyclerview.apply{
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }
        // get db values every creation
        triviaViewModel.dbRepository.getCategoriesFromDb().observe(viewLifecycleOwner) { categories ->
            categories?.let{ viewAdapter.setCategories(it) }
        }
//        alarmViewModel.allAlarmsSorted?.observe(viewLifecycleOwner, Observer { alarms ->
//            alarms?.let{ viewAdapter.setAlarms(it) }
//            Log.d("alarms", alarms.toString())
//        })

    }


}

package com.example.triviaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.R
import com.example.triviaapp.api.TriviaResultsResponse
import com.example.triviaapp.databinding.RecyclerviewTriviaRowBinding
import com.example.triviaapp.fragments.AllTrivias
import com.example.triviaapp.fragments.TriviaEntity
import com.example.triviaapp.viewModels.TriviaEntityViewModel

class TriviaAdapter(val fragment:AllTrivias) : RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder>() {
    private var trivias = emptyList<TriviaResultsResponse>()

    class TriviaViewHolder(val binding: RecyclerviewTriviaRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriviaViewHolder {
        val binding:RecyclerviewTriviaRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_trivia_row,
            parent,
            false
        )
//        binding.root.setOnClickListener {
//            loadFragment(TriviaEntity())
//        }
        return TriviaViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return trivias.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: TriviaViewHolder, position: Int) {
        val trivia = trivias[position]
        holder.itemView.tag = trivia
        //collisions
//        fragment.triviaEntityViewModel.setTrivia(trivia)
//        holder.binding.viewModel = fragment.triviaEntityViewModel
        val triviaEntityViewModel = TriviaEntityViewModel()
        triviaEntityViewModel.setTrivia(trivia)
        holder.binding.viewModel = triviaEntityViewModel
        holder.binding.root.setOnClickListener {
            loadFragment(fragment, TriviaEntity(), trivia)
        }
    }

    internal fun setTrivias(trivias: List<TriviaResultsResponse>) {
        this.trivias = trivias
        notifyDataSetChanged()
    }

    private fun loadFragment(
        srcFragment: AllTrivias,
        destFragment: TriviaEntity,
        trivia: TriviaResultsResponse
    ){
        srcFragment.triviaEntityViewModel.setTrivia(trivia)
        val transaction = srcFragment.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.root_layout, destFragment)
        transaction?.addToBackStack("allTrivias")
        transaction?.commit()
    }
//    fun getId(context: Context, day: String): Int {
//        return context.resources.getIdentifier(day, "id", context.packageName)
//    }
}
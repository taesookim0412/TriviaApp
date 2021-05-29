package com.example.triviaapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.R
import com.example.triviaapp.data.Trivia
import com.example.triviaapp.viewModels.TriviaEntityViewModel

class TriviaAdapter() : RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder>() {
    private var trivias = emptyList<Trivia>()


    class TriviaViewHolder(val binding: com.example.triviaapp.databinding.RecyclerviewTriviaRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TriviaViewHolder {
        return TriviaViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_trivia_row,
                parent,
                false
            )
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
        holder.binding.viewModel = TriviaEntityViewModel(trivia)
    }

//    internal fun setAlarms(alarms: List<Alarm>) {
//        this.alarms = alarms
//        notifyDataSetChanged()
//    }

//    fun getId(context: Context, day: String): Int {
//        return context.resources.getIdentifier(day, "id", context.packageName)
//    }
}
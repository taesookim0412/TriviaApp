package com.example.triviaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.triviaapp.R
import com.example.triviaapp.databinding.RecyclerviewCategoryRowBinding
import com.example.triviaapp.fragments.AllCategories
import com.example.triviaapp.fragments.AllTrivias
import com.example.triviaapp.schemas.Trivia.TriviaPoints
import com.example.triviaapp.viewModels.CategoryEntityViewModel

class CategoryAdapter(val fragment:AllCategories) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var triviaPoints = emptyList<TriviaPoints>()

    class CategoryViewHolder(val binding: RecyclerviewCategoryRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding:RecyclerviewCategoryRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recyclerview_category_row,
            parent,
            false
        )
//        binding.root.setOnClickListener {
//            loadFragment(TriviaEntity())
//        }
        return CategoryViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return triviaPoints.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val triviaPointsCategory = triviaPoints[position]
        holder.itemView.tag = triviaPointsCategory
        val viewModel = CategoryEntityViewModel(triviaPointsCategory.apiId, triviaPointsCategory.category, triviaPointsCategory.points)
        holder.binding.viewModel = viewModel
        holder.binding.root.setOnClickListener {
            loadFragment(fragment, AllTrivias(viewModel))
        }
    }

    internal fun setCategories(triviaPoints: List<TriviaPoints>) {
        this.triviaPoints = triviaPoints
        notifyDataSetChanged()
    }

    private fun loadFragment(srcFragment:Fragment, destFragment: Fragment){
        val transaction = srcFragment.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.root_layout, destFragment)
        transaction?.addToBackStack("allCategories")
        transaction?.commit()
    }
//    fun getId(context: Context, day: String): Int {
//        return context.resources.getIdentifier(day, "id", context.packageName)
//    }
}
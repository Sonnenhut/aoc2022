package com.aoc2022.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.aoc2022.R
import kotlin.reflect.KCallable

class DayListAdapter(private val dataSet: Array<Day>) :
    RecyclerView.Adapter<DayListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.day_selection_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = viewHolder.view.context.getString(dataSet[position].textId)

        viewHolder.textView.setOnClickListener {
            val dayNum = dataSet[position].dayNum
            navigateToDay(viewHolder, dayNum)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    /**
     * Navigate to the day's fragment, if an action with id "day${dayNum}fragment" exists
     */
    private fun navigateToDay(viewHolder: ViewHolder, dayNum: Int) {

        var funName = DayListFragmentDirections::class.members.firstOrNull {
            it.name.lowercase().contains("day${dayNum}fragment")
        }
        if(funName == null) {
            funName = DayListFragmentDirections::actionDayListFragmentToDayWithValuesFragment
        }
        viewHolder.view.findNavController().navigate(funName.call(dayNum) as NavDirections)
    }
}

package com.aoc2022.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.LinearLayoutManager
import com.aoc2022.R
import com.aoc2022.databinding.FragmentDayListBinding

class DayListFragment : Fragment() {
    private var _binding: FragmentDayListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = DayListAdapter(Day.values())
    }
}

enum class Day(val textId: Int, val dayNum: Int) {
    DAY_1(R.string.day1, 1),
    DAY_2(R.string.day2, 2),
    DAY_3(R.string.day3, 3),
    DAY_4(R.string.day4, 4),
    DAY_5(R.string.day5, 5),
    DAY_6(R.string.day6, 6),
    DAY_7(R.string.day7, 7),
    DAY_8(R.string.day8, 8),
    DAY_9(R.string.day9, 9),
    DAY_10(R.string.day10, 10),
    DAY_11(R.string.day11, 11),
    DAY_12(R.string.day12, 12),
    DAY_13(R.string.day13, 13),
    DAY_14(R.string.day14, 14),
    DAY_15(R.string.day15, 15),
    DAY_16(R.string.day16, 16),
    DAY_17(R.string.day17, 17),
    DAY_18(R.string.day18, 18),
    DAY_19(R.string.day19, 19),
    DAY_20(R.string.day20, 20),
    DAY_21(R.string.day21, 21),
    DAY_22(R.string.day22, 22),
    DAY_23(R.string.day23, 23),
    DAY_24(R.string.day24, 24),
    DAY_25(R.string.day25, 25);

}
package com.aoc2022.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aoc2022.databinding.FragmentDayGenericResultBinding

class DayWithValueFragment : Fragment() {
    private lateinit var dayClass : Class<out GenericDay>
    private var dayNum : Int = 0
    private var _binding: FragmentDayGenericResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayGenericResultBinding.inflate(inflater, container, false)

        dayNum = arguments?.getInt("day")?: 0
        val currPackage = (this::class.java.`package`?.name ?: "")
        dayClass = try {
            Class.forName("$currPackage.Day$dayNum") as Class<out GenericDay>
        } catch (e : ClassNotFoundException) {
            DayNotImplemented::class.java
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titleIdentifier = requireContext().resources.getIdentifier(dayClass.simpleName.lowercase(), "string", requireContext().packageName) // e.g. R.string.day1
        val inputIdentifier = requireContext().resources.getIdentifier(dayClass.simpleName.lowercase(), "raw", requireContext().packageName) // e.g. R.raw.day1

        //TODO change title...
        requireActivity().title = "WHOO"

        val lines : List<String> = requireContext().resources.openRawResource(inputIdentifier)
            .bufferedReader()
            .readLines()

        val instance = dayClass.newInstance()
        val solve1Str = "${instance.solve1(lines)}"
        binding.solve1.text = solve1Str

        val solve2Str = "${instance.solve2(lines)}"
        binding.solve2.text = solve2Str
    }
}


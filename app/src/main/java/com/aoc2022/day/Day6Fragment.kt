package com.aoc2022.day

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import com.aoc2022.databinding.FragmentDay6Binding
import kotlinx.coroutines.delay


class Day6Fragment : Fragment() {
    private var dayClass : Class<Day6> = Day6::class.java
    private var dayNum : Int = 0
    private var _binding: FragmentDay6Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDay6Binding.inflate(inflater, container, false)

        dayNum = arguments?.getInt("day")?: 0

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val inputIdentifier = requireContext().resources.getIdentifier(dayClass.simpleName.lowercase(), "raw", requireContext().packageName) // e.g. R.raw.day1

        val lines : List<String> = requireContext().resources.openRawResource(inputIdentifier)
            .bufferedReader()
            .readLines()
        val inputLine = lines[0]

        val instance = dayClass.newInstance()
        val solve1Idx = instance.solve1(lines) // cbls
        val pt1markerLen = 4
        val pt1MarkerStartIdx = solve1Idx - pt1markerLen

        binding.solve1.text = coloredMarkerPacket(inputLine, pt1MarkerStartIdx, pt1markerLen)
        scrollToMarker(binding.solve1wrapper, binding.solve1, inputLine, pt1MarkerStartIdx)

        val solve2Idx = instance.solve2(lines) // qdnfscgvhztjwl
        val pt2markerLen = 14
        val pt2MarkerStartIdx = solve2Idx - pt2markerLen

        binding.solve2.text = coloredMarkerPacket(inputLine, pt2MarkerStartIdx, pt2markerLen)
        scrollToMarker(binding.solve2wrapper, binding.solve2, inputLine, pt2MarkerStartIdx)
    }

    private fun scrollToMarker(scrollView : HorizontalScrollView, textView : TextView, lineText: String,  markerIdx : Int) {
        val offsetIdxOnScreen = 5.0
        scrollView.post {
            val pointPerIdx : Double = textView.right.toDouble() / lineText.length.toDouble()
            val targetPoint : Double = pointPerIdx * (markerIdx - offsetIdxOnScreen)

            // relatively smooth scrolling to the marker
            SpringAnimation(scrollView, SpringAnimation.SCROLL_X, targetPoint.toFloat()).apply {
                spring.dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
                spring.stiffness = SpringForce.STIFFNESS_VERY_LOW
                start()
            }
            /*
            scrollView.smoothScrollTo(
                targetPoint.toInt(),
                textView.top
            )*/
        }
    }

    private fun coloredMarkerPacket(inputLine : String, markerStartIdx : Int, markerLen : Int) : Spanned {
        val pre = inputLine.substring(0, markerStartIdx)
        val marker = inputLine.substring(markerStartIdx, markerStartIdx + markerLen)
        val post = inputLine.substring(markerStartIdx + markerLen)

        return Html.fromHtml("${pre}<font color=#cc0029>${marker}</font>${post}", 0)
    }
}


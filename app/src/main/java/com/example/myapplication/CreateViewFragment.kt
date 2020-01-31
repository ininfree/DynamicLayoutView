package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class CreateViewFragment : Fragment() {

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: Int, param2: Int): CreateViewFragment {

            val fragment = CreateViewFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, param1)
            args.putInt(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: CreateViewViewModel by viewModel()
    private var row = 1
    private var column = 1
    private var heightIndex = 0
    private var totalCount = 0
    private lateinit var timer: Timer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("CreateViewFragment","onCreateView")
        val view = inflater.inflate(R.layout.create_view_fragment, container, false)
        initValue()
        createLayoutView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //setHeight(4 % totalCount)
        startTimer()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        timer?.let {
            it.cancel()
        }
        super.onDestroyView()
    }

    private fun initValue() {
        arguments?.let {
            row = it.getInt(ARG_PARAM1)
            column = it.getInt(ARG_PARAM2)
            totalCount = row * column
        }
    }

    private fun createLayoutView(view: View) {
        for (i in 1..column) {
            (view as ViewGroup).addView(createColumnView(i - 1))
        }
    }

    private fun createColumnView(col: Int): LinearLayout {
        val linearLayout = LinearLayout(context)
        val linearParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearParams.weight = 1f
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = linearParams

        for (i in 1..row) {
            val colors = resources.obtainTypedArray(R.array.color_array)
            val item = TextView(context)
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
            layoutParams.weight = 1f
            if (i == 1) layoutParams.topMargin = 10
            layoutParams.marginStart = 10
            layoutParams.marginEnd = 10
            item.layoutParams = layoutParams
            item.gravity = Gravity.CENTER
            item.setBackgroundColor(colors.getColor((i - 1) % 12, 0))
            item.setOnClickListener {
                clearHeight()
                setHeight(col * row + (i - 1))
            }
            linearLayout.addView(item)
        }

        val button = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        layoutParams.weight = 0.5f
        layoutParams.marginEnd = 10
        layoutParams.bottomMargin = 10
        layoutParams.marginStart = 10
        button.layoutParams = layoutParams
        button.gravity = Gravity.CENTER
        button.text = getString(android.R.string.ok)
        button.setTextColor(Color.WHITE)
        button.setBackgroundColor(Color.BLACK)
        button.setOnClickListener({ clearHeight() })
        linearLayout.addView(button)

        return linearLayout
    }

    private fun setHeight(index: Int) {
        heightIndex = index
        var indexColumn = index / row
        val indexRow = index % row
        val heightColumnView = (view as ViewGroup).getChildAt(indexColumn) as ViewGroup
        heightColumnView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorAccent))
        val heightItem = heightColumnView.getChildAt(indexRow) as TextView
        heightItem.text = "Random"
        heightItem.ellipsize = TextUtils.TruncateAt.END
        heightItem.maxLines = 1
    }

    private fun clearHeight() {
        var indexColumn = heightIndex / row
        val indexRow = heightIndex % row
        val heightColumnView = (view as ViewGroup).getChildAt(indexColumn)
        heightColumnView.setBackgroundColor(0)
        for (i in 0..(row - 1)) {
            val heightItem = (heightColumnView as ViewGroup).getChildAt(i) as TextView
            heightItem.text = ""
        }
    }


    private fun startTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread {
                        clearHeight()
                        setHeight(RandomNumber())
                    }
                }
            },
            0, 10000
        )
    }

    private fun RandomNumber(): Int {
        return (0..(totalCount - 1)).random()
    }
}

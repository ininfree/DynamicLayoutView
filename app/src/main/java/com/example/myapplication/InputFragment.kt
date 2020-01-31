package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.input_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class InputFragment : Fragment() {

    companion object {
        fun newInstance() = InputFragment()
    }

    private val viewModel: InputViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.input_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        confirm_btn.setOnClickListener { checkInputNotEmptyOrZero() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkInputNotEmptyOrZero() {
        when {
            editText.text.isNullOrEmpty() -> Snackbar.make(
                confirm_btn,
                getString(R.string.row).plus(" ") + getString(R.string.null_or_empty),
                Snackbar.LENGTH_LONG
            ).show()
            editText2.text.isNullOrEmpty() -> Snackbar.make(
                confirm_btn,
                getString(R.string.column).plus(" ") + getString(R.string.null_or_empty),
                Snackbar.LENGTH_LONG
            ).show()
            editText.text.toString().toInt() == 0 -> Snackbar.make(
                confirm_btn,
                getString(R.string.row).plus(" ") + getString(R.string.zero_attention),
                Snackbar.LENGTH_LONG
            ).show()
            editText2.text.toString().toInt() == 0 -> Snackbar.make(
                confirm_btn,
                getString(R.string.column).plus(" ") + getString(R.string.zero_attention),
                Snackbar.LENGTH_LONG
            ).show()
            else -> showCreateViewFragment(
                editText.text.toString().toInt(),
                editText2.text.toString().toInt()
            )
        }
    }

    private fun showCreateViewFragment(row: Int, col: Int) {
        val activity = activity as MainActivity
        activity.replaceFragment(CreateViewFragment.newInstance(row, col), R.id.container)
    }

}

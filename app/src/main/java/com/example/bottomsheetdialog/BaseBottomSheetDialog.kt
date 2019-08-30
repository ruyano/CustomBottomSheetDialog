package com.example.bottomsheetdialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.base_bottom_sheet_dialog_layout.view.*

abstract class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    abstract fun getLayoutResource(): Int

    abstract fun getPeekHeight(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.base_bottom_sheet_dialog_layout, container, false)
        view.view_stub.layoutResource = getLayoutResource()
        view.view_stub.inflate()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPeekHeight(view)
    }

    private fun setupPeekHeight(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            dialog?.let { dialog ->
                val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                val behavior = BottomSheetBehavior.from(bottomSheet)
                var peekHeight = getPeekHeight()
                context?.let { context ->
                    peekHeight += getHeaderHeight(context)
                }
                behavior.peekHeight = peekHeight
            }
        }
    }

    private fun getHeaderHeight(context: Context) : Int = (40 * context.resources.displayMetrics.density).toInt()

}
package com.example.bottomsheetdialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_item_list_dialog.*


const val ARG_ITEM_COUNT = "item_count"

class BottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_list_dialog, container, false)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        val itemCount = arguments?.getInt(ARG_ITEM_COUNT)
        itemCount.let {
            list.adapter = ItemAdapter(itemCount!!)
        }

        view.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog
            val bottomSheet = dialog!!.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)


            itemCount.let {
                if (itemCount!! > 3) {
                    val singleItemHeight = list.getChildAt(0).height
                    val singleItemHalfHeight = singleItemHeight/2
                    val height = singleItemHalfHeight + (2*singleItemHeight)
                    val headerHeight = context?.let { it1 -> convertDpToPx(it1, 40) }
                    behavior.setPeekHeight(height + headerHeight!!)
                } else {
                    val headerHeight = context?.let { it1 -> convertDpToPx(it1, 40) }
                    val toalHeight = view.height + headerHeight!!
                    behavior.setPeekHeight(toalHeight)
                }
            }
        }
    }

    companion object {
        fun newInstance(itemCount: Int): BottomSheetDialog =
            BottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }

    fun convertDpToPx(context: Context, dp: Int): Int {
        return (dp * context.getResources().getDisplayMetrics().density).toInt()
    }
}

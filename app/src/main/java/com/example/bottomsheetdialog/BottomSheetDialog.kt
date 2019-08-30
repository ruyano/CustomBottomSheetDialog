package com.example.bottomsheetdialog

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_item_list_dialog.*


const val ARG_ITEM_COUNT = "item_count"
var peekHeight = 300

class BottomSheetDialog : BaseBottomSheetDialog() {

    override fun getPeekHeight(): Int = peekHeight

    override fun getLayoutResource(): Int = R.layout.fragment_item_list_dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(context)
        val itemCount = arguments?.getInt(ARG_ITEM_COUNT)
        itemCount.let {
            list.adapter = ItemAdapter(itemCount!!)
        }

        view.viewTreeObserver.addOnGlobalLayoutListener {
            itemCount.let {
                if (itemCount!! > 3) {
                    val singleItemHeight = list.getChildAt(0).height
                    val singleItemHalfHeight = singleItemHeight/2
                    val height = singleItemHalfHeight + (2*singleItemHeight)
                    peekHeight = height
                } else {
                    peekHeight = view.height
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

}

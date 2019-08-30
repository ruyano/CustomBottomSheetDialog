package com.example.bottomsheetdialog

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_bottom_sheet_recycler_view.*

private const val ARG_ITEM_COUNT = "item_count"
private var peekHeight = 300
private var itemCount = 0

class BottomSheetDialogRecyclerView : BaseBottomSheetDialog() {

    override fun getPeekHeight(): Int = peekHeight

    override fun getLayoutResource(): Int = R.layout.fragment_bottom_sheet_recycler_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemCount = arguments?.getInt(ARG_ITEM_COUNT) ?: 0
        setupRecyclerViewAdapter()
        setupBottomSheetPeekHeight(view)
    }

    private fun setupRecyclerViewAdapter() {
        list.adapter = ItemAdapter(itemCount)
    }

    private fun setupBottomSheetPeekHeight(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            peekHeight = if (itemCount > 3) {
                val singleItemHeight = list.getChildAt(0).height
                val singleItemHalfHeight = singleItemHeight / 2
                val height = singleItemHalfHeight + (2 * singleItemHeight)
                height
            } else {
                view.height
            }
        }
    }

    companion object {
        fun newInstance(itemCount: Int): BottomSheetDialogRecyclerView =
            BottomSheetDialogRecyclerView().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }
    }

}

package com.example.bottomsheetdialog

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_bottom_sheet_recycler_view.*

private const val ACCOUNTS_LIST = "accounts_list"

class BottomSheetDialogRecyclerView : BaseBottomSheetDialog() {

    private var peekHeight = 300
    private lateinit var accountsList: ArrayList<Account>
    private var listener: OnAccountSelectedListener? = null

    override fun getPeekHeight(): Int = peekHeight

    override fun getLayoutResource(): Int = R.layout.fragment_bottom_sheet_recycler_view

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAccountSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnAccountSelectedListener")
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountsList = arguments?.getParcelableArrayList<Account>(ACCOUNTS_LIST) as ArrayList<Account>
        setupRecyclerViewAdapter()
        setupBottomSheetPeekHeight(view)
    }

    private fun setupRecyclerViewAdapter() {
        list.adapter = ItemAdapter(accountsList) { account -> listener?.onAccountSelected(account)}
        if (accountsList.size > 3) {
            list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    title_header.elevation = getTitleHeaderElevation(recyclerView, newState)
                }
            })
        }
    }

    private fun getTitleHeaderElevation(recyclerView: RecyclerView, newState: Int): Float =
        if (haveScrolledOrIsScrolling(recyclerView, newState)) {
            var elevation = 0f
            context?.let { context ->
                elevation = getTitleHeaderElevation(context)
            }
            elevation
        } else {
            0f
        }

    private fun haveScrolledOrIsScrolling(recyclerView: RecyclerView, newState: Int) : Boolean =
        recyclerView.canScrollVertically(-1) || newState == 1

    private fun getTitleHeaderElevation(context: Context) : Float =
        10 * context.resources.displayMetrics.density

    private fun setupBottomSheetPeekHeight(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener {
            peekHeight = if (accountsList.size > 3) {
                calculateRecyclerViewHeight()
            } else {
                view.height
            }
        }
    }

    private fun calculateRecyclerViewHeight(): Int {
        val singleItemHeight = list.getChildAt(0).height
        val singleItemHalfHeight = singleItemHeight / 2
        val height = singleItemHalfHeight + (2 * singleItemHeight)
        var titleHeaderHeight = 0
        context?.let {
            titleHeaderHeight = getTitleHeaderHeight(it)
        }
        return height + titleHeaderHeight
    }

    private fun getTitleHeaderHeight(context: Context) : Int = (48 * context.resources.displayMetrics.density).toInt()

    companion object {
        fun newInstance(accountsList: ArrayList<Account>): BottomSheetDialogRecyclerView =
            BottomSheetDialogRecyclerView().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ACCOUNTS_LIST, accountsList)
                }
            }
    }

    interface OnAccountSelectedListener {
        fun onAccountSelected(account: Account)
    }

}

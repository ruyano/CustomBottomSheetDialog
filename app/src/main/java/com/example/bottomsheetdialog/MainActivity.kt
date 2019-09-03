package com.example.bottomsheetdialog

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomSheetDialogRecyclerView.OnAccountSelectedListener {

    private lateinit var dialog: BottomSheetDialogRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show_btn.setOnClickListener {
            val texto = edittex.text.toString()
            var itemCount = 100
            if (!texto.isNullOrEmpty()) {
                itemCount = texto.toInt()
            }
            dialog = BottomSheetDialogRecyclerView.newInstance(generateList(itemCount))
            dialog.show(supportFragmentManager, "bottomSheet")
        }
    }


    private fun generateList(quantity: Int) : ArrayList<Account> {
        val list = arrayListOf<Account>()
        for (i in 1..quantity) {
            val account = Account(i, "$i adsf adf", "adsf****@gmail.com")
            list.add(account)
        }
        return list
    }

    override fun onAccountSelected(account: Account) {
        dialog.dismiss()
        Toast.makeText(this, account.toString(), Toast.LENGTH_SHORT).show()
    }

}

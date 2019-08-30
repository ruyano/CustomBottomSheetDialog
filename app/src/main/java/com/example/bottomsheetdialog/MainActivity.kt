package com.example.bottomsheetdialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show_btn.setOnClickListener {
            val texto = edittex.text.toString()
            var itemCount = 100
            if (!texto.isNullOrEmpty()) {
                itemCount = texto.toInt()
            }
            val dialog = BottomSheetDialogRecyclerView.newInstance(itemCount)
            dialog.show(supportFragmentManager, "bottomSheet")
        }
    }
}

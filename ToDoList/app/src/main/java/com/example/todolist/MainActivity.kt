package com.example.todolist

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var item : EditText
    private lateinit var add : Button
    private lateinit var listView: ListView

    var itemList = ArrayList<String>()

    var fileHandler = FileHandler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.listView)

        itemList = fileHandler.readData(this)

        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)

        listView.adapter = arrayAdapter

        add.setOnClickListener {
            var itmName : String = item.text.toString()
            itemList.add(itmName)
            item.setText("")
            fileHandler.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }
        listView.setOnItemClickListener { adapterView, view, position, l ->
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Delete")
            alertDialog.setMessage("Do you want to delete this item from the list ?")
            alertDialog.setCancelable(false)
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alertDialog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHandler.writeData(itemList,applicationContext)
            })
            alertDialog.create().show()
        }
    }
}
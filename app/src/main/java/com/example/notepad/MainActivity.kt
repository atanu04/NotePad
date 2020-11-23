package com.example.notepad



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AlertDialog

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepad.editNotesActivity.EditNoteActivity
import com.example.notepad.model.Note
import com.example.notepad.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton



class MainActivity : AppCompatActivity(), NoteClicked {


    private lateinit var mViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = RecyclerAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_Id)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)


        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mViewModel.readAllNotes.observe(this, {
            adapter.setData(it)
        })





        val fab = findViewById<FloatingActionButton>(R.id.newNoteAddId)
        fab.setOnClickListener {
            //new Intent
            val intent = Intent(this, EditNoteActivity::class.java)
            intent.putExtra("title","")
            intent.putExtra("description","")
            intent.putExtra("operation","Add")
            intent.putExtra("id",0)
            //intent.putExtra("date",currentDate())
            startActivity(intent)

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_actiivity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAllId ->{
                deleteAllNotesFromRV()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteAllNotesFromRV(){
        mViewModel.deleteAllNotes()
    }

    override fun onItemClicked(note: Note) {
        val intent = Intent(this,EditNoteActivity::class.java)

        //intent.putExtra("NOTE_OBJECT",note)
        intent.putExtra("title",note.title)
        intent.putExtra("description",note.desc)
        intent.putExtra("operation","Update")
        intent.putExtra("id",note.id)
        intent.putExtra("date",note.date)
        startActivity(intent)
    }

    override fun onItemLongClicked(note: Note): Boolean {
        deleteAlertDialog(note)
        return true
    }

    private fun deleteAlertDialog(note: Note){
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("DELETE")
        alertDialogBuilder.setMessage("Do you want to delete this note ?")
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_delete_24)
        alertDialogBuilder.setPositiveButton("Yes"
        ) { _, _ ->
            //Toast.makeText(this, "Positive Work", Toast.LENGTH_SHORT).show()
            //delete
            mViewModel.deleteNote(note)
        }
        alertDialogBuilder.setNegativeButton("No"
        ){ _,_ ->

        }
        alertDialogBuilder.setNeutralButton("Cancel"
        ){ _,_ ->

        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
package com.example.notepad.editNotesActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem

import androidx.lifecycle.ViewModelProvider

import com.example.notepad.R
import com.example.notepad.model.Note
import com.example.notepad.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_edit_note.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditNoteActivity : AppCompatActivity() {

    private lateinit var mViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_edit_note)


        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        val noteTitle = intent.extras?.getString("title","error")
        val noteDesc = intent.extras?.getString("description","error")
        val noteOp = intent.extras?.getString("operation","error")
        val noteId = intent.extras?.getInt("id",0)


        edit_title_id.text =Editable.Factory.getInstance().newEditable(noteTitle)
        edit_desc_id.text =Editable.Factory.getInstance().newEditable(noteDesc)





        val addButton = findViewById<FloatingActionButton>(R.id.add_button_Id)
        addButton.setOnClickListener {
            val title = edit_title_id.text.toString()
            val desc = edit_desc_id.text.toString()
            if(noteOp == "Add") {
                val note1 = Note(0,title,desc,currentDate())
                mViewModel.addNote(note1)

            }
            else{
                val note = noteId?.let { it1 -> Note(it1,title,desc,currentDate()) }
                if (note != null) {
                    mViewModel.updateNote(note)
                }
            }
            finish()
        }
    }
    private fun currentDate():String{
        val date = LocalDateTime.now()
        val formatter  = DateTimeFormatter.ofPattern("dd/MM/yy")
        return date.format(formatter).toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.editactivity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.shareId->{
                //share
                val ti = edit_title_id.text.toString()
                val des = edit_desc_id.text.toString()
                val s = ti+"\n"+des
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type ="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT,s)
                startActivity(Intent.createChooser(shareIntent,"Share via"))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
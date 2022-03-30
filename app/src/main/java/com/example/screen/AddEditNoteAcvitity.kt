package com.example.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast.makeText as toastMakeText

class AddEditNoteAcvitity : AppCompatActivity() {
    lateinit var noteTitleEdt :EditText
    lateinit var noteDescriptionEdt :EditText
    lateinit var addupdateBtn : Button
    lateinit var viewModel: NoteViewModel
    var noteID = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note_acvitity)

        noteTitleEdt = findViewById(R.id.idEditNoteTitle)
        noteDescriptionEdt = findViewById(R.id.idEditNoteDescription)
        addupdateBtn = findViewById(R.id.idbtnAddUpdate)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")){
            val noteTitle = intent.getStringArrayExtra("noteTitle")
            val noteDesc = intent.getStringArrayExtra("noteDescription")
            noteID = intent.getIntExtra("noteID",-1)
            addupdateBtn.setText("Update Note")
         //   noteTitleEdt.setText(noteTitle)
           // noteDescriptionEdt.setText(noteDesc)

        }else{
            addupdateBtn.setText("Save Note")
        }
        addupdateBtn.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteDescriptionEdt.text.toString()
            if (noteType.equals("Edit")){
                if (noteTitle.isNotEmpty()&& noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM ,yyyy - HH:mm")
                    val  currentDate:String = sdf.format(Date())
                    val updateNote = Note(noteTitle,noteDescription,currentDate)
                    updateNote.id = noteID
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated..",Toast.LENGTH_LONG).show()
                }
            }else{
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM ,yyyy - HH:mm")
                    val  currentDate:String = sdf.format(Date())
                    val addNote = viewModel.addNote(Note(noteTitle, noteDescription, currentDate))
                    Toast.makeText(this,"Note Added..",Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }
    }
}


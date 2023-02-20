package com.example.notes

//import android.support.v7.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val newNoteActivityRequestCode = 1
    private val NoteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NoteListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        NoteViewModel.allWords.observe(this) { notes ->
            // Update the cached copy of the words in the adapter.
            notes.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

       var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
         if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.getStringExtra(NewNoteActivity.EXTRA_REPLY)?.let { reply->
                val word = Note(reply)
                NoteViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
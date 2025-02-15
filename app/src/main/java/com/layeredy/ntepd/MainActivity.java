package com.layeredy.ntepd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.layeredy.ntepd.databinding.ActivityMainBinding;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // set the default note title
        TextView noteTitle = findViewById(R.id.note_title);
        noteTitle.setText(Language.UNTITLED_NOTE);

        // set the list notes button title
        TextView noteListButtonView = findViewById(R.id.list_notes_button);
        noteListButtonView.setText(Language.LIST_NOTES_BUTTON_TITLE);

        // set the view name
        getSupportActionBar().setTitle(Language.APP_TITLE);

        // add the save note button listener & set button title
        Button saveNoteButton = findViewById(R.id.save_note_button);
        saveNoteButton.setText(Language.SAVE_NOTE_BUTTON_TITLE);


        // save the note when the save button is clicked
        saveNoteButton.setOnClickListener(view -> {
            String noteTitleToSave = noteTitle.getText().toString();

            if(noteTitleToSave.equals("Untitled Note")) {
                noteTitleToSave = "Note " + UUID.randomUUID().toString().replace("-", "").substring(0, 5);
            }

            EditText noteContent = findViewById(R.id.note);
            String noteToSave = noteContent.getText().toString();

            NoteUtils.saveNote(noteTitleToSave, noteToSave, this);

        });

        String savedNote = NoteUtils.readNote("demo", this);
        System.out.println("saved note: " + savedNote);



        NoteUtils.readAllNotes(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_note_button) {
            //TODO: create a new note view & summon it here
            System.out.println("meow");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

// hi cackle
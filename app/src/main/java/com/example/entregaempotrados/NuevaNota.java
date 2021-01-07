package com.example.entregaempotrados;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevaNota extends AppCompatActivity {

    NotesDbHelper dbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_nota);
        Button crear = findViewById(R.id.button);
        EditText titulo = findViewById((R.id.editText));
        EditText contenido = findViewById((R.id.editText2));
        crear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(titulo.getText().toString().matches("")){
                    mostrarVentanaError();
                }else {
                    dbHelper = new NotesDbHelper(getApplicationContext(), "notas2.db");
                    db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(NotesContract.NoteEntry.COLUMN_NAME_KEY, titulo.getText().toString());
                    values.put(NotesContract.NoteEntry.COLUMN_NAME_VAL, contenido.getText().toString());
                    long id = db.insert(NotesContract.NoteEntry.TABLE_NAME, null, values);
                    Intent i = new Intent(getApplicationContext(), Notas.class);
                    startActivity(i);
                }

            }
        });
        Button salir = findViewById(R.id.button2);
        salir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                    Intent i = new Intent(getApplicationContext(), Notas.class);
                    startActivity(i);
            }
        });
    }

    private void mostrarVentanaError(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NuevaNota.this);
        mBuilder.setTitle(R.string.error);
        mBuilder.setMessage(R.string.conError);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog = mBuilder.create();

        mDialog.show();
    }
}
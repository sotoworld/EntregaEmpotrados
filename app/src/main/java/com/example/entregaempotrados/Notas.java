package com.example.entregaempotrados;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Notas extends AppCompatActivity {
    NotesDbHelper dbHelper;
    SQLiteDatabase db;
    List<String> listId = new ArrayList<String>();
    List<String> listTitulos = new ArrayList<String>();
    List<String> listContenido = new ArrayList<String>();
    String[] titulo;
    String[] contenido;
    private ListView lista;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//ir atras

        lista = (ListView) findViewById(R.id.ListView_ListaNotas);
        lista.setLongClickable(true);
        dbHelper = new NotesDbHelper(getApplicationContext(), "notas2.db");
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query(NotesContract.NoteEntry.TABLE_NAME,null,null,null,null,null,null);
        int i = 0;
        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry._ID));
                String clave = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NAME_KEY));
                String valor = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NAME_VAL));
                listId.add(id);
                listTitulos.add(clave);
                listContenido.add(valor);
            }
        } finally {
            cursor.close();
        }
        FloatingActionButton nueva = findViewById(R.id.floatingActionButton2);
        nueva.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(getApplicationContext(),NuevaNota.class);
                startActivity(i);

            }
        });
        titulo = new String[listTitulos.size()];
        contenido = new String[listContenido.size()];
        titulo = listTitulos.toArray(titulo);
        contenido = listContenido.toArray(contenido);
        adapter = new ListViewAdapter(getApplicationContext(),null,titulo,contenido);
        lista.setAdapter(adapter);

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Log.v("long clicked","pos: " + pos);
                AlertDialog dialog = new AlertDialog
                        .Builder(Notas.this)
                        .setPositiveButton(R.string.sieliminar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String where = NotesContract.NoteEntry._ID + " = ?";
                                String[] whereArgs = { listId.get(pos) };
                                long cnt = db.delete(NotesContract.NoteEntry.TABLE_NAME, where, whereArgs);
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.borrado), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),Notas.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle(R.string.confirm)
                        .setMessage(R.string.eliminar)
                        .create();
                dialog.show();

                return true;
            }
        });

    }


    /******************* LISTVIEW ADAPTER **************************/

    public class ListViewAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        int[] imagenes;
        String[] titulos;
        String[] contenido;
        LayoutInflater inflater;

        public ListViewAdapter(Context context, int[] imagenes, String[] titulos, String[] contenido ) {
            this.context = context;
           // this.imagenes = imagenes;
            this.titulos = titulos;
            this.contenido = contenido;
        }

        @Override
        public int getCount() {
            return titulos.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            // Declare Variables
            //ImageView imgImg;
            TextView txtTitle;
            TextView txtContenido;

            //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.menu_notas, parent, false);

            // Locate the TextViews in listview_item.xml
           // imgImg = (ImageView) itemView.findViewById(R.id.imagenMenuSimple);
            txtTitle = (TextView) itemView.findViewById(R.id.tituloMenuSimple);
            txtContenido = (TextView) itemView.findViewById(R.id.contenidoMenuSimple);

            // Capture position and set to the TextViews
           // imgImg.setImageResource(imagenes[position]);
            txtTitle.setText(titulos[position]);
            txtContenido.setText(contenido[position]);

            return itemView;
        }
    }

}

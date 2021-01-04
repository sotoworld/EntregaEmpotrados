package com.example.entregaempotrados;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Notas extends AppCompatActivity {
    NotesDbHelper dbHelper;
    SQLiteDatabase db;
    String[] titulo = new String[10];
    String[] contenido = new String[10];
    private ListView lista;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//ir atras

        lista = (ListView) findViewById(R.id.ListView_ListaNotas);
        //lista = (ListView) findViewById(R.id.ListView_ListaMenu);
        dbHelper = new NotesDbHelper(getApplicationContext(), "notas.db");
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        /*values.put(NotesContract.NoteEntry.COLUMN_NAME_KEY, "hola");
        values.put(NotesContract.NoteEntry.COLUMN_NAME_VAL, "El titulo es hola y yo escribo asi");
        long id = db.insert(NotesContract.NoteEntry.TABLE_NAME, null, values);*/
        Cursor cursor = db.query(NotesContract.NoteEntry.TABLE_NAME,null,null,null,null,null,null);
        int i = 0;
        try {
            while (cursor.moveToNext()) {
                String clave = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NAME_KEY));
                String valor = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_NAME_VAL));
                titulo[i] = clave;
                contenido[i] = valor;
                i++;
                //list.add(new Pair<String,String>(clave, valor));
            }
        } finally {
            cursor.close();
        }
        adapter = new ListViewAdapter(getApplicationContext(),null,titulo,contenido);
        lista.setAdapter(adapter);

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
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
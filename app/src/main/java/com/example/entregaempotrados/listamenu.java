package com.example.entregaempotrados;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class listamenu extends AppCompatActivity {

    int[] imagenMonumentos = {
            R.drawable.monumentos_arco,
            R.drawable.monumentos_banos,
            R.drawable.monumentos_iglesia,
            R.drawable.monumentos_muralla,
            R.drawable.monumentos_rey_moro,
            R.drawable.monumentos_toro,
    };

    int[] imagenMuseos = {
            R.drawable.museos_lara,
            R.drawable.museos_palacio_mondragon,
            R.drawable.museos_bandolero,
            R.drawable.museos_unicaja,
            R.drawable.museos_taurino,
            R.drawable.museos_plaza,
    };

    private int[] imagenCultural = {
            R.drawable.ayuntamiento,

    };


    String[] titulo;
    String[] contenido;

    private ListView lista;
    ListViewAdapter adapter;

    static String clave = "clave";
    static String clave2 = "clave2";
    static String clave3 = "clave3";
    static String clave4 = "clave4";

    int currentViewPager;
    String nombreMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listamenu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//ir atras


        Bundle extras = getIntent().getExtras();
        //currentViewPager = extras.getInt("currentViewPager");
        currentViewPager = (int) SingletonMap.getInstance().get(MainActivity.clave);
        nombreMenu = (String) SingletonMap.getInstance().get(MainActivity.clave2);
        actionBar.setTitle(nombreMenu);
        Log.i("domingo", "currentViewPager:" + currentViewPager);


        //indicar titulo



        lista = (ListView) findViewById(R.id.ListView_ListaMenu);
        switch (currentViewPager) {
            case 0://monumentos
                Toast.makeText(getApplicationContext(), "Cargando", Toast.LENGTH_SHORT).show();
                titulo = getResources().getStringArray(R.array.monumentos_titulo);
                contenido = getResources().getStringArray(R.array.monumentos_contenido);
                adapter = new ListViewAdapter(this,imagenMonumentos, titulo, contenido);
                break;
            case 1://museos
                Toast.makeText(getApplicationContext(), "Cargando", Toast.LENGTH_SHORT).show();
                titulo = getResources().getStringArray(R.array.museos_titulo);
                contenido = getResources().getStringArray(R.array.museos_contenido);
                adapter = new ListViewAdapter(this,imagenMuseos,titulo, contenido);
                break;
            case 2://agenda cultural
                Toast.makeText(getApplicationContext(), "Cargando", Toast.LENGTH_SHORT).show();
                titulo = getResources().getStringArray(R.array.agendacultural_titulo);
                contenido = getResources().getStringArray(R.array.agendacultural_contenido);
                adapter = new ListViewAdapter(this,imagenCultural, titulo, contenido);
                break;
            case 3://donde comer
                titulo = getResources().getStringArray(R.array.monumentos_titulo);
                contenido = getResources().getStringArray(R.array.monumentos_contenido);
                adapter = new ListViewAdapter(this,imagenMonumentos, titulo, contenido);
                break;
            case 4://donde dormir
                titulo = getResources().getStringArray(R.array.monumentos_titulo);
                contenido = getResources().getStringArray(R.array.monumentos_contenido);
                adapter = new ListViewAdapter(this,imagenMonumentos,titulo, contenido);
                break;
            case 5://informacion de interes
                titulo = getResources().getStringArray(R.array.monumentos_titulo);
                contenido = getResources().getStringArray(R.array.monumentos_contenido);
                adapter = new ListViewAdapter(this, imagenMonumentos, titulo, contenido);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Cargando", Toast.LENGTH_SHORT).show();
        }
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), paginasmenu.class);
                i.putExtra("idmenu", currentViewPager);
                i.putExtra("position", position);
                i.putExtra("nombreMenu", nombreMenu);
                i.putExtra("nombreSubMenu", titulo);
                SingletonMap.getInstance().put(listamenu.clave, currentViewPager);
                SingletonMap.getInstance().put(listamenu.clave2, position);
                SingletonMap.getInstance().put(listamenu.clave3, nombreMenu);
                SingletonMap.getInstance().put(listamenu.clave4, titulo);
                startActivity(i);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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
            this.imagenes = imagenes;
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
            ImageView imgImg;
            TextView txtTitle;
            TextView txtContenido;

            //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.menusimple, parent, false);

            // Locate the TextViews in listview_item.xml
            imgImg = (ImageView) itemView.findViewById(R.id.imagenMenuSimple);
            txtTitle = (TextView) itemView.findViewById(R.id.tituloMenuSimple);
            txtContenido = (TextView) itemView.findViewById(R.id.contenidoMenuSimple);

            // Capture position and set to the TextViews
            imgImg.setImageResource(imagenes[position]);
            txtTitle.setText(titulos[position]);
            txtContenido.setText(contenido[position]);

            return itemView;
        }
    }

}
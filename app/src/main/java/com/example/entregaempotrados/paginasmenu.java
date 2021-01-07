package com.example.entregaempotrados;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by DomingoJavier on 08/05/2016.
 */
public class paginasmenu extends AppCompatActivity {

    private ImageView imgImagen;
    private TextView txtTitulo,txtContenido;
    String []titulo;
    String []contenido;

    private int []imagenMonumentos ={
            R.drawable.monumentos_arco,
            R.drawable.monumentos_banos,
            R.drawable.monumentos_iglesia,
            R.drawable.monumentos_muralla,
            R.drawable.monumentos_rey_moro,
            R.drawable.monumentos_toro,
            R.drawable.monumentos_arco,
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paginasmenu);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//ir atras

        Bundle extras = getIntent().getExtras();
        /*int idmenu = extras.getInt("idmenu");
        final int position = extras.getInt("position");
        String nombreMenu = extras.getString("nombreMenu");
        String nombreSubMenu = extras.getString("nombreSubMenu");*/
        int idmenu  = (int) SingletonMap.getInstance().get(listamenu.clave);
        final int position = (int) SingletonMap.getInstance().get(listamenu.clave2);
        String nombreMenu = (String) SingletonMap.getInstance().get(listamenu.clave3);
       // String[] nombreSubMenu = (String[]) SingletonMap.getInstance().get(listamenu.clave4);


        //indicar tutilo y subtitulo

        actionBar.setTitle(nombreMenu);
       // actionBar.setSubtitle(nombreSubMenu);

        txtTitulo = (TextView) findViewById(R.id.tv_titulo_paginasmenu);
        txtContenido = (TextView) findViewById(R.id.tv_contenido_paginasmenu);
        imgImagen = (ImageView) findViewById(R.id.tv_imagen_paginasmenu);


        switch (idmenu) {
            case 0://monumentos
                titulo = getResources().getStringArray(R.array.monumentos_titulo);
                contenido = getResources().getStringArray(R.array.monumentos_contenido);
                imgImagen.setImageResource(imagenMonumentos[position]);
                break;

            default:
                Toast.makeText(getApplicationContext(), "Cargando", Toast.LENGTH_SHORT).show();
        }
        txtTitulo.setText(titulo[position]);
        txtContenido.setText(contenido[position]);
        txtContenido.setMovementMethod(new ScrollingMovementMethod());
    }
}

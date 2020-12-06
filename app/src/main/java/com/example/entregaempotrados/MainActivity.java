package com.example.entregaempotrados;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ViewPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    public String [] mMenu;
    static String clave = "clave";
    private String info;
    private PagerAdapter adapter;

    protected static Integer[] mImageIds={
            R.drawable.monumentos,//todas las imagenes del menu//
    };

    ListView lista;
    String [] informacion={
            "Taxis",
            "Alquiler Bicicletas",
            "Parkings públicos",
            "Oficina de Turismo"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setLocale("en");
        loadLocale();
        setContentView(R.layout.activity_main);
        //listview//
        lista=(ListView)findViewById(R.id.listViewHome);
        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,informacion);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getApplicationContext(),"posicion" + (i+1) + informacion [i],Toast.LENGTH_SHORT).show();

            }
        });
        Button cambiar = findViewById(R.id.button);
        cambiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               mostrarVentanaCambio();
                /*setLocale("es");
                recreate();*/
            }
        });
                //viewpager//
        mMenu=getResources().getStringArray(R.array.menu);
        mViewPager=(ViewPager)findViewById(R.id.pager);
        mSectionsPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        //adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        String a = getResources().getString(R.string.prueba);
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(0,mMenu[0],mImageIds));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(1,mMenu[1],mImageIds));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(2,mMenu[2],mImageIds));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(3,mMenu[3],mImageIds));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(4,mMenu[4],mImageIds));
        mSectionsPagerAdapter.addFragment(Fragmentos.newInstance(5,mMenu[5],mImageIds));

        mViewPager.setAdapter(mSectionsPagerAdapter);
    }



    public class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;//guardar fragmentos

        //constructor
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            fragments = new ArrayList<Fragment>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {//muestra el numero de paginas
            return this.fragments.size();
        }

        public void addFragment(Fragment xfragment) {
            this.fragments.add(xfragment);
        }
    }
    //fragmentos
    public static class Fragmentos extends Fragment {
        private static final String CURRENT_VIEWPAGER="currentviewpager";
        private static final String NOMBRE_MENU="menu";
        private static final String IMAGEVIEW="image";
        private String info;
        private int currentViewPager;
        private String nombre_menu;
        private int image;

        public static Fragmentos newInstance(int currentViewPager, String menuNombre, Integer[] image){
            Fragmentos fragment=new Fragmentos();

            Bundle args= new Bundle();
            args.putInt(CURRENT_VIEWPAGER,currentViewPager);
            args.putString(NOMBRE_MENU,menuNombre);
            args.putInt(IMAGEVIEW,image[0]);
            fragment.setArguments(args);
            fragment.setRetainInstance(true);
            return fragment;
        }

        @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            //cuando crea una instancia sino le enviamos parametros lo hace aleatorio
            if (getArguments()!=null){
                this.currentViewPager=getArguments().getInt(CURRENT_VIEWPAGER);
                this.nombre_menu=getArguments().getString(NOMBRE_MENU);
                this.image=getArguments().getInt(IMAGEVIEW);
            }
        }
        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
            View rootView=inflater.inflate(R.layout.fragment_menu,container,false);
            TextView tv_menu=(TextView) rootView.findViewById(R.id.tv_menu);
            tv_menu.setText(nombre_menu);

            ImageView frg_image= (ImageView)rootView.findViewById(R.id.frg_imageView);
            frg_image.setImageResource(image);
            frg_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), listamenu.class);
                    i.putExtra("currentViewPager", currentViewPager);
                    i.putExtra("nombreMenu", nombre_menu);
                    SingletonMap.getInstance().put(MainActivity.clave, currentViewPager);
                    startActivity(i);
                }
            });
            return rootView;
        }
    }

    private void mostrarVentanaCambio(){
        final String[] ids = {"English", "Español"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(ids, -1, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface interfaz, int i){
                if (i==0){
                    setLocale("en");
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    //recreate();
                }else if (i==1){
                    setLocale("es");
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    //recreate();
                }
                interfaz.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();

        mDialog.show();
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config  = getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics()) ;
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    private void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String idioma = prefs.getString("My_Lang", "");
        setLocale(idioma);
    }
}


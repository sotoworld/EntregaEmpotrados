package com.example.entregaempotrados;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    public String [] mMenu;
    private String info;

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
        //viewpager//
        mMenu=getResources().getStringArray(R.array.menu);
        mSectionsPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager=(ViewPager)findViewById(R.id.pager);

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
                    startActivity(i);


                }
            });
            return rootView;
        }
    }
}
package com.drproyects.nbafantasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.drproyects.nbafantasy.fragments.PerfilFragment;
import com.drproyects.nbafantasy.fragments.AlineacionFragment;
import com.drproyects.nbafantasy.fragments.ClasificacionFragment;
import com.drproyects.nbafantasy.fragments.MercadoFragment;
import com.drproyects.nbafantasy.fragments.MiEquipoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener  {

    private static final String TAG = "MainActivity";

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;

    private final AlineacionFragment alineacionFragment = new AlineacionFragment();
    private final MercadoFragment mercadoFragment  = new MercadoFragment();
    private final ClasificacionFragment clasificacionFragment = new ClasificacionFragment();
    private final PerfilFragment perfilFragment = new PerfilFragment();

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(this);// Supervisar el cambio de la página viewPager
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return alineacionFragment;
                    case 1:
                        return mercadoFragment;
                    case 2:
                        return clasificacionFragment;
                    case 3:
                        return perfilFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        requestQueue = Volley.newRequestQueue(this);
    }

    public void seleccionarJugador(View v){
        Bundle bundle = new Bundle();
        bundle.putString("String", v.toString());
        Fragment fragment = new MiEquipoFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment, null);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.hide(new AlineacionFragment());
        fragmentTransaction.commit();
        Button btnVenderJugador = findViewById(R.id.btnVenderJugador);
        btnVenderJugador.setVisibility(v.GONE);
    }

    @Override
    public void onBackPressed()
    {
        // Añade más funciones si fuese necesario
        Button btnVenderJugador = findViewById(R.id.btnVenderJugador);
        btnVenderJugador.setVisibility(View.VISIBLE);
        super.onBackPressed();  // Invoca al método
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // El atributo android: orderInCategory en menu / navigation.xml es el valor tomado por item.getOrder () a continuación
        viewPager.setCurrentItem(item.getOrder());
        return true;
    }
}
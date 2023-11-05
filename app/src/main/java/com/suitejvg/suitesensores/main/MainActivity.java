package com.suitejvg.suitesensores.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.suitejvg.suitesensores.R;
import com.suitejvg.suitesensores.utils.HomeFragmen;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    * menu hamburguesa
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        * menu de la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        * menu hamburguesa e iniciar event listener
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        * cargar el menu
        navigationView.inflateMenu(R.menu.nav_menu);

//        * cargar el header
        navigationView.inflateHeaderView(R.layout.nav_header);

//        * crear el menu hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        * abrir el fragmento home al iniciar la app
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragmen()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

//    * crear la toolbar de la app
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //    * manejar los eventos de la toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.dele) {
            Toast.makeText(this, "Dele", Toast.LENGTH_SHORT).show();

//              * cambiar el menu hamburguesa
            NavigationView navigationView = findViewById(R.id.nav_view);
//              * limpiar el menu hamburguesa para que no se duplique
            navigationView.getMenu().clear();
            navigationView.removeHeaderView(navigationView.getHeaderView(0));
//              * cargar el menu de cada uno
            navigationView.inflateMenu(R.menu.nav_menu_dele);
//              * cargar el header
            navigationView.inflateHeaderView(R.layout.nav_header_dele);
        }
        return super.onOptionsItemSelected(item);
    }

    //    * manejar los eventos del menu hamburguesa
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
//            * en vez de intent para mantener menus
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragmen()).commit();
            Toast.makeText(this, "Home*", Toast.LENGTH_SHORT).show();
//            * localizar el menu hamburguesa
            NavigationView navigationView = findViewById(R.id.nav_view);
//            * limpiar el menu hamburguesa para que no se duplique
            navigationView.getMenu().clear();
            navigationView.removeHeaderView(navigationView.getHeaderView(0));

//            * cargar el menu de cada uno
            navigationView.inflateMenu(R.menu.nav_menu);
//            * cargar el header
            navigationView.inflateHeaderView(R.layout.nav_header);

        } else if (item.getItemId()==R.id.nav_temper) {
            Toast.makeText(this, "Temperatura*", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.nav_luminosidad) {
            Toast.makeText(this, "Luminosidad*", Toast.LENGTH_SHORT).show();
            Intent linterna = new Intent(this, Linterna.class);
            startActivity(linterna);
        } else if (item.getItemId()==R.id.nav_proximidad) {
            Toast.makeText(this, "Proximidad*", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId()==R.id.nav_creditos) {
            Toast.makeText(this, "Créditos*", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId()==R.id.nav_info) {
            Toast.makeText(this, "Info*", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId()==R.id.nav_salir) {
            Toast.makeText(this, "Saliendo*", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
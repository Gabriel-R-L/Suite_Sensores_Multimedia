package com.suitejvg.suitesensores.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.suitejvg.suitesensores.R;
import com.suitejvg.suitesensores.utils.HomeFragmen;
import com.suitejvg.suitesensores.utils.LinternaFragment;

public class Linterna extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linterna);

//        * menu de la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        * menu hamburguesa e iniciar event listener
        drawerLayout = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

//        * cargar el menu
        navigationView.inflateMenu(R.menu.nav_menu_dele);

//        * cargar el header
        navigationView.inflateHeaderView(R.layout.nav_header_dele);

//        * crear el menu hamburguesa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        * abrir el fragmento home al iniciar la app
        //if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1, new LinternaFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        //}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
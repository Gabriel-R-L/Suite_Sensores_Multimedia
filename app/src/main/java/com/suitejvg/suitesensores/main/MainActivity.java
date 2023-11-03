package com.suitejvg.suitesensores.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.suitejvg.suitesensores.R;
import com.suitejvg.suitesensores.utils.HomeFragmen;
import com.suitejvg.suitesensores.utils.TicTacPtP;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

//    * menu hamburguesa
    private DrawerLayout drawerLayout;

    TicTacPtP ticTacPtP;
    String mode = "";
    public Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;

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

        if (item.getItemId() == R.id.vix) {
            Toast.makeText(this, "Vix", Toast.LENGTH_SHORT).show();

//            * localizar el menu hamburguesa
            NavigationView navigationView = findViewById(R.id.nav_view);
//            * limpiar el menu hamburguesa para que no se duplique
            navigationView.getMenu().clear();
            navigationView.removeHeaderView(navigationView.getHeaderView(0));

//            * cargar el menu de cada uno
            navigationView.inflateMenu(R.menu.nav_menu_vix);
//            * cargar el header
            navigationView.inflateHeaderView(R.layout.nav_header_vix);

        } else if (item.getItemId() == R.id.dele) {
            Toast.makeText(this, "Dele", Toast.LENGTH_SHORT).show();

//              * cambiar el menu hamburguesa
//            NavigationView navigationView = findViewById(R.id.nav_view);
//              * limpiar el menu hamburguesa para que no se duplique
//            navigationView.getMenu().clear();
//              * cargar el menu de cada uno
//            navigationView.inflateMenu(R.menu.nav_menu_dele);
//              * cargar el header
//            navigationView.inflateHeaderView(R.layout.nav_header_dele);

        } else if (item.getItemId() == R.id.jona) {
            Toast.makeText(this, "Jona", Toast.LENGTH_SHORT).show();

//              * cambiar el menu hamburguesa
//            NavigationView navigationView = findViewById(R.id.nav_view);
//              * limpiar el menu hamburguesa para que no se duplique
//            navigationView.getMenu().clear();
//              * cargar el menu de cada uno
//            navigationView.inflateMenu(R.menu.nav_menu_jona);
//              * cargar el header
//            navigationView.inflateHeaderView(R.layout.nav_header_jona);
        }

        return super.onOptionsItemSelected(item);
    }

    //    * manejar los eventos del menu hamburguesa
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.nav_home){
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

        } else if (item.getItemId()==R.id.nav_giroscopio) {
            Toast.makeText(this, "Giroscopio*", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId()==R.id.nav_humedad) {
            Toast.makeText(this, "Humedad*", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId()==R.id.nav_presion) {
            Toast.makeText(this, "Presión*", Toast.LENGTH_SHORT).show();

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
//    * manejar los eventos de los botones
    public void handleMode(View view) {

        if (view.getId() == R.id.ptp) {
            Toast.makeText(this, "PtP", Toast.LENGTH_SHORT).show();
            ticTacPtP = new TicTacPtP(this);
            mode = "ptp";

        } else if (view.getId() == R.id.medium) {
            Toast.makeText(this, "Medium", Toast.LENGTH_SHORT).show();

        } else if (view.getId() ==  R.id.easy) {
            Toast.makeText(this, "Easy", Toast.LENGTH_SHORT).show();

        } else if (view.getId() ==  R.id.hard) {
            Toast.makeText(this, "Hard", Toast.LENGTH_SHORT).show();
        }
    }

    public void restart(View v){
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
        reinicio();
    }

    public void reinicio() {
        Toast.makeText(this, "Reiniciando..",Toast.LENGTH_LONG).show();
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
    }

    public void handleMove(View view) {

        Toast.makeText(this, "Jugando", Toast.LENGTH_SHORT).show();

        if (mode.equals("")) {
            Toast.makeText(this, "Seleccione un modo de juego", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mode.equals("ptp")) {
            ticTacPtP.presion(view);
            Toast.makeText(this, "Jugando PtP", Toast.LENGTH_SHORT).show();

        }
    }
}
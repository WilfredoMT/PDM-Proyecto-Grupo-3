package sv.edu.ues.fia.pdm.proyecto.grupo3;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;

import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.ActivityMainBinding;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.home.HomeFragment;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;



public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public ActivityMainBinding binding;
    private BaseDatosHelper baseDatosHelper;

    public String[] infoUsuario;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Acceso a SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        baseDatosHelper = new BaseDatosHelper(this);


        String usuarioLogeado;
        int imagenLogeadoId;

        String rolLogeado;

        //Recuperar info de usuario
        Intent intent = getIntent();
        usuarioLogeado = intent.getStringExtra("Usuario");
        rolLogeado = intent.getStringExtra("Rol");
        imagenLogeadoId = intent.getIntExtra("Imagen", 0);





        //pasar bundle a fragment de home
        infoUsuario = new String[]{usuarioLogeado, rolLogeado};



        //rolLogeado
        Log.e("main", usuarioLogeado);
        Log.e("main", rolLogeado);





        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarMain.toolbar);

        //Acciones FAB
        binding.appBarMain.fab.setImageResource(R.drawable.menu);
        binding.appBarMain.fab.setVisibility(View.VISIBLE);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirMenu();
            }

        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        //Mostrar/Ocultar menus segun usuario

        MenuItem menuCiclo = navigationView.getMenu().findItem(R.id.nav_menuCiclo);
        MenuItem menuMaterias = navigationView.getMenu().findItem(R.id.nav_menuMaterias);
        MenuItem menuLocales = navigationView.getMenu().findItem(R.id.nav_menuLocales);
        MenuItem menuCoordinador = navigationView.getMenu().findItem(R.id.nav_menuCoordinador);
        MenuItem menuAsignar = navigationView.getMenu().findItem(R.id.nav_menuAsignar);
        MenuItem menuEncargados = navigationView.getMenu().findItem(R.id.nav_menuEncargados);
        MenuItem menuSolicitudesHorario = navigationView.getMenu().findItem(R.id.nav_menuSolicitudesHorario);
        MenuItem menuRespuestaSolicitude = navigationView.getMenu().findItem(R.id.nav_menuRespuestaSolicitud);
        MenuItem menuSolicitudesAtendidas = navigationView.getMenu().findItem(R.id.nav_menuSolicitudesAtendidas);
        MenuItem menuPropuestas = navigationView.getMenu().findItem(R.id.nav_menuPropuesta);
        MenuItem menuHorarios = navigationView.getMenu().findItem(R.id.nav_menuHorarios);
        MenuItem menuEventos = navigationView.getMenu().findItem(R.id.nav_menuEventos);
        menuCiclo.setVisible(false);
        menuMaterias.setVisible(false);
        menuLocales.setVisible(false);
        menuCoordinador.setVisible(false);
        menuAsignar.setVisible(false);
        menuEncargados.setVisible(false);
        menuSolicitudesHorario.setVisible(false);
        menuRespuestaSolicitude.setVisible(false);
        menuPropuestas.setVisible(false);
        menuHorarios.setVisible(false);
        menuEventos.setVisible(false);
        menuSolicitudesAtendidas.setVisible(false);

        //usuario admin
        if (rolLogeado.equals("Administrador")) {
            menuCiclo.setVisible(true);
            menuMaterias.setVisible(true);
            menuLocales.setVisible(true);
            menuCoordinador.setVisible(true);
            menuEncargados.setVisible(true);
            menuAsignar.setVisible(true);



        }
        if (rolLogeado.equals("Encargado de Horario")) {

            menuHorarios.setVisible(true);
            menuPropuestas.setVisible(true);
            menuSolicitudesAtendidas.setVisible(true);


        }
        if (rolLogeado.equals("Coordinador")) {
            menuHorarios.setVisible(true);
            menuSolicitudesHorario.setVisible(true);
            menuRespuestaSolicitude.setVisible(true);

        }





        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                //Añadir los otros fragments
                R.id.nav_home, R.id.nav_menuCiclo, R.id.nav_menuMaterias, R.id.nav_menuLocales, R.id.nav_menuCoordinador, R.id.nav_menuAsignar, R.id.nav_menuEncargados, R.id.nav_menuSolicitudesHorario, R.id.nav_menuHorarios, R.id.nav_menuRespuestaSolicitud, R.id.nav_menuSolicitudesAtendidas, R.id.nav_menuPropuesta, R.id.nav_menuEventos )

                .setOpenableLayout(drawer)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        //setear header con info de usuario
        View header = navigationView.getHeaderView(0);
        TextView textViewNombre = header.findViewById(R.id.textViewNombre);
        textViewNombre.setText(usuarioLogeado);
        TextView textViewRol = header.findViewById(R.id.textViewRol);
        textViewRol.setText(rolLogeado);
        ImageView imageViewperfil = header.findViewById(R.id.imageView);
        Cursor cursor = baseDatosHelper.getUsuario(usuarioLogeado);
        byte[] imagenUsuario = cursor.getBlob(imagenLogeadoId);
        Bitmap imagen = baseDatosHelper.getBitmapFromBytes(imagenUsuario);
        Bitmap imagenReducida = Bitmap.createScaledBitmap(imagen, 140, 140, false);
        imageViewperfil.setImageBitmap(imagenReducida);


    }

    public void toggleFabVisibility(boolean visible) {
        if (visible) {
            binding.appBarMain.fab.setVisibility(View.VISIBLE);
        } else {
            binding.appBarMain.fab.setVisibility(View.GONE);
        }
    }

    // click listener para FAB
    public void setFabClickListener(View.OnClickListener listener) {
        binding.appBarMain.fab.setOnClickListener(listener);
    }
    // Icono Para FAB
    public void setFabIcon(Drawable icon) {
        binding.appBarMain.fab.setImageDrawable(icon);
    }

    private void logoff() {
        // borrar credenciales
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("loggedIn");
        editor.remove("username");
        editor.remove("rol");
        editor.remove("imagenIndex");
        editor.apply();

        // redirigir a la pantalla de login
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logoff){
            logoff();
        }
        else
        {
            //Barra de menus
            if (id == android.R.id.home){
                DrawerLayout drawer = binding.drawerLayout;
                drawer.openDrawer(GravityCompat.START);
            }

        }
        return true;
    }
    public void abrirMenu(){
        DrawerLayout drawer = binding.drawerLayout;
        drawer.openDrawer(GravityCompat.START);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
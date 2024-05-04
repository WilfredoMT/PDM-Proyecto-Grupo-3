package sv.edu.ues.fia.pdm.proyecto.grupo3;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;

import sv.edu.ues.fia.pdm.proyecto.grupo3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private BaseDatosHelper baseDatosHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseDatosHelper = new BaseDatosHelper(this);


        String usuarioLogeado;
        int imagenLogeadoId;

        String rolLogeado;

        //Recuperar info de usuario
        Intent intent = getIntent();
        usuarioLogeado = intent.getStringExtra("Usuario");
        rolLogeado = intent.getStringExtra("Rol");
        imagenLogeadoId = intent.getIntExtra("Imagen", 0);


        //rolLogeado
        //Log.e("main", usuarioLogeado);
        //Log.e("main", rolLogeado);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                //Añadir los otros fragments
                R.id.nav_home, R.id.nav_menuCiclo, R.id.nav_menuMaterias, R.id.nav_menuLocales, R.id.nav_menuCoordinador, R.id.nav_menuSolicitudes, R.id.nav_menuDocentes, R.id.nav_menuHorarios, R.id.nav_menuEventos )

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
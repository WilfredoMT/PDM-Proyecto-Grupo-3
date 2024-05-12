package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;


public class AgregarEncargadosActivity extends AppCompatActivity {

    BaseDatosHelper baseDatosHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_encargados);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.agregar_encargado_de_horario);

        Button buttonAgregar = findViewById(R.id.buttonAgregarEncargado);
        EditText editTextNombre = findViewById(R.id.EditTextNombreEncargado);
        EditText editTextApellido = findViewById(R.id.EditTextApellidoEncargado);
        EditText editTextEmail = findViewById(R.id.EditTextEmail);

        EditText editTextUsuario= findViewById(R.id.EditTextNombreUsuario);
        EditText editTextClave = findViewById(R.id.EditTextContra);
        ImageView imageViewPerfil = findViewById(R.id.imageViewPerfil);

        Context context;
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.docente);
        imageViewPerfil.setImageDrawable(drawable);

        imageViewPerfil.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseDatosHelper = new BaseDatosHelper(getBaseContext());

                String nombreUsuario, clave, nombreEncargado, apellidoEncargado, email;


                nombreUsuario = editTextUsuario.getText().toString();
                clave = editTextClave.getText().toString();
                nombreEncargado = editTextNombre.getText().toString();
                apellidoEncargado = editTextApellido.getText().toString();
                email = editTextEmail.getText().toString();


                //extraer imagen
                imageViewPerfil.setDrawingCacheEnabled(true);
                imageViewPerfil.buildDrawingCache();

                Bitmap imagenPerfilExtraida = Bitmap.createBitmap(imageViewPerfil.getDrawingCache());

                if (!baseDatosHelper.existeEncargado (nombreEncargado)) {
                    // Si no existe agregar ciclo

                    //Crear cuenta primero y recuperar id

                    long idCreado = baseDatosHelper.agregarUsuario(nombreUsuario, clave, "Encargado de Horario", imagenPerfilExtraida);
                    String idCreadoStr = String.valueOf(idCreado);

                    //crear encargado

                    baseDatosHelper.agregarEncargado(nombreEncargado, apellidoEncargado, email, idCreadoStr);
                    Toast.makeText(AgregarEncargadosActivity.this, R.string.encargado_registrado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(AgregarEncargadosActivity.this, R.string.el_encargado_ya_esta_registrado, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ImageView imageViewPerfil = findViewById(R.id.imageViewPerfil);
                imageViewPerfil.setImageBitmap(bitmap);





            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
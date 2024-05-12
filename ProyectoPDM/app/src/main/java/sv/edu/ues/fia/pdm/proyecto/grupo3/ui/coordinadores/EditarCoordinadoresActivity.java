package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.coordinadores;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.io.IOException;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.locales.EditarLocalesActivity;

public class EditarCoordinadoresActivity extends AppCompatActivity {

    BaseDatosHelper baseDatosHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    public Bitmap imagenPefil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_coordinadores);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editar_coordinador);

        Button buttonEditar = findViewById(R.id.buttonAgregarCoordinador);
        EditText editTextNombre = findViewById(R.id.EditTextNombreCoordinador);
        EditText editTextApellido = findViewById(R.id.EditTextApellidoCoordinador);
        EditText editTextEmail = findViewById(R.id.EditTextEmail);

        EditText editTextUsuario= findViewById(R.id.EditTextNombreUsuario);
        EditText editTextClave = findViewById(R.id.EditTextContra);
        TextView textViewEditando = findViewById(R.id.textView);
        ImageView imageViewPerfil = findViewById(R.id.imageViewPerfil);

        //recuperar info de intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("idEditar");

        imageViewPerfil.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        String nombreUsuario, clave, nombreCoord, apellidoCoord, email, idUsuario;

        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());


        //info de coordinador
        Cursor cursor = baseDatosHelper.getCoordinador(id);
        nombreCoord = cursor.getString(1);
        apellidoCoord = cursor.getString(2);
        email = cursor.getString(3);
        idUsuario = cursor.getString(4);

        //info de usuarios

        //segundo argumento nombreCoord es un dummy para diferfenciar el merodo sobrecargado
        cursor = baseDatosHelper.getUsuario(idUsuario, nombreCoord);
        nombreUsuario = cursor.getString(1);
        clave = cursor.getString(2);
        imagenPefil = baseDatosHelper.getBitmapFromBytes(cursor.getBlob(4));

        //llenando views

        textViewEditando.setText(getString(R.string.editar_coordinador) + ": " + nombreCoord);

        imageViewPerfil.setImageBitmap(imagenPefil);

        editTextNombre.setText(nombreCoord);
        editTextUsuario.setText(nombreUsuario);
        editTextApellido.setText(apellidoCoord);
        editTextEmail.setText(email);
        editTextClave.setText(clave);

        buttonEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nombreUsuarioEditado, claveEditado, nombreCoordEditado, apellidoCoordEditado, emailEditado;
                Bitmap imagenPerfilEditado;

                nombreUsuarioEditado = editTextUsuario.getText().toString();
                claveEditado = editTextClave.getText().toString();
                nombreCoordEditado = editTextNombre.getText().toString();
                apellidoCoordEditado = editTextApellido.getText().toString();
                emailEditado = editTextEmail.getText().toString();

                //recuperar imagen
                imageViewPerfil.setDrawingCacheEnabled(true);
                imageViewPerfil.buildDrawingCache(true);
                imagenPerfilEditado = Bitmap.createBitmap(imageViewPerfil.getDrawingCache());
                // eliminar cache
                imageViewPerfil.setDrawingCacheEnabled(false);





                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeCoordinador(nombreCoordEditado, id)) {
                    // Si no existe permitir editar local

                    Log.e("EditarCoordinadoresActivity", nombreUsuarioEditado);
                    baseDatosHelper.actualizarUsuario(idUsuario, nombreUsuarioEditado, claveEditado, "Coordinador", imagenPerfilEditado);
                    baseDatosHelper.actualizarCoordinador(id, nombreCoordEditado, apellidoCoordEditado, emailEditado);
                    Toast.makeText(EditarCoordinadoresActivity.this, R.string.coordinador_editado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(EditarCoordinadoresActivity.this, R.string.el_coordinador_ya_esta_registrado, Toast.LENGTH_SHORT).show();
                }


            }
        });

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

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
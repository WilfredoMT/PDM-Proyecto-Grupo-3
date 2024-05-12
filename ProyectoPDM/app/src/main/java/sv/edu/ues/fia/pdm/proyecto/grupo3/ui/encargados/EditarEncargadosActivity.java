package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.encargados;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;

public class EditarEncargadosActivity extends AppCompatActivity {

    BaseDatosHelper baseDatosHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    public Bitmap imagenPefil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_encargados);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.editandoencargado);

        Button buttonEditar = findViewById(R.id.buttonAgregarEncargado);
        EditText editTextNombre = findViewById(R.id.EditTextNombreEncargado);
        EditText editTextApellido = findViewById(R.id.EditTextApellidoEncargado);
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

        String nombreUsuario, clave, nombreEncargado, apellidoEncargado, email, idUsuario;

        //recuperar datos
        baseDatosHelper = new BaseDatosHelper(getBaseContext());


        //info de Encargado
        Cursor cursor = baseDatosHelper.getEncargado(id);
        nombreEncargado = cursor.getString(1);
        apellidoEncargado= cursor.getString(2);
        email = cursor.getString(3);
        idUsuario = cursor.getString(4);

        //info de usuarios

        //segundo argumento nombreEncargado es un dummy para diferfenciar el merodo sobrecargado
        cursor = baseDatosHelper.getUsuario(idUsuario, nombreEncargado);
        nombreUsuario = cursor.getString(1);
        clave = cursor.getString(2);
        imagenPefil = baseDatosHelper.getBitmapFromBytes(cursor.getBlob(4));

        //llenando views

        textViewEditando.setText(getString(R.string.editandoencargado) + ": " + nombreEncargado);

        imageViewPerfil.setImageBitmap(imagenPefil);

        editTextNombre.setText(nombreEncargado);
        editTextUsuario.setText(nombreUsuario);
        editTextApellido.setText(apellidoEncargado);
        editTextEmail.setText(email);
        editTextClave.setText(clave);

        buttonEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nombreUsuarioEditado, claveEditado, nombreEncargadoEditado, apellidoEncargadoEditado, emailEditado;
                Bitmap imagenPerfilEditado;

                nombreUsuarioEditado = editTextUsuario.getText().toString();
                claveEditado = editTextClave.getText().toString();
                nombreEncargadoEditado = editTextNombre.getText().toString();
                apellidoEncargadoEditado = editTextApellido.getText().toString();
                emailEditado = editTextEmail.getText().toString();

                //recuperar imagen
                imageViewPerfil.setDrawingCacheEnabled(true);
                imageViewPerfil.buildDrawingCache(true);
                imagenPerfilEditado = Bitmap.createBitmap(imageViewPerfil.getDrawingCache());
                // eliminar cache
                imageViewPerfil.setDrawingCacheEnabled(false);





                baseDatosHelper = new BaseDatosHelper(getBaseContext());
                if (!baseDatosHelper.existeEncargado(nombreEncargadoEditado, id)) {
                    // Si no existe permitir editar local


                    baseDatosHelper.actualizarUsuario(idUsuario, nombreUsuarioEditado, claveEditado, "Encargado de Horario", imagenPerfilEditado);
                    baseDatosHelper.actualizarEncargado(id, nombreEncargadoEditado, apellidoEncargadoEditado, emailEditado);
                    Toast.makeText(EditarEncargadosActivity.this, R.string.encargado_editado_correctamente, Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    // Si existe mostrar error
                    Toast.makeText(EditarEncargadosActivity.this, R.string.el_encargado_ya_esta_registrado, Toast.LENGTH_SHORT).show();
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
package sv.edu.ues.fia.pdm.proyecto.grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsuario, editTextClave;
    private Button buttonLogin;
    private BaseDatosHelper baseDatosHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        baseDatosHelper = new BaseDatosHelper(this);

        //Valores iniciales
        baseDatosHelper.insertarDatosInicialesUsuarios();

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextClave = findViewById(R.id.editTextClave);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editTextUsuario.getText().toString();
                String clave = editTextClave.getText().toString();

                if (usuario.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor ingrese el Usuario y la Contraseña", Toast.LENGTH_SHORT).show();
                }
                else{

                    Cursor cursor = baseDatosHelper.getUsuario(usuario);

                    if (cursor != null && cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        int passwordIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_clave);
                        int rolIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_rol);
                        int imagenIndex = cursor.getColumnIndex(BaseDatosHelper.KEY_imagen_perfil);

                        if (passwordIndex != -1) {
                            String storedPassword = cursor.getString(passwordIndex);
                            String rol = cursor.getString(rolIndex);
                            //byte[] imagenUsuario = cursor.getBlob(imagenIndex);
                            if (clave.equals(storedPassword)) {
                                // Login correcto
                                Toast.makeText(LoginActivity.this, "Login Correcto", Toast.LENGTH_SHORT).show();
                                //Ir a actividades
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                intent.putExtra("Usuario", usuario);

                                intent.putExtra("Rol", rol);

                                intent.putExtra("Imagen", imagenIndex);
                                startActivity(intent);
                                 // Close LoginActivity


                            } else {
                                // Contraseña incorrecta
                                Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Si coloumna de contraseña no se encuentra
                            Toast.makeText(LoginActivity.this, "Columna contraseña no encontrada", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Usuario no encontrado
                        Toast.makeText(LoginActivity.this, "Usuario Incorrecto", Toast.LENGTH_SHORT).show();
                    }




                }







            }
        });
    }
}


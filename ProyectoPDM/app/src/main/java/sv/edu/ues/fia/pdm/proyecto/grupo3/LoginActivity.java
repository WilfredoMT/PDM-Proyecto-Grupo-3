package sv.edu.ues.fia.pdm.proyecto.grupo3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsuario, editTextClave;
    private Button buttonLogin;
    private CheckBox checkBoxRecordar;
    private SharedPreferences sharedPreferences;
    private BaseDatosHelper baseDatosHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        baseDatosHelper = new BaseDatosHelper(this);
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        //checkear si ya esta logueado
        if (sharedPreferences.getBoolean("loggedIn", false)) {
            String usuario = sharedPreferences.getString("username", "");
            String rol = sharedPreferences.getString("rol", "");
            int imagenIndex = sharedPreferences.getInt("imagenIndex", 0);
            irMainActivity(usuario, rol, imagenIndex);
        }


        //Valores iniciales
        baseDatosHelper.insertarDatosInicialesUsuarios();

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextClave = findViewById(R.id.editTextClave);
        buttonLogin = findViewById(R.id.buttonLogin);
        checkBoxRecordar = findViewById(R.id.checkBox);


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

                                //Recordar el checkbox
                                if (checkBoxRecordar.isChecked()) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("loggedIn", true);
                                    editor.putString("username", usuario);
                                    editor.putString("rol", rol);
                                    editor.putInt("imagenIndex", imagenIndex);
                                    editor.apply();
                                }


                                //Ir a actividades
                                irMainActivity(usuario, rol, imagenIndex);




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
    private void irMainActivity(String usuario, String rol, int imagenIndex) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("Usuario", usuario);
        intent.putExtra("Rol", rol);
        intent.putExtra("Imagen", imagenIndex);
        startActivity(intent);
        finish();
    }

}




package com.example.pm01tareamvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm01tareamvc.configuracion.SQLiteConexion;
import com.example.pm01tareamvc.configuracion.transaccion;

public class ActivityAgregarEmpleado extends AppCompatActivity {
    EditText txtAgregarNombres, txtAgregarApellidos, txtAgregarEdad, txtAgregarDireccion, txtAgregarPuesto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_empleado);

        txtAgregarNombres= (EditText) findViewById(R.id.txtAgregarNombres);
        txtAgregarApellidos= (EditText) findViewById(R.id.txtAgregarApellidos);
        txtAgregarEdad= (EditText) findViewById(R.id.txtAgregarEdad);
        txtAgregarDireccion= (EditText) findViewById(R.id.txtAgregarDireccion);
        txtAgregarPuesto= (EditText) findViewById(R.id.txtAgregarPuesto);

        Button btnAgregarGuardar = (Button) findViewById(R.id.btnAgregarGuardar);

        btnAgregarGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarPersona();
            }
        });
    }

    private void AgregarPersona() {
        SQLiteConexion conexion = new SQLiteConexion(this, transaccion.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(transaccion.nombre, txtAgregarNombres.getText().toString());
        valores.put(transaccion.apellido, txtAgregarApellidos.getText().toString());
        valores.put(transaccion.edad, txtAgregarEdad.getText().toString());
        valores.put(transaccion.direccion, txtAgregarDireccion.getText().toString());
        valores.put(transaccion.puesto, txtAgregarPuesto.getText().toString());


        if(txtAgregarNombres.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de nombres", Toast.LENGTH_LONG).show();
        }else if(txtAgregarApellidos.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de apellidos", Toast.LENGTH_LONG).show();
        }else if(txtAgregarEdad.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de edad", Toast.LENGTH_LONG).show();
        }else if(txtAgregarDireccion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de direccion", Toast.LENGTH_LONG).show();
        }else if(txtAgregarPuesto.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de puesto", Toast.LENGTH_LONG).show();
        }else{
            Long resultado = db.insert(transaccion.tablaEmpleados, transaccion.id, valores);
            Toast.makeText(getApplicationContext(), "Empleado Ingresado: " + resultado.toString(), Toast.LENGTH_LONG).show();

            db.close();

            LimpiarPantalla();
        }
    }


    private void LimpiarPantalla() {
        txtAgregarNombres.setText("");
        txtAgregarApellidos.setText("");
        txtAgregarEdad.setText("");
        txtAgregarDireccion.setText("");
        txtAgregarPuesto.setText("");
    }
}
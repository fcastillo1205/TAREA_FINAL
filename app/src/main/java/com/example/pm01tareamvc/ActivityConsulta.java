package com.example.pm01tareamvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm01tareamvc.configuracion.SQLiteConexion;
import com.example.pm01tareamvc.configuracion.transaccion;

public class ActivityConsulta extends AppCompatActivity {
    SQLiteConexion conexion;
    EditText txtConsultaId, txtConsultaNombres, txtConsultaApellidos, txtConsultaEdad, txtConsultaDireccion,txtConsultaPuesto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        conexion = new SQLiteConexion(this, transaccion.NameDatabase, null, 1);

        // Botones
        Button btnConsultaBuscar = (Button) findViewById(R.id.btnConsultaBuscar);
        Button btnConsultaEliminar = (Button) findViewById(R.id.btnConsultaEliminar);
        Button btnConsultaActualizar = (Button) findViewById(R.id.btnConsultaActualizar);

        txtConsultaId = (EditText) findViewById(R.id.txtConsultaId);
        txtConsultaNombres = (EditText) findViewById(R.id.txtConsultaNombres);
        txtConsultaApellidos = (EditText) findViewById(R.id.txtConsultaApellidos);
        txtConsultaEdad = (EditText) findViewById(R.id.txtConsultaEdad);
        txtConsultaDireccion = (EditText) findViewById(R.id.txtConsultaDireccion);
        txtConsultaPuesto = (EditText) findViewById(R.id.txtConsultaPuesto);

        btnConsultaBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar();
            }
        });

        btnConsultaActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar();
            }
        });

        btnConsultaEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar();
            }
        });


    }

    private void Actualizar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] params = {txtConsultaId.getText().toString()};

        ContentValues valores = new ContentValues();
        valores.put(transaccion.nombre, txtConsultaNombres.getText().toString());
        valores.put(transaccion.apellido, txtConsultaApellidos.getText().toString());
        valores.put(transaccion.edad, txtConsultaEdad.getText().toString());
        valores.put(transaccion.direccion, txtConsultaDireccion.getText().toString());
        valores.put(transaccion.puesto, txtConsultaPuesto.getText().toString());


        if(txtConsultaId.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de Id", Toast.LENGTH_LONG).show();
        }else if(txtConsultaNombres.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de nombres", Toast.LENGTH_LONG).show();
        }else if(txtConsultaApellidos.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de Apellidos", Toast.LENGTH_LONG).show();
        }else if(txtConsultaEdad.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de Edad", Toast.LENGTH_LONG).show();
        }else if(txtConsultaDireccion.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de Direccion", Toast.LENGTH_LONG).show();
        }else if(txtConsultaPuesto.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Debe completar el campo de puesto", Toast.LENGTH_LONG).show();
        }else{
            db.update(transaccion.tablaEmpleados, valores, transaccion.id + "=?", params);
            Toast.makeText(getApplicationContext(), "Dato actualizado", Toast.LENGTH_LONG).show();
            ClearScreen();
        }
    }

    private void Eliminar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {txtConsultaId.getText().toString()};
        String wherecond = transaccion.id + "=?";
        if(!txtConsultaId.getText().toString().isEmpty()){
            db.delete(transaccion.tablaEmpleados, wherecond, params);
            Toast.makeText(getApplicationContext(), "Dato eliminado", Toast.LENGTH_LONG).show();
            ClearScreen();
        }else{
            Toast.makeText(getApplicationContext(), "Campo de Id Vacio", Toast.LENGTH_LONG).show();
        }

    }

    private void Buscar() {
        SQLiteDatabase db = conexion.getWritableDatabase();

        /* Parametros de configuracion de la sentencia SELECT */
        String [] params = {txtConsultaId.getText().toString()}; // parametro de la busqueda
        String [] fields = {transaccion.nombre,
                transaccion.apellido,
                transaccion.edad,
                transaccion.direccion,
                transaccion.puesto
        };
        String wherecond = transaccion.id + "=?";

        try{
            Cursor cdata = db.query(transaccion.tablaEmpleados, fields, wherecond, params, null,null, null );
            cdata.moveToFirst();
            txtConsultaNombres.setText(cdata.getString(0));
            txtConsultaApellidos.setText(cdata.getString(1));
            txtConsultaEdad.setText(cdata.getString(2));
            txtConsultaDireccion.setText(cdata.getString(3));
            txtConsultaPuesto.setText(cdata.getString(4));

            Toast.makeText(getApplicationContext(), "Empleado encontrado con exito",Toast.LENGTH_LONG).show();
        } catch (Exception ex){
            ClearScreen();
            Toast.makeText(getApplicationContext(), "Empleado no encontrado",Toast.LENGTH_LONG).show();
        }
    }

    private void ClearScreen() {
        txtConsultaNombres.setText("");
        txtConsultaApellidos.setText("");
        txtConsultaEdad.setText("");
        txtConsultaDireccion.setText("");
        txtConsultaPuesto.setText("");
    }

}
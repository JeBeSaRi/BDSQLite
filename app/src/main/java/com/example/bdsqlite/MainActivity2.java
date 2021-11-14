package com.example.bdsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    //Objetos de tipo EditText de la pantalla
    private EditText txt_IDCompra, txt_NombreProducto, txt_CantidadCompra, txt_PrecioUnitario, txt_MontoTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Relacion entre los componentes logicos con los graficos
        txt_IDCompra=(EditText) findViewById(R.id.txt_IDCompra);
        txt_NombreProducto=(EditText) findViewById(R.id.txt_NombreProducto);
        txt_CantidadCompra=(EditText) findViewById(R.id.txt_CantidadCompra);
        txt_PrecioUnitario=(EditText) findViewById(R.id.txt_PrecioUnitario);
        txt_MontoTotal=(EditText) findViewById(R.id.txt_MontoTotal);
    }

    //Metodos para los botones
    //Para agregar componentes a la tabla Prenda y conectar con la base de datos
    public void Registar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ViedkaBD"/*Nombre final de la BD*/, null,2);
        //Abrir la BD en modo lectura-escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recepcion de los valores escritos en los EditText por el usuario
        String idCompra = txt_IDCompra.getText().toString();
        String NmbPrd = txt_NombreProducto.getText().toString();
        String CntCompra = txt_CantidadCompra.getText().toString();
        String PrcUnit = txt_PrecioUnitario.getText().toString();
        String MT = txt_MontoTotal.getText().toString();

        //Metodos de verificacion
        if(!idCompra.isEmpty() && !NmbPrd.isEmpty() && !CntCompra.isEmpty() && !PrcUnit.isEmpty() && !MT.isEmpty()){
            //Agregar un registro a la Base de Datos
            ContentValues registroPrenda = new ContentValues();
            //Referenciar los valores locales de las columnas con los valores reales de la tabla de la BD
            registroPrenda.put("idCompra", idCompra);
            registroPrenda.put("NombreProd",NmbPrd);
            registroPrenda.put("Cantidad",CntCompra);
            registroPrenda.put("PrecioUnitario",PrcUnit);
            registroPrenda.put("MontoTotal",MT);

            //Guardar valores dentro de la Base de Datos
            BaseDeDatos.insert("Compra", null, registroPrenda);
            //Cerrar base de datos despues de la transaccion
            BaseDeDatos.close();

            //Limpar campos de la interfaz
            txt_IDCompra.setText("");
            txt_NombreProducto.setText("");
            txt_CantidadCompra.setText("");
            txt_PrecioUnitario.setText("");
            txt_MontoTotal.setText("");

            //Mensaje de registro Exitoso
            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

        }else/*Mensaje de Excepcion*/{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para consultar
    //Para consultar componentes a la tabla Prenda y conectar con la base de datos
    public void Consultar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ViedkaBD"/*Nombre final de la BD*/, null,2);
        //Abrir la BD en modo lectura-escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recepcion de los valores escritos en los EditText por el usuario
        String idCompra = txt_IDCompra.getText().toString();

        //Metodos de verificacion que ID no este vacia
        if(!idCompra.isEmpty() ){
            //Aplicar un select a la Base de Datos
            Cursor fila = BaseDeDatos.rawQuery
                    ("select NombreProd,Cantidad,PrecioUnitario,MontoTotal from Compra where idCompra ="+idCompra, null);
            //Metodo para verificar si existe o no el elemento en la tabla
            if(fila.moveToFirst()){
                txt_NombreProducto.setText(fila.getString(0));
                txt_CantidadCompra.setText(fila.getString(1));
                txt_PrecioUnitario.setText(fila.getString(2));
                txt_MontoTotal.setText(fila.getString(3));

                //Cerrar Base de Datos
                BaseDeDatos.close();
            }else {
                Toast.makeText(this, "Esta Compra no existe", Toast.LENGTH_SHORT).show();
                //Cerrar Base de Datos
                BaseDeDatos.close();
            }
            //Mensaje de registro Exitoso
            Toast.makeText(this, "Registro Encontrado con Exito", Toast.LENGTH_SHORT).show();

        }else/*Mensaje de Excepcion*/{
            Toast.makeText(this, "Debes introducir el ID de la Compra", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para Eliminar
    //Para eliminar componentes a la tabla Prenda y conectar con la base de datos
    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ViedkaBD"/*Nombre final de la BD*/, null,2);
        //Abrir la BD en modo lectura-escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recepcion de los valores escritos en los EditText por el usuario
        String idCompra = txt_IDCompra.getText().toString();

        //Metodos de verificacion que ID no este vacia
        if(!idCompra.isEmpty() ){
            //Aplicar un delete a la Base de Datos
            int cantidad = BaseDeDatos.delete("Compra", "idCompra ="+idCompra, null);

            //Cerrar Base de Datos
            BaseDeDatos.close();

            //Limpar campos de la interfaz
            txt_IDCompra.setText("");
            txt_NombreProducto.setText("");
            txt_CantidadCompra.setText("");
            txt_PrecioUnitario.setText("");
            txt_MontoTotal.setText("");

            if(cantidad==1){
                Toast.makeText(this, "La Compra a sido eliminada Exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "La Compra no existe", Toast.LENGTH_SHORT).show();
            }
        }else/*Mensaje de Excepcion*/{
            Toast.makeText(this, "Debes introducir el ID de la Compra", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para Modificar
    //Para modificar componentes a la tabla Prenda y conectar con la base de datos
    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ViedkaBD"/*Nombre final de la BD*/, null,2);
        //Abrir la BD en modo lectura-escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recepcion de los valores escritos en los EditText por el usuario
        String idCompra = txt_IDCompra.getText().toString();
        String NmbPrd = txt_NombreProducto.getText().toString();
        String CntCompra = txt_CantidadCompra.getText().toString();
        String PrcUnit = txt_PrecioUnitario.getText().toString();
        String MT = txt_MontoTotal.getText().toString();

        //Metodos de verificacion que ID u otro valor no este vacia
        if
        (!idCompra.isEmpty() && !NmbPrd.isEmpty() && !CntCompra.isEmpty() && !PrcUnit.isEmpty() && !MT.isEmpty()){
            //Agregar un registro modificado a la Base de Datos
            ContentValues registroPrenda = new ContentValues();
            //Referenciar los valores locales de las columnas con los valores reales de la tabla de la BD
            registroPrenda.put("idCompra", idCompra);
            registroPrenda.put("NombreProd",NmbPrd);
            registroPrenda.put("Cantidad",CntCompra);
            registroPrenda.put("PrecioUnitario",PrcUnit);
            registroPrenda.put("MontoTotal",MT);

            //Aqui se realiza la actualizacion
            int cantidad = BaseDeDatos.update("Prendas", registroPrenda, "idCompra="+idCompra, null);
            BaseDeDatos.close();

            //Limpar campos de la interfaz
            txt_IDCompra.setText("");
            txt_NombreProducto.setText("");
            txt_CantidadCompra.setText("");
            txt_PrecioUnitario.setText("");
            txt_MontoTotal.setText("");

            if(cantidad==1){
                Toast.makeText(this, "La Compra a sido Actualizada Exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "La Compra que desea actualizar NO existe", Toast.LENGTH_SHORT).show();
            }

        }else/*Mensaje de Excepcion*/{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void PantallaPrendas (View view){
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }
}
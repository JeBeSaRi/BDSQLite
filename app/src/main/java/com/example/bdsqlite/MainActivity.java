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

public class MainActivity extends AppCompatActivity {

    //Objetos de tipo EditText de la pantalla
    private EditText txt_IDPrenda, txt_Nombre, txt_Descripcion, txt_Categoria, txt_Cantidad, txt_Precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relacion entre los componentes logicos con los graficos
        txt_IDPrenda=(EditText) findViewById(R.id.txt_IDCompra);
        txt_Nombre=(EditText) findViewById(R.id.txt_NombreProducto);
        txt_Descripcion=(EditText) findViewById(R.id.txt_Descripcion);
        txt_Categoria=(EditText) findViewById(R.id.txt_Categoria);
        txt_Cantidad=(EditText) findViewById(R.id.txt_CantidadCompra);
        txt_Precio=(EditText) findViewById(R.id.txt_PrecioUnitario);
    }

    //Metodos para los botones
    //Para agregar componentes a la tabla Prenda y conectar con la base de datos
    public void Registar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ViedkaBD"/*Nombre final de la BD*/, null,2);
        //Abrir la BD en modo lectura-escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recepcion de los valores escritos en los EditText por el usuario
        String idPrenda = txt_IDPrenda.getText().toString();
        String NmbP = txt_Nombre.getText().toString();
        String DescP = txt_Descripcion.getText().toString();
        String CtgP = txt_Categoria.getText().toString();
        String CtnP = txt_Cantidad.getText().toString();
        String PrcP = txt_Precio.getText().toString();

        //Metodos de verificacion
        if(!idPrenda.isEmpty() && !NmbP.isEmpty() && !DescP.isEmpty() && !CtgP.isEmpty() && !CtnP.isEmpty() && !PrcP.isEmpty()){
            //Agregar un registro a la Base de Datos
            ContentValues registroPrenda = new ContentValues();
            //Referenciar los valores locales de las columnas con los valores reales de la tabla de la BD
            registroPrenda.put("idPrenda", idPrenda);
            registroPrenda.put("Nombre",NmbP);
            registroPrenda.put("Descripcion",DescP);
            registroPrenda.put("Categoria",CtgP);
            registroPrenda.put("Cantidad",CtnP);
            registroPrenda.put("Precio",PrcP);

            //Guardar valores dentro de la Base de Datos
            BaseDeDatos.insert("Prendas", null, registroPrenda);
            //Cerrar base de datos despues de la transaccion
            BaseDeDatos.close();

            //Limpar campos de la interfaz
            txt_IDPrenda.setText("");
            txt_Nombre.setText("");
            txt_Descripcion.setText("");
            txt_Categoria.setText("");
            txt_Cantidad.setText("");
            txt_Precio.setText("");

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
        String idPrenda = txt_IDPrenda.getText().toString();

        //Metodos de verificacion que ID no este vacia
        if(!idPrenda.isEmpty() ){
            //Aplicar un select a la Base de Datos
            Cursor fila = BaseDeDatos.rawQuery
                    ("select Nombre,Descripcion,Categoria,Cantidad,Precio from Prendas where idPrenda ="+idPrenda, null);
            //Metodo para verificar si existe o no el elemento en la tabla
            if(fila.moveToFirst()){
                txt_Nombre.setText(fila.getString(0));
                txt_Descripcion.setText(fila.getString(1));
                txt_Categoria.setText(fila.getString(2));
                txt_Cantidad.setText(fila.getString(3));
                txt_Precio.setText(fila.getString(4));

                //Cerrar Base de Datos
                BaseDeDatos.close();
            }else {
                Toast.makeText(this, "Esta Prenda no existe", Toast.LENGTH_SHORT).show();
                //Cerrar Base de Datos
                BaseDeDatos.close();
            }
            //Mensaje de registro Exitoso
            Toast.makeText(this, "Registro Encontrado con Exito", Toast.LENGTH_SHORT).show();

        }else/*Mensaje de Excepcion*/{
            Toast.makeText(this, "Debes introducir el ID del Articulo", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para Eliminar
    //Para eliminar componentes a la tabla Prenda y conectar con la base de datos
    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ViedkaBD"/*Nombre final de la BD*/, null,2);
        //Abrir la BD en modo lectura-escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recepcion de los valores escritos en los EditText por el usuario
        String idPrenda = txt_IDPrenda.getText().toString();

        //Metodos de verificacion que ID no este vacia
        if(!idPrenda.isEmpty() ){
            //Aplicar un delete a la Base de Datos
            int cantidad = BaseDeDatos.delete("Prendas", "idPrenda ="+idPrenda, null);

            //Cerrar Base de Datos
            BaseDeDatos.close();

            //Limpar campos de la interfaz
            txt_IDPrenda.setText("");
            txt_Nombre.setText("");
            txt_Descripcion.setText("");
            txt_Categoria.setText("");
            txt_Cantidad.setText("");
            txt_Precio.setText("");

            if(cantidad==1){
                Toast.makeText(this, "La Prenda a sido eliminada Exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "La Prenda no existe", Toast.LENGTH_SHORT).show();
            }
        }else/*Mensaje de Excepcion*/{
            Toast.makeText(this, "Debes introducir el ID de la Prenda", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para Modificar
    //Para modificar componentes a la tabla Prenda y conectar con la base de datos
    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "ViedkaBD"/*Nombre final de la BD*/, null,2);
        //Abrir la BD en modo lectura-escritura
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        //Recepcion de los valores escritos en los EditText por el usuario
        String idPrenda = txt_IDPrenda.getText().toString();
        String NmbP = txt_Nombre.getText().toString();
        String DescP = txt_Descripcion.getText().toString();
        String CtgP = txt_Categoria.getText().toString();
        String CtnP = txt_Cantidad.getText().toString();
        String PrcP = txt_Precio.getText().toString();

        //Metodos de verificacion que ID u otro valor no este vacia
        if
        (!idPrenda.isEmpty() && !NmbP.isEmpty() && !DescP.isEmpty() && !CtgP.isEmpty() && !CtnP.isEmpty() && !PrcP.isEmpty()){
            //Agregar un registro modificado a la Base de Datos
            ContentValues registroPrenda = new ContentValues();
            //Referenciar los valores locales de las columnas con los valores reales de la tabla de la BD
            registroPrenda.put("idPrenda", idPrenda);
            registroPrenda.put("Nombre",NmbP);
            registroPrenda.put("Descripcion",DescP);
            registroPrenda.put("Categoria",CtgP);
            registroPrenda.put("Cantidad",CtnP);
            registroPrenda.put("Precio",PrcP);

            //Aqui se realiza la actualizacion
            int cantidad = BaseDeDatos.update("Prendas", registroPrenda, "idPrenda="+idPrenda, null);
            BaseDeDatos.close();

            //Limpar campos de la interfaz
            txt_IDPrenda.setText("");
            txt_Nombre.setText("");
            txt_Descripcion.setText("");
            txt_Categoria.setText("");
            txt_Cantidad.setText("");
            txt_Precio.setText("");

            if(cantidad==1){
                Toast.makeText(this, "La Prenda a sido Actualizada Exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "La Prenda que desea actualizar NO existe", Toast.LENGTH_SHORT).show();
            }

        }else/*Mensaje de Excepcion*/{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void PantallaCompra (View view){
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}
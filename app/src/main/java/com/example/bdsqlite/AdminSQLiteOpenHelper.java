package com.example.bdsqlite;
//clase OpenHelper
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//SQLiteOpenHelper genera los metodos onCreate onUpgrade y AdminSQLiteOpenHelper
public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos/*Nombre de la Base de Datos*/) {
        //Creacion de la tabla prendas en la BaseDeDatos
        BaseDeDatos.execSQL("create table Prendas"/*Nombre de la table*/ +
                "(idPrenda int primary key autoincrement not null unique, " +
                "Nombre text not null,Descripcion text," +
                "Categoria text, Cantidad int not null, " +
                "Precio real not null)");/*Columnas de la tabla*/

        BaseDeDatos.execSQL("create table Compra"/*Nombre de la table*/ +
                "(idCompra int primary key autoincrement not null unique, " +
                "NombreProd text not null,Descripcion text," +
                "Cantidad int not null, " +
                "PrecioUnitario real not null," +
                "MontoTotal real not null)");/*Columnas de la tabla*/

        BaseDeDatos.execSQL("create table Compra_Prendas"/*Nombre de la table*/ +
                "(Compra_idCompra int references Compra (idCompra) ON DELETE RESTRICT ON UPDATE RESTRICT NOT NULL," +
                "Prendas_idPrenda int references Prendas (idPrenda) ON DELETE RESTRICT ON UPDATE RESTRICT NOT NULL," +
                "PRIMARY KEY (Compra_idCompra, Prendas_idPrenda))"); /*Columnas de la tabla*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

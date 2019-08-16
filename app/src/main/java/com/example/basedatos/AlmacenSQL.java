package com.example.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class AlmacenSQL extends SQLiteOpenHelper
{
    private SQLiteDatabase db;
    Context contexto;

    public AlmacenSQL(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, nombre, factory, version);
        contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sentenciaCreacion = "CREATE TABLE almacen (id INTEGER, nombre TEXT);";
        db.execSQL(sentenciaCreacion);
        this.db = db;

        CharSequence text = "Se ha creado la base de datos";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(contexto, text, duration);
        toast.show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sentenciaDestruccion = "DROP TABLE IF EXISTS almacen";
        db.execSQL(sentenciaDestruccion);
    }
}

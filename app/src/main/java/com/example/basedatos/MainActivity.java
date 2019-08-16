package com.example.basedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private AlmacenSQL almacen;
    private int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        almacen = new AlmacenSQL(this, "BaseDatos", null, 1);
        indice = obtenerUltimoIndice();
        consultarBD();

        final EditText consulta = (EditText)findViewById(R.id.CuadroEntrada);

        Button insertar = (Button)findViewById(R.id.Insertar);
        insertar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                CharSequence text = "Se ha pulsado insertar";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();

                String nombreProducto = consulta.getText().toString();
                insertarProducto(nombreProducto);
                consultarBD();
                consulta.setText("");
            }

        });

        Button borrar = (Button)findViewById(R.id.Borrar);
        borrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CharSequence text = "Se ha pulsado borrar";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();

                String nombreProducto = consulta.getText().toString();
                borrarProducto(nombreProducto);
                consultarBD();;
                consulta.setText("");
            }
        });


    }

    public void insertarProducto(String nombreProducto)
    {
        String sentenciaCreacion = "INSERT INTO almacen (id, nombre) VALUES (" + indice + ", '" + nombreProducto + "');";

        if(!nombreProducto.equals(""))
        {
            db = almacen.getWritableDatabase();
            db.execSQL(sentenciaCreacion);
            db.close();
            indice++;
        }

    }


    public void borrarProducto(String nombreProducto)
    {
        String sentenciaBorrado = "DELETE FROM almacen WHERE (nombre='" + nombreProducto + "');";

        db = almacen.getWritableDatabase();
        db.execSQL(sentenciaBorrado);
        db.close();
    }


    public ArrayList<ArrayList<String>> getListadoCompletoAlmacen()
    {
        db = almacen.getWritableDatabase();

        String[] CAMPOS = { "id", "nombre"};

        ArrayList<ArrayList<String>> resultadoConsulta = new ArrayList<ArrayList<String>>();

        Cursor cursor = db.query("almacen", CAMPOS, null, null, null, null, null, null);

        int i = 0;

        if(cursor.getCount() > 0)
            while (cursor.moveToNext())
            {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(cursor.getString(0));
                temp.add(cursor.getString(1));
                resultadoConsulta.add(temp);
            }

        db.close();;

        return resultadoConsulta;

    }



    public int obtenerUltimoIndice()
    {
        db = almacen.getWritableDatabase();

        String[] CAMPOS = { "id", "nombre"};
        int indiceDevuelto = -1;

        Cursor cursor = db.query("almacen", null, "id=(SELECT id from almacen order by id desc limit 1)", null, null, null, null);

        int i = 0;

        if(cursor.getCount() > 0)
            while (cursor.moveToNext())
            {
                indiceDevuelto = cursor.getInt(0);
            }

        db.close();

            CharSequence text = "El último índice es: " + indiceDevuelto;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(MainActivity.this, text, duration);
            toast.show();

            return indiceDevuelto + 1;

    }


    public void consultarBD()
    {
        db = almacen.getWritableDatabase();

        EditText multiline = (EditText)findViewById(R.id.CuadroSalidas);
        multiline.setFocusable(false);

        ArrayList<ArrayList<String>> resultados = new ArrayList<ArrayList<String>>();
        resultados = getListadoCompletoAlmacen();

        multiline.setText("");

        for(int i = 0; i < resultados.size(); i++)
        {
            multiline.append("id: " + resultados.get(i).get(0) + ", nombre: " + resultados.get(i).get(1) + "\n");
        }

        db.close();
    }








}

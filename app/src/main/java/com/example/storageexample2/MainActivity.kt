package com.example.storageexample2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

// Almacenamiento Interno
import java.io.File
import java.io.FileOutputStream

//Almacenamiento Externo
import android.os.Environment
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import kotlin.text.StringBuilder

//Alamacenamiento Cache
import  android.content.Context

class MainActivity : AppCompatActivity() {
    // Almacenamiento Interno
    val nombreArchivoAI = "miarchivoAI.txt"
    val datosAI = "Contenido del archivo en almacenamiento interno."

    // Almacenamiento Externo
    val nombreArchivoAE = "miarchivoAE.txt"
    val datosAE = "Contenido del archivo en almacenamiento externo."


    // Almacenamiento Cache
    val clave = "Clave"
    val valor = "Mi valor de cache"

    // Almacenamiento SQLite
    val databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Almacenamiento Interno
        //escribirDatosAlmacenamientoInterno(nombreArchivoAI, datosAI)

        // Almacenamiento Externo
        //escribirDatosAlmacenamientoExterno(nombreArchivoAE, datosAE)

        // Almacenamiento Cache
        //escribirDatosAlmacenamientoCache(this, clave,valor)

        // Almacenamiento SQLite
        databaseHelper.addContact("Claudia", "4681032200")

    }


    override fun onResume() {
        super.onResume()

        // Almacenamiento Interno
        //val contenido = leerDatosAlmacenamientoInterno(nombreArchivoAI)
        //Toast.makeText(this,contenido,Toast.LENGTH_LONG).show()

        // Almacenamiento Externo
        //val contenido = leerDatosAlmacenamientoExterno(nombreArchivoAE)
        //Toast.makeText(this,contenido,Toast.LENGTH_LONG).show()

        // Almacenamiento Cache
        //val contenido = leerDatosAlmacenamientoCache(this, clave)
        //Toast.makeText(this,contenido,Toast.LENGTH_LONG).show()

        //Almacenamiento SQLite
        val contenido = databaseHelper.getAllContacts()
        for (contact in contenido) {
            Toast.makeText(this, "ID: ${contact.id}, Name: ${contact.name}, Phone: ${contact.phone}",Toast.LENGTH_LONG).show()
        }
    }

    // Almacenamiento Interno
          fun escribirDatosAlmacenamientoInterno(nombreArchivo: String, datos: String) {
          val archivo = File(this.filesDir, nombreArchivo)
          archivo.writeText(datos)
     }
         fun  leerDatosAlmacenamientoInterno(nombreArchivo: String): String {
         val archivo = File(this.filesDir, nombreArchivo)
          return archivo.readText()
    }

    //Almacenamiento Externo

    private fun escribirDatosAlmacenamientoExterno(nombreArchivo: String, datos: String) {
        val estado = isExternalStorageWriteable()
        if (estado) {
            val directorio = getExternalFilesDir(null)
            val archivo = File(directorio, nombreArchivo)
            try {
                FileOutputStream(archivo).use {
                    it.write(datosAE.toByteArray())
                }
           }  catch (e: IOException)  {
                e.printStackTrace()
            }
        }
    }


    private fun leerDatosAlmacenamientoExterno(nombreArchivo: String): String {
        val estado = isExternalStorageReadable()
        if (estado) {
            val directorio = getExternalFilesDir(null)
            val archivo = File(directorio, nombreArchivo)
            var fileInputStream = FileInputStream(archivo)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            fileInputStream.close()

            return stringBuilder.toString()
        }
         return ""
    }

    //Almacenamiento Externo
    private fun  isExternalStorageWriteable(): Boolean {
        val estado = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == estado
    }

    private fun isExternalStorageReadable(): Boolean {
        val estado = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == estado || Environment.MEDIA_MOUNTED_READ_ONLY == estado
    }


    // Almacenamiento Cache
    fun escribirDatosAlmacenamientoCache(context: Context, clave: String, valor: String) {
       val sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE)
       val editor = sharedPreferences.edit()
       editor.putString(clave, valor)
       editor.apply()
    }

    fun leerDatosAlmacenamientoCache(context: Context, clave: String): String? {
     val sharedPreferences =context.getSharedPreferences("cache",Context.MODE_PRIVATE)
     return sharedPreferences.getString(clave, null)

     }
}
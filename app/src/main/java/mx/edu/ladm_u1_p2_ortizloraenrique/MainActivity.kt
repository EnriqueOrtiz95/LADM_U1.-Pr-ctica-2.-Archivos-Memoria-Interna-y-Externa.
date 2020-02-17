package mx.edu.ladm_u1_p2_ortizloraenrique

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    //private static final String FILE_NAME = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Guardar.setOnClickListener {

            if (radInt.isChecked) {
                guardarArchivoInterno() }
            if (radSD.isChecked) {
                guardarArchivoSD() }
        }

        Abrir.setOnClickListener {
            if (radInt.isChecked){
                leerArchivoInterno() }
            if (radInt.isChecked){
                leerArchivoSD() }
        }

    }


    private fun guardarArchivoInterno() {
        try {
            var flujoSalida =
                OutputStreamWriter(openFileOutput(txtFile.text.toString(), Context.MODE_PRIVATE))
            var data = txtFrase.text.toString()
            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("Archivo interno almacenado")
            asignarText("")
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }
    }

    fun leerArchivoInterno() {
        try {
            var flujoEnntrada = BufferedReader(InputStreamReader(openFileInput(
                txtFile.text.toString())))
            var data = flujoEnntrada.readLine()
            var vector = data.split("&")
            asignarText(vector[0])
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }finally {
            mensaje("Proceso terminado")
        }
    }

    fun leerArchivoSD() {
        if (noSD()) {
            return
        }
        try {
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath, txtFile.text.toString())
            var flujoEnntrada = BufferedReader(InputStreamReader(FileInputStream(datosArchivo)))
            var data = flujoEnntrada.readLine()
            var vector = data.split("&")
            asignarText(vector[0])
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }finally {
            mensaje("Proceso terminado")
        }
    }
    fun guardarArchivoSD() {
        if (noSD()) {
            mensaje("No tienes archivos en memoria")
            return
        }
        try {
            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath, txtFile.text.toString())
            var flujoSalida = OutputStreamWriter(FileOutputStream(datosArchivo))
            var data = txtFrase.text.toString()
            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("Archivo almacenado")
            asignarText("")
        } catch (error: IOException) {
            mensaje(error.message.toString())
        }
    }

    fun mensaje(msg: String) {
        AlertDialog.Builder(this).setTitle("ATENCION").setMessage(msg)
            .setPositiveButton("OK") { d, i ->
            }.show()
    }
    fun noSD(): Boolean {
        var estado = Environment.getExternalStorageState()
        if (estado != Environment.MEDIA_MOUNTED) {
            return true }
        else{
            return false }}

    fun asignarText(t1: String) {
        txtFrase.setText(t1)
    }
}

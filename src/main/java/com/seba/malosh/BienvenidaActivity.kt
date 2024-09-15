package com.seba.malosh

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction

class BienvenidaActivity : AppCompatActivity() {

    // Lista de hábitos registrados
    private val registeredHabits = ArrayList<String>()

    // Referencias a los elementos UI que se ocultarán
    private lateinit var logoImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        // Configurar la Toolbar como ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Menú"

        // Referenciar los elementos que se van a ocultar
        logoImageView = findViewById(R.id.logoImageView)
        titleTextView = findViewById(R.id.titleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
    }

    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_navegacion, menu)
        return true
    }

    // Manejar las acciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_registrar_habitos -> {
                // Solo permitir registrar hábitos si hay menos de 4 registrados
                if (registeredHabits.size >= 4) {
                    Toast.makeText(this, "Ya has registrado el máximo de hábitos permitidos", Toast.LENGTH_SHORT).show()
                } else {
                    ocultarElementosUI()
                    val fragment = SeleccionarHabitosFragment.newInstance(registeredHabits)
                    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                true
            }
            R.id.menu_item_metas -> {
                // Cargar el fragmento de metas con los hábitos registrados
                ocultarElementosUI()
                val fragment = MetasFragment.newInstance(registeredHabits)
                val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
                true
            }
            R.id.menu_item_logout -> {
                // Redirigir al login cuando se presione "Cerrar Sesión"
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Método para ocultar los elementos UI (logo, título, descripción)
    private fun ocultarElementosUI() {
        logoImageView.visibility = ImageView.GONE
        titleTextView.visibility = TextView.GONE
        descriptionTextView.visibility = TextView.GONE
    }

    // Método para restaurar los elementos UI (logo, título, descripción)
    fun mostrarElementosUI() {
        logoImageView.visibility = ImageView.VISIBLE
        titleTextView.visibility = TextView.VISIBLE
        descriptionTextView.visibility = TextView.VISIBLE
    }

    // Método para actualizar la lista de hábitos registrados
    fun updateRegisteredHabits(newHabits: List<String>) {
        registeredHabits.addAll(newHabits)
    }

    // Sobreescribir el comportamiento del botón "Volver"
    override fun onBackPressed() {
        // Mostrar los elementos nuevamente si se vuelve atrás
        mostrarElementosUI()
        super.onBackPressed()
    }
}

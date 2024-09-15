package com.seba.malosh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ConfirmarHabitosFragment : Fragment() {

    private lateinit var siButton: Button
    private lateinit var noButton: Button
    private lateinit var selectedHabitsTextView: TextView

    companion object {
        private const val SELECTED_HABITS_KEY = "selected_habits"

        // Método para instanciar el fragmento con los hábitos seleccionados
        fun newInstance(selectedHabits: ArrayList<String>): ConfirmarHabitosFragment {
            val fragment = ConfirmarHabitosFragment()
            val bundle = Bundle()
            bundle.putStringArrayList(SELECTED_HABITS_KEY, selectedHabits)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_confirmar_habitos, container, false)

        // Inicializar los elementos del layout
        siButton = view.findViewById(R.id.siButton)
        noButton = view.findViewById(R.id.noButton)
        selectedHabitsTextView = view.findViewById(R.id.selectedHabitsTextView)

        // Mostrar los hábitos seleccionados en el TextView
        val selectedHabits = arguments?.getStringArrayList(SELECTED_HABITS_KEY)
        selectedHabitsTextView.text = selectedHabits?.joinToString(separator = "\n")

        // Acción para el botón "Sí"
        siButton.setOnClickListener {
            Toast.makeText(context, "Malos hábitos registrados exitosamente", Toast.LENGTH_SHORT).show()

            // Actualizar la lista de hábitos en la actividad principal
            (requireActivity() as BienvenidaActivity).updateRegisteredHabits(selectedHabits ?: emptyList())

            // Redirigir al menú principal
            requireActivity().supportFragmentManager.popBackStack(null, 1)
        }

        // Acción para el botón "No"
        noButton.setOnClickListener {
            // Volver al fragmento de selección de hábitos
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}

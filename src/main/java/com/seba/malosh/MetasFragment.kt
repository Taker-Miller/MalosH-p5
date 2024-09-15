package com.seba.malosh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

class MetasFragment : Fragment() {

    private lateinit var volverButton: Button
    private lateinit var definirMetasButton: Button
    private lateinit var habitosLayout: LinearLayout
    private var registeredHabits: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_metas, container, false)

        volverButton = view.findViewById(R.id.volverButton)
        definirMetasButton = view.findViewById(R.id.definirMetasButton)
        habitosLayout = view.findViewById(R.id.habitosLayout)

        registeredHabits = arguments?.getStringArrayList("registeredHabits") ?: arrayListOf()

        if (registeredHabits.isEmpty()) {
            Toast.makeText(context, "Aún no tienes malos hábitos registrados.", Toast.LENGTH_SHORT).show()
        } else {
            registeredHabits.forEach { habito ->
                val checkBox = CheckBox(context)
                checkBox.text = habito
                habitosLayout.addView(checkBox)
            }
        }

        definirMetasButton.setOnClickListener {
            val selectedHabits = mutableListOf<String>()

            for (i in 0 until habitosLayout.childCount) {
                val child = habitosLayout.getChildAt(i)
                if (child is CheckBox && child.isChecked) {
                    selectedHabits.add(child.text.toString())
                }
            }

            if (selectedHabits.isEmpty()) {
                Toast.makeText(context, "Selecciona al menos un mal hábito para definir una meta.", Toast.LENGTH_SHORT).show()
            } else if (selectedHabits.size > 2) {
                Toast.makeText(context, "Solo puedes seleccionar hasta 2 malos hábitos.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Metas definidas para los hábitos seleccionados.", Toast.LENGTH_SHORT).show()
            }
        }

        volverButton.setOnClickListener {
            (activity as? BienvenidaActivity)?.mostrarElementosUI()
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

    companion object {
        fun newInstance(registeredHabits: ArrayList<String>): MetasFragment {
            val fragment = MetasFragment()
            val args = Bundle()
            args.putStringArrayList("registeredHabits", registeredHabits)
            fragment.arguments = args
            return fragment
        }
    }
}

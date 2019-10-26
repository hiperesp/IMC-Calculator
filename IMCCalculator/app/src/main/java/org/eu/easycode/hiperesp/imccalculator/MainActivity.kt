package org.eu.easycode.hiperesp.imccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

class MainActivity : AppCompatActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private val sexoListItems = arrayOf("Masculino", "Feminino")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        addSpinnerItems()
        addEvents()
    }
    private fun addSpinnerItems(){
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexoListItems)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spnSexo!!.adapter = aa
    }
    private fun addEvents() {
        skbPeso.setOnSeekBarChangeListener(this)
        skbAltura.setOnSeekBarChangeListener(this)
        btnCalcular.setOnClickListener(this)
    }
    private fun updateTxvValues() {
        txvPesoValue.text = ""+skbPeso.progress
        txvAlturaValue.text = ""+skbAltura.progress.toDouble()/100
    }
    private fun getImc(ehHomem:Boolean, peso:Int, altura:Double): String {
        val imc = peso/(altura.pow(2.0))
        var classe = ""
            if(imc.isNaN()||imc==Double.POSITIVE_INFINITY) return "Incalculável"
            if(imc<18.5)      classe = "Magreza"
            else if(imc<25.0) classe = "Normal"
            else if(imc<30.0) classe = "Sobrepreso"
            else if(imc<40.0) classe = "Obesidade"
            else /*        */ classe = "Obesidade Grave"
        return "de "+imc+" (classe: "+classe+")"
    }

    override fun onClick(p0: View?) {
        val imcValue = getImc(spnSexo.selectedItem==sexoListItems[0], skbPeso.progress, skbAltura.progress.toDouble()/100)

        val builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle("Cálculo do IMC")
        builder.setMessage("O IMC para uma pessoa do sexo "+spnSexo.selectedItem+", com "+skbPeso.progress+"kg e "+skbAltura.progress+"cm é "+imcValue+".")
        builder.setPositiveButton("OK"){a, b -> {}}
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        updateTxvValues()
    }
    override fun onStartTrackingTouch(seekBar: SeekBar) {
        updateTxvValues()
    }
    override fun onStopTrackingTouch(seekBar: SeekBar) {
        updateTxvValues()
    }
}

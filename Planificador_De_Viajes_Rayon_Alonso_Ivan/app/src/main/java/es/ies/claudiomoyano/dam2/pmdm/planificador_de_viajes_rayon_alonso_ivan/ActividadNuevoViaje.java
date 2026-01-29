package es.ies.claudiomoyano.dam2.pmdm.planificador_de_viajes_rayon_alonso_ivan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class ActividadNuevoViaje extends AppCompatActivity {

    private static final int CODIGO_PERMISO_CAMARA = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_nuevo_viaje);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // --- Referencias a componentes del formulario ---
        Switch swRecordatorio = findViewById(R.id.sw_recordatorio);
        SeekBar skbPresupuesto = findViewById(R.id.skb_presupuesto);
        RatingBar rtbValoracion = findViewById(R.id.rtb_valoracion);
        Spinner spnTipoActividad = findViewById(R.id.spn_tipo_actividad);
        EditText edtFechaSalida = findViewById(R.id.edt_fecha_salida);
        EditText edtHoraSalida = findViewById(R.id.edt_hora_salida);
        EditText edtTituloViaje = findViewById(R.id.edt_titulo_viaje);
        EditText edtDescripcion = findViewById(R.id.edt_descripcion_viaje);
        Button btnGuardarViaje = findViewById(R.id.btn_guardar_viaje);
        Button btnSeleccionarImagen = findViewById(R.id.btn_seleccionar_imagen);
        TextView txtPresupuestoValor = findViewById(R.id.txt_presupuesto_valor);

        // Texto inicial del presupuesto
        txtPresupuestoValor.setText("Presupuesto: 0€");

        // --- Spinner (tipo de viaje) ---
        ArrayAdapter<CharSequence> adaptadorSpinner =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.tipos_viaje_array,
                        android.R.layout.simple_spinner_item);

        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoActividad.setAdapter(adaptadorSpinner);

        spnTipoActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipoSeleccionado = parent.getItemAtPosition(position).toString();
                // Aquí se podría guardar el tipoSeleccionado para usarlo al guardar el viaje
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se selecciona nada
            }
        });

        // --- Selector de FECHA (DatePickerDialog) ---
        edtFechaSalida.setFocusable(false);
        edtFechaSalida.setClickable(true);

        edtFechaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendario = Calendar.getInstance();
                int anio = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ActividadNuevoViaje.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String fechaSeleccionada = String.format("%02d/%02d/%04d",
                                        dayOfMonth, month + 1, year);
                                edtFechaSalida.setText(fechaSeleccionada);
                            }
                        },
                        anio, mes, dia);

                datePickerDialog.show();
            }
        });

        // --- Selector de HORA (TimePickerDialog) ---
        edtHoraSalida.setFocusable(false);
        edtHoraSalida.setClickable(true);

        edtHoraSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendario = Calendar.getInstance();
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minuto = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ActividadNuevoViaje.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                                edtHoraSalida.setText(horaSeleccionada);
                            }
                        },
                        hora, minuto, true);

                timePickerDialog.show();
            }
        });

        // --- Listener del Switch ---
        swRecordatorio.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // acción cuando se activa/desactiva el recordatorio
                    }
                });

        // --- Listener del SeekBar (presupuesto) ---
        skbPresupuesto.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        txtPresupuestoValor.setText("Presupuesto: " + progress + "€");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // acción al empezar a mover la barra
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // acción al soltar la barra
                    }
                });

        // --- Listener del RatingBar ---
        rtbValoracion.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        // acción al cambiar la valoración
                    }
                });

        // --- Botón GUARDAR: devolver datos a MainActivity ---
        btnGuardarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = edtTituloViaje.getText().toString();
                String fecha = edtFechaSalida.getText().toString();
                String descripcion = edtDescripcion.getText().toString();

                Intent datos = new Intent();
                datos.putExtra("titulo", titulo);
                datos.putExtra("fecha", fecha);
                datos.putExtra("descripcion", descripcion);

                setResult(RESULT_OK, datos);
                finish();
            }
        });

        // --- Botón SELECCIONAR IMAGEN: solicita permiso de CÁMARA ---
        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(
                        ActividadNuevoViaje.this,
                        android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                            ActividadNuevoViaje.this,
                            new String[]{android.Manifest.permission.CAMERA},
                            CODIGO_PERMISO_CAMARA);
                } else {
                    // Aquí se podría abrir la cámara o la galería
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODIGO_PERMISO_CAMARA) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido
            } else {
                // Permiso denegado
            }
        }
    }
}
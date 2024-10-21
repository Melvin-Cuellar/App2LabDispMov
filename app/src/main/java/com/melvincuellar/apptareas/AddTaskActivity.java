package com.melvincuellar.apptareas;

import static com.melvincuellar.apptareas.MainActivity.lstTareas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    Button btnFecha, btnHora, btnGuardar;
    EditText edtTitulo, edtDescripcion, edtFecha, edtHora;
    int position = -1; // Para verificar si es una tarea nueva o se quiere modificar una existente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnFecha = findViewById(R.id.btnFecha);
        btnHora = findViewById(R.id.btnHora);
        btnGuardar = findViewById(R.id.btnGuardar);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtFecha = findViewById(R.id.edtFecha);
        edtHora = findViewById(R.id.edtHora);

        // Verificar si es una tarea existente que se va a modificar
        if (getIntent().hasExtra("position")) {
            position = getIntent().getIntExtra("position", -1);
            edtTitulo.setText(getIntent().getStringExtra("titulo"));
            edtDescripcion.setText(getIntent().getStringExtra("descripcion"));
            edtFecha.setText(getIntent().getStringExtra("fecha"));
            edtHora.setText(getIntent().getStringExtra("hora"));
        }

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtener la hora actual
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                        String SelecHora = String.format(Locale.getDefault(), "%02d:%02d", hourofDay, minute);
                        edtHora.setText(SelecHora);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtener la hora actual
                final Calendar c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String FechaSelec = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        edtFecha.setText(FechaSelec);
                    }

                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(validarCampos()){
                    Tareas tarea = new Tareas();
                    tarea.setTitulo(edtTitulo.getText().toString());
                    tarea.setDescripcion(edtDescripcion.getText().toString());
                    tarea.setFecha(edtFecha.getText().toString());
                    tarea.setHora(edtHora.getText().toString());

                    if (position == -1) {
                        // Es una nueva tarea
                        lstTareas.add(tarea);
                    } else {
                        // Es una tarea existente que ha sido modificada
                        lstTareas.set(position, tarea);
                    }
                    finish();
                }
            }
        });
    }

    private boolean validarCampos() {
        String titulo = edtTitulo.getText().toString().trim();
        String descripcion = edtDescripcion.getText().toString().trim();
        String fecha = edtFecha.getText().toString().trim();
        String hora = edtHora.getText().toString().trim();

        if (titulo.isEmpty()) {
            edtTitulo.setError("Campo obligatorio");
            return false;
        }
        if (descripcion.isEmpty()) {
            edtDescripcion.setError("Campo obligatorio");
            return false;
        }
        if (fecha.isEmpty()) {
            edtFecha.setError("Campo obligatorio");
            return false;
        }
        if (hora.isEmpty()) {
            edtHora.setError("Campo obligatorio");
            return false;
        }
        return true;
    }
}
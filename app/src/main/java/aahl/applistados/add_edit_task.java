package aahl.applistados;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import aahl.applistados.Enums.TaskState;
import aahl.applistados.Models.TaskElement;
import aahl.applistados.Utils.TaskStateParser;

public class add_edit_task extends AppCompatActivity {

    private TextView labelTitle;
    private TextView labelErrorTituloTask;
    private TextView labelErrorDescripcionTask;
    private TextInputEditText textInputNombreTask;
    private TextInputEditText textInputDescripcionTask;
    private Button btnGuardarTask;
    private Button btnBorrarTask;
    private Space[] spaces;
    private Spinner spinnerTaskState;
    private int position = -1; // Default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        labelTitle = findViewById(R.id.LabelTituloAddEdit);
        textInputNombreTask = findViewById(R.id.textInputNombreTask);
        textInputDescripcionTask = findViewById(R.id.textInputDescripcionTask);
        btnGuardarTask = findViewById(R.id.btnGuardarTask);
        btnBorrarTask = findViewById(R.id.btnBorrarTask);
        labelErrorTituloTask = findViewById(R.id.LabelErrorTituloTask);
        labelErrorDescripcionTask = findViewById(R.id.LabelErrorDescriptionTask);

        spinnerTaskState = findViewById(R.id.spinnerTaskState);
        setupInicialSpinner();

        spaces = new Space[]{
                findViewById(R.id.spaceInicioBtns),
                findViewById(R.id.spaceMidBtns),
                findViewById(R.id.spaceFinalBtns)
        };

        if (getIntent().hasExtra("TaskElement")) {
            TaskElement task = getIntent().getSerializableExtra("TaskElement", TaskElement.class);
            position = getIntent().getIntExtra("Position", -1);

            labelTitle.setText("Editar Tarea");
            textInputNombreTask.setText(task.getName());
            textInputDescripcionTask.setText(task.getDescription());
            spinnerTaskState.setSelection(TaskStateParser.getIndex(task.getCurrentState()));

            for (Space space : spaces) {
                space.setVisibility(View.VISIBLE);
            }
            btnGuardarTask.setText("Actualizar");
            btnBorrarTask.setVisibility(View.VISIBLE);
            setTitle("Editar Tarea");
        } else {
            labelTitle.setText("Agregar Tarea");
            setTitle("Agregar Tarea");
        }

        btnGuardarTask.setOnClickListener(v -> guardarTask());
        btnBorrarTask.setOnClickListener(v -> borrarTask());
    }

    private void setupInicialSpinner() {
        String[] statusLabels = {
                TaskStateParser.parseStateToString(TaskState.PENDING),
                TaskStateParser.parseStateToString(TaskState.IN_PROGRESS),
                TaskStateParser.parseStateToString(TaskState.COMPLETED)
        };

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusLabels);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskState.setAdapter(spinnerAdapter);
    }

    private void guardarTask() {
        String taskName = textInputNombreTask.getText().toString().trim();
        String taskDescription = textInputDescripcionTask.getText().toString().trim();
        TaskState spinnerResult = TaskStateParser.reverseParsingState(spinnerTaskState.getSelectedItem().toString());

        if (taskName.isEmpty()) {
            labelErrorTituloTask.setVisibility(View.VISIBLE);
        } else {
            labelErrorTituloTask.setVisibility(View.GONE);
        }

        if (taskDescription.isEmpty()){
            labelErrorDescripcionTask.setVisibility(View.VISIBLE);
        } else {
            labelErrorDescripcionTask.setVisibility(View.GONE);
        }

        if (taskName.isEmpty() || taskDescription.isEmpty()) {
            return;
        }

        TaskElement nuevaTask = new TaskElement(taskName, taskDescription, spinnerResult);
        Intent res = new Intent();
        res.putExtra("TaskElement", nuevaTask);
        res.putExtra("Position", position);
        setResult(AppCompatActivity.RESULT_OK, res);
        finish();
    }

    private void borrarTask() {
        Intent res = new Intent();
        res.putExtra("Position", position);
        res.putExtra("Borrar", true);

        setResult(AppCompatActivity.RESULT_OK, res);
        finish();
    }
}
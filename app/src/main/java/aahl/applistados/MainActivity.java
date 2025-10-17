package aahl.applistados;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import aahl.applistados.Models.TaskElement;
import aahl.applistados.Utils.TaskAdapter;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TaskElement> Tasks = new ArrayList<>();
    private TaskAdapter adapter;
    private ActivityResultLauncher<Intent> addEditTaskLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        FloatingActionButton floatBtnAdd = findViewById(R.id.floatBtnAddTask);

        configuracionInicialDelRecyclerView(recyclerView);

        addEditTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::procesarResultadoDelLauncher
        );

        floatBtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, add_edit_task.class);
            addEditTaskLauncher.launch(intent);
        });

    }

    private void configuracionInicialDelRecyclerView(RecyclerView recyclerView) {
        adapter = new TaskAdapter(Tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration divisorDeElementos = new DividerItemDecoration(
                recyclerView.getContext(),
                LinearLayoutManager.VERTICAL
        );
        recyclerView.addItemDecoration(divisorDeElementos);

        adapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, add_edit_task.class);
            intent.putExtra("TaskElement", Tasks.get(position));
            intent.putExtra("Position", position);
            addEditTaskLauncher.launch(intent);
        });
    }

    private void procesarResultadoDelLauncher(ActivityResult result){
        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
            Intent data = result.getData();

            if (data.getBooleanExtra("Borrar", false)) {
                int positionToDelete = data.getIntExtra("Position", -1);
                if (positionToDelete != -1) {
                    Tasks.remove(positionToDelete);
                    adapter.notifyDataSetChanged();
                    showToast("Tarea borrada");
                }
                return;
            }

            TaskElement taskElement = data.getSerializableExtra("TaskElement", TaskElement.class);
            int position = data.getIntExtra("Position", -1);

            if (position == -1) {
                Tasks.add(taskElement);
                showToast("Tarea guardada");
            } else {
                Tasks.set(position, taskElement);
                showToast("Tarea actualizada");
            }

            adapter.notifyDataSetChanged();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
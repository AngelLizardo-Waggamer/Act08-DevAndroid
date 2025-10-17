package aahl.applistados.Utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aahl.applistados.Enums.TaskState;
import aahl.applistados.Models.TaskElement;
import aahl.applistados.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<TaskElement> taskList;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TaskAdapter(List<TaskElement> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView, listener);
    }

    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position){
        TaskElement currentTask = taskList.get(position);
        holder.taskName.setText(currentTask.getName());
        holder.taskStatus.setText(TaskStateParser.parseStateToString(currentTask.getCurrentState()));
        holder.taskDescription.setText(currentTask.getDescription());

        if (currentTask.getCurrentState() == TaskState.PENDING){
            // TODO: Buscar un mejor color para representar que la tarea está pendiente
            holder.taskIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.Red)));
        } else if (currentTask.getCurrentState() == TaskState.IN_PROGRESS){
            // TODO: Lo mismo pero para las que están en proceso
            holder.taskIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.Old_gold)));
        } else {
            // TODO: Lo mismo para las que ya se terminaron
            holder.taskIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.Finished_Green)));
        }
    }

    public int getItemCount(){
        return taskList.size();
    }

}

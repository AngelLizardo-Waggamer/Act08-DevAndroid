package aahl.applistados.Utils;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import aahl.applistados.R;

public class TaskViewHolder extends RecyclerView.ViewHolder{
    public TextView taskName;
    public TextView taskStatus;
    public TextView taskDescription;
    public Button btnDetallesTask;
    public ImageView taskIcon;

    public TaskViewHolder(@NonNull View itemView, final OnItemClickListener listener){
        super(itemView);
        taskName = itemView.findViewById(R.id.textViewTitleTask);
        taskStatus = itemView.findViewById(R.id.textViewTaskStatus);
        taskDescription = itemView.findViewById(R.id.textViewDescriptionTask);
        btnDetallesTask = itemView.findViewById(R.id.btnDetallesTask);
        taskIcon = itemView.findViewById(R.id.iconItemTask);

        btnDetallesTask.setOnClickListener(v -> {
            if (listener != null) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(position);
                }
            }
        });
    }
}

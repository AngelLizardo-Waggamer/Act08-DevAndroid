package aahl.applistados.Models;

import java.io.Serializable;

import aahl.applistados.Enums.TaskState;

public class TaskElement implements Serializable {

    private String name;
    private String description;
    private TaskState currentState;

    // Constructor default sin especificar el estado actual del Task
    public TaskElement(String name, String description) {
        this.name = name;
        this.description = description;
        this.currentState = TaskState.PENDING;
    }

    // Constructor para cuando se quiere especificar un estado actual del Task
    public TaskElement(String name, String description, TaskState state) {
        this.name = name;
        this.description = description;
        this.currentState = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TaskState currentState) {
        this.currentState = currentState;
    }
}

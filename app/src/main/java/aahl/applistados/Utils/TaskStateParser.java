package aahl.applistados.Utils;

import aahl.applistados.Enums.TaskState;

public class TaskStateParser {

    static public String parseStateToString(TaskState state) {
        switch (state) {
            case PENDING:
                return "Pendiente";
            case IN_PROGRESS:
                return "En proceso";
            case COMPLETED:
                return "Completada";
            default:
                return "Desconocido";
        }
    }

    static public TaskState reverseParsingState(String stateLabel){
        switch(stateLabel){
            case "Pendiente":
                return TaskState.PENDING;
            case "En proceso":
                return TaskState.IN_PROGRESS;
            case "Completada":
                return TaskState.COMPLETED;
            default:
                return TaskState.PENDING;
        }
    }

    static public int getIndex(TaskState state){
        switch (state) {
            case PENDING:
                return 0;
            case IN_PROGRESS:
                return 1;
            case COMPLETED:
                return 2;
            default:
                return 0;
        }
    }

}

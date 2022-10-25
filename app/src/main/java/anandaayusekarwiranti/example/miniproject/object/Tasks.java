package anandaayusekarwiranti.example.miniproject.object;


public class Tasks {
    private int idTask;
    private String taskName;

    public Tasks(int idTask, String taskName) {
        this.idTask = idTask;
        this.taskName = taskName;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}

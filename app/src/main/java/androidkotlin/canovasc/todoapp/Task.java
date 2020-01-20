package androidkotlin.canovasc.todoapp;


import java.io.Serializable;

public class Task implements Serializable {

    private String taskName;
    public String filename;

    Task(String taskName){
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


}



public class Task {

    private String task;
    private boolean done;

    public Task(String task) {
        this.task=task;
        done = false;
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public void setDone(boolean done) {
        this.done = done;
    }
    
}

import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> taskList = new ArrayList<Task>();

    private TaskManager() {

    }
    private static TaskManager instance = null;

    public static TaskManager getInstance() {
        if(instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public ArrayList<Task> getTaskList () { return taskList ; }

    public void addToList(Task tasK , ArrayList<Task> taskArrList) {
        taskArrList.add(tasK);
    }

    public void removeFromList(Task taSk , ArrayList<Task> taskArrList) {
        taskArrList.remove(taSk);
    }

}

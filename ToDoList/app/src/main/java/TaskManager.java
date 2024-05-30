import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.ObservableList;

public class TaskManager {

    //arraylist to store the task objects
    private ArrayList<Task> taskList = new ArrayList<Task>();

    //singleton creational design pattern is used
    //there will be only one TaskManager object
    private TaskManager() {

    }
    private static TaskManager instance = null;

    public static TaskManager getInstance() {
        if(instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    //get method to reach the taskList
    public ArrayList<Task> getTaskList () { return taskList ; }

    //add-remove-edit-check methods
    public void addToList(String task,String description,String date,String time,String importance,boolean done) {
        Task taskObject = new Task(task, description, date, time, importance, done);
        instance.getTaskList().add(taskObject);
    }
    public void removeFromList(Task taskObject) {
        instance.getTaskList().remove(taskObject);
        instance.getTaskList().trimToSize();
    }
    public void editList(Task taskObject ,String task,String description,String date,String time) {

        if(task!=null)taskObject.setTask(task);
        else taskObject.setTask(task);
        if(description!=null)taskObject.setDescription(description);
        else taskObject.setDescription(description);
        if(date!=null)taskObject.setDate(date);
        else taskObject.setDate("01/01/0001");
        if(time!=null)taskObject.setTime(time);
        else taskObject.setTime("00:00");

        taskObject.updateAllInfo();
    }

    //seach method
    public ArrayList<Task> searchTheList(String search) {
        search = search.toLowerCase();
        ArrayList<String> searchStr = new ArrayList<String>();
        for (String element : search.split("[ :]+")) {
            searchStr.add(element);
        }
        ArrayList<Task> searchList = new ArrayList<Task>();
        for (String selement : searchStr) {
            for (Task element : taskList) {
                if (element.getAllInfo().contains(selement)){
                    if(!searchList.contains(element)) {searchList.add(element);}
                }
            }
        }
        return searchList;
    }

    //listing methods 
    //list by time
//Bzoulursa eskisi
    public ArrayList<Task> listByTime(){
        Collections.sort(taskList, new TaskComparator());
        return taskList;
    }

    //list by importance
    public ArrayList<Task> listByImportance(){
        Collections.sort(taskList, new TaskImportance());
        return taskList;
    }

    //list by undone
    public ArrayList<Task> listByDone(){
        Collections.sort(taskList, new TaskDone());
        return taskList;
    }

    public ObservableList<Task> listByTimeO(ObservableList<Task> tasks){
        Collections.sort(tasks, new TaskComparator());
        return tasks;
    }

    //list by importance
    public ObservableList<Task> listByImportanceO(ObservableList<Task> tasks){
        Collections.sort(tasks, new TaskImportance());
        return tasks;
    }

    //list by undone
    public ObservableList<Task> listByDoneO(ObservableList<Task> tasks){
        Collections.sort(tasks, new TaskDone());
        return tasks;
    }
}

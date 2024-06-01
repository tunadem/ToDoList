import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;


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

    //search method
    public ArrayList<Task> searchTheList(String search) {
        search = search.toLowerCase();
        ArrayList<String> searchStr = new ArrayList<String>();
        for (String element : search.split("[ :]+")) {
            searchStr.add(element);
        }
        ArrayList<Task> searchList = new ArrayList<Task>();
        for (String selement : searchStr) {
            for (Task element : taskList) {
                if(element.getAllInfo()!=null){
                    if (element.getAllInfo().contains(selement)){
                        if(!searchList.contains(element)) {searchList.add(element);}
                    }
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
    private List<Task> tasks;

    // Constructor, getters, setters, and other methods...

    /*public void loadTasksFromJson(String filePath) {
        Gson gson = new GsonBuilder().create();
        try (FileReader reader = new FileReader(filePath)) {
            Task[] taskArray = gson.fromJson(reader, Task[].class);
            tasks = new ArrayList<>(Arrays.asList(taskArray));
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }*/
    public void loadTasksFromJson() {
        // Specify the file path relative to the project directory or use an absolute path
        String jsonFileName = "tasks.json";
        String filePath = Paths.get("TDL/json", jsonFileName).toString();
        File file = new File(filePath);
    
        if (!file.exists()) {
            // If the file doesn't exist, create it with an empty JSON array
            writeEmptyJsonArray(filePath);
        }
    
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            Type type = new TypeToken<List<Task>>(){}.getType();
            List<Task> taskList = new Gson().fromJson(reader, type);
            instance.getTaskList().addAll(taskList);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
    
    public void writeEmptyJsonArray(String filePath) {
        // Create directories if they don't exist
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
        }
    
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write("[]");
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
    public void writeTasksToJson(ArrayList<Task> tasks) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
        // Determine the file path relative to the current working directory
        String jsonFileName = "/tasks.json";
        String filePath = Paths.get("TDL/json", jsonFileName).toString();
    
        // Create directories if they don't exist
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
        }
    
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
    public void writeEmptyJsonArray() {
        // Determine the file path relative to the build directory
        String jsonFileName = "tasks.json";
        String filePath = Paths.get("TDL/json", jsonFileName).toString();
    
        // Create directories if they don't exist
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // Create parent directories if they don't exist
        }
    
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write("[]");
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
}

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Task {

    //necesarry variables
    private String task;
    private String description;
    private String date;
    private String time;
    private String importance;
    private boolean done;
    //for search purpose
    private ArrayList<String> allInfo = new ArrayList<String>();

    //object constructor
    public Task(String task,String description,String date,String time,String importance,boolean done) {
        this.task=task;
        this.description = description;
        this.date=date;
        this.time=time;
        this.importance=importance;
        this.done=done;
    }

    //get-set methods
    public String getTask() {
        return task;
    }
    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getImportance() {
        return importance;
    }
    public String getTime() {
        return time;
    }
    public Boolean getDone() {
        return done;
    }
    public ArrayList<String> getAllInfo() {
        return allInfo;
    }

    public void setTask(String task) {
        this.task = task;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImportance(String importance) {
        this.importance = importance;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    //this method will used when seaching 
    public void updateAllInfo() {
        for (String element : task.split("[ .]+")) {
            allInfo.add(element);
        }
        for (String element : description.split("[ .]+")) {
            allInfo.add(element);
        }
        allInfo.add(date);
        allInfo.add(time);
    }

    // Method to parse date and time into a single Date object for easy comparison
    public Date getDateTime() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH.mm");
        return dateFormat.parse(date + " " + time);
    }
    @Override
    public String toString() {
        return "Task{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", importance='" + importance + '\'' +
                '}';
    }
    
}

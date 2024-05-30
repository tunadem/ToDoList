import java.util.Comparator;
import java.util.Date;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        try {
            Date dateTime1 = t1.getDateTime();
            Date dateTime2 = t2.getDateTime();
            return dateTime1.compareTo(dateTime2);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date or time format", e);
        }
    }
}

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TaskDone implements Comparator<Task> {
    private static final Map<Boolean , Integer> importanceMap = new HashMap<>();

    static {
        importanceMap.put(true, 2);
        importanceMap.put(false, 1);
    }

    @Override
    public int compare(Task t1, Task t2) {
        try {
                int importance1 = importanceMap.get(t1.getDone());
                int importance2 = importanceMap.get(t2.getDone());
                return Integer.compare(importance1, importance2);
            
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
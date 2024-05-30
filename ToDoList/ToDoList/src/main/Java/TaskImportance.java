import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TaskImportance implements Comparator<Task> {
    private static final Map<String, Integer> importanceMap = new HashMap<>();

    static {
        importanceMap.put("lesser", 1);
        importanceMap.put("middle", 2);
        importanceMap.put("greater", 3);
    }

    @Override
    public int compare(Task t1, Task t2) {
        try {
                int importance1 = importanceMap.get(t1.getImportance());
                int importance2 = importanceMap.get(t2.getImportance());
                return Integer.compare(importance1, importance2);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date or time format", e);
        }
    }
}
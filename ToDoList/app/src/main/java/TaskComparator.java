import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        try {
            Date dateTime1 = t1.getDateTime();
            Date dateTime2 = t2.getDateTime();
            return dateTime1.compareTo(dateTime2);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date or time format" + e);
        }
    }
    
    public static String dateValidation(String input){
        if (input != null){// Define the regex pattern to match numbers and "/"
            Pattern pattern = Pattern.compile("[0-9/]+");
            Matcher matcher = pattern.matcher(input);

            StringBuilder result = new StringBuilder();

            // Find all matches and append to the result, keeping track of "/"
            while (matcher.find()) {
                String match = matcher.group();
                result.append(match);
            }
            String output = result.toString();
            String outputreturn = processString(output);
            return outputreturn;
        }
        return " ";
    }
    public static String timeValidation(String input){
        // Define the regex pattern to match numbers and ":"
        Pattern pattern = Pattern.compile("[0-9:]+");
        Matcher matcher = pattern.matcher(input);

        StringBuilder result = new StringBuilder();
        int colonCount = 0;

        // Find all matches and append to the result, keeping track of ":"
        while (matcher.find()) {
            String match = matcher.group();
            for (char c : match.toCharArray()) {
                if (Character.isDigit(c)) {
                    result.append(c);
                } else if (c == ':') {
                    if (colonCount < 1) {
                        result.append(c);
                        colonCount++;
                    }
                }
            }
        }
        String output = result.toString();
        String outputreturn = processStringTime(output);
        return outputreturn;
        //return processStringTime(output);
    }

    private static String processString(String input) {

        // Split the input by "/"
        try {
            String[] parts = input.split("/");
                    // Process each part according to the requirements
            String part1 = parts[0];
            if(Integer.valueOf(part1) > 31) part1 = "31";
            if(Integer.valueOf(part1) < 1) part1 = "01";
            String part2 = parts[1];
            if(Integer.valueOf(part2) > 12) part2 = "12";
            if(Integer.valueOf(part2) < 1) part2 = "01";
            String part3 = parts[2];
            if(Integer.valueOf(part3) > 9999) part3 = "9999";
            if(Integer.valueOf(part3) < 1) part3 = "0001";
            return part1 + "/" + part2 + "/" + part3;
        }
        catch (Exception e) {
            return null;
        }


    }

    private static String processStringTime(String input) {

        try {
            String[] parts = input.split(":");
            
            // Process each part to extract the first two digits
            String part1 = parts[0];
            if(Integer.valueOf(part1) > 23) part1 = "23";
            String part2 = parts[1];
            if(Integer.valueOf(part2) > 59) part2 = "59";

        return part1 + ":" + part2;
        }
        catch (Exception e) {
            return null;
        }


    }

}

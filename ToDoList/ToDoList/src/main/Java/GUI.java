

public class GUI {
    public static void main(String[] args) {
        TaskManager tM = TaskManager.getInstance();
        tM.addToList(null, null, null, null, null, true);
        tM.addToList("ce223", null, null, null, null, true);
        tM.addToList("ce216", "quiz", null, null, null, false);
        tM.addToList("eng210", "project", "21/02/2023", null, null, false);
        tM.addToList("ger202", "quiz", "21/02/2023", "22.00", null, false);
        tM.addToList("ieu100", "online quiz", "21/02/2023", "23.00", "lesser", true);
        tM.addToList("phy100", "online quiz", "21/02/2022", "21.00", "greater", true);


        for (Task element : tM.searchTheList("")) {
            System.out.println(element.toString());
        }
    }
    
}

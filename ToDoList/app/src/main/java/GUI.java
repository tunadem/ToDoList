import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.sql.rowset.spi.SyncResolver;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.ToolBar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class GUI extends Application {

    private TaskManager tm = TaskManager.getInstance();
    private Task selectedTask;
    private String AddImpCombo;
    private ObservableList<Task> tasks = FXCollections.observableArrayList();
    private ObservableList<Task> searchedTasks = FXCollections.observableArrayList();

    // root pane
    private HBox hRootBox = new HBox();

    // Vbox panes
    private VBox leftPane = new VBox();
    private VBox rightPane = new VBox();
    private VBox upperRightPane = new VBox();
    private VBox lowerRightPane = new VBox();

    // Hbox
    private HBox taskBox = new HBox(8);
    private HBox descriptionBox = new HBox(8);
    private HBox dateBox = new HBox(8);
    private HBox timeBox = new HBox(8);
    private HBox importanceBox = new HBox(8);
    private HBox buttonBox = new HBox(180);
    private HBox searchBox = new HBox(8);
    private HBox taskBoxAdd = new HBox(8);
    private HBox descriptionBoxAdd = new HBox(8);
    private HBox dateBoxAdd = new HBox(8);
    private HBox timeBoxAdd = new HBox(8);
    private HBox importanceBoxAdd = new HBox(8);

    // ListView for tasks
    private ListView<Task> listView = new ListView<>();

    // Buttons
    private Button searchButton = new Button("Search");
    private Button addButton = new Button("Add");
    private Button editButton = new Button("Edit");
    private Button deleteButton = new Button("Delete");
    private Button helpButton = new Button("Help");
    private Button doneButton = new Button("Done");

    // Labels
    private Label sortLabel = new Label("Sort: ");
    private Label taskLabel = new Label("Task: ");
    private Label descriptionLabel = new Label("Description: ");
    private Label dateLabel = new Label("Date: ");
    private Label timeLabel = new Label("Time: ");
    private Label importanceLabel = new Label("Importance: ");
    private Label sortLabelAdd = new Label("Sort: ");
    private Label taskLabelAdd = new Label("Task: ");
    private Label descriptionLabelAdd = new Label("Description: ");
    private Label dateLabelAdd = new Label("Date: ");
    private Label timeLabelAdd = new Label("Time: ");
    private Label importanceLabelAdd = new Label("Importance: ");

    // TextFields
    private TextField searchBar = new TextField();
    private TextField taskTextField = new TextField();
    private TextField descriptionTextField = new TextField();
    private TextField dateTextField = new TextField();
    private TextField timeTextField = new TextField();
    private TextField searchBarAdd = new TextField();
    private TextField taskTextFieldAdd = new TextField();
    private TextField descriptionTextFieldAdd = new TextField();
    private TextField dateTextFieldAdd = new TextField();
    private TextField timeTextFieldAdd = new TextField();

    // Combobox
    private ComboBox<String> ImportanceCombo = new ComboBox<>();
    private ComboBox<String> ImportanceComboAdd = new ComboBox<>();
    private ComboBox<String> SortCombo = new ComboBox<>();

    @Override
    public void start(Stage primaryStage) {
        
        // Takes data from Gson and writes back
        tm.addToList(null, null, null, null, null, true);
        tm.addToList("ce223", null, null, null, null, true);
        tm.addToList("ce214", "quiz", null, null, null, false);
        tm.addToList("eng210", "project", "21/02/2023", null, null, false);
        tm.addToList("ger202", "quiz", "21/02/2023", "22:00", null, false);
        tm.addToList("ieu100", "online quiz", "21/02/2023", "23:00", "Low", true);
        tm.addToList("phy100", "online quiz", "21/02/2022", "21:00", "High", true);

        /*// root pane
        HBox hRootBox = new HBox();

        // Vbox panes
        VBox leftPane = new VBox();
        VBox rightPane = new VBox();
        VBox upperRightPane = new VBox();
        VBox lowerRightPane = new VBox();

        // Hbox
        HBox taskBox = new HBox(8);
        HBox descriptionBox = new HBox(8);
        HBox dateBox = new HBox(8);
        HBox timeBox = new HBox(8);
        HBox importanceBox = new HBox(8);
        HBox buttonBox = new HBox(180);
        HBox searchBox = new HBox(8);

        // pane split horizontally*/
        leftPane.setPrefWidth(400);
        rightPane.setPrefWidth(300);

       /*  // ListView for tasks
        ListView<Task> listView = new ListView<>();

        // Buttons
        Button searchButton = new Button("Search");
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button helpButton = new Button("Help");
        Button doneButton = new Button("Done");

        // Labels
        Label sortLabel = new Label("Sort: ");
        Label taskLabel = new Label("Task: ");
        Label descriptionLabel = new Label("Description: ");
        Label dateLabel = new Label("Date: ");
        Label timeLabel = new Label("Time: ");
        Label importanceLabel = new Label("Importance: ");

        // TextFields
        TextField searchBar = new TextField();
        TextField taskTextField = new TextField();
        TextField descriptionTextField = new TextField();
        TextField dateTextField = new TextField();
        TextField timeTextField = new TextField();

        // Combobox
        ComboBox<String> ImportanceCombo = new ComboBox<>();
        ComboBox<String> SortCombo = new ComboBox<>();*/

        // Stage for adding tasks
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setTitle("Add Task");

        // Constructing tasks 
        for (Task element : tm.getTaskList()) {
            tasks.add(element);
        }
        tm.listByDoneO(tasks);
        listView.setItems(tasks);
        listView.setCellFactory(param -> new TaskCell());


        
        


        searchBar.setPromptText("Search...");

        ImportanceCombo.getItems().addAll("Low", "Middle", "High");
        ImportanceComboAdd.getItems().addAll("Low", "Middle", "High");
        SortCombo.getItems().addAll("Time", "Importance", "Undone");
        
        ImportanceCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if(selectedTask!=null)selectedTask.setImportance(newValue);
            }
        });
        ImportanceComboAdd.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                AddImpCombo = newValue;
            }
        });
        
        SortCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Time")) {
                listView.getItems().clear();
                listView.setItems(tasks);
                tm.listByTimeO(tasks);
                listView.setItems(tasks);
                listView.setCellFactory(param -> new TaskCell());
            }
            if (newValue.equals("Importance")) {
                listView.getItems().clear();
                listView.setItems(tasks);
                tm.listByImportanceO(tasks);
                listView.setItems(tasks);
                listView.setCellFactory(param -> new TaskCell());
            }
            if (newValue.equals("Undone")) {
                listView.getItems().clear();
                listView.setItems(tasks);
                tm.listByDoneO(tasks);
                listView.setItems(tasks);
                listView.setCellFactory(param -> new TaskCell());
            }
        });
        //




        // Font sizes for Label and TextFields
        sortLabel.setStyle("-fx-font-size: 16px;");
        searchButton.setStyle("-fx-font-size: 12px;");
        searchBar.setStyle("-fx-font-size: 12px;");
        taskLabel.setStyle("-fx-font-size: 16px;");
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        dateLabel.setStyle("-fx-font-size: 14px;");
        timeLabel.setStyle("-fx-font-size: 14px;");
        importanceLabel.setStyle("-fx-font-size: 14px;");
        taskTextField.setStyle("-fx-font-size: 14px;");
        descriptionTextField.setStyle("-fx-font-size: 12px;");
        dateTextField.setStyle("-fx-font-size: 12px;");
        timeTextField.setStyle("-fx-font-size: 12px;");

        // Setting TextFields editable
        taskTextField.setEditable(false);
        descriptionTextField.setEditable(false);
        dateTextField.setEditable(false);
        timeTextField.setEditable(false);





        // Set on Actions
        editButton.setOnAction(event -> {
            if ("Edit".equals(editButton.getText())) {
                // Make the TextField editable and change button text to "Done"
                
                taskTextField.setEditable(true);
                descriptionTextField.setEditable(true);
                dateTextField.setEditable(true);
                timeTextField.setEditable(true);
                editButton.setText("Done");

            } else {
                // Call methodA, make the TextField not editable, and change button text to "Edit"
                tm.editList(selectedTask, taskTextField.getText(), descriptionTextField.getText(), TaskComparator.dateValidation(dateTextField.getText()), TaskComparator.timeValidation(timeTextField.getText()));
                
                taskTextField.setEditable(false);
                descriptionTextField.setEditable(false);
                dateTextField.setEditable(false);
                timeTextField.setEditable(false);
                editButton.setText("Edit");
            }
        });

        deleteButton.setOnAction(event -> {
            tm.removeFromList(selectedTask);
            tasks.remove(selectedTask);
        });

        searchButton.setOnAction(event -> {
            if(!searchBar.getText().equals("")) {
                searchedTasks.clear();
                for (Task element : tm.searchTheList(searchBar.getText())) {
                    searchedTasks.add(element);
                }
                tm.listByDoneO(searchedTasks);
                listView.setItems(searchedTasks);
        
            } else {
                listView.setItems(tasks);
            }
        });
        addButton.setOnAction(event -> {

            taskTextFieldAdd.setEditable(true);
            descriptionTextFieldAdd.setEditable(true);
            dateTextFieldAdd.setEditable(true);
            timeTextFieldAdd.setEditable(true);

            VBox adding = new VBox(10);
            adding.getChildren().addAll(taskBoxAdd,descriptionBoxAdd,dateBoxAdd,timeBoxAdd,importanceBoxAdd,doneButton);
            Scene addPopUp = new Scene(adding,300,400);
            dialog.setScene(addPopUp);
            dialog.show();

        });
        doneButton.setOnAction(event -> {
            tm.addToList(taskTextFieldAdd.getText(), descriptionTextFieldAdd.getText(), TaskComparator.dateValidation(dateTextFieldAdd.getText()), TaskComparator.timeValidation(timeTextFieldAdd.getText()),AddImpCombo,false);
            tasks.clear();
            for (Task element : tm.getTaskList()) {
                tasks.add(element);
            }
            tm.listByDoneO(tasks);
            listView.setItems(tasks);
            dialog.close();
        });



        // Set scene and stage

        upperRightPane.getChildren().addAll(taskBox, descriptionBox,dateBox,timeBox,importanceBox,buttonBox);
        upperRightPane.setSpacing(15);

        leftPane.getChildren().addAll(searchBox,listView, addButton);
        leftPane.setSpacing(8);
        leftPane.setPadding(new Insets(0, 0, 5, 0));

        searchBox.getChildren().addAll(searchButton, searchBar,sortLabel,SortCombo);
        searchBox.setPadding(new Insets(0, 0, 0, 12));

        taskBox.getChildren().addAll(taskLabel, taskTextField);
        descriptionBox.getChildren().addAll(descriptionLabel, descriptionTextField);
        dateBox.getChildren().addAll(dateLabel, dateTextField);
        timeBox.getChildren().addAll(timeLabel, timeTextField);
        importanceBox.getChildren().addAll(importanceLabel, ImportanceCombo);
        buttonBox.getChildren().addAll(editButton, deleteButton);

        taskBoxAdd.getChildren().addAll(taskLabelAdd, taskTextFieldAdd);
        descriptionBoxAdd.getChildren().addAll(descriptionLabelAdd, descriptionTextFieldAdd);
        dateBoxAdd.getChildren().addAll(dateLabelAdd, dateTextFieldAdd);
        timeBoxAdd.getChildren().addAll(timeLabelAdd, timeTextFieldAdd);
        importanceBoxAdd.getChildren().addAll(importanceLabelAdd, ImportanceComboAdd);

        VBox.setVgrow(listView, Priority.ALWAYS);
        VBox.setVgrow(upperRightPane, Priority.ALWAYS);
        VBox.setVgrow(lowerRightPane, Priority.ALWAYS);

        HBox.setHgrow(rightPane, Priority.ALWAYS);
                

        // Add upper and lower right panes to the right pane
        rightPane.getChildren().addAll(upperRightPane, lowerRightPane);
        rightPane.setSpacing(10);

        // Add left and right panes to the hRootBox
        hRootBox.getChildren().addAll(leftPane, rightPane);
        hRootBox.setSpacing(10);

        Scene scene = new Scene(hRootBox, 700, 600);
        primaryStage.setTitle("To-Do List");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Display for the upperRightPane
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                if (newValue != null) {
                    selectedTask = newValue;
                    taskLabel.setText("Task: ");
                    descriptionLabel.setText("Description: ");
                    dateLabel.setText("Date: ");
                    timeLabel.setText("Time: ");
                    importanceLabel.setText("Importance: ");
                    taskTextField.setText(newValue.getTask());
                    descriptionTextField.setText(newValue.getDescription());
                    dateTextField.setText(newValue.getDate());
                    timeTextField.setText(newValue.getTime());
                    ImportanceCombo.setValue(newValue.getImportance());
        
                }  else {
                    // Clear the fields if no task is selected
                    taskLabel.setText("");
                    descriptionLabel.setText("");
                    dateLabel.setText("");
                    timeLabel.setText("");
                    importanceLabel.setText("");
                    taskTextField.setText("");
                    descriptionTextField.setText("");
                    dateTextField.setText("");
                    timeTextField.setText("");
                    ImportanceCombo.setValue(null);  // Clear the ComboBox selection
                }
                
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void handleCloseRequest(WindowEvent event) {
        taskTextField.setEditable(false);
        descriptionTextField.setEditable(false);
        dateTextField.setEditable(false);
        timeTextField.setEditable(false);
    }



    public static class TaskCell extends ListCell<Task> {
        private HBox content;
        private Label taskName;
        private CheckBox checkBox;

        public TaskCell() {
            super();
            taskName = new Label();
            checkBox = new CheckBox();
            checkBox.setOnAction(event -> {
                Task task = getItem();
                if (task != null) {
                    task.setDone(checkBox.isSelected());
                }
            });
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            content = new HBox(taskName,spacer, checkBox);
            taskName.setStyle("-fx-font-size: 14px;");
            content.setSpacing(10);
            HBox.setHgrow(taskName, Priority.ALWAYS);
        }

        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            if (empty || task == null) {
                setText(null);
                setGraphic(null);
            } else {
                taskName.setText(task.getTask());
                checkBox.setSelected(task.getDone());
                setGraphic(content);
            }
        }
    }
}

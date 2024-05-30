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
import javafx.stage.Stage;
import javafx.scene.control.ToolBar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class GUI extends Application {

    private Task selectedTask;

    @Override
    public void start(Stage primaryStage) {
        TaskManager tm = TaskManager.getInstance();
        tm.addToList(null, null, null, null, null, true);
        tm.addToList("ce223", null, null, null, null, true);
        tm.addToList("ce214", "quiz", null, null, null, false);
        tm.addToList("eng210", "project", "21/02/2023", null, null, false);
        tm.addToList("ger202", "quiz", "21/02/2023", "22:00", null, false);
        tm.addToList("ieu100", "online quiz", "21/02/2023", "23:00", "Low", true);
        tm.addToList("phy100", "online quiz", "21/02/2022", "21:00", "High", true);

        // Root pane
        HBox root = new HBox();

        // ListView for tasks
        ListView<Task> listView = new ListView<>();
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        ObservableList<Task> searchedTasks = FXCollections.observableArrayList();
        for (Task element : tm.getTaskList()) {
            tasks.add(element);
        }
        tm.listByDoneO(tasks);
        listView.setItems(tasks);

        listView.setCellFactory(param -> new TaskCell());

        // Left pane with search bar and add button
        VBox leftPane = new VBox();
        leftPane.setPrefWidth(400);
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-font-size: 12px;");
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search...");
        searchBar.setStyle("-fx-font-size: 12px;");
        Label sortLabel = new Label("Sort: ");
        sortLabel.setStyle("-fx-font-size: 16px;");
        ComboBox<String> sortBox = new ComboBox<>();
        sortBox.getItems().addAll("Time", "Importance", "Undone");
        //
        sortBox.valueProperty().addListener((observable, oldValue, newValue) -> {
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

        // Create an HBox to hold the label and the text field
        HBox searchBox = new HBox(8); // 5 pixels spacing between label and text field
        searchBox.getChildren().addAll(searchButton, searchBar,sortLabel,sortBox);
        // Add padding to the right of the text field to ensure it stops 10 to 8 pixels before the edge
        searchBox.setPadding(new Insets(0, 0, 0, 12)); // 10 pixels padding on the right

        // ListView for tasks

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button helpButton = new Button("Help");

        ToolBar toolBar = new ToolBar(helpButton);

        // Setting VBox grow priority for the list view to expand
        VBox.setVgrow(listView, Priority.ALWAYS);

        // Adding components to the left pane
        leftPane.getChildren().addAll(searchBox,listView, addButton);
        leftPane.setSpacing(8);
        leftPane.setPadding(new Insets(0, 0, 5, 0));



        // Right pane split horizontally
        VBox rightPane = new VBox();
        rightPane.setPrefWidth(300);
        VBox upperRightPane = new VBox();
        VBox lowerRightPane = new VBox();

        // Labels for task and description in the upper right pane
        Label taskLabel = new Label("Task:");
        taskLabel.setStyle("-fx-font-size: 16px;");
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        Label dateLabel = new Label("Date:");
        dateLabel.setStyle("-fx-font-size: 14px;");
        Label timeLabel = new Label("Time:");
        timeLabel.setStyle("-fx-font-size: 14px;");
        Label importanceLabel = new Label("Importance:");
        importanceLabel.setStyle("-fx-font-size: 14px;");
        //
        TextField taskTextField = new TextField();
        taskTextField.setEditable(false);
        taskTextField.setStyle("-fx-font-size: 14px;");
        TextField descriptionTextField = new TextField();
        descriptionTextField.setEditable(false);
        descriptionTextField.setStyle("-fx-font-size: 12px;");
        TextField dateTextField = new TextField();
        dateTextField.setEditable(false);
        dateTextField.setStyle("-fx-font-size: 12px;");
        TextField timeTextField = new TextField();
        timeTextField.setEditable(false);
        timeTextField.setStyle("-fx-font-size: 12px;");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Low", "Middle", "High");
        //
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                selectedTask.setImportance(newValue);
            }
        });
        //
        HBox taskBox = new HBox(8);
        HBox descriptionBox = new HBox(8);
        HBox dateBox = new HBox(8);
        HBox timeBox = new HBox(8);
        HBox importanceBox = new HBox(8);
        taskBox.getChildren().addAll(taskLabel, taskTextField);
        descriptionBox.getChildren().addAll(descriptionLabel, descriptionTextField);
        dateBox.getChildren().addAll(dateLabel, dateTextField);
        timeBox.getChildren().addAll(timeLabel, timeTextField);
        importanceBox.getChildren().addAll(importanceLabel, comboBox);

        HBox buttonBox = new HBox(180);
        buttonBox.getChildren().addAll(editButton, deleteButton);
                
        upperRightPane.getChildren().addAll(taskBox, descriptionBox,dateBox,timeBox,importanceBox,buttonBox);
        upperRightPane.setSpacing(15);

        // Add upper and lower right panes to the right pane
        rightPane.getChildren().addAll(upperRightPane, lowerRightPane);
        rightPane.setSpacing(10);
        VBox.setVgrow(upperRightPane, Priority.ALWAYS);
        VBox.setVgrow(lowerRightPane, Priority.ALWAYS);

        // Add left and right panes to the root
        root.getChildren().addAll(leftPane, rightPane);
        root.setSpacing(10);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

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



        // Set scene and stage
        Scene scene = new Scene(root, 700, 600);
        primaryStage.setTitle("To-Do List");
        primaryStage.setScene(scene);
        primaryStage.show();

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
                    comboBox.setValue(newValue.getImportance());
        
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
                    comboBox.setValue(null);  // Clear the ComboBox selection
                }
                
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
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

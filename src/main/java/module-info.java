module com.example.jeti_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.jeti_project to javafx.fxml;
    exports com.example.jeti_project;
}
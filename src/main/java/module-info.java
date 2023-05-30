module com.phcbest.aabtools {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.phcbest.aabtools to javafx.fxml;
    exports com.phcbest.aabtools;
}
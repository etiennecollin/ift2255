module clientGUI {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.etiennecollin.ift2255.clientGUI to javafx.fxml;
    exports com.etiennecollin.ift2255.clientGUI;
}
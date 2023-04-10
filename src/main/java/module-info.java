module Tamagoshi {
    requires javafx.controls;
    requires javafx.fxml;

    exports Tamagoshi;
    opens Tamagoshi to javafx.fxml;
}
module Saper {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires jdk.jfr;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.params;

    //exports pl.polsl.lab.saper to javafx.graphics;
    //exports pl.polsl.lab.saper.view to javafx.fxml;
    exports pl.polsl.lab.saper.controller to javafx.fxml;
    opens pl.polsl.lab.saper;
    opens pl.polsl.lab.saper.controller;
    //opens pl.polsl.lab.saper.view;
}
package com.projekt.oum_projekt;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.InputStream;

public class Jupiter extends Planet {

    public Jupiter() {
        super(1100, "/images/jupiter.jpg"); // Die Sonne hat einen Radius von 1000
    }

    public Node prepareJupiter() {
        return getPlanetSphere(); // Rückgabe der Kugel (Sonne)
    }
}

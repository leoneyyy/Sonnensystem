package com.projekt.oum_projekt;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.InputStream;

public class Erde extends Planet {
    public Erde() {
        super(100, "/images/earth.jpg"); // Die Sonne hat einen Radius von 1000
    }

    public Node prepareErde() {
        return getPlanetSphere(); // Rückgabe der Kugel (Sonne)
    }


}

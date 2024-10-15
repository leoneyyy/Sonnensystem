package com.projekt.oum_projekt;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.InputStream;

public class Mond extends Application {
    public Sphere mond = new Sphere(100);
    @Override
    public void start(Stage primaryStage) {

    }
    public Node prepareMond(){
        PhongMaterial material = new PhongMaterial();
        InputStream imageStream = getClass().getResourceAsStream("/images/moon.jpg");
        if (imageStream == null) {
            System.out.println("Bild nicht gefunden!");
            return null;

        }
        material.setDiffuseMap(new Image(imageStream));
        mond.setRotationAxis(Rotate.Y_AXIS);
        mond.setMaterial(material);
        return mond;
    }

    public Sphere getMond() {
        return mond;
    }
}

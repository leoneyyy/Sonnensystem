package com.projekt.oum_projekt;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.InputStream;

public class Erde extends Application {
    public   final Sphere erde = new Sphere(500);
    @Override
    public void start(Stage primaryStage) {
    }
    public Node prepareEarth(){
        PhongMaterial material = new PhongMaterial();

        InputStream imageStream = getClass().getResourceAsStream("/images/earth.jpg");
        if (imageStream == null) {
            System.out.println("Bild nicht gefunden!");
            return null; // oder eine Ausnahme werfen
        }

        material.setDiffuseMap(new Image(imageStream));
        erde.setRotationAxis(Rotate.Y_AXIS);
        erde.setMaterial(material);

        return erde;
    }

    public Sphere getErde() {
        return erde;
    }


}

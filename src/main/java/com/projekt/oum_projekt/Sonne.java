package com.projekt.oum_projekt;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.InputStream;

public class Sonne extends Application {

    public Sphere sonne = new Sphere(1000);

    @Override
    public void start(Stage primaryStage){

    }

    public Node prepareSonne(){
        PhongMaterial material =new PhongMaterial();

        InputStream imageStream = getClass().getResourceAsStream("/images/sun.jpg");
        if (imageStream == null) {
            System.out.println("Bild nicht gefunden!");
            return null; // oder eine Ausnahme werfen
        }

        material.setDiffuseMap(new Image(imageStream));
        sonne.setRotationAxis(Rotate.Y_AXIS);
        sonne.setMaterial(material);
        return sonne;

    }

    public Sphere getSonne() {
        return sonne;
    }
}

package com.projekt.oum_projekt;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.InputStream;

public class Merkur extends Application {
    Sphere merkur = new Sphere(350);
    @Override
    public void start(Stage primaryStage) {

    }
    public Node prepareMerkur(){
        PhongMaterial material = new PhongMaterial();

        InputStream imageStream = getClass().getResourceAsStream("/images/mercury.jpg");
        if (imageStream == null) {
            System.out.println("Bild nicht gefunden!");
            return null; // oder eine Ausnahme werfen
        }

        material.setDiffuseMap(new Image(imageStream));
        merkur.setRotationAxis(Rotate.Y_AXIS);
        merkur.setMaterial(material);

        return merkur;
    }

    public Sphere getMerkur(){
        return merkur;
    }


}

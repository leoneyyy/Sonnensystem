package com.projekt.oum_projekt;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import java.io.InputStream;

public class Saturn extends Planet {

    private final Cylinder ringe;

    public Saturn() {
        super(900, "/images/saturn.jpg"); // Saturn hat einen Radius von 400
        ringe = new Cylinder(700, 10);    // Ringe um Saturn
        PhongMaterial ringMaterial = new PhongMaterial();
        InputStream ringImageStream = getClass().getResourceAsStream("/images/saturn_rings.png");
        if (ringImageStream != null) {
            ringMaterial.setDiffuseMap(new Image(ringImageStream));
        }
        ringe.setMaterial(ringMaterial);
        ringe.setRotationAxis(Rotate.X_AXIS);
        ringe.setRotate(90); // Die Ringe horizontal drehen
    }

    public Node prepareSaturn() {
        Group saturnWithRings = new Group();
        saturnWithRings.getChildren().addAll(getPlanetSphere(), ringe); // Kugel (Saturn) + Ringe
        return saturnWithRings;
    }

}



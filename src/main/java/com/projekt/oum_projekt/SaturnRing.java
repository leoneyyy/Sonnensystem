package com.projekt.oum_projekt;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

import java.io.InputStream;

public class SaturnRing {

    private final Cylinder ring;

    public SaturnRing(double radius, double thickness, String texturePath) {

        ring = new Cylinder(radius, thickness);


        PhongMaterial material = new PhongMaterial();
        InputStream imageStream = getClass().getResourceAsStream(texturePath);
        if (imageStream != null) {
            material.setDiffuseMap(new Image(imageStream));
        } else {
            System.out.println("Bild nicht gefunden: " + texturePath);
        }

        ring.setRotationAxis(Rotate.Y_AXIS);
        ring.setRotate(90);

    }

    public Cylinder getRing() {
        return ring;
    }


}
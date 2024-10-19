package com.projekt.oum_projekt;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.io.InputStream;

public class Planet {
    protected Sphere planetSphere;
    protected PhongMaterial material;

    public Planet(double radius, String texturePath) {
        planetSphere = new Sphere(radius);
        material = new PhongMaterial();

        InputStream imageStream = getClass().getResourceAsStream(texturePath);
        if (imageStream != null) {
            material.setDiffuseMap(new Image(imageStream));
        } else {
            System.out.println("Bild nicht gefunden: " + texturePath);
        }

        planetSphere.setRotationAxis(Rotate.Y_AXIS);
        planetSphere.setMaterial(material);
    }

    public Sphere getPlanetSphere() {
        return planetSphere;
    }
}


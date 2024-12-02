package com.projekt.oum_projekt;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import java.io.InputStream;

public class Saturn extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Saturn() {
        super(1372, "/images/saturn.jpg");
    }

    public Node prepareSaturn() {
        return getPlanetSphere();
    }
}



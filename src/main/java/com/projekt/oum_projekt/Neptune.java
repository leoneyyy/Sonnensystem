package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Neptune extends Planet {
    public Neptune() {
        super(390, "/images/neptune.jpg");
    }

    public Node prepareNeptune() {
        return getPlanetSphere();
    }
}

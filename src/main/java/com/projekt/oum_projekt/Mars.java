package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Mars extends Planet {

    public Mars() {
        super(53, "/images/mars.jpg");
    }

    public Node prepareMars() {
        return getPlanetSphere();
    }
}

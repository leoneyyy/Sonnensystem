package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Uranus extends Planet {
    public Uranus() {
        super(597, "/images/Uranus.jpg");
    }

    public Node prepareUranus() {
        return getPlanetSphere();
    }
}

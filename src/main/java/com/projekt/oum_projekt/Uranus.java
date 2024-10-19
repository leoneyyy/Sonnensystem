package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Uranus extends Planet {
    public Uranus() {
        super(400, "/images/Uranus.jpg");
    }

    public Node prepareUranus() {
        return getPlanetSphere();
    }
}

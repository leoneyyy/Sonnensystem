package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Mond extends Planet {
    public Mond() {
        super(27, "/images/moon.jpg");
    }

    public Node prepareMond() {
        return getPlanetSphere();
    }
}

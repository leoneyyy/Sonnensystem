package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Jupiter extends Planet {

    public Jupiter() {
        super(1100, "/images/jupiter.jpg");
    }

    public Node prepareJupiter() {
        return getPlanetSphere();
    }
}

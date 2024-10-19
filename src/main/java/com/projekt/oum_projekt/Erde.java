package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Erde extends Planet {
    public Erde() {
        super(100, "/images/earth.jpg");
    }

    public Node prepareErde() {
        return getPlanetSphere();
    }


}

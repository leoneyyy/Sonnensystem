package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Erde extends Planet {
    public Erde() {
        super(150, "/images/earth.jpg");
    }

    public Node prepareErde() {
        return getPlanetSphere();
    }


}

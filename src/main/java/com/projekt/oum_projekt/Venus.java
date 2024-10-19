package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Venus extends Planet {

    public Venus() {
        super(95, "/images/venus.jpg");
    }

    public Node prepareVenus() {
        return getPlanetSphere();
    }
}

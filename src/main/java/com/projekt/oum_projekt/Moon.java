package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Moon extends Planet {
    public Moon() {
        super(38, "/images/moon.jpg");
    }

    public Node prepareMond() {
        return getPlanetSphere();
    }
}

package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Mars extends Planet {
    //Quelle:https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Mars() {
        super(80, "/images/mars.jpg");
    }

    public Node prepareMars() {
        return getPlanetSphere();
    }
}

package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Uranus extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Uranus() {
        super(597, "/images/Uranus.jpg");
    }

    public Node prepareUranus() {
        return getPlanetSphere();
    }
}

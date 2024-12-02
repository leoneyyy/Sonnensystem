package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Moon extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Moon() {
        super(38, "/images/moon.jpg");
    }

    public Node prepareMond() {
        return getPlanetSphere();
    }
}

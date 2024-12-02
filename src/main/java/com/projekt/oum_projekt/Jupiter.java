package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Jupiter extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Jupiter() {
        super(1646, "/images/jupiter.jpg");
    }

    public Node prepareJupiter() {
        return getPlanetSphere();
    }
}

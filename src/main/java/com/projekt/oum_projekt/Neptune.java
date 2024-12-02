package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Neptune extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Neptune() {
        super(580, "/images/neptune.jpg");
    }

    public Node prepareNeptune() {
        return getPlanetSphere();
    }
}

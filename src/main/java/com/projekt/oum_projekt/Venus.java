package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Venus extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Venus() {
        super(135, "/images/venus.jpg");
    }

    public Node prepareVenus() {
        return getPlanetSphere();
    }
}

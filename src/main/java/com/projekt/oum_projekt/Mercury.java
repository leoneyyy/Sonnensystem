package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Mercury extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Mercury() {
        super(53, "/images/mercury.jpg");
    }

    public Node prepareMerkur() {
        return getPlanetSphere();
    }


}

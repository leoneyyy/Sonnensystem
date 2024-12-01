package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Mercury extends Planet {
    public Mercury() {
        super(53, "/images/mercury.jpg");
    }

    public Node prepareMerkur() {
        return getPlanetSphere();
    }


}

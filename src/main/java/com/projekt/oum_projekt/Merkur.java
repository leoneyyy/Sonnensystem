package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Merkur extends Planet {
    public Merkur() {
        super(38, "/images/mercury.jpg");
    }

    public Node prepareMerkur() {
        return getPlanetSphere();
    }


}

package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Earth extends Planet {
    public Earth() {
        super(150, "/images/earth.jpg");
    }

    public Node prepareErde() {
        return getPlanetSphere();
    }


}

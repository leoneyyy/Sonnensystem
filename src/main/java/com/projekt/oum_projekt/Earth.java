package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Earth extends Planet {
    //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Earth() {
        super(150, "/images/earth.jpg");
    }

    public Node prepareErde() {
        return getPlanetSphere();
    }


}

package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Sun extends Planet {
    //Quelle https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
    public Sun() {
        super(16350, "/images/sun.jpg"); // Die Sonne hat einen Radius von 16350
    }

    public Node prepareSonne() {
        return getPlanetSphere(); // Rückgabe der Kugel (Sonne)
    }
}

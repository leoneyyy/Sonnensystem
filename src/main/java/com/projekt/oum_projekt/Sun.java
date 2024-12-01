package com.projekt.oum_projekt;


import javafx.scene.Node;


public class Sun extends Planet {

    public Sun() {
        super(16350, "/images/sun.jpg"); // Die Sonne hat einen Radius von 1000
    }

    public Node prepareSonne() {
        return getPlanetSphere(); // Rückgabe der Kugel (Sonne)
    }
}

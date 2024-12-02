package com.projekt.oum_projekt;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.Objects;

public class SolarSystem extends Application {
    public static double width = 0;
    public static double height = 0;

    private boolean isEarthCameraActive = false;
    private boolean isMoonCameraActive = false;
    private boolean isVenusCameraActive = false;
    private boolean isUranusCameraActive = false;
    private boolean isMercuryCameraActive = false;
    private boolean isSaturnCameraActive = false;
    private boolean isJupiterCameraActive = false;
    private boolean isNeptuneCameraActive = false;
    private boolean isMarsCameraActive = false;
    boolean isAllCameraActive = true;
    private boolean halfSpeed = false;
    private boolean quarterSpeed = false;

    public PerspectiveCamera camera;
    public PerspectiveCamera secondCamera;
    double saturnX;
    double saturnZ;
    double speed;
    SmartGroup sunGroup;
    SmartGroup earthGroup;
    SmartGroup mercuryGroup;
    SmartGroup moonGroup;
    SmartGroup venusGroup;
    SmartGroup saturnGroup;
    SmartGroup jupiterGroup;
    SmartGroup marsGroup;
    SmartGroup uranusGroup;
    SmartGroup neptuneGroup;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        //Fullscreen
        width =  Screen.getPrimary().getBounds().getWidth();
        height = Screen.getPrimary().getBounds().getHeight();

        //Kamera
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(200000);
        camera.translateZProperty().set(-100000);
        camera.setFieldOfView(60);

        //Kamera gezoomt an die Planeten
        secondCamera = new PerspectiveCamera(true);
        secondCamera.setNearClip(1);
        secondCamera.setFarClip(200000);

        //Sonne
        Sun sun = new Sun();
        sunGroup = new SmartGroup();
        sunGroup.getChildren().add(sun.prepareSonne());

        //Erde
        Earth earth = new Earth();
        earthGroup = new SmartGroup();
        earthGroup.getChildren().add(earth.prepareErde());

        //Merkur
        Mercury mercury = new Mercury();
        mercuryGroup = new SmartGroup();
        mercuryGroup.getChildren().add(mercury.prepareMerkur());

        //Mond
        Moon moon = new Moon();
        moonGroup = new SmartGroup();
        moonGroup.getChildren().add(moon.prepareMond());

        //Venus
        Venus venus  = new Venus();
        venusGroup = new SmartGroup();
        venusGroup.getChildren().add(venus.prepareVenus());

        //Saturn
        Saturn saturn = new Saturn();
        saturnGroup = new SmartGroup();
        saturnGroup.getChildren().add(saturn.prepareSaturn());

        //SaturnRing
        //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
        SaturnRing saturnRing = new SaturnRing(2046, 30, "/Images/saturn_ring.png");
        saturnGroup.getChildren().add(saturnRing.getRing());

        //Jupiter
        Jupiter jupiter = new Jupiter();
        jupiterGroup = new SmartGroup();
        jupiterGroup.getChildren().add(jupiter.prepareJupiter());

        //Mars
        Mars mars = new Mars();
        marsGroup = new SmartGroup();
        marsGroup.getChildren().add(mars.prepareMars());

        //Uranus
        Uranus uranus = new Uranus();
        uranusGroup = new SmartGroup();
        uranusGroup.getChildren().add(uranus.prepareUranus());


        //Neptune
        Neptune neptune = new Neptune();
        neptuneGroup = new SmartGroup();
        neptuneGroup.getChildren().add(neptune.prepareNeptune());


        Group root = new Group();
        root.getChildren().add(sunGroup);
        root.getChildren().add(prepareImageView());

        Text text = new Text("Interaktive Darstellung des Sonnensystems");
        text.setX(-3000);
        text.setY(-4280);
        text.setTranslateZ(-30000*3);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,250));
        text.setFill(Color.WHITE);
        Text beschreibung = new Text("Tasten um auf Planeten zu zoomen: \nErde: Taste E\nMond: Taste M\nMars: Taste A\nMerkur: Taste Y\nUranus: Taste U\nVenus: Taste V\nSaturn: Taste S\nJupiter: Taste J\nNeptun: Taste N\n\nGeschwindigkeit reduzieren:\nTaste 1:50% verlangsamen\nTaste 2: 75% verlangsamen\nZurücksetzen:Taste nochmal drücken");
        beschreibung.setX(-8000);
        beschreibung.setY(2000);
        beschreibung.setTranslateZ(-30000*3);
        beschreibung.setFont(Font.font("verdana",FontWeight.NORMAL, FontPosture.REGULAR , 130));
        beschreibung.setFill(Color.WHITE);
        sunGroup.getChildren().addAll(text, beschreibung);

        earthGroup.getChildren().add(moonGroup);
        sunGroup.getChildren().add(earthGroup);
        sunGroup.getChildren().add(mercuryGroup);
        sunGroup.getChildren().add(venusGroup);
        sunGroup.getChildren().add(saturnGroup);
        sunGroup.getChildren().add(jupiterGroup);
        sunGroup.getChildren().add(marsGroup);
        sunGroup.getChildren().add(uranusGroup);
        sunGroup.getChildren().add(neptuneGroup);

        //Scene
        scene = new Scene(root, width, height, true,null);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);
        scene.setOnKeyPressed(this::handleKeyPress);
        stage.setTitle("Sonnensystem");
        stage.setScene(scene);
        stage.show();
        prepareAnimation(earth.getPlanetSphere(), moon.getPlanetSphere(), sun.getPlanetSphere(), mercury.getPlanetSphere(), venus.getPlanetSphere(),neptune.getPlanetSphere(), saturn.getPlanetSphere(),mars.getPlanetSphere(), uranus.getPlanetSphere(), jupiter.getPlanetSphere(),saturnRing.getRing());
    }

    private void prepareAnimation(Sphere erde, Sphere mond, Sphere sonne, Sphere merkur, Sphere venus, Sphere neptune, Sphere saturn , Sphere mars, Sphere uranus, Sphere jupiter, Cylinder ring) {
        AnimationTimer timer = new AnimationTimer() {
            private double earthAngle = 0;    // Winkel für die Umlaufbahn der Erde um die Sonne
            private double moonAngle = 0;     // Winkel für die Umlaufbahn des Mondes um die Erde
            private double mercuryAngle = 0;  // Winkel für die Umlaufbahn des Merkurs um die Sonne
            private double venusAngle = 0;    // Winkel für die Umlaufbahn der Venus um die Sonne
            private double marsAngle = 0;     // Winkel für die Umlaufbahn des Mars um die Sonne
            private double jupiterAngle = 0;  // Winkel für die Umlaufbahn des Jupiter um die Sonne
            private double saturnAngle = 0;   // Winkel für die Umlaufbahn des Saturns um die Sonne
            private double uranusAngle = 0;   // Winkel für die Umlaufbahn des Uranus um die Sonne
            private double neptuneAngle = 0;  // Winkel für die Umlaufbahn des Neptuns um die Sonne
            private double sunRotationAngle = 0;

            @Override
            public void handle(long now) {

                if (quarterSpeed) {
                    speed = 4; // 25 % der ursprünglichen Geschwindigkeit
                } else if (halfSpeed) {
                    speed = 2; // 50 % der ursprünglichen Geschwindigkeit
                } else {
                    speed = 1; // Volle Geschwindigkeit
                }
                // Erde rotiert um sich selbst
                erde.rotateProperty().set(erde.getRotate() - 2.5/speed);

                // Erde bewegt sich in einer Umlaufbahn um die Sonne
                earthAngle += 0.028/speed;  // Winkel für die Umlaufbahn der Erde
                // Entfernung Erde-Sonne
                // Größere Darstellung der Sonne
                double sunRadius = 16350;
                double earthOrbitRadius = 1754 + sunRadius;
                double earthX = earthOrbitRadius * Math.cos(earthAngle) ;  // X-Koordinate der Erde
                double earthZ = earthOrbitRadius * Math.sin(earthAngle);// Z-Koordinate der Erde
                erde.setTranslateX(earthX);
                erde.setTranslateZ(earthZ);

                //OpenAi ChatGPT benutzt um Originalcode zu verbessern https://chatgpt.com/
                if (isEarthCameraActive) {
                    secondCamera.setTranslateX(earthX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(earthZ - 850);
                    scene.setCamera(secondCamera);

                    earthGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text erdText = new Text("Die Erde");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 85
                    Text erdInfo = new Text("Drücke Taste: E um zurückzukehren\n\n"+
                            "Mittlerer Radius: 6371km\n" +
                            "Masse: 1 Me\n" +
                            "Orbitalperiode: 365,24 Tage\n" +
                            "Rotationsperiode: 23,93h\n" +
                            "Anzahl der Monde: einen\n" +
                            "Orbitaldistanz zur Sonne: 1,0 AE");
                    erdInfo.setX(earthX-430);
                    erdInfo.setY(70);
                    erdInfo.setTranslateZ(earthZ+60);
                    erdInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
                    erdInfo.setFill(Color.WHITE);
                    erdText.setX(earthX-40);
                    erdText.setY(-175);
                    erdText.setTranslateZ(earthZ);
                    erdText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    erdText.setFill(Color.WHITE);
                    earthGroup.getChildren().addAll(erdText,erdInfo);
                }



                // Mond bewegt sich in einer Umlaufbahn um die Erde
                moonAngle += 0.02;  // Schnellerer Winkel für die Umlaufbahn des Mondes
                // Entfernung Mond-Erde
                double moonOrbitRadius = 650;
                double moonX = moonOrbitRadius * Math.cos(moonAngle);  // X-Koordinate des Mondes relativ zur Erde
                double moonZ = moonOrbitRadius * Math.sin(moonAngle);
                mond.rotateProperty().set(mond.getRotate() - 0.09/speed);// Z-Koordinate des Mondes relativ zur Erde
                mond.setTranslateX(earthX + moonX);  // Mond positionieren relativ zur Erde
                mond.setTranslateZ(earthZ + moonZ);

                if (isMoonCameraActive) {
                    // Setze die Kamera direkt vor den Mond
                    secondCamera.setTranslateX(earthX+moonX);
                    secondCamera.setTranslateY(0); // Leicht über dem Mond
                    secondCamera.setTranslateZ(earthZ+moonZ - 350);
                    moonGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text mondText = new Text("Der Mond");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 98
                    Text mondInfo = new Text("Drücke Taste: M um zurückzukehren\n\n"+
                            "Mittlerer Radius: 1737,5km \n" +
                            "Rotationsperiode: 27,32 Tage \n" +
                            "Orbitalperiode: 27,32 Tage \n" +
                            "Entfernung zur Erde: 384,400km");
                    mondInfo.setX(earthX+moonX-450);
                    mondInfo.setY(100);
                    mondInfo.setTranslateZ(earthZ+moonZ+600);
                    mondInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    mondInfo.setFill(Color.WHITE);
                    mondText.setX(earthX+moonX-70);
                    mondText.setY(-180);
                    mondText.setTranslateZ(earthZ+moonZ+600);
                    mondText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 30));
                    mondText.setFill(Color.WHITE);
                    moonGroup.getChildren().addAll(mondText,mondInfo);
                }

                // Merkur bewegt sich in einer schnelleren Umlaufbahn um die Sonne
                mercuryAngle += 0.047/speed;  // Schnellerer Winkel für die Umlaufbahn des Merkurs
                // Entfernung Merkur-Sonne
                double mercuryOrbitRadiusX = 678 + sunRadius;
                double mercuryX = mercuryOrbitRadiusX * Math.cos(mercuryAngle);  // X-Koordinate des Merkurs
                double mercuryOrbitRadiusZ = 678 + sunRadius;
                double mercuryZ = mercuryOrbitRadiusZ * Math.sin(mercuryAngle);  // Z-Koordinate des Merkurs
                merkur.setTranslateX(mercuryX);
                merkur.setTranslateZ(mercuryZ);
                merkur.rotateProperty().set(merkur.getRotate() - 0.043/speed);

                if (isMercuryCameraActive) {
                    secondCamera.setTranslateX(mercuryX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(mercuryZ - 1350);
                    mercuryGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text mercuryText = new Text("Der Merkur");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 32
                    Text mercuryInfo = new Text("Drücke Taste: Y um zurückzukehren\n\n"+
                            "Mittlerer Radius: 2439,7km\n" +
                            "Masse: 0,055 Me\n" +
                            "Orbitalperiode: 87,97 Tage\n" +
                            "Rotationsperiode: 58,67 Tage\n" +
                            "Anzahl der Monde: keine\n" +
                            "Orbitaldistanz zur Sonne: mittleren Abstand von 0,4 AE\n");
                    mercuryInfo.setX(mercuryX-600);
                    mercuryInfo.setY(100);
                    mercuryInfo.setTranslateZ(mercuryZ);
                    mercuryInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 26));
                    mercuryInfo.setFill(Color.WHITE);
                    mercuryText.setX(mercuryX-90);
                    mercuryText.setY(-340);
                    mercuryText.setTranslateZ(mercuryZ+300);
                    mercuryText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 34));
                    mercuryText.setFill(Color.WHITE);
                    mercuryGroup.getChildren().addAll(mercuryText,mercuryInfo);

                }

                // Venus bewegt sich um die Sonne
                venusAngle += 0.018/speed;  // Langsamerer Winkel für die Umlaufbahn der Venus
                // Entfernung Venus-Sonne
                double venusOrbitRadius = 1268 + sunRadius;
                double venusX = venusOrbitRadius * Math.cos(venusAngle);
                double venusZ = venusOrbitRadius * Math.sin(venusAngle);
                venus.rotateProperty().set(venus.getRotate() + 0.010/speed);
                venus.setTranslateX(venusX);
                venus.setTranslateZ(venusZ);
                if (isVenusCameraActive) {
                    secondCamera.setTranslateX(venusX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(venusZ - 2050);
                    venusGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text venusText = new Text("Die Venus");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 53
                    Text venusInfo = new Text("Drücke Taste: V um zurückzukehren\n\n"+
                            "Mittlerer Radius: 6051,8km \n" +
                            "Masse: 0,815 Me \n" +
                            "Orbitalperiode: 224,70 Tage \n" +
                            "Rotationsperiode: 243,02 Tage \n" +
                            "Atmosphäre: etwa 90mal dichter als die der Erde, Wolkenhülle verdeckt dauerhaft Sicht auf Oberfläche \n" +
                            "Anzahl der Monde: keinen \n" +
                            "Orbitaldistanz zur Sonne: 0,723 AE \n");
                    venusInfo.setX(venusX-950);
                    venusInfo.setY(250);
                    venusInfo.setTranslateZ(venusZ);
                    venusInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    venusInfo.setFill(Color.WHITE);
                    venusText.setX(venusX-80);
                    venusText.setY(-400);
                    venusText.setTranslateZ(venusZ-30);
                    venusText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 40));
                    venusText.setFill(Color.WHITE);
                    venusGroup.getChildren().addAll(venusText,venusInfo);
                    
                }

                // Mars bewegt sich um die Sonne
                marsAngle += 0.008/speed;  // Winkel für die Umlaufbahn des Mars
                // Entfernung Mars-Sonne
                double marsOrbitRadius = 2670 + sunRadius;
                double marsX = marsOrbitRadius * Math.cos(marsAngle);
                double marsZ = marsOrbitRadius * Math.sin(marsAngle);
                mars.setTranslateX(marsX);
                mars.setTranslateZ(marsZ);
                mars.rotateProperty().set(mars.getRotate() - 2.53/speed);
                if (isMarsCameraActive) {
                    secondCamera.setTranslateX(marsX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(marsZ - 1050);
                    marsGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text marsText = new Text("Der Mars");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 123
                    Text marsInfo = new Text("Drücke Taste: A um zurückzukehren\n\n"+
                            "Masse: 0,107 Erdmasse – erheblich kleiner als Erde / 0,151 Me \n" +
                            "Mittlerer Radius: 3386,2km \n" +
                            "Orbitalperiode: 687 Tage \n" +
                            "Rotationsperiode: 24h 37min \n" +
                            "Anzahl der Monde: zwei kleine ( Phobos + Deimos) \n" +
                            "Orbitaldistanz zur Sonne: 1,5 AE \n");
                    marsInfo.setX(marsX-500);
                    marsInfo.setY(80);
                    marsInfo.setTranslateZ(marsZ);
                    marsInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
                    marsInfo.setFill(Color.WHITE);
                    marsText.setX(marsX-70);
                    marsText.setY(-300);
                    marsText.setTranslateZ(marsZ+400);
                    marsText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 30));
                    marsText.setFill(Color.WHITE);
                    marsGroup.getChildren().addAll(marsInfo,marsText);
                }

                // Jupiter bewegt sich um die Sonne
                jupiterAngle += 0.002/speed;  // Winkel für die Umlaufbahn des Jupiters
                // Entfernung Jupiter-Sonne
                double jupiterOrbitRadius = 9113 + sunRadius;
                double jupiterX = jupiterOrbitRadius * Math.cos(jupiterAngle);
                double jupiterZ = jupiterOrbitRadius * Math.sin(jupiterAngle);
                jupiter.setTranslateX(jupiterX);
                jupiter.setTranslateZ(jupiterZ);
                jupiter.rotateProperty().set(jupiter.getRotate() - 6.05/speed);

                if (isJupiterCameraActive) {
                    secondCamera.setTranslateX(jupiterX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(jupiterZ - 8900);
                    jupiterGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text jupiterText = new Text("Der Jupiter");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 184
                    Text jupiterInfo = new Text("Drücke Taste: J um zurückzukehren\n\n"+
                            "Mittlerer Radius: : [äquatorial]: 71,492km  [polar]: 66,854km \n" +
                            "Masse: 2,5 mal die Masse aller Planeten zusammengenommen (entspricht ca. 318 \n" +
                            "Erdmassen) 317,8 Me \n" +
                            "Orbitalperiode: 11,86 Jahre \n"+
                            "Rotationsperiode: 9,925h\n"+
                            "Anzahl der Monde: aktuell 79, viele weniger als 10km Durchmesser; vier Galilei’schen Monde: Io, Europa, Ganymed, Kallisto");
                    jupiterInfo.setX(jupiterX-645);
                    jupiterInfo.setY(160);
                    jupiterInfo.setTranslateZ(jupiterZ-7500);
                    jupiterInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    jupiterInfo.setFill(Color.WHITE);
                    jupiterText.setX(jupiterX-400);
                    jupiterText.setY(-1770);
                    jupiterText.setTranslateZ(jupiterZ-30);
                    jupiterText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 140));
                    jupiterText.setFill(Color.WHITE);
                    jupiterGroup.getChildren().addAll(jupiterText,jupiterInfo);
                }

                // Saturn bewegt sich um die Sonne
                saturnAngle += 0.0006/speed;  // Winkel für die Umlaufbahn des Saturns
                // Entfernung Saturn-Sonne
                double saturnOrbitRadius = 16721 + sunRadius;
                saturnX = saturnOrbitRadius * Math.cos(saturnAngle);
                saturnZ = saturnOrbitRadius * Math.sin(saturnAngle);
                saturn.rotateProperty().set(saturn.getRotate() - 5.6/speed);
                saturn.setTranslateX(saturnX);
                saturn.setTranslateZ(saturnZ);

                ring.setTranslateX(saturnX);
                ring.setTranslateZ(saturnZ);
                ring.setTranslateY(-100);
                if (isSaturnCameraActive) {
                    secondCamera.setTranslateX(saturnX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(saturnZ - 8900);
                    saturnGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text saturnText = new Text("Der Saturn");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 229
                    Text saturnInfo = new Text("Drücke Taste: S um zurückzukehren\n\n"+
                         "Mittlerer Radius: [äquatorial]: 60,268km  [polar]: 54,364km \n" +
                            "Masse: 95,152 Me  \n" +
                            "Anzahl der Monde: aktuell 79, viele weniger als 10km Durchmesser; vier Galilei’schen Monde: Io, Europa, Ganymed, Kallisto\n"+
                            "Orbitalperiode: 29,4 Jahre \n" +
                            "Rotationsperiode: 10,233h");
                    saturnInfo.setX(saturnX-645);
                    saturnInfo.setY(170);
                    saturnInfo.setTranslateZ(saturnZ-7500);
                    saturnInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    saturnInfo.setFill(Color.WHITE);
                    saturnText.setX(saturnX-400);
                    saturnText.setY(-1770);
                    saturnText.setTranslateZ(saturnZ-30);
                    saturnText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 140));
                    saturnText.setFill(Color.WHITE);
                    saturnGroup.getChildren().addAll(saturnText,saturnInfo);
                }

                // Uranus bewegt sich um die Sonne
                uranusAngle += 0.0003/speed;  // Winkel für die Umlaufbahn des Uranus
                // Entfernung Uranus-Sonne
                double uranusOrbitRadius = 33549 + sunRadius;
                double uranusX = uranusOrbitRadius * Math.cos(uranusAngle);
                double uranusZ = uranusOrbitRadius * Math.sin(uranusAngle);
                uranus.setTranslateX(uranusX);
                uranus.setTranslateZ(uranusZ);
                uranus.rotateProperty().set(uranus.getRotate() - 3.48/speed);
                if (isUranusCameraActive) {
                    secondCamera.setTranslateX(uranusX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(uranusZ - 8900);
                    uranusGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text uranusText = new Text("Der Uranus");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 277
                    Text uranusInfo = new Text("Drücke Taste: U um zurückzukehren\n\n"+
                            "Mittlerer Radius:[äquatorial]: 25,559km, [polar}: 24,973km \n" +
                            "Masse: 14,536 Me  \n" +
                            "Orbitalperiode: 84,02 Jahre \n" +
                            "Rotationsperiode:17,24h \n" +"Anzahl der Monde: aktuell 62 bekannte Monde, Titan und Enceladus scheinen geologisch aktiv zu sein\n"+
                            "Anzahl der Monde: 27 bekannte, größten: Titania, Oberon, Umbriel, Ariel, Miranda");
                    uranusInfo.setX(uranusX-645);
                    uranusInfo.setY(150);
                    uranusInfo.setTranslateZ(uranusZ-7500);
                    uranusInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    uranusInfo.setFill(Color.WHITE);
                    uranusText.setX(uranusX-400);
                    uranusText.setY(-1700);
                    uranusText.setTranslateZ(uranusZ-30);
                    uranusText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 140));
                    uranusText.setFill(Color.WHITE);
                    uranusGroup.getChildren().addAll(uranusText,uranusInfo);

                }


                // Neptun bewegt sich um die Sonne
                neptuneAngle += 0.0002/speed;  // Winkel für die Umlaufbahn des Neptuns
                // Entfernung Neptun-Sonne
                double neptuneOrbitRadius = 52489 + sunRadius;
                double neptuneX = neptuneOrbitRadius * Math.cos(neptuneAngle);
                double neptuneZ = neptuneOrbitRadius * Math.sin(neptuneAngle);
                neptune.setTranslateX(neptuneX);
                neptune.setTranslateZ(neptuneZ);
                neptune.rotateProperty().set(neptune.getRotate() - 3.73/speed);
                if (isNeptuneCameraActive) {
                    secondCamera.setTranslateX(neptuneX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(neptuneZ - 8900);
                    neptuneGroup.getChildren().removeIf(node -> node instanceof Text);
                    Text neptuneText = new Text("Der Neptun");
                    //Quelle: "Expedition zu fremden Welten" von R. Jaumann,  U. Köhler, F. Sohl, D. Tirsch, S. Pieth - Springer Verlag, 2018 - S. 258
                    Text neptuneInfo = new Text("Drücke Taste: N um zurückzukehren\n\n"+
                            "Mittlerer Radius: [äquatorial]: 25,746km [polar}: 24,341km \n" +
                            "Masse: 17,147 Me \n" +
                            "Orbitalperiode: 164,79 Jahre \n" +
                            "Rotationsperiode: 16,11h \n" +
                            "Anzahl der Monde: 14 bekannte : größter: Tritan: geologisch aktiver Mond, Rotation ist \n");
                    neptuneInfo.setX(neptuneX-640);
                    neptuneInfo.setY(170);
                    neptuneInfo.setTranslateZ(neptuneZ-7500);
                    neptuneInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    neptuneInfo.setFill(Color.WHITE);
                    neptuneText.setX(neptuneX-400);
                    neptuneText.setY(-1770);
                    neptuneText.setTranslateZ(neptuneZ-30);
                    neptuneText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 140));
                    neptuneText.setFill(Color.WHITE);
                    neptuneGroup.getChildren().addAll(neptuneText,neptuneInfo);

                }
                // Sonne dreht sich um sich selbst
                sunRotationAngle -= 0.1/speed; // Geschwindigkeit der Rotation
                sonne.setRotate(sunRotationAngle);
            }
        };
        timer.start();
    }

    private ImageView prepareImageView(){
        //Quelle: https://www.solarsystemscope.com/textures/[zugegriffen am 4.11.2024]
        //Quelle:https://www.youtube.com/watch?v=f3ggPt9WN58&list=PLhs1urmduZ295Ryetga7CNOqDymN_rhB_&index=17&ab_channel=GenuineCoder[zugegriffen am 4.11.2024]]
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/stars_milky_way.jpg")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200000);
        imageView.setFitWidth(450000);

        imageView.setTranslateX(-imageView.getFitWidth() / 2); // Horizontal zentrieren
        imageView.setTranslateY(-imageView.getFitHeight() / 2); // Vertikal zentrieren
        imageView.getTransforms().add(new Translate(0,0,70000));
        return imageView;
    }
    //OpenAi ChatGPT benutzt um Originalcode zu verbessern https://chatgpt.com/
    private KeyCode activeCameraKey = null;

    private void handleKeyPress(KeyEvent event) {
        // Überprüfen, ob eine Kamera aktiv ist und ob die gedrückte Taste die aktive Kamera-Taste ist
        if (activeCameraKey != null && !event.getCode().equals(activeCameraKey)) {
            // Wenn eine Kamera aktiv ist und eine andere Taste als die aktive Kamera-Taste gedrückt wird (außer Z), ignoriere den Tastendruck
            return;
        }

        switch (event.getCode()) {
            case E:
                isEarthCameraActive = !isEarthCameraActive;
                toggleCamera(KeyCode.E, secondCamera, isEarthCameraActive, earthGroup);
                break;
            case Y:
                isMercuryCameraActive = !isMercuryCameraActive;
                toggleCamera(KeyCode.Y, secondCamera, isMercuryCameraActive, mercuryGroup);
                break;
            case M:
                isMoonCameraActive = !isMoonCameraActive;
                toggleCamera(KeyCode.M, secondCamera, isMoonCameraActive, moonGroup);
                break;
            case J:
                isJupiterCameraActive = !isJupiterCameraActive;
                toggleCamera(KeyCode.J, secondCamera, isJupiterCameraActive, jupiterGroup);
                break;
            case S:
                isSaturnCameraActive = !isSaturnCameraActive;
                toggleCamera(KeyCode.S, secondCamera, isSaturnCameraActive, saturnGroup);
                break;
            case A:
                isMarsCameraActive = !isMarsCameraActive;
                toggleCamera(KeyCode.A, secondCamera, isMarsCameraActive, marsGroup);
                break;
            case U:
                isUranusCameraActive = !isUranusCameraActive;
                toggleCamera(KeyCode.U, secondCamera, isUranusCameraActive, uranusGroup);
                break;
            case N:
                isNeptuneCameraActive = !isNeptuneCameraActive;
                toggleCamera(KeyCode.N, secondCamera, isNeptuneCameraActive, neptuneGroup);
                break;
            case V:
                isVenusCameraActive = !isVenusCameraActive;
                toggleCamera(KeyCode.V, secondCamera, isVenusCameraActive, venusGroup);
                break;
            case DIGIT1:
                halfSpeed  = !halfSpeed;
                break;
            case DIGIT2:
                quarterSpeed = !quarterSpeed;
                break;

            default:
                break;
        }
    }

    private void toggleCamera(KeyCode key, Camera targetCamera, boolean isActive, Group group) {
        if (isActive) {
            // Kamera aktivieren
            scene.setCamera(targetCamera);
            activeCameraKey = key; // Speichert die aktive Kamera-Taste
            isAllCameraActive = false; // Andere Tasten deaktivieren
        } else {
            // Zurück zur Hauptkamera
            scene.setCamera(camera);
            group.getChildren().removeIf(node -> node instanceof Text); // Entfernt Text-Elemente der spezifischen Gruppe
            activeCameraKey = null; // Aktive Kamera-Taste zurücksetzen
            isAllCameraActive = true; // Alle Tasten wieder aktivieren
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
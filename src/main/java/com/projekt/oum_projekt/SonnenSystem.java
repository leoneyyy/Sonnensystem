package com.projekt.oum_projekt;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class SonnenSystem extends Application {
    public static double width = 0;
    public static double height = 0;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private boolean isEarthCameraActive = false;
    private boolean isMoonCameraActive = false;
    private boolean isVenusCameraActive = false;
    private boolean isUranusCameraActive = false;
    private boolean isMercuryCameraActive = false;
    private boolean isSaturnCameraActive = false;
    private boolean isJupiterCameraActive = false;
    private boolean isNeptuneCameraActive = false;
    private boolean isMarsCameraActive = false;
    private boolean isAllCameraActive = true;

    public PerspectiveCamera camera;
    public PerspectiveCamera secondCamera;
    public PerspectiveCamera topDownCamera;
    double saturnX;
    double saturnZ;
    SmartGroup sonnenGruppe;
    SmartGroup erdGruppe;
    SmartGroup merkurGruppe;
    SmartGroup mondGruppe;
    SmartGroup venusGruppe;
    SmartGroup saturnGruppe;
    SmartGroup jupiterGruppe;
    SmartGroup marsGruppe;
    SmartGroup uranusGruppe;
    SmartGroup neptuneGruppe;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        width =  Screen.getPrimary().getBounds().getWidth();
        height = Screen.getPrimary().getBounds().getHeight();

        //Kamera
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(200000);
        camera.translateZProperty().set(-100000);
        camera.setFieldOfView(60);

        //ErdKamera
        secondCamera = new PerspectiveCamera(true);
        secondCamera.setNearClip(1);
        secondCamera.setFarClip(200000);

        topDownCamera = new PerspectiveCamera(true);
        topDownCamera.setTranslateX(0);
        topDownCamera.setTranslateY(width/2);
        topDownCamera.setTranslateZ(0);
        topDownCamera.setRotate(90);
        topDownCamera.setRotationAxis(Rotate.X_AXIS);

        //Sonne
        Sonne sonne = new Sonne();
        sonnenGruppe = new SmartGroup();
        sonnenGruppe.getChildren().add(sonne.prepareSonne());
        Node sonnenNode= sonne.prepareSonne();

        //Erde
        Erde erde  = new Erde();
        erdGruppe = new SmartGroup();
        erdGruppe.getChildren().add(erde.prepareErde());
        Node erdeNode= erde.prepareErde();

        //Merkur
        Merkur merkur = new Merkur();
        merkurGruppe = new SmartGroup();
        merkurGruppe.getChildren().add(merkur.prepareMerkur());
        Node merkurNode= merkur.prepareMerkur();


        //Mond
        Mond mond  = new Mond();
        mondGruppe = new SmartGroup();
        mondGruppe.getChildren().add(mond.prepareMond());
        Node mondNode= mond.prepareMond();

        //Venus
        Venus venus  = new Venus();
        venusGruppe = new SmartGroup();
        venusGruppe.getChildren().add(venus.prepareVenus());
        Node venusNode= venus.prepareVenus();

        //Saturn
        Saturn saturn = new Saturn();
        saturnGruppe = new SmartGroup();
        saturnGruppe.getChildren().add(saturn.prepareSaturn());
        Node saturnNode= saturn.prepareSaturn();

        //SaturnRing
        SaturnRing saturnRing = new SaturnRing(2046, 30, "/Images/saturn_ring.png");
        saturnGruppe.getChildren().add(saturnRing.getRing());

        //Jupiter
        Jupiter jupiter = new Jupiter();
        jupiterGruppe = new SmartGroup();
        jupiterGruppe.getChildren().add(jupiter.prepareJupiter());
        Node jupiterNode= jupiter.prepareJupiter();


        //Mars
        Mars mars = new Mars();
        marsGruppe = new SmartGroup();
        marsGruppe.getChildren().add(mars.prepareMars());
        Node marsNode= mars.prepareMars();

        //Uranus
        Uranus uranus = new Uranus();
        uranusGruppe = new SmartGroup();
        uranusGruppe.getChildren().add(uranus.prepareUranus());
        Node uranusNode= uranus.prepareUranus();

        //Neptune
        Neptune neptune = new Neptune();
        neptuneGruppe = new SmartGroup();
        neptuneGruppe.getChildren().add(neptune.prepareNeptune());
        Node neptuneNode= neptune.prepareNeptune();


        Group root = new Group();

        root.getChildren().add(sonnenGruppe);
        root.getChildren().add(prepareImageView());



        Text text = new Text("Interaktive Darstellung des Sonnensystems");
        text.setX(-3000);
        text.setY(-4280);
        text.setTranslateZ(-30000*3);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR,250));
        text.setFill(Color.WHITE);
        Text beschreibung = new Text("Tasten um auf Planeten zu zoomen: \nErde: Taste E\nMond: Taste M\nMars: Taste A\nMerkur: Taste Y\nUranus: Taste U\nVenus: Taste V\nSaturn: Taste S\nJupiter: Taste J\nNeptun: Taste N");
        beschreibung.setX(-8000);
        beschreibung.setY(2000);
        beschreibung.setTranslateZ(-30000*3);
        beschreibung.setFont(Font.font("verdana",FontWeight.NORMAL, FontPosture.REGULAR , 130));
        beschreibung.setFill(Color.WHITE);
        sonnenGruppe.getChildren().addAll(text, beschreibung);

        erdGruppe.getChildren().add(mondGruppe);
        sonnenGruppe.getChildren().add(erdGruppe);
        sonnenGruppe.getChildren().add(merkurGruppe);
        sonnenGruppe.getChildren().add(venusGruppe);
        sonnenGruppe.getChildren().add(saturnGruppe);
        sonnenGruppe.getChildren().add(jupiterGruppe);
        sonnenGruppe.getChildren().add(marsGruppe);
        sonnenGruppe.getChildren().add(uranusGruppe);
        sonnenGruppe.getChildren().add(neptuneGruppe);

        //Scene
        scene = new Scene(root, width, height, true,null);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);
        scene.setOnKeyPressed(event -> handleKeyPress(event));
        if (!isAllCameraActive) {
            initMouseControl(sonnenGruppe, scene, stage);
        }
        stage.setTitle("Sonnensystem");
        stage.setScene(scene);
        stage.show();
        prepareAnimation(erde.getPlanetSphere(), mond.getPlanetSphere(), sonne.getPlanetSphere(), merkur.getPlanetSphere(), venus.getPlanetSphere(),neptune.getPlanetSphere(), saturn.getPlanetSphere(),mars.getPlanetSphere(), uranus.getPlanetSphere(), jupiter.getPlanetSphere(),saturnRing.getRing());
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

            private final double sunRadius = 16350;        // Größere Darstellung der Sonne
            private final double earthOrbitRadius = 1754 +sunRadius; // Entfernung Erde-Sonne
            private final double moonOrbitRadius = 650;   // Entfernung Mond-Erde
            private final double mercuryOrbitRadiusX = 678 +sunRadius; // Entfernung Merkur-Sonne
            private final double mercuryOrbitRadiusZ = 678 +sunRadius;
            private final double venusOrbitRadius = 1268 +sunRadius; // Entfernung Venus-Sonne
            private final double marsOrbitRadius = 2670 +sunRadius;  // Entfernung Mars-Sonne
            private final double jupiterOrbitRadius = 9113 +sunRadius;  // Entfernung Jupiter-Sonne
            private final double saturnOrbitRadius = 16721 +sunRadius;  // Entfernung Saturn-Sonne
            private final double uranusOrbitRadius = 33549 +sunRadius;  // Entfernung Uranus-Sonne
            private final double neptuneOrbitRadius = 52489 +sunRadius; // Entfernung Neptun-Sonne

            @Override
            public void handle(long now) {
                // Erde rotiert um sich selbst
                erde.rotateProperty().set(erde.getRotate() - 0.2);

                // Erde bewegt sich in einer Umlaufbahn um die Sonne
                earthAngle += 0.005;  // Winkel für die Umlaufbahn der Erde
                double earthX = earthOrbitRadius * Math.cos(earthAngle) ;  // X-Koordinate der Erde
                double earthZ = earthOrbitRadius * Math.sin(earthAngle);// Z-Koordinate der Erde
                erde.setTranslateX(earthX);
                erde.setTranslateZ(earthZ);
                //System.out.println(earthX);

                if (isEarthCameraActive) {
                    secondCamera.setTranslateX(earthX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(earthZ - 850);
                    scene.setCamera(secondCamera);

                    erdGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text erdText = new Text("Die Erde");
                    Text erdInfo = new Text("Erde\n" +
                            "Mittlerer Radius: 6371km\n" +
                            "Masse: 1 Me\n" +
                            "Orbitalperiode: 365,24\n" +
                            "Tage Rotationsperiode: 23,93h\n" +
                            "Anzahl der Monde: einen\n" +
                            "Orbitaldistanz zur Sonne: 1,0 AE");
                    erdInfo.setX(earthX-400);
                    erdInfo.setY(50);
                    erdInfo.setTranslateZ(earthZ);
                    erdInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    erdInfo.setFill(Color.WHITE);
                    erdText.setX(earthX-40);
                    erdText.setY(-175);
                    erdText.setTranslateZ(earthZ);
                    erdText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    erdText.setFill(Color.WHITE);
                    erdGruppe.getChildren().addAll(erdText,erdInfo);
                }

                mond.rotateProperty().set(mond.getRotate() - 0.2);

                // Mond bewegt sich in einer Umlaufbahn um die Erde
                moonAngle += 0.02;  // Schnellerer Winkel für die Umlaufbahn des Mondes
                double moonX = moonOrbitRadius * Math.cos(moonAngle);  // X-Koordinate des Mondes relativ zur Erde
                double moonZ = moonOrbitRadius * Math.sin(moonAngle);  // Z-Koordinate des Mondes relativ zur Erde
                mond.setTranslateX(earthX + moonX);  // Mond positionieren relativ zur Erde
                mond.setTranslateZ(earthZ + moonZ);

                if (isMoonCameraActive) {
                    // Setze die Kamera direkt vor den Mond
                    secondCamera.setTranslateX(earthX+moonX);
                    secondCamera.setTranslateY(0); // Leicht über dem Mond
                    secondCamera.setTranslateZ(earthZ+moonZ - 350);
                    mondGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text mondText = new Text("Der Mond");
                    Text mondInfo = new Text("Mittlerer Radius: 1737,5km \n" +
                            "Rotationsperiode: 27,32 Tage \n" +
                            "Orbitalperiode: 27,32 Tage \n" +
                            "Entfernung zur Erde: 384,400km");
                    mondInfo.setX(earthX+moonX-450);
                    mondInfo.setY(140);
                    mondInfo.setTranslateZ(earthZ+moonZ+600);
                    mondInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    mondInfo.setFill(Color.WHITE);
                    mondText.setX(earthX+moonX-70);
                    mondText.setY(-180);
                    mondText.setTranslateZ(earthZ+moonZ+600);
                    mondText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 30));
                    mondText.setFill(Color.WHITE);
                    mondGruppe.getChildren().addAll(mondText,mondInfo);
                }

                // Merkur bewegt sich in einer schnelleren Umlaufbahn um die Sonne
                mercuryAngle += 0.01;  // Schnellerer Winkel für die Umlaufbahn des Merkurs
                double mercuryX = mercuryOrbitRadiusX * Math.cos(mercuryAngle);  // X-Koordinate des Merkurs
                double mercuryZ = mercuryOrbitRadiusZ * Math.sin(mercuryAngle);  // Z-Koordinate des Merkurs
                merkur.setTranslateX(mercuryX);
                merkur.setTranslateZ(mercuryZ);

                if (isMercuryCameraActive) {
                    secondCamera.setTranslateX(mercuryX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(mercuryZ - 350);
                    merkurGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text merkurText = new Text("Der Merkur");
                    Text mekurInfo = new Text("Durchmesser: 4.878 km\n" +
                            "Mittlerer Radius: 2439,7km\n" +
                            "Masse: 0,055 Me\n" +
                            "Oberflächentemperatur: schwankt zwischen \n -173C auf Nachtseite und +427 C auf Tagseite\n" +
                            "Orbitalperiode: 87,97 Tage\n" +
                            "Rotationsperiode: 58,67 Tage\n" +
                            "Atmosphäre: keine Atmosphäre im „herkömmlichen Sinne“\n" +
                            "Anzahl der Monde: keine\n" +
                            "Orbitaldistanz zur Sonne: mittleren Abstand von 0,4 AE\n" +
                            "Besondere Merkmale: durch fehlen einer ausgeprägten, schützenden Atmosphäre ist er ein\n" +
                            "Planet der Extreme, Kern im Verhältnis zu Gesamtdurchmesser weit größer als bei anderen\n" +
                            "Planeten\n");
                    mekurInfo.setX(mercuryX-400);
                    mekurInfo.setY(30);
                    mekurInfo.setTranslateZ(mercuryZ+500);
                    mekurInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));
                    mekurInfo.setFill(Color.WHITE);
                    merkurText.setX(mercuryX-30);
                    merkurText.setY(-60);
                    merkurText.setTranslateZ(mercuryZ);
                    merkurText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
                    merkurText.setFill(Color.WHITE);
                    merkurGruppe.getChildren().addAll(merkurText,mekurInfo);

                }

                // Venus bewegt sich um die Sonne
                venusAngle += 0.004;  // Langsamerer Winkel für die Umlaufbahn der Venus
                double venusX = venusOrbitRadius * Math.cos(venusAngle);
                double venusZ = venusOrbitRadius * Math.sin(venusAngle);
                venus.setTranslateX(venusX);
                venus.setTranslateZ(venusZ);
                if (isVenusCameraActive) {
                    secondCamera.setTranslateX(venusX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(venusZ - 2050);
                    venusGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text venusText = new Text("Die Venus");
                    Text venusInfo = new Text("Venus – „Zwilling der Erde“ \n" +
                            "Mittlerer Radius: 6051,8km \n" +
                            "Masse: 0,815 Me \n" +
                            "Orbitalperiode: 224,70 Tage \n" +
                            "Rotationsperiode: 243,02 Tage \n" +
                            "Atmosphäre: etwa 90mal dichter als die der Erde, Wolkenhülle verdeckt dauerhaft Sicht auf Oberfläche \n" +
                            "Anzahl der Monde: keinen \n" +
                            "Orbitaldistanz zur Sonne: 0,723 AE \n" +
                            "Besondere Merkmale: schwaches Magnetfeld ");
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
                    venusGruppe.getChildren().addAll(venusText,venusInfo);
                    
                }

                // Mars bewegt sich um die Sonne
                marsAngle += 0.003;  // Winkel für die Umlaufbahn des Mars
                double marsX = marsOrbitRadius * Math.cos(marsAngle);
                double marsZ = marsOrbitRadius * Math.sin(marsAngle);
                mars.setTranslateX(marsX);
                mars.setTranslateZ(marsZ);
                mars.rotateProperty().set(mars.getRotate() - 0.05);
                if (isMarsCameraActive) {
                    secondCamera.setTranslateX(marsX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(marsZ - 1050);
                    marsGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text marsText = new Text("Der Mars");
                    Text marsInfo = new Text("Mars -  „der rote Planet“ \n" +
                            "Masse: 0,107 Erdmasse – erheblich kleiner als Erde / 0,151 Me \n" +
                            "Mittlerer Radius: 3386,2km \n" +
                            "Orbitalperiode: 687 Tage \n" +
                            "Rotationsperiode: 24h 37min \n" +
                            "Atmosphäre:dünn. Besteht hauptsächlich aus Kohlenstoffdioxid, Oberfläche geprägt durch \n" +
                            "riesige  Vulkane (z.B. 27km hoher Olympus Mons) und tiefe Täler und Canyons \n" +
                            "Anzahl der Monde: zwei kleine ( Phobos + Deimos) \n" +
                            "Orbitaldistanz zur Sonne: 1,5 AE \n" +
                            "Besondere Merkmale: Marsbeben deuten darauf hin, dass der Planet sachrumpft ");
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
                    marsGruppe.getChildren().addAll(marsInfo,marsText);
                }

                // Jupiter bewegt sich um die Sonne
                jupiterAngle += 0.002;  // Winkel für die Umlaufbahn des Jupiters
                double jupiterX = jupiterOrbitRadius * Math.cos(jupiterAngle);
                double jupiterZ = jupiterOrbitRadius * Math.sin(jupiterAngle);
                jupiter.setTranslateX(jupiterX);
                jupiter.setTranslateZ(jupiterZ);
                jupiter.rotateProperty().set(jupiter.getRotate() - 0.05);

                if (isJupiterCameraActive) {
                    secondCamera.setTranslateX(jupiterX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(jupiterZ - 8900);
                    jupiterGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text jupiterText = new Text("Der Jupiter");
                    Text jupiterInfo = new Text("Jupiter \n" +
                            "Durchmesser: knapp 142.000 km  (Erde würde 11 mal reinpassen) \n" +
                            "Mittlerer Radius: : [äquatorial]: 71,492km  [polar]: 66,854km \n" +
                            "Masse: 2,5 mal die Masse aller Planeten zusammengenommen (entspricht ca. 318 \n" +
                            "Erdmassen) 317,8 Me \n" +
                            "Orbitalperiode: 11,86 Jahre \n");
                    jupiterInfo.setX(jupiterX-645);
                    jupiterInfo.setY(200);
                    jupiterInfo.setTranslateZ(jupiterZ-7500);
                    jupiterInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    jupiterInfo.setFill(Color.WHITE);
                    jupiterText.setX(jupiterX-400);
                    jupiterText.setY(-1770);
                    jupiterText.setTranslateZ(jupiterZ-30);
                    jupiterText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 140));
                    jupiterText.setFill(Color.WHITE);
                    jupiterGruppe.getChildren().addAll(jupiterText,jupiterInfo);
                }

                // Saturn bewegt sich um die Sonne
                saturnAngle += 0.0015;  // Winkel für die Umlaufbahn des Saturns
                saturnX = saturnOrbitRadius * Math.cos(saturnAngle);
                saturnZ = saturnOrbitRadius * Math.sin(saturnAngle);
                saturn.setTranslateX(saturnX);
                saturn.setTranslateZ(saturnZ);

                ring.setTranslateX(saturnX);
                ring.setTranslateZ(saturnZ);
                ring.setTranslateY(-100);
                if (isSaturnCameraActive) {
                    secondCamera.setTranslateX(saturnX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(saturnZ - 8900);
                    saturnGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text saturnText = new Text("Der Saturn");
                    Text saturnInfo = new Text("Saturn – „Herr der Ringe“ \n" +
                            "Durchmesser: 120.000km, 20.000km weniger als Jupiter \n" +
                            "Mittlerer Radius: [äquatorial]: 60,268km  [polar]: 54,364km \n" +
                            "Masse: 95,152 Me  \n" +
                            "Orbitalperiode: 29,4 Jahre \n" +
                            "Rotationsperiode: 10,233h");
                    saturnInfo.setX(saturnX-645);
                    saturnInfo.setY(200);
                    saturnInfo.setTranslateZ(saturnZ-7500);
                    saturnInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    saturnInfo.setFill(Color.WHITE);
                    saturnText.setX(saturnX-400);
                    saturnText.setY(-1770);
                    saturnText.setTranslateZ(saturnZ-30);
                    saturnText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 140));
                    saturnText.setFill(Color.WHITE);
                    saturnGruppe.getChildren().addAll(saturnText,saturnInfo);
                }

                // Uranus bewegt sich um die Sonne
                uranusAngle += 0.001;  // Winkel für die Umlaufbahn des Uranus
                double uranusX = uranusOrbitRadius * Math.cos(uranusAngle);
                double uranusZ = uranusOrbitRadius * Math.sin(uranusAngle);
                uranus.setTranslateX(uranusX);
                uranus.setTranslateZ(uranusZ);
                uranus.rotateProperty().set(uranus.getRotate() - 0.05);
                if (isUranusCameraActive) {
                    secondCamera.setTranslateX(uranusX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(uranusZ - 8900);
                    uranusGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text uranusText = new Text("Der Uranus");
                    Text uranusInfo = new Text("Uranus \n" +
                            "Mittlerer Radius:[äquatorial]: 25,559km, [polar}: 24,973km \n" +
                            "Masse: 14,536 Me  \n" +
                            "Orbitalperiode: 84,02 Jahre \n" +
                            "Rotationsperiode:17,24h \n" +"Anzahl der Monde: aktuell 62 bekannte Monde, Titan und Enceladus scheinen geologisch aktiv zu sein" );
                    uranusInfo.setX(uranusX-645);
                    uranusInfo.setY(200);
                    uranusInfo.setTranslateZ(uranusZ-7500);
                    uranusInfo.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    uranusInfo.setFill(Color.WHITE);
                    uranusText.setX(uranusX-400);
                    uranusText.setY(-1700);
                    uranusText.setTranslateZ(uranusZ-30);
                    uranusText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 140));
                    uranusText.setFill(Color.WHITE);
                    uranusGruppe.getChildren().addAll(uranusText,uranusInfo);

                }


                // Neptun bewegt sich um die Sonne
                neptuneAngle += 0.0008;  // Winkel für die Umlaufbahn des Neptuns
                double neptuneX = neptuneOrbitRadius * Math.cos(neptuneAngle);
                double neptuneZ = neptuneOrbitRadius * Math.sin(neptuneAngle);
                neptune.setTranslateX(neptuneX);
                neptune.setTranslateZ(neptuneZ);
                neptune.rotateProperty().set(neptune.getRotate() - 0.2);
                if (isNeptuneCameraActive) {
                    secondCamera.setTranslateX(neptuneX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(neptuneZ - 8900);
                    neptuneGruppe.getChildren().removeIf(node -> node instanceof Text);
                    Text neptuneText = new Text("Der Neptun");
                    Text neptuneInfo = new Text("Neptun \n" +
                            "Durchmesser: knapp 50.000 km  \n" +
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
                    neptuneGruppe.getChildren().addAll(neptuneText,neptuneInfo);

                }

                // Sonne dreht sich um sich selbst
                sunRotationAngle -= 0.1; // Geschwindigkeit der Rotation
                sonne.setRotate(sunRotationAngle); // Rotation auf die Sonne anwenden
            }
        };
        timer.start();
    }


    private void initMouseControl(SmartGroup group, Scene scene,Stage stage) {
        Rotate xRotate = new Rotate();
        Rotate yRotate = new Rotate();
        group.getTransforms().addAll(
                xRotate=new Rotate(0, Rotate.X_AXIS),
                yRotate=new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed( event ->{

            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged( event -> {
            angleX.set(anchorAngleX - (anchorY -event.getSceneY()));
            angleY.set(anchorAngleY + (anchorY -event.getSceneX()));

        });

        stage.addEventHandler(ScrollEvent.SCROLL, event ->{
            double delta = event.getDeltaY();

            double currentTranslateZ = group.getTranslateZ();
            double newTranslateZ = group.getTranslateZ() + delta;

            double minZoom = -100000;
            double maxZoom = 20000;

            if (newTranslateZ > minZoom && newTranslateZ < maxZoom) {
                group.translateZProperty().set(newTranslateZ);
            }

        } );

    }



    private ImageView prepareImageView(){
        Image image = new Image(getClass().getResourceAsStream("/images/stars_milky_way.jpg"));
        ImageView imageView = new ImageView(image);


        imageView.setFitHeight(200000);
        imageView.setFitWidth(450000);

        imageView.setTranslateX(-imageView.getFitWidth() / 2); // Horizontal zentrieren
        imageView.setTranslateY(-imageView.getFitHeight() / 2); // Vertikal zentrieren
        imageView.getTransforms().add(new Translate(0,0,70000));
        return imageView;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case E:
                isEarthCameraActive = !isEarthCameraActive;
                switchCamera(secondCamera, isEarthCameraActive,erdGruppe);
                break;
            case Y:
                isMercuryCameraActive = !isMercuryCameraActive;
                switchCamera(secondCamera, isMercuryCameraActive,merkurGruppe);
                break;
            case M:
                isMoonCameraActive = !isMoonCameraActive;
                switchCamera(secondCamera, isMoonCameraActive,mondGruppe);
                break;
            case J:
                isJupiterCameraActive = !isJupiterCameraActive;
                switchCamera(secondCamera, isJupiterCameraActive,jupiterGruppe);
                break;
            case S:
                isSaturnCameraActive = !isSaturnCameraActive;
                switchCamera(secondCamera, isSaturnCameraActive,saturnGruppe);
                break;
            case A:
                isMarsCameraActive = !isMarsCameraActive;
                switchCamera(secondCamera, isMarsCameraActive,marsGruppe);
                break;
            case U:
                isUranusCameraActive = !isUranusCameraActive;
                switchCamera(secondCamera, isUranusCameraActive,uranusGruppe);
                break;
            case N:
                isNeptuneCameraActive = !isNeptuneCameraActive;
                switchCamera(secondCamera, isNeptuneCameraActive,neptuneGruppe);
                break;
            case V:
                isVenusCameraActive = !isVenusCameraActive;
                switchCamera(secondCamera, isVenusCameraActive, venusGruppe);
                break;
             default:
                break;
        }
    }

    private void switchCamera(Camera targetCamera, boolean isActive, Group group) {
        if (isActive) {
            scene.setCamera(targetCamera);
            isAllCameraActive = false;
        } else {
            scene.setCamera(camera);
            group.getChildren().removeIf(node -> node instanceof Text);// Zurück zur Hauptkamera
        }
    }

     public static void main(String[] args) {
        launch();
    }
}
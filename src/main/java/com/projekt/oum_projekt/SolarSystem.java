package com.projekt.oum_projekt;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class SolarSystem extends Application {

    // Bildschirmdimensionen
    public static double width = 0;
    public static double height = 0;

    // Kamera-Status-Flags
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

    // Geschwindigkeitseinstellungen
    private boolean halfSpeed = false;
    private boolean quarterSpeed = false;
    double speed;

    // Kameras
    public PerspectiveCamera camera;
    public PerspectiveCamera secondCamera;
    private KeyCode activeCameraKey = null;

    // GUI Elemente (Das neue HUD)
    private SubScene subScene; // Container für die 3D Welt
    private Label planetNameLabel;
    private Label planetInfoLabel;
    private VBox hudInfoBox;

    // Koordinaten für Saturn (global benötigt für Ring)
    double saturnX;
    double saturnZ;

    // Gruppen
    SmartGroup sunGroup, earthGroup, mercuryGroup, moonGroup, venusGroup,
            saturnGroup, jupiterGroup, marsGroup, uranusGroup, neptuneGroup;

    @Override
    public void start(Stage stage) {
        // Fullscreen Abmessungen holen
        width = Screen.getPrimary().getBounds().getWidth();
        height = Screen.getPrimary().getBounds().getHeight();

        // --- 1. Kameras initialisieren ---
        camera = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(200000);
        camera.translateZProperty().set(-100000);
        camera.setFieldOfView(60);

        secondCamera = new PerspectiveCamera(true);
        secondCamera.setNearClip(1);
        secondCamera.setFarClip(200000);

        // --- 2. Planeten erstellen ---
        // Sonne
        Sun sun = new Sun();
        sunGroup = new SmartGroup();
        sunGroup.getChildren().add(sun.prepareSonne());

        PointLight universeLight = new PointLight(Color.WHITE);
        universeLight.setTranslateX(0);
        universeLight.setTranslateY(0);
        universeLight.setTranslateZ(0);

        universeLight.getExclusionScope().add(sun.getPlanetSphere());

        sunGroup.getChildren().add(universeLight);

        PhongMaterial sunMaterial = (PhongMaterial) sun.getPlanetSphere().getMaterial();
        sunMaterial.setSelfIlluminationMap(sunMaterial.getSelfIlluminationMap());

        AmbientLight sunAmbient = new AmbientLight(Color.WHITE);

        sunGroup.getChildren().add(sunAmbient);




        // Erde
        Earth earth = new Earth();
        earthGroup = new SmartGroup();
        earthGroup.getChildren().add(earth.prepareErde());

        // Merkur
        Mercury mercury = new Mercury();
        mercuryGroup = new SmartGroup();
        mercuryGroup.getChildren().add(mercury.prepareMerkur());

        // Mond
        Moon moon = new Moon();
        moonGroup = new SmartGroup();
        moonGroup.getChildren().add(moon.prepareMond());

        // Venus
        Venus venus = new Venus();
        venusGroup = new SmartGroup();
        venusGroup.getChildren().add(venus.prepareVenus());

        // Saturn
        Saturn saturn = new Saturn();
        saturnGroup = new SmartGroup();
        saturnGroup.getChildren().add(saturn.prepareSaturn());

        // SaturnRing
        SaturnRing saturnRing = new SaturnRing(2046, 30, "/Images/saturn_ring.png");
        saturnGroup.getChildren().add(saturnRing.getRing());

        // Jupiter
        Jupiter jupiter = new Jupiter();
        jupiterGroup = new SmartGroup();
        jupiterGroup.getChildren().add(jupiter.prepareJupiter());

        // Mars
        Mars mars = new Mars();
        marsGroup = new SmartGroup();
        marsGroup.getChildren().add(mars.prepareMars());

        // Uranus
        Uranus uranus = new Uranus();
        uranusGroup = new SmartGroup();
        uranusGroup.getChildren().add(uranus.prepareUranus());

        // Neptun
        Neptune neptune = new Neptune();
        neptuneGroup = new SmartGroup();
        neptuneGroup.getChildren().add(neptune.prepareNeptune());

        // Hierarchie aufbauen (Mond zur Erde, alle zur Sonne wäre logisch,
        // aber wir packen sie hier in eine gemeinsame Welt-Gruppe)
        earthGroup.getChildren().add(moonGroup);

        // --- 3. Die 3D-Welt zusammenbauen ---
        Group world3DGroup = new Group();

        // Hintergrundbild
        world3DGroup.getChildren().add(prepareImageView());

        // Alle Planetengruppen hinzufügen
        world3DGroup.getChildren().addAll(sunGroup, earthGroup, mercuryGroup, venusGroup,
                saturnGroup, jupiterGroup, marsGroup, uranusGroup, neptuneGroup);

        // --- 4. SubScene erstellen (Isoliert 3D von 2D) ---
        // Wir nutzen SceneAntialiasing.BALANCED für schönere Kanten
        subScene = new SubScene(world3DGroup, width, height, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);
        subScene.setCamera(camera); // Die Kamera gehört zur SubScene!

        // --- 5. Das 2D-Overlay (HUD) erstellen ---
        BorderPane hudPane = createHUD();

        // --- 6. Alles stapeln (StackPane) ---
        StackPane mainRoot = new StackPane();
        mainRoot.getChildren().addAll(subScene, hudPane);
        // subScene ist unten (hinten), hudPane liegt oben drauf (vorne)

        // --- 7. Scene und Stage ---
        Scene scene = new Scene(mainRoot);
        scene.setFill(Color.BLACK);

        // Event-Handling auf der Haupt-Scene
        scene.setOnKeyPressed(this::handleKeyPress);

        stage.setTitle("Sonnensystem");
        stage.setScene(scene);
        stage.setFullScreen(true); // Kannst du aktivieren, wenn gewünscht
        stage.show();

        // Animation starten
        prepareAnimation(earth.getPlanetSphere(), moon.getPlanetSphere(), sun.getPlanetSphere(),
                mercury.getPlanetSphere(), venus.getPlanetSphere(), neptune.getPlanetSphere(),
                saturn.getPlanetSphere(), mars.getPlanetSphere(), uranus.getPlanetSphere(),
                jupiter.getPlanetSphere(), saturnRing.getRing());
    }

    /**
     * Erstellt das 2D Interface (HUD) für die Texte
     */
    private BorderPane createHUD() {
        BorderPane hudPane = new BorderPane();

        // Damit Mausklicks durch das leere Pane auf die 3D Welt gehen
        hudPane.setPickOnBounds(false);

        // 1. Label für den Namen
        planetNameLabel = new Label("");
        planetNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
        planetNameLabel.setTextFill(Color.WHITE);

        // 2. Label für die Infos
        planetInfoLabel = new Label("");
        planetInfoLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));
        planetInfoLabel.setTextFill(Color.LIGHTGRAY);
        planetInfoLabel.setWrapText(true); // Automatischer Zeilenumbruch

        // 3. Containerbox für die Infos
        hudInfoBox = new VBox(15); // 15px Abstand zwischen Titel und Text
        hudInfoBox.getChildren().addAll(planetNameLabel, planetInfoLabel);

        // Styling der Box (halbtransparenter schwarzer Hintergrund, abgerundete Ecken)
        hudInfoBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-background-radius: 15; -fx-padding: 20;");
        hudInfoBox.setMaxWidth(400); // Maximale Breite der Infobox
        hudInfoBox.setVisible(false); // Am Anfang unsichtbar

        // Platzierung: Oben Links mit etwas Abstand
        hudPane.setTop(hudInfoBox);
        BorderPane.setMargin(hudInfoBox, new Insets(20));


        Label helpText = new Label("Steuerung: 1/2 (Speed) | E, M, A, Y, U, V, S, J, N (Zoom) | Erneut drücken für Reset");
        helpText.setTextFill(Color.WHITE);
        helpText.setFont(Font.font("Verdana", 14));
        helpText.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 5;");
        hudPane.setBottom(helpText);
        BorderPane.setMargin(helpText, new Insets(10));

        return hudPane;
    }

    private void prepareAnimation(Sphere erde, Sphere mond, Sphere sonne, Sphere merkur,
                                  Sphere venus, Sphere neptune, Sphere saturn, Sphere mars,
                                  Sphere uranus, Sphere jupiter, Cylinder ring) {

        AnimationTimer timer = new AnimationTimer() {
            private double earthAngle = 0;
            private double moonAngle = 0;
            private double mercuryAngle = 0;
            private double venusAngle = 0;
            private double marsAngle = 0;
            private double jupiterAngle = 0;
            private double saturnAngle = 0;
            private double uranusAngle = 0;
            private double neptuneAngle = 0;
            private double sunRotationAngle = 0;

            @Override
            public void handle(long now) {
                // Geschwindigkeit setzen
                if (quarterSpeed) {
                    speed = 4;
                } else if (halfSpeed) {
                    speed = 2;
                } else {
                    speed = 1;
                }

                double sunRadius = 16350;

                // --- ERDE ---
                erde.rotateProperty().set(erde.getRotate() - 2.5 / speed);
                earthAngle += 0.028 / speed;
                double earthOrbitRadius = 1754 + sunRadius;
                double earthX = earthOrbitRadius * Math.cos(earthAngle);
                double earthZ = earthOrbitRadius * Math.sin(earthAngle);
                erde.setTranslateX(earthX);
                erde.setTranslateZ(earthZ);

                if (isEarthCameraActive) {
                    secondCamera.setTranslateX(earthX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(earthZ - 850);
                }

                // --- MOND ---
                moonAngle += 0.02;
                double moonOrbitRadius = 650;
                double moonX = moonOrbitRadius * Math.cos(moonAngle);
                double moonZ = moonOrbitRadius * Math.sin(moonAngle);
                mond.rotateProperty().set(mond.getRotate() - 0.09 / speed);
                mond.setTranslateX(earthX + moonX);
                mond.setTranslateZ(earthZ + moonZ);

                if (isMoonCameraActive) {
                    secondCamera.setTranslateX(earthX + moonX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(earthZ + moonZ - 350);
                }

                // --- MERKUR ---
                mercuryAngle += 0.047 / speed;
                double mercuryOrbitRadius = 678 + sunRadius;
                double mercuryX = mercuryOrbitRadius * Math.cos(mercuryAngle);
                double mercuryZ = mercuryOrbitRadius * Math.sin(mercuryAngle);
                merkur.setTranslateX(mercuryX);
                merkur.setTranslateZ(mercuryZ);
                merkur.rotateProperty().set(merkur.getRotate() - 0.043 / speed);

                if (isMercuryCameraActive) {
                    secondCamera.setTranslateX(mercuryX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(mercuryZ - 1350);
                }

                // --- VENUS ---
                venusAngle += 0.018 / speed;
                double venusOrbitRadius = 1268 + sunRadius;
                double venusX = venusOrbitRadius * Math.cos(venusAngle);
                double venusZ = venusOrbitRadius * Math.sin(venusAngle);
                venus.setTranslateX(venusX);
                venus.setTranslateZ(venusZ);
                venus.rotateProperty().set(venus.getRotate() + 0.010 / speed);

                if (isVenusCameraActive) {
                    secondCamera.setTranslateX(venusX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(venusZ - 2050);
                }

                // --- MARS ---
                marsAngle += 0.008 / speed;
                double marsOrbitRadius = 2670 + sunRadius;
                double marsX = marsOrbitRadius * Math.cos(marsAngle);
                double marsZ = marsOrbitRadius * Math.sin(marsAngle);
                mars.setTranslateX(marsX);
                mars.setTranslateZ(marsZ);
                mars.rotateProperty().set(mars.getRotate() - 2.53 / speed);

                if (isMarsCameraActive) {
                    secondCamera.setTranslateX(marsX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(marsZ - 1050);
                }

                // --- JUPITER ---
                jupiterAngle += 0.002 / speed;
                double jupiterOrbitRadius = 9113 + sunRadius;
                double jupiterX = jupiterOrbitRadius * Math.cos(jupiterAngle);
                double jupiterZ = jupiterOrbitRadius * Math.sin(jupiterAngle);
                jupiter.setTranslateX(jupiterX);
                jupiter.setTranslateZ(jupiterZ);
                jupiter.rotateProperty().set(jupiter.getRotate() - 6.05 / speed);

                if (isJupiterCameraActive) {
                    secondCamera.setTranslateX(jupiterX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(jupiterZ - 8900);
                }

                // --- SATURN ---
                saturnAngle += 0.0006 / speed;
                double saturnOrbitRadius = 16721 + sunRadius;
                saturnX = saturnOrbitRadius * Math.cos(saturnAngle);
                saturnZ = saturnOrbitRadius * Math.sin(saturnAngle);
                saturn.setTranslateX(saturnX);
                saturn.setTranslateZ(saturnZ);
                saturn.rotateProperty().set(saturn.getRotate() - 5.6 / speed);

                // Ring bewegt sich mit Saturn
                ring.setTranslateX(saturnX);
                ring.setTranslateZ(saturnZ);
                ring.setTranslateY(-100);

                if (isSaturnCameraActive) {
                    secondCamera.setTranslateX(saturnX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(saturnZ - 8900);
                }

                // --- URANUS ---
                uranusAngle += 0.0003 / speed;
                double uranusOrbitRadius = 33549 + sunRadius;
                double uranusX = uranusOrbitRadius * Math.cos(uranusAngle);
                double uranusZ = uranusOrbitRadius * Math.sin(uranusAngle);
                uranus.setTranslateX(uranusX);
                uranus.setTranslateZ(uranusZ);
                uranus.rotateProperty().set(uranus.getRotate() - 3.48 / speed);

                if (isUranusCameraActive) {
                    secondCamera.setTranslateX(uranusX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(uranusZ - 8900);
                }

                // --- NEPTUN ---
                neptuneAngle += 0.0002 / speed;
                double neptuneOrbitRadius = 52489 + sunRadius;
                double neptuneX = neptuneOrbitRadius * Math.cos(neptuneAngle);
                double neptuneZ = neptuneOrbitRadius * Math.sin(neptuneAngle);
                neptune.setTranslateX(neptuneX);
                neptune.setTranslateZ(neptuneZ);
                neptune.rotateProperty().set(neptune.getRotate() - 3.73 / speed);

                if (isNeptuneCameraActive) {
                    secondCamera.setTranslateX(neptuneX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(neptuneZ - 8900);
                }

                // --- SONNE ---
                sunRotationAngle -= 0.1 / speed;
                sonne.setRotate(sunRotationAngle);
            }
        };
        timer.start();
    }

    private ImageView prepareImageView() {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/stars_milky_way.jpg")));
        ImageView imageView = new ImageView(image);
        // Hintergrund groß genug machen
        imageView.setFitHeight(200000);
        imageView.setFitWidth(450000);

        imageView.setTranslateX(-imageView.getFitWidth() / 2);
        imageView.setTranslateY(-imageView.getFitHeight() / 2);
        imageView.getTransforms().add(new Translate(0, 0, 70000));
        return imageView;
    }

    private void handleKeyPress(KeyEvent event) {
        if (activeCameraKey != null && !event.getCode().equals(activeCameraKey)) {
            // Ignoriere andere Tasten, wenn eine Kamera aktiv ist
            return;
        }

        switch (event.getCode()) {
            case E:
                isEarthCameraActive = !isEarthCameraActive;
                String earthInfo = "Mittlerer Radius: 6371km\n" +
                        "Masse: 1 Me\n" +
                        "Orbitalperiode: 365,24 Tage\n" +
                        "Rotationsperiode: 23,93h\n" +
                        "Anzahl der Monde: einer\n" +
                        "Distanz zur Sonne: 1,0 AE";
                toggleCamera(KeyCode.E, secondCamera, isEarthCameraActive, "Die Erde", earthInfo);
                break;
            case M:
                isMoonCameraActive = !isMoonCameraActive;
                String moonInfo = "Mittlerer Radius: 1737,5km\n" +
                        "Rotationsperiode: 27,32 Tage\n" +
                        "Orbitalperiode: 27,32 Tage\n" +
                        "Entfernung zur Erde: 384.400km";
                toggleCamera(KeyCode.M, secondCamera, isMoonCameraActive, "Der Mond", moonInfo);
                break;
            case Y:
                isMercuryCameraActive = !isMercuryCameraActive;
                String mercuryInfo = "Mittlerer Radius: 2439,7km\n" +
                        "Masse: 0,055 Me\n" +
                        "Orbitalperiode: 87,97 Tage\n" +
                        "Rotationsperiode: 58,67 Tage\n" +
                        "Monde: keine";
                toggleCamera(KeyCode.Y, secondCamera, isMercuryCameraActive, "Der Merkur", mercuryInfo);
                break;
            case V:
                isVenusCameraActive = !isVenusCameraActive;
                String venusInfo = "Mittlerer Radius: 6051,8km\n" +
                        "Masse: 0,815 Me\n" +
                        "Orbitalperiode: 224,70 Tage\n" +
                        "Atmosphäre: extrem dicht\n" +
                        "Monde: keine";
                toggleCamera(KeyCode.V, secondCamera, isVenusCameraActive, "Die Venus", venusInfo);
                break;
            case A:
                isMarsCameraActive = !isMarsCameraActive;
                String marsInfo = "Mittlerer Radius: 3386,2km\n" +
                        "Masse: 0,107 Me\n" +
                        "Orbitalperiode: 687 Tage\n" +
                        "Monde: Phobos & Deimos\n" +
                        "Distanz zur Sonne: 1,5 AE";
                toggleCamera(KeyCode.A, secondCamera, isMarsCameraActive, "Der Mars", marsInfo);
                break;
            case J:
                isJupiterCameraActive = !isJupiterCameraActive;
                String jupiterInfo = "Radius: 71.492km (Äquator)\n" +
                        "Masse: 317,8 Me\n" +
                        "Orbitalperiode: 11,86 Jahre\n" +
                        "Monde: 79 (u.a. Io, Europa)";
                toggleCamera(KeyCode.J, secondCamera, isJupiterCameraActive, "Der Jupiter", jupiterInfo);
                break;
            case S:
                isSaturnCameraActive = !isSaturnCameraActive;
                String saturnInfo = "Radius: 60.268km (Äquator)\n" +
                        "Masse: 95,152 Me\n" +
                        "Orbitalperiode: 29,4 Jahre\n" +
                        "Monde: 82 (u.a. Titan)";
                toggleCamera(KeyCode.S, secondCamera, isSaturnCameraActive, "Der Saturn", saturnInfo);
                break;
            case U:
                isUranusCameraActive = !isUranusCameraActive;
                String uranusInfo = "Radius: 25.559km\n" +
                        "Masse: 14,536 Me\n" +
                        "Orbitalperiode: 84,02 Jahre\n" +
                        "Monde: 27";
                toggleCamera(KeyCode.U, secondCamera, isUranusCameraActive, "Der Uranus", uranusInfo);
                break;
            case N:
                isNeptuneCameraActive = !isNeptuneCameraActive;
                String neptuneInfo = "Radius: 24.622km\n" +
                        "Masse: 17,147 Me\n" +
                        "Orbitalperiode: 164,79 Jahre\n" +
                        "Monde: 14 (u.a. Triton)";
                toggleCamera(KeyCode.N, secondCamera, isNeptuneCameraActive, "Der Neptun", neptuneInfo);
                break;
            case DIGIT1:
                halfSpeed = !halfSpeed;
                break;
            case DIGIT2:
                quarterSpeed = !quarterSpeed;
                break;
            default:
                break;
        }
    }

    private void toggleCamera(KeyCode key, Camera targetCamera, boolean isActive, String name, String infoText) {
        if (isActive) {
            // 1. Kamera umschalten (auf die SubScene, nicht die Haupt-Scene)
            subScene.setCamera(targetCamera);
            activeCameraKey = key;
            isAllCameraActive = false;

            // 2. HUD aktualisieren und anzeigen
            planetNameLabel.setText(name);
            planetInfoLabel.setText(infoText);
            hudInfoBox.setVisible(true);

        } else {
            // 1. Zurück zur Hauptkamera
            subScene.setCamera(camera);
            activeCameraKey = null;
            isAllCameraActive = true;

            // 2. HUD ausblenden
            hudInfoBox.setVisible(false);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
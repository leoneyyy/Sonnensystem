package com.projekt.oum_projekt;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.SubScene;
import javafx.util.Duration;


import java.io.IOException;

public class SonnenSystem extends Application {
    public static final int width = 1920;
    public static final int height = 1080;

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
        //text.setScaleX(55); // Beispielwert, anpassen nach Bedarf
        //text.setScaleY(55);
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
                    erdText.setX(earthX-40);
                    erdText.setY(-175);
                    erdText.setTranslateZ(earthZ);
                    erdText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
                    erdText.setFill(Color.WHITE);
                    erdGruppe.getChildren().add(erdText);
                }

                mond.rotateProperty().set(mond.getRotate() + 0.2);

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
                    secondCamera.setTranslateZ(earthZ+moonZ - 350); // Positioniere die Kamera weiter entfernt
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
                }

                // Mars bewegt sich um die Sonne
                marsAngle += 0.003;  // Winkel für die Umlaufbahn des Mars
                double marsX = marsOrbitRadius * Math.cos(marsAngle);
                double marsZ = marsOrbitRadius * Math.sin(marsAngle);
                mars.setTranslateX(marsX);
                mars.setTranslateZ(marsZ);
                if (isMarsCameraActive) {
                    secondCamera.setTranslateX(marsX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(marsZ - 1050);
                }

                // Jupiter bewegt sich um die Sonne
                jupiterAngle += 0.002;  // Winkel für die Umlaufbahn des Jupiters
                double jupiterX = jupiterOrbitRadius * Math.cos(jupiterAngle);
                double jupiterZ = jupiterOrbitRadius * Math.sin(jupiterAngle);
                jupiter.setTranslateX(jupiterX);
                jupiter.setTranslateZ(jupiterZ);

                if (isJupiterCameraActive) {
                    secondCamera.setTranslateX(jupiterX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(jupiterZ - 8900);
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
                    secondCamera.setTranslateZ(saturnZ - 8050);
                }

                // Uranus bewegt sich um die Sonne
                uranusAngle += 0.001;  // Winkel für die Umlaufbahn des Uranus
                double uranusX = uranusOrbitRadius * Math.cos(uranusAngle);
                double uranusZ = uranusOrbitRadius * Math.sin(uranusAngle);
                uranus.setTranslateX(uranusX);
                uranus.setTranslateZ(uranusZ);
                if (isUranusCameraActive) {
                    secondCamera.setTranslateX(uranusX);
                    secondCamera.setTranslateY(0);
                    secondCamera.setTranslateZ(uranusZ - 8050);
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
                    secondCamera.setTranslateZ(neptuneZ - 8050);
                }

                // Sonne dreht sich um sich selbst
                sunRotationAngle -= 0.2; // Geschwindigkeit der Rotation
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
        imageView.setFitWidth(350000);

        imageView.setTranslateX(-imageView.getFitWidth() / 2); // Horizontal zentrieren
        imageView.setTranslateY(-imageView.getFitHeight() / 2); // Vertikal zentrieren
        imageView.getTransforms().add(new Translate(0,0,70000));
        return imageView;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case E:
                isEarthCameraActive = !isEarthCameraActive;
                switchCamera(secondCamera, isEarthCameraActive);
                break;
            case Y:
                isMercuryCameraActive = !isMercuryCameraActive;
                switchCamera(secondCamera, isMercuryCameraActive);
                break;
            case M:
                isMoonCameraActive = !isMoonCameraActive;
                switchCamera(secondCamera, isMoonCameraActive);
                break;
            case J:
                isJupiterCameraActive = !isJupiterCameraActive;
                switchCamera(secondCamera, isJupiterCameraActive);
                break;
            case S:
                isSaturnCameraActive = !isSaturnCameraActive;
                switchCamera(secondCamera, isSaturnCameraActive);
                break;
            case A:
                isMarsCameraActive = !isMarsCameraActive;
                switchCamera(secondCamera, isMarsCameraActive);
                break;
            case U:
                isUranusCameraActive = !isUranusCameraActive;
                switchCamera(secondCamera, isUranusCameraActive);
                break;
            case N:
                isNeptuneCameraActive = !isNeptuneCameraActive;
                switchCamera(secondCamera, isNeptuneCameraActive);
                break;
            case V:
                isVenusCameraActive = !isVenusCameraActive;
                switchCamera(secondCamera, isVenusCameraActive);
                break;
            default:
                break;
        }
    }

    private void switchCamera(Camera targetCamera, boolean isActive) {
        if (isActive) {
            scene.setCamera(targetCamera);
            isAllCameraActive = false;
        } else {
            scene.setCamera(camera); // Zurück zur Hauptkamera
        }
    }

     public static void main(String[] args) {
        launch();
    }
}
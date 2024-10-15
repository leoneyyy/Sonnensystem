package com.projekt.oum_projekt;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.scene.SubScene;
import javafx.util.Duration;


import java.io.IOException;

public class SonnenSystem extends Application {
    public static final int width = 1400;
    public static final int height = 800;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);


    @Override
    public void start(Stage stage) throws IOException {
        //Kamera
        PerspectiveCamera camera  = new PerspectiveCamera(true);
        camera.setNearClip(1);
        camera.setFarClip(15000);
        camera.translateZProperty().set(-5000);
        camera.setFieldOfView(1000);

        //Sonne
        Sonne sonne = new Sonne();
        SmartGroup sonnenGruppe = new SmartGroup();
        sonnenGruppe.getChildren().add(sonne.prepareSonne());
        Node sonnenNode= sonne.prepareSonne();

        //Erde
        Erde erde  = new Erde();
        SmartGroup erdGruppe = new SmartGroup();
        erdGruppe.getChildren().add(erde.prepareEarth());
        Node erdeNode= erde.prepareEarth();

        //Merkur
        Merkur merkur = new Merkur();
        SmartGroup merkurGruppe = new SmartGroup();
        merkurGruppe.getChildren().add(merkur.prepareMerkur());
        Node merkurNode= merkur.prepareMerkur();


        //Mond
        Mond mond  = new Mond();
        SmartGroup mondGruppe = new SmartGroup();
        mondGruppe.getChildren().add(mond.prepareMond());
        Node mondNode= mond.prepareMond();




        Group root = new Group();

        root.getChildren().add(sonnenGruppe);
        //root.getChildren().add(prepareImageView());




        erdGruppe.getChildren().add(mondGruppe);
        sonnenGruppe.getChildren().add(erdGruppe);
        sonnenGruppe.getChildren().add(merkurGruppe);

        //Scene
        Scene scene = new Scene(root, width, height, true,null);
        scene.setFill(Color.BLACK);
        scene.setCamera(camera);


        initMouseControl(sonnenGruppe,scene,stage);



        stage.setTitle("Sonnensystem");
        stage.setScene(scene);
        stage.show();

        prepareAnimation(erde.getErde(), mond.getMond(), sonne.getSonne(), merkur.getMerkur());

    }
    private void prepareAnimation(Sphere erde, Sphere mond, Sphere sonne, Sphere merkur) {
        AnimationTimer timer = new AnimationTimer() {
            private double earthAngle = 0;    // Winkel für die Umlaufbahn der Erde um die Sonne
            private double moonAngle = 0;     // Winkel für die Umlaufbahn des Mondes um die Erde
            private double mercuryAngle = 0;  // Winkel für die Umlaufbahn des Merkurs um die Sonne

            private final double sunRadius = 1000;        // Größere Darstellung der Sonne
            private final double earthOrbitRadius = 6000; // Entfernung Erde-Sonne
            private final double moonOrbitRadius = 650;    // Entfernung Mond-Erde
            private final double mercuryOrbitRadiusX = 1900;// Entfernung Merkur-Sonne (näher zur Sonne)
            private final double mercuryOrbitRadiusZ = 1400;

            @Override
            public void handle(long now) {
                // Erde rotiert um sich selbst
                erde.rotateProperty().set(erde.getRotate() + 0.2);

                // Erde bewegt sich in einer Umlaufbahn um die Sonne
                earthAngle += 0.005;  // Winkel für die Umlaufbahn der Erde
                double earthX = earthOrbitRadius * Math.cos(earthAngle);  // X-Koordinate der Erde
                double earthZ = earthOrbitRadius * Math.sin(earthAngle);  // Z-Koordinate der Erde
                erde.setTranslateX(earthX);
                erde.setTranslateZ(earthZ);

                // Mond bewegt sich in einer Umlaufbahn um die Erde
                moonAngle += 0.02;  // Schnellerer Winkel für die Umlaufbahn des Mondes
                double moonX = moonOrbitRadius * Math.cos(moonAngle);  // X-Koordinate des Mondes relativ zur Erde
                double moonZ = moonOrbitRadius * Math.sin(moonAngle);  // Z-Koordinate des Mondes relativ zur Erde
                mond.setTranslateX(earthX + moonX);  // Mond positionieren relativ zur Erde
                mond.setTranslateZ(earthZ + moonZ);

                // Merkur bewegt sich in einer schnelleren Umlaufbahn um die Sonne
                mercuryAngle += 0.01;  // Schnellerer Winkel für die Umlaufbahn des Merkurs
                double mercuryX = mercuryOrbitRadiusX * Math.cos(mercuryAngle);  // X-Koordinate des Merkurs
                double mercuryZ = mercuryOrbitRadiusZ * Math.sin(mercuryAngle);  // Z-Koordinate des Merkurs
                merkur.setTranslateX(mercuryX);
                merkur.setTranslateZ(mercuryZ);
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
            double maxZoom = 1800;

            if (newTranslateZ > minZoom && newTranslateZ < maxZoom) {
                group.translateZProperty().set(newTranslateZ);
            }

        } );

    }


    private ImageView prepareImageView(){
        Image image = new Image(getClass().getResourceAsStream("/images/stars.jpg"));
        ImageView imageView = new ImageView(image);


        imageView.setPreserveRatio(true);
        imageView.getTransforms().add(new Translate(-image.getWidth()/2, -image.getHeight()/2, 1000));
        return imageView;
    }












    public static void main(String[] args) {
        launch();
    }
}
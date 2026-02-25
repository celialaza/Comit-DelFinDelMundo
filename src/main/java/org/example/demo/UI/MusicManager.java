package org.example.demo.UI;

import javafx.scene.media.AudioClip;
import java.net.URL;

public class MusicManager {
    private static AudioClip introClip;
    private static AudioClip finalClip;
    private static AudioClip selectionClip;
    private static AudioClip clickClip;

    private static AudioClip backgroundClip;

    public static void playBackgroundMusic() {
        if (backgroundClip == null) {
            try {
                // Supongamos que el archivo se llama cancionAmbiente.mp3
                URL resource = MusicManager.class.getResource("/org/example/demo/cancionAmbiente.mp3");
                if (resource != null) {
                    backgroundClip = new AudioClip(resource.toExternalForm());
                    backgroundClip.setCycleCount(AudioClip.INDEFINITE); // Bucle infinito
                    backgroundClip.setVolume(0.2); // Muy bajito (20%)
                    backgroundClip.play();
                    System.out.println("✅ Música de ambiente constante iniciada.");
                }
            } catch (Exception e) {
                System.err.println("Error Background: " + e.getMessage());
            }
        }
    }
    public static void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip = null;
        }
    }

    public static void playIntroMusic() {
        stopFinalMusic();
        stopSelectionMusic();
        if (introClip == null) {
            try {
                URL resource = MusicManager.class.getResource("/org/example/demo/cancionIntroComite.mp3");
                if (resource != null) {
                    introClip = new AudioClip(resource.toExternalForm());
                    introClip.setCycleCount(AudioClip.INDEFINITE);
                    introClip.setVolume(0.6);
                    introClip.play();
                }
            } catch (Exception e) { System.err.println("Error Intro: " + e.getMessage()); }
        }
    }

    public static void stopIntroMusic() {
        if (introClip != null) {
            introClip.stop();
            introClip = null;
        }
    }

    // MODIFICADO: Ahora permite reiniciar la canción si se llama otra vez
    public static void playSelectionMusic() {
        if (selectionClip != null) {
            selectionClip.stop(); // Lo paramos para que reinicie desde el segundo 0
        }
        try {
            if (selectionClip == null) {
                URL resource = MusicManager.class.getResource("/org/example/demo/cancionIndividualGrupo.mp3");
                if (resource != null) {
                    selectionClip = new AudioClip(resource.toExternalForm());
                }
            }
            if (selectionClip != null) {
                selectionClip.setCycleCount(1);
                selectionClip.setVolume(0.7);
                selectionClip.play();
            }
        } catch (Exception e) { System.err.println("Error Selección: " + e.getMessage()); }
    }

    public static void stopSelectionMusic() {
        if (selectionClip != null) {
            selectionClip.stop();
            selectionClip = null;
        }
    }

    public static void playFinalMusic() {
        if (finalClip == null) {
            try {
                URL resource = MusicManager.class.getResource("/org/example/demo/cancionFinal.mp3");
                if (resource != null) {
                    finalClip = new AudioClip(resource.toExternalForm());
                    finalClip.setCycleCount(1);
                    finalClip.setVolume(0.8);
                    finalClip.play();
                }
            } catch (Exception e) { System.err.println("Error Final: " + e.getMessage()); }
        }
    }

    public static void stopFinalMusic() {
        if (finalClip != null) {
            finalClip.stop();
            finalClip = null;
        }
    }
    public static void playClickSound() {
        try {
            if (clickClip == null) {
                URL resource = MusicManager.class.getResource("/org/example/demo/cancionClick.mp3");
                if (resource != null) {
                    clickClip = new AudioClip(resource.toExternalForm());
                    clickClip.setVolume(0.5); // Volumen moderado para no asustar
                }
            }
            if (clickClip != null) {
                clickClip.play();
            }
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido de clic: " + e.getMessage());
        }
    }
}
package fingerprinting;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

/*********************************************************************
* AudioFingerprinting is the class which contains the main method.
*
* Usage:     java AudioFingerprinting -matching | -add "songTitle"
* Example (1): java AudioFingerprinting -add "Wish you were here"
* Example (2): java AudioFingerprinting -matching);
* 
**********************************************************************/

public class AudioFingerprinting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        boolean exit = false;

        // Controlling input params
        switch (args.length) {
            case 1:
                // If we want to run matching
                if (args[0].equals("-matching")) {
                    try {
                        AudioRecognizer fingerPrintingExample = new AudioRecognizer();
                        // For matching we provide an empty string and isMatching=true
                        fingerPrintingExample.listening("", true);
                        exit = true;
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(AudioFingerprinting.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 2:
                // If we want to add a new song to the song repository
                String songId = args[1];
                if (args[0].equals("-add")) {
                    try {
                        AudioRecognizer fingerPrintingExample = new AudioRecognizer();
                        // For adding a song we provide a string with its identifier (i.e., title) and isMatching=false
                        fingerPrintingExample.listening(songId, false);
                        exit = true;
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(AudioFingerprinting.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
        if (!exit) {
            System.err.println("\nUsage:     java AudioFingerprinting -matching | -add \"songTitle\"");
            System.err.println("Example (1): java AudioFingerprinting -add \"Wish you were here\"");
            System.err.println("Example (2): java AudioFingerprinting -matching\n\n");
        }    
    }
}

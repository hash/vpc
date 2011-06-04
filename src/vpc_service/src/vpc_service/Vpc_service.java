
package vpc_service;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.awt.AWTException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vpc_service {

    private static Obey o;
    
    public static void main(String[] args) throws IOException, AWTException {        
        o = new Obey();
        
        ConfigurationManager cm;

        if (args.length > 0) {
            cm = new ConfigurationManager(args[0]);
        } else {
            cm = new ConfigurationManager(Vpc_service.class.getResource("vpc_service.config.xml"));
            //cm = new ConfigurationManager("file:/../../vpc_service.config.xml"); //url jeśli config byłby w katalogu razem z .gram
        }
        

        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        // start the microphone or exit if the programm if this is not possible
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        //System.out.println("Say: (chrome)");
        o.cmdList();

        // loop the recognition until the programm exits.
        while (true) {
            System.out.println("Wypowiedz komendę!  |  ctrl-C - quit.\n");

            Result result = recognizer.recognize();

            if (result != null) {
                String resultText = result.getBestFinalResultNoFiller();
                System.out.println("komenda: " + resultText + '\n');
                try {
                    o.command(resultText);
                } catch (AWTException ex) {
                    Logger.getLogger(Vpc_service.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                System.out.println("Nie słyszę Cie!\n");
            }
        }
    }
}

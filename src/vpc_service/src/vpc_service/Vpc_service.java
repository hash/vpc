package vpc_service;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.awt.AWTException;
import java.io.IOException;
import javax.swing.JOptionPane;
/**
 * Główna klasa usługi, nicjalizuje komponenty odpowiedzialne za nasłuchiwanie i 
 * rozpoznawanie komend głosowych
 */
public class Vpc_service {

    /**
     * Obiekt klasy Obey, służący do obsługi przechwyconych komend.
     */
    private static Obey o;
    /**
     * Konstruktor
     * @param args  Parametry uruchamiania. Jako parametr można podać ścieżkę do 
     *              pliku konfiguracyjnego, który zostanie użyty zamiast domyślnego.
     * @throws IOException  błąd odczytu pliku konfiguratora.
     * @throws AWTException błąd inicjalizacji obiektu Obey
     */
    public static void main(String[] args) throws IOException, AWTException {        
        o = new Obey();
        
        /**
         * Inicjalizacja menadzera konfiguracji Sphinx
         */
        ConfigurationManager cm;

        if (args.length > 0) {
            cm = new ConfigurationManager(args[0]);
        } else {
            cm = new ConfigurationManager(Vpc_service.class.getResource("vpc_service.config.xml"));
            //cm = new ConfigurationManager("file:/../../vpc_service.config.xml"); //url jeśli config byłby w katalogu razem z .gram
        }
        /**
         * Inicjalizacja komponentu do rozpoznawania komend.
         */
        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        /**
         * Inicjalizacja komponentu do obsługi mikrofonu.
         * W przypadku, gdy nie można uruchomić mikrofonu usługa zostanie wyłączona.
         */
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        //Lista komend (konsola)
        o.cmdList();

        /**
         * Pętla przechwytywania i rozpoznawania komend głosowych.
         */
        while (true) {
            System.out.println("Wypowiedz komendę!  |  ctrl-C - quit.\n");

            //Obiekt klasy przetwarzania wyniku.
            Result result = recognizer.recognize();

            if (result != null) {
                String resultText = result.getBestFinalResultNoFiller();
                System.out.println("komenda: " + resultText + '\n');
                try {
                    //Przekazywanie wyniku klasyfikacji do obiektu Obey
                    o.command(resultText);
                } catch (AWTException ex) {
                    JOptionPane.showMessageDialog(null, "Nie można zainicjalizować "
                            + "komponentu obsługi zdarzeń! Uruchom ponownie aplikacje.");
                    return;
                }                
            } else {
                System.out.println("Nie słyszę Cie!\n");
            }
        }
    }
}

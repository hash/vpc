package vpc_service;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        SystemTray tray = SystemTray.getSystemTray();
        Image img = Toolkit.getDefaultToolkit().getImage("../config/ico.png");
        PopupMenu popup = new PopupMenu();
        
        MenuItem mItem1 = new MenuItem("Zakończ");
        mItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popup.add(mItem1);        
        TrayIcon trayIcon = new TrayIcon(img, "VoicePC", popup);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "Nie można zainicjalizować ikonki "
                    + "zasobnika! Uruchom ponownie aplikację.");
        }
        

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

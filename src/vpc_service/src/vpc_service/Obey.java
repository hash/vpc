package vpc_service;


import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
/**
 * Klasa obsługi komend, przechowuje listę komend w kontenerze HashMap.
 */
public class Obey {

    /**
     * Mapa przechowująca listę komend i ścieżki do uruchamianych aplikacji i zmienne 
     * pomocnicze mapy. 
     */
    private HashMap<String,String> cmdList;
    private Set set;
    private Iterator it;
    /**
     * Objekt typu Command obsługujący wbudowane komendy emulujące skróty klawiszowe.
     */
    private Command command;
    /**
     * Konstruktor, inicjalizuje obiekt klasy Command, wywołuje metodę zczytującą
     * plik komend
     * @throws AWTException błąd inicjalizacji obiektu Command
     */
    public Obey() throws AWTException
    {   
        command = new Command();
        try {
            readCmdList();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Nie można znaleźć pliku komend! "
                    + "Sprawdź lokalizację i uruchom ponownie aplikacje.");
            ex.printStackTrace();
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Błąd odczytu pliku!");
            ex.printStackTrace();
            return;
        }
    }

    /**
     * Metoda obsługi komend, sprawdza typ komendy (action/run) i wykonuję zadanie.
     * @param cmd   Obiekt String przechowujący rozpoznaną przez Recognizer komendę.
     * @throws IOException błąd odczytu obiektu Runtime przy uruchamianiu aplikacji
     * @throws AWTException błąd wykonywania komendy-skrótu
     */
    public void command(String cmd) throws IOException, AWTException
    {
        Runtime rt = Runtime.getRuntime();
        
        if (cmd.split(" ")[0].equals("run"))
        {
            if(cmdList.containsKey(cmd))
            {
                try {
                    rt.exec(cmdList.get(cmd));
                } catch (Exception e) {
                    return;
                }
            }
        } else if(cmd.split(" ")[0].equals("action")) {
             if (cmd.split(" ")[1].equals("close")) {
                 command.close();
            } else if (cmd.split(" ")[1].equals("hide")) {
                 command.hideAll();
            } else if (cmd.split(" ")[1].equals("show")) {            
                 command.showAll();
            } else if(cmd.split(" ")[1].equals("scroll")) {
                if (cmd.split(" ")[2].equals("down")) {
                     command.scrollDown();
                } else if (cmd.split(" ")[2].equals("up")) {
                     command.scrollUp();
                }
            } else if (cmd.split(" ")[1].equals("search")) {            
                 command.search();
            } else if (cmd.split(" ")[1].equals("save")) {            
                 command.save();
            }else if(cmd.split(" ")[1].equals("switch")) {
                if (cmd.split(" ")[2].equals("left")) {    
                     command.switchLeft();
                } else if (cmd.split(" ")[2].equals("right")) {             
                     command.switchRight();
                }
            }
        }
        else if(cmdList.containsKey(cmd))
        {
            try {
                rt.exec(cmdList.get(cmd));
            } catch (Exception e) {
                return;
            }
        }
    }
    
    /**
     * Wczytywanie listy komend i wprowadzanie ich do hash-mapy.
     * @throws FileNotFoundException Brak pliku komend
     * @throws IOException Błąd odczytu pliku
     */
    private void readCmdList() throws FileNotFoundException, IOException
    {
        cmdList = new HashMap();
        FileReader fr = new FileReader("../config/cmdlist.txt");
        BufferedReader br = new BufferedReader(fr);
        String s;
        while((s = br.readLine()) != null)
        {
            if(s.contains("\t"))
                if(s.split("\t").length == 4)
                    //przypadek z wczytywaniem dodatkowych opcji
                    cmdList.put(s.split("\t")[1], s.split("\t")[2] + " " + s.split("\t")[3]);
                else
                    cmdList.put(s.split("\t")[1], s.split("\t")[2]);
            else 
                cmdList.put(s, " ");

	}
        fr.close();
        set = cmdList.entrySet();
        it = set.iterator();
    }
    
    /**
     * Metoda wyświetlająca dostępne komendy (tylko konsola)
     */
    public void cmdList()
    {
        while(it.hasNext())
        {
            Map.Entry me = (Map.Entry)it.next();
            System.out.println(me.getKey() + " : " + me.getValue());
        }
        //wbudowane komentarze do obsługi podstawowych akcji windowsowskich (nieedytowalne, nieusuwalne)
        System.out.println("action + (show all | close | hide all | switch right | switch left | scroll down | scroll up | cancel | search | save)");
    }
}


package vpc_conf;

import java.io.*;
import java.net.URL;
import com.sun.speech.freetts.lexicon.LetterToSound;
import com.sun.speech.freetts.lexicon.LetterToSoundImpl;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Klasa do kontroli słownika, konwersja słów na postać słownikową, sprawdzanie
 * dostępności słów. Wykorzystuje narzędzia do wydobywania fonemów z biblioteki FreeTTS.
 */
public class LTS {

    //Obiekt konwertera
    private LetterToSound lts = null;
    //Listy słów w słowniku oraz słów komendy do dodania.
    private ArrayList<String> words, cmdLTS;
    //Zmienna pomocnicza
    private boolean exists = true;

    /**
     * Konstruktor. Inizjalizuje obiekty pól do obsługi konwertera.
     */
    public LTS() {    
        words = new ArrayList<String>();
        cmdLTS = new ArrayList<String>();
    }
    /**
     * Rozdziela podaną komendę na wyrazy i konwertuje na postać słownikową
     * @param word podana komenda do przekonwertowania
     * @throws IOException błąd odczytu konwertera cmulex
     */
    public void getLTS(String word) throws IOException{
        //inicjalizacja konwertera cmulex do wydobywania fonemow ze słowa
        lts = new LetterToSoundImpl(new URL("file:cmulex/cmulex_lts.bin"),
                                    true);
        //jeśli komenda składa się z więcej niżjednego słowa rozdzielamy je i
        //sprawdzamy każde słowo osobno.
        if (word.contains(" ")) {
            for (int w = 0 ; w < word.split(" ").length ; w++) {
                splitWord(word.split(" ")[w]);
            }
        } else {
            //w przypadku jednego słowa od razu przechodzimy do konwersji.
            splitWord(word);              
        }         
    }
    //konwersja pojedynczego słowa na postać słownikową
    //np: befsztyk -> BEFSZTYK B EH F SH T IH K
    private void splitWord(String word){
        String[] ltspM_phone_array;
        String phones = "";
        ltspM_phone_array = lts.getPhones(word, null);
        //System.out.print(
        //        "Word: " + word.toUpperCase() + " Phones: ");
        phones += word.toUpperCase(); //słowa muszą być wpisane do słownika dużymi literami
        phones += " ";
        //zmiana fonemów AX na AH - konwerter cmulex działa w nieco innym standardzie niż
        // Sphinx i przez niektóre fonemy się różnią - różnica między brzmieniem
        // AX i AH jest w tym wypadku pomijalna
        for (int i = 0 ; i < ltspM_phone_array.length ; i++) {
            if (ltspM_phone_array[i].equals("ax")) {
                ltspM_phone_array[i] = "ah";
            }
            if (ltspM_phone_array[i].endsWith("1") || ltspM_phone_array[i].
                    endsWith("2")) {
                ltspM_phone_array[i] = ltspM_phone_array[i].substring(0,
                                                                      ltspM_phone_array[i].
                        length() - 1);
            }
            //System.out.print(ltspM_phone_array[i].toUpperCase() + " ");
            phones += " ";
            phones += ltspM_phone_array[i].toUpperCase();
        }
        cmdLTS.add(phones);    
    }
    /**
     * Dodawanie otrzymanych słów do słownika pod odpowiednią postacią
     * @throws FileNotFoundException brak pliku słownika
     * @throws IOException błąd odczytu/zapisu pliku
     */
    public void addToDict() throws FileNotFoundException, IOException
    {
        for(int i = 0; i < cmdLTS.size(); i++)
            words.add(cmdLTS.get(i));  
        saveDict();   
        exists = true;
        cmdLTS.clear();
    }
    //wczytywanie pliku słownika do pamięci - lista words
    private void readDict() throws FileNotFoundException, IOException
    {
        words.clear();
        String s;
        FileReader fr = new FileReader("../config/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
        BufferedReader br = new BufferedReader(fr);
        while((s = br.readLine()) != null) {
            for(int i = 0; i < cmdLTS.size(); i++) {
                    if(s.split(" ")[0].equals(cmdLTS.get(i).split(" ")[0])) {
                        cmdLTS.remove(i);   
                    }
            }
            words.add(s);
	}
        if(cmdLTS.isEmpty()) exists = true;
        else exists = false;
        br.close();
        fr.close();      
    }
    //zapisywanie listy words do pliku słownika
    private void saveDict() throws FileNotFoundException, IOException
    {
        FileWriter fw = new FileWriter("../config/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < words.size(); i++)
        {
            bw.write(words.get(i));
            bw.newLine();
        }
        bw.close();
        fw.close();  
    }
    /**
     * Sprawdza czy podane w komendzie słowa znajdują się w słowniku. Selekcja słów
     * przeprowadzana jest podczas wczytywania pliku słownika do pamięci
     * @param cmd podana komenda
     * @return <code>true</code> - jeśli wszystkie ze słów znajdują sie w słowniku
     *         <code>false</code> - jeśli przynajmniej jednego ze słów nie ma w słowniku
     */
    public boolean cmdExists(String cmd)
    {
        try {
            getLTS(cmd);
            words.clear();
            readDict();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Nie znaleziono pliku słownika! "
                    + "Sprawdź czy wszystkie pliki są na swoich miejscach i spróbuj "
                    + "ponownie uruchomić aplikacje");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Nie można odczytać pliku!");
        } 
        return exists;
    }
}

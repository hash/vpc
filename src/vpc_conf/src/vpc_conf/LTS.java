
package vpc_conf;

import java.io.*;
import java.net.URL;
import com.sun.speech.freetts.lexicon.LetterToSound;
import com.sun.speech.freetts.lexicon.LetterToSoundImpl;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class LTS {

    LetterToSound lts = null;
    ArrayList<String> words, cmdLTS, cmdWords;
    boolean exists = true;

    public LTS() {    
        words = new ArrayList<String>();
        cmdLTS = new ArrayList<String>();
        cmdWords = new ArrayList<String>();
    }
    //konwersja słów z komendy do postaci slownikowej
    public void getLTS(String word) throws IOException{
        lts = new LetterToSoundImpl(new URL("file:cmulex/cmulex_lts.bin"),
                                    true);
        if (word.contains(" ")) {
            System.out.println("komenda złożona");
            for (int w = 0 ; w < word.split(" ").length ; w++) {
                splitWord(word.split(" ")[w]);
                cmdWords.add(word.split(" ")[w]);
            }
        } else {
            System.out.println("komenda pojedyncza");
            splitWord(word);
            cmdWords.add(word);                
        }
        System.out.println(" ");
        for(int i = 0; i < cmdLTS.size(); i++)
            System.out.println(cmdLTS.get(i));
        for(int i = 0; i < cmdWords.size(); i++)
            System.out.println(cmdWords.get(i));
            
    }
    //konwersja pojedynczego słowa
    private void splitWord(String word){
        String[] ltspM_phone_array;
        String phones = "";
        ltspM_phone_array = lts.getPhones(word, null);
        System.out.print(
                "Word: " + word.toUpperCase() + " Phones: ");
        phones += word.toUpperCase();
        phones += " ";
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
            System.out.print(ltspM_phone_array[i].toUpperCase() + " ");
            phones += " ";
            phones += ltspM_phone_array[i].toUpperCase();
        }
        cmdLTS.add(phones);
        System.out.println();
        System.out.println("sowo dodane do listy");        
    }
    
    public boolean addToDict() throws FileNotFoundException, IOException
    {
        for(int i = 0; i < cmdLTS.size(); i++)
            words.add(cmdLTS.get(i));
        System.out.println("" + words.size() + " słów");     
        saveDict();
        System.out.println("słownik zpaisany");     
        exists = true;
        cmdLTS.clear();
        cmdWords.clear();
        return true;
    }
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
                        System.out.println(s + "wykryto słowo w "
                                + "słowniku, usuwanie z listy do dodania: " + cmdWords.get(i));
                    }
            }
            words.add(s);
	}
        if(cmdLTS.isEmpty()) exists = true;
        else exists = false;
        br.close();
        fr.close();      
        System.out.println("\nwczytalem plik slownika...");
        System.out.println("" + words.size() + " słów"); 
    }
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
        System.out.println("zapisalem do slownika");
    }
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

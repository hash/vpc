
package vpc_conf;

import java.io.*;
import java.net.URL;
import com.sun.speech.freetts.lexicon.LetterToSound;
import com.sun.speech.freetts.lexicon.LetterToSoundImpl;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LTS {

    LetterToSound lts = null;

    public LTS() {        
    }
    public void getLTS(String word) {
        try {
            lts = new LetterToSoundImpl(new URL("file:cmulex/cmulex_lts.bin"), true); 
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        String[] ltspM_phone_array;
        String phones = "";
        ltspM_phone_array = lts.getPhones(word, null);
        System.out.print("Word: " + word.toUpperCase() + " Phones: ");
        phones += word.toUpperCase();
        phones += "\t";
        for (int i = 0; i < ltspM_phone_array.length; i++) {
            if(ltspM_phone_array[i].equals("ax")) ltspM_phone_array[i] = "ah";
            if(ltspM_phone_array[i].endsWith("1") || ltspM_phone_array[i].endsWith("2")) 
                ltspM_phone_array[i] = ltspM_phone_array[i].substring(0, ltspM_phone_array[i].length()-1);
            System.out.print(ltspM_phone_array[i].toUpperCase() + " ");
            phones += " ";
            phones += ltspM_phone_array[i].toUpperCase();            
        }
        try {
            addToDict(phones);
            System.out.println();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(" ");
    }
    private boolean addToDict(String word) throws FileNotFoundException, IOException
    {
        ArrayList<String> words = new ArrayList<String>();
        String s;
        boolean exists;
        FileReader fr = new FileReader("../config/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
        BufferedReader br = new BufferedReader(fr);
        while((s = br.readLine()) != null) {
            if(s.equals(word)) {
                System.out.println("słowo już istnieje!");
                return false;
            }
            words.add(s);
	}
        br.close();
        fr.close();      
        System.out.println("\nwczytalem plik slownika...");
        System.out.println("" + words.size() + " słów");        
        words.add(word);
        System.out.println("" + words.size() + " słów");
        FileWriter fw = new FileWriter("../config/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < words.size(); i++)
        {
            bw.write(words.get(i));
            bw.newLine();
        }
        bw.close();
        fw.close();  
        System.out.println("zapisalem plik do slownika, słowo dodane poprawnie...");
        return true;
    }
    public boolean cmdExists(String cmd)
    {
        String s;
        FileReader fr;
        BufferedReader br;
        try {
            fr = new FileReader("../config/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");        
            br = new BufferedReader(fr);
            while((s = br.readLine()) != null) {
                if(s.equals(cmd)) {
                    return true;
                }
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LTS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LTS.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
}

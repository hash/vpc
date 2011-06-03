/*
 * 
 */
package vpc_service;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Obey {

    //private Process o;
    private Robot r;
    private HashMap cmdList;
    private Set set;
    private Iterator it;
    
    public Obey() throws AWTException {
        r = new Robot();
        try {
            readCmdList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Obey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Obey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void command(String cmd) throws IOException, AWTException {
        
        if (cmd.split(" ")[0].equals("music")) {
        } else if (cmd.split(" ")[0].equals("run")) {
            Runtime rt = Runtime.getRuntime();
            //tu bedzie czytanie listy komend i dobieranie
            try {
                if (cmd.split(" ")[1].equals("music")) {
                    rt.exec("rundll32 url.dll,FileProtocolHandler " + "winamp");
                    //o = Runtime.getRuntime().exec("winamp.exe");                    
                }
                if (cmd.split(" ")[1].equals("browser")) {
                    rt.exec("rundll32 url.dll,FileProtocolHandler " + "firefox");
                    //o = Runtime.getRuntime().exec("chrome.exe");
                }
                if (cmd.split(" ")[1].equals("notes")) {
                    rt.exec("rundll32 url.dll,FileProtocolHandler " + "notepad");
                    //o = Runtime.getRuntime().exec("notepad.exe");
                }
            } catch (Exception e) {
                return;
            }//emulacja klawiszy, czesc nie bangla
        } else if (cmd.split(" ")[0].equals("close")) {           
             r.keyPress(KeyEvent.VK_ALT);
             r.keyPress(KeyEvent.VK_F4);
             r.keyRelease(KeyEvent.VK_ALT);
             r.keyRelease(KeyEvent.VK_F4);             
        } else if (cmd.split(" ")[0].equals("hide all")) {           
             r.keyPress(KeyEvent.VK_WINDOWS);
             r.keyPress(KeyEvent.VK_M);
             r.keyRelease(KeyEvent.VK_WINDOWS);
             r.keyRelease(KeyEvent.VK_M);             
        } else if (cmd.split(" ")[0].equals("show all")) {            
             r.keyPress(KeyEvent.VK_WINDOWS);
             r.keyPress(KeyEvent.VK_SHIFT);
             r.keyPress(KeyEvent.VK_M);
             r.keyRelease(KeyEvent.VK_WINDOWS);
             r.keyRelease(KeyEvent.VK_SHIFT); 
             r.keyRelease(KeyEvent.VK_M);             
        } else if (cmd.split(" ")[0].equals("scroll down")) {    
             r.keyPress(KeyEvent.VK_PAGE_DOWN);
             r.keyRelease(KeyEvent.VK_PAGE_DOWN);             
        } else if (cmd.split(" ")[0].equals("scroll up")) {             
             r.keyPress(KeyEvent.VK_PAGE_UP);
             r.keyRelease(KeyEvent.VK_PAGE_UP);             
        }

    }
    //metody do zczytywania listy komend z z pliku, jeszcze nie dziala
    private void readCmdList() throws FileNotFoundException, IOException
    {
        cmdList = new HashMap();
        FileReader fr = new FileReader("cmdlist.txt");
        BufferedReader br = new BufferedReader(fr);
        String s;
        while((s = br.readLine()) != null)
        {
            if(s.contains("\t")) cmdList.put(s.split("\t")[0], s.split("\t")[1]);
            else cmdList.put(s, " ");
	}
        fr.close();
        set = cmdList.entrySet();
        it = set.iterator();   
    }
    public void cmdList()
    {
        while(it.hasNext()){
        Map.Entry me = (Map.Entry)it.next();
        System.out.println(me.getKey() + " : " + me.getValue() );
    }
    }
}


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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Obey {

    private HashMap<String,String> cmdList;
    private Set set;
    private Iterator it;
    private Command command;
    
    public Obey() throws AWTException
    {   
        command = new Command();
        try {
            readCmdList();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Obey.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Obey.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    
    //Metoda wyświetlająca dostępne komendy
    public void cmdList()
    {
        while(it.hasNext())
        {
            Map.Entry me = (Map.Entry)it.next();
            System.out.println(me.getKey() + " : " + me.getValue());
        }
        System.out.println("action + (show all | close | hide all | switch right | switch left | scroll down | scroll up | cancel | search | save)");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpc_conf;

/**
 *
 * @author hash
 */
public class VoiceCommand {
    
    private String name, command, request, options = "";

    public VoiceCommand(String name, String command, String request){
        this.request = request.replace("\"", ""); //usuwa nadprogramowe cudzysłowy
        this.command = command;
        this.name = name;
    }
    
    public VoiceCommand(String name, String command, String request, String options){
        this.request = request.replace("\"", ""); //usuwa nadprogramowe cudzysłowy
        this.command = command;
        this.name = name;
        this.options = options;
    }
    
    public String To_cmdlist_File()
    {     
        if(options.equals(""))
            return name + "\t" + command + "\t" + request + "\r\n";
        else        
            return name + "\t" + command + "\t\"" + request + "\"\t" + options  +"\r\n";
    }
    
    public String To_gram_File(boolean ostatni)
    {
        if(ostatni == false)
            return  command + " | ";
        else
            return command + ";";
    }
    
    public String getCommand(){
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setRequest(String request){
        this.request = request;
    }

    public String getRequest(){
        return request;
    }    
    
    public String getOptions(){
        return options;
    }
    
    public void setOptions(String options){
        this.options = options;
    }

    @Override
    public String toString(){
        return getName();
    }

   /*@Override
    public boolean equals(Object obj){
        if (obj instanceof VoiceCommand) {
            return request.equals(((VoiceCommand)obj).request);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 19 * hash + (this.request != null ? this.request.hashCode() : 0);
        return hash;
    }*/


}

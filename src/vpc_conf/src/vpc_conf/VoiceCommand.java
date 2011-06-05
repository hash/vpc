package vpc_conf;

/**
 * Klasa pojedynczej komendy głosowej
 */
public class VoiceCommand {
    
    /**
     * Pola przechowujące informacje o komendach
     */
    private String name, command, request, options = "";
    
    /**
     * Konstruktor parametryczny wypełniający informacje o komendach
     */
    public VoiceCommand(String name, String command, String request){
        this.request = request.replace("\"", ""); //usuwa nadprogramowe cudzysłowy
        this.command = command;
        this.name = name;
    }
    
    /**
     * Przeciążony konstruktor, posiada dodatkową informację o opcjach aplikacji
     */
    public VoiceCommand(String name, String command, String request, String options){
        this.request = request.replace("\"", ""); //usuwa nadprogramowe cudzysłowy
        this.command = command;
        this.name = name;
        this.options = options;
    }
    
    /**
     * Metoda zwracająca stringa użytego do zapisu pliku cmdlist.txt
     */
    public String To_cmdlist_File()
    {     
        if(options.equals(""))
            return name + "\t" + command + "\t" + request + "\r\n";
        else        
            return name + "\t" + command + "\t\"" + request + "\"\t" + options  +"\r\n";
    }
    
    /**
     * Metoda zwracająca stringa użytego do zapisu pliku VPC.gram
     */
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
}

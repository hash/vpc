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

    private String acoustic, command, name;

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

    public void setAcoustic(String acoustic){
        this.acoustic = acoustic;
    }

    public VoiceCommand(String acoustic, String command, String name){
        this.acoustic = acoustic;
        this.command = command;
        this.name = name;
    }

    @Override
    public String toString(){
        return getName();
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof VoiceCommand) {
            return acoustic.equals(((VoiceCommand)obj).acoustic);
        }
        return false;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 19 * hash + (this.acoustic != null ? this.acoustic.hashCode() : 0);
        return hash;
    }
}

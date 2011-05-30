/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpc_conf;

import java.util.List;
import java.util.Vector;
import javax.swing.DefaultListModel;

/**
 *
 * @author hash
 */
public class VoiceCommandModel extends DefaultListModel {

    private List<VoiceCommand> vcl = new Vector<VoiceCommand>();
    private static VoiceCommandModel single = null;

    private VoiceCommandModel(){
        VoiceCommand bla = new VoiceCommand("blabla", "run blabla", "bla");
        for (int i = 0, s = 10 ; i < s ; i++) {
            vcl.add(bla);
        }
    }

    public static VoiceCommandModel getModel(){
        if (single == null) {
            single = new VoiceCommandModel();
        }
        return single;
    }

    @Override
    public int getSize(){
        return vcl.size();
    }

    @Override
    public Object getElementAt(int index){
        return vcl.get(index);
    }
}

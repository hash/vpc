/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vpc_conf;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hash
 */
public class VoiceCommandModel extends AbstractTableModel {

    ArrayList<VoiceCommand> vcl = new ArrayList<VoiceCommand>();

    public int getRowCount() {
        return vcl.size();
    }

    public int getColumnCount() {
        return 4;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex)
        {
            case 0: return rowIndex+1;
            case 1: return vcl.get(rowIndex).name;
            case 2: return vcl.get(rowIndex).command;
            case 3: return vcl.get(rowIndex).request;
            default: return "!!!";
        }
    }

    @Override
    public String getColumnName(int kol)
    {
        switch(kol)
        {
            case 0: return "Lp.";
            case 1: return "Nazwa";
            case 2: return "Komenda";
            case 3: return "Wykonaj";
            default: return "!!!";
        }
    }
}

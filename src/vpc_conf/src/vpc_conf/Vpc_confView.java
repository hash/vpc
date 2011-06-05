/*
 * Vpc_confView.java
 */
package vpc_conf;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.awt.Dimension;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The application's main frame.
 */
public class Vpc_confView extends FrameView {

    private VoiceCommandModel ListaKomend = new VoiceCommandModel();
    
    public Vpc_confView(SingleFrameApplication app) throws FileNotFoundException, IOException{
        super(app);
        JFrame frame = new JFrame("VPC konfigurator");
        frame.setMinimumSize(new Dimension(400, 200));
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        this.setFrame(frame);
        initComponents();
        
        Odczyt_cmdlist();
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e){
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger(
                "StatusBar.busyAnimationRate");
        for (int i = 0 ; i < busyIcons.length ; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e){
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt){
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                }
            }
        });
        
//Przyciski dolne i "usuń" w menu DISABLED, jeśli żadna pozycja w tabeli nie zaznaczona
        VoiceCommands.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    boolean rowsAreSelected = VoiceCommands.getSelectedRowCount() > 0;
                    test.setEnabled(rowsAreSelected);
                    komenda.setEnabled(rowsAreSelected);
                    usun.setEnabled(rowsAreSelected);
                    usunMenu.setEnabled(rowsAreSelected);
                }
            }
        });
    }

    public int getTableRow()
    {
        return VoiceCommands.getSelectedRow();
    }
    
    @Action
    public void showAboutBox(){
        if (aboutBox == null) {
            JFrame mainFrame = Vpc_confApp.getApplication().getMainFrame();
            aboutBox = new Vpc_confAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        Vpc_confApp.getApplication().show(aboutBox);
    }

    @Action
    public void showAddVCDialog(){
        
        JFrame mainFrame = Vpc_confApp.getApplication().getMainFrame();
        addVC = new AddVCDialog(mainFrame, true, ListaKomend, false, getTableRow());
        addVC.setLocationRelativeTo(mainFrame);
        Vpc_confApp.getApplication().show(addVC);
        
        Zapis();
    }
    
    @Action
    public void showEditVCDialog() {

        if(getTableRow() != -1)
        {
             JFrame mainFrame = Vpc_confApp.getApplication().getMainFrame();
            addVC = new AddVCDialog(mainFrame, true, ListaKomend, true, getTableRow());
            addVC.setLocationRelativeTo(mainFrame);
            Vpc_confApp.getApplication().show(addVC);
        }

        Zapis();
    }

    @Action
    public void DeleteVC()
    {
        if(getTableRow() != -1)
        {
            if(JOptionPane.showConfirmDialog(mainPanel, "Napewno usunąć?", "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            {
                ListaKomend.vcl.remove(getTableRow());
                ListaKomend.fireTableDataChanged();
            }
        }
        
        Zapis();
    }    
    
    @Action
    public void TestBox()
    {
        if(getTableRow() != -1)
        {
            if(JOptionPane.showConfirmDialog(mainPanel, "Naciśnij OK i wypowiedz po chwili wybraną komendę", "Test wymowy komendy głosowej", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.OK_OPTION)
            {
                ConfigurationManager cm;
                cm = new ConfigurationManager(Vpc_confView.class.getResource("vpc_conf.config.xml"));
                
                Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
                recognizer.allocate();
                
                Microphone microphone = (Microphone) cm.lookup("microphone");
                if (!microphone.startRecording()) {
                    System.out.println("Cannot start microphone.");
                    recognizer.deallocate();
                    System.exit(1);
                }
                
                // loop the recognition until the programm exits.
                while (true) {

                    Result result = recognizer.recognize();

                    if (result != null) {
                        String resultText = result.getBestFinalResultNoFiller();
                       
                        if(ListaKomend.vcl.get(getTableRow()).getCommand().equals(resultText))
                        {
                            JOptionPane.showConfirmDialog(mainPanel, "Gratulacje, wypowiedziałeś \"" + resultText + "\" poprawnie!", "Wynik testu", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                        else
                        {
                            if (JOptionPane.showConfirmDialog(mainPanel, "Błąd! Wypowiediałeś komendę źle. \r\nNaciśnij OK, i spróbój jeszcze raz", "Wynik testu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) ==  JOptionPane.OK_OPTION)
                                continue;
                            else
                                break;
                        }
                    }
                }
            }
        }
    } 
    
    private void Odczyt_cmdlist() throws FileNotFoundException, IOException
    {
        FileReader fr = new FileReader("../config/cmdlist.txt");
        BufferedReader br = new BufferedReader(fr);
        String s;
        while((s = br.readLine()) != null)
        {
            if(s.split("\t").length == 3)
                ListaKomend.vcl.add(new VoiceCommand(s.split("\t")[0], s.split("\t")[1], s.split("\t")[2]));
            else if(s.split("\t").length == 4)
                ListaKomend.vcl.add(new VoiceCommand(s.split("\t")[0], s.split("\t")[1], s.split("\t")[2], s.split("\t")[3]));
        }
        fr.close();
    }
    
    private void Zapis()
    {
        try
        {
//zapis komend do cmdlist.txt
            FileWriter fw = new FileWriter("../config/cmdlist.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for(VoiceCommand vc : ListaKomend.vcl)
            {
                bw.write(vc.To_cmdlist_File());
            }
            
            bw.close();
            fw.close();
            
//zapis komend do VPC.gram
            fw = new FileWriter("../config/VPC.gram");
            bw = new BufferedWriter(fw);

            bw.write("#JSGF V1.0;\r\n\r\ngrammar VPC;\r\n\r\npublic <action> = action <control>;\r\n\t<control> = show all | close | hide all | switch right | switch left | scroll down | scroll up | cancel | search | save;\r\n\r\npublic <run> = ");
            
            int i = 0;
            
            for(VoiceCommand vc : ListaKomend.vcl)
            {
                i++;

                if(ListaKomend.vcl.size() > i)
                    bw.write(vc.To_gram_File(false));
                else
                    bw.write(vc.To_gram_File(true));
            }

            bw.close();
            fw.close();
        }
        catch(Exception ex)
        {
            Logger.getLogger(Vpc_confView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        test = new javax.swing.JButton();
        komenda = new javax.swing.JButton();
        usun = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        VoiceCommands = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        AddVC = new javax.swing.JMenuItem();
        usunMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();

        mainPanel.setName("mainPanel"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(vpc_conf.Vpc_confApp.class).getContext().getActionMap(Vpc_confView.class, this);
        test.setAction(actionMap.get("TestBox")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(vpc_conf.Vpc_confApp.class).getContext().getResourceMap(Vpc_confView.class);
        test.setText(resourceMap.getString("test.text")); // NOI18N
        test.setEnabled(false);
        test.setName("test"); // NOI18N

        komenda.setAction(actionMap.get("showEditVCDialog")); // NOI18N
        komenda.setText(resourceMap.getString("komenda.text")); // NOI18N
        komenda.setEnabled(false);
        komenda.setName("komenda"); // NOI18N

        usun.setAction(actionMap.get("DeleteVC")); // NOI18N
        usun.setText(resourceMap.getString("usun.text")); // NOI18N
        usun.setEnabled(false);
        usun.setName("usun"); // NOI18N
        usun.setNextFocusableComponent(VoiceCommands);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(test)
                .addGap(18, 18, 18)
                .addComponent(komenda, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(usun)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(test)
                    .addComponent(komenda)
                    .addComponent(usun))
                .addContainerGap())
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        VoiceCommands.setModel(ListaKomend);
        VoiceCommands.setName("VoiceCommands"); // NOI18N
        jScrollPane1.setViewportView(VoiceCommands);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        AddVC.setAction(actionMap.get("showAddVCDialog")); // NOI18N
        AddVC.setText(resourceMap.getString("AddVC.text")); // NOI18N
        AddVC.setName("AddVC"); // NOI18N
        fileMenu.add(AddVC);

        usunMenu.setAction(actionMap.get("DeleteVC")); // NOI18N
        usunMenu.setText(resourceMap.getString("usunMenu.text")); // NOI18N
        usunMenu.setEnabled(false);
        usunMenu.setName("usunMenu"); // NOI18N
        fileMenu.add(usunMenu);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        jCheckBoxMenuItem1.setText(resourceMap.getString("jCheckBoxMenuItem1.text")); // NOI18N
        jCheckBoxMenuItem1.setName("jCheckBoxMenuItem1"); // NOI18N
        fileMenu.add(jCheckBoxMenuItem1);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenu.add(jSeparator2);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 518, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AddVC;
    private javax.swing.JTable VoiceCommands;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JButton komenda;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton test;
    private javax.swing.JButton usun;
    private javax.swing.JMenuItem usunMenu;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private AddVCDialog addVC;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddVCDialog.java
 *
 * Created on 2011-06-02, 18:49:24
 */
package vpc_conf;

import org.jdesktop.application.Action;

/**
 *
 * @author hash
 */
public class AddVCDialog extends javax.swing.JDialog {

    private VoiceCommandModel ListaKomend = new VoiceCommandModel();
    private boolean edit;
    private int tableRow;

    /** Creates new form AddVCDialog */
    public AddVCDialog(java.awt.Frame parent, boolean modal, VoiceCommandModel ListaKomend, boolean edit, int tableRow){
        super(parent, modal);
        initComponents();
        
        this.ListaKomend = ListaKomend;
        this.edit = edit;
        this.tableRow = tableRow;
        
        emptyAll();
        
        if(edit)
        {
            textName.setText(ListaKomend.vcl.get(tableRow).name);
            textCommand.setText(ListaKomend.vcl.get(tableRow).command);
            textRequest.setText(ListaKomend.vcl.get(tableRow).request);
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

        Anuluj = new javax.swing.JButton();
        OK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textCommand = new javax.swing.JTextField();
        textRequest = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(vpc_conf.Vpc_confApp.class).getContext().getResourceMap(AddVCDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(vpc_conf.Vpc_confApp.class).getContext().getActionMap(AddVCDialog.class, this);
        Anuluj.setAction(actionMap.get("Cancel")); // NOI18N
        Anuluj.setText(resourceMap.getString("Anuluj.text")); // NOI18N
        Anuluj.setName("Anuluj"); // NOI18N

        OK.setAction(actionMap.get("OKHide")); // NOI18N
        OK.setText(resourceMap.getString("OK.text")); // NOI18N
        OK.setName("OK"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        textCommand.setText(resourceMap.getString("textCommand.text")); // NOI18N
        textCommand.setName("textCommand"); // NOI18N
        textCommand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textCommandKeyTyped(evt);
            }
        });

        textRequest.setText(resourceMap.getString("textRequest.text")); // NOI18N
        textRequest.setName("textRequest"); // NOI18N
        textRequest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textCommandKeyTyped(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        textName.setText(resourceMap.getString("textName.text")); // NOI18N
        textName.setName("textName"); // NOI18N
        textName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textCommandKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(OK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Anuluj)
                        .addGap(162, 162, 162))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(30, 30, 30)
                        .addComponent(textName, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textCommand, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                            .addComponent(textRequest, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textCommand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textRequest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Anuluj)
                    .addComponent(OK))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textCommandKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textCommandKeyTyped
        if (textRequest.getText().equals("")
            || textCommand.getText().equals("")
            || textName.getText().equals("")) {
            OK.setEnabled(false);
        } else {
            OK.setEnabled(true);
        }
    }//GEN-LAST:event_textCommandKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run(){
                AddVCDialog dialog = new AddVCDialog(new javax.swing.JFrame(),
                                                     true, null, false, 0);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e){
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    @Action
    public void OKHide(){
        if(edit)
        {
            ListaKomend.vcl.get(tableRow).name = textName.getText();
            ListaKomend.vcl.get(tableRow).command = textCommand.getText();
            ListaKomend.vcl.get(tableRow).request = textRequest.getText();
        }
        else
            ListaKomend.vcl.add(new VoiceCommand(textName.getText(), textCommand.getText(), textRequest.getText()));

        this.dispose();
        ListaKomend.fireTableDataChanged();
        setVisible(false);
        emptyAll();
    }

    @Action
    public void Cancel(){
        setVisible(false);
        emptyAll();
    }

    private void emptyAll(){
        textRequest.setText("");
        textCommand.setText("");
        textName.setText("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Anuluj;
    private javax.swing.JButton OK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField textCommand;
    public javax.swing.JTextField textName;
    private javax.swing.JTextField textRequest;
    // End of variables declaration//GEN-END:variables
}

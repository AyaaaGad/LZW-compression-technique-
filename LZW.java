
import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class LZW extends javax.swing.JFrame {

    public LZW() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Enter your Choice :");

        jButton1.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 0, 0));
        jButton1.setText("Compress");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 0, 0));
        jButton2.setText("Decompress");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(165, 165, 165)
                                .addComponent(jLabel2)
                                .addGap(57, 57, 57))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)))
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(64, 64, 64))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    Scanner sc;

    public void open_file(String FileName) {
        try {
            sc = new Scanner(new File(FileName));
        } catch (Exception e) {
            jLabel2.setText("Can not find File ");
        }
    }

    public void close_file() {
        sc.close();
    }

    Formatter out; 

    public void openfile(String path) {
        try {
            out = new Formatter(path);
        } catch (Exception e) {
            jLabel2.setText("Can not find File ");
        }

    }

    public void closefile() {
        out.close();
    }

    public void write_tag(int pointer) {

        String s = "";
        s += pointer;
        out.format("%s", s);
        out.format("%n");
        out.flush(); 

    }
    
    ArrayList<String> dictionary = new ArrayList<String>();
      
    public void initialize_array ()
    { 
        for( int asci=0 ; asci <= 127 ; asci++) 
        {
            String character = "";
            character += (char) asci;
            dictionary.add(character);
        }
    }  
        
    public void compress(String data) {
        
        initialize_array();
        
        int indx = 0; 

        String cur = "";  
        int dic_indx;   
        
        openfile("Compressed Data.txt");

        cur += data.charAt(indx++);  

        while ( indx <= data.length()) 
        {
            dic_indx = dictionary.indexOf(cur);
            
            while ((dic_indx != -1) && (indx < data.length())) 
            {    
                cur += data.charAt(indx++); 
                dic_indx = dictionary.indexOf(cur); 

            }
             
            if (cur.length() > 1 && dic_indx == -1)
            {
                write_tag(dictionary.indexOf(cur.substring(0, cur.length() - 1))); 
                dictionary.add(cur); 
                char c = cur.charAt(cur.length() - 1); 
                cur = "";
                cur += c;

            }
              
            else 
            {
                write_tag(dictionary.indexOf(cur)); 
                break;

            }
               
        }
        
        closefile();
    
    }

    public void read_file(String FileName) { 

        open_file(FileName);
        String data = "";

        while (sc.hasNext()) 
        {
            String cur = sc.next(); 
            data += cur;
        }

        compress(data);
        close_file();

    }

    String res = "";

    public void decompress() 
    {
         
        initialize_array();  
        int prev_dic_pointer ;
        int cur_dic_pointer = pointers.get(0) ; 
        
        res += dictionary.get(cur_dic_pointer); 
        
        
        for (int i=1 ; i<pointers.size() ; i++) 
        {
            cur_dic_pointer = pointers.get(i) ; 
            prev_dic_pointer = pointers.get(i-1) ; 
            
            if ((cur_dic_pointer >= 0) && (cur_dic_pointer < dictionary.size())) 
            {
                res += dictionary.get(cur_dic_pointer); 
                dictionary.add( dictionary.get(prev_dic_pointer)+dictionary.get(cur_dic_pointer).charAt(0)); 
            }
            
            else 
            {   
                String unknown_word =  dictionary.get(prev_dic_pointer) + dictionary.get(prev_dic_pointer).charAt(0); 
                res += unknown_word ;
                dictionary.add (unknown_word); 
            }
            
        }
       
    }

    public void write_res() {

        openfile("Original Data.txt"); 
        out.format("%s", res);
        out.flush();
        closefile();

    }
    
    ArrayList<Integer> pointers = new ArrayList<Integer>();
  
    public void read_compressed_data(String path) 
    {
        open_file(path);
      
        while (sc.hasNext()) 
        {
           pointers.add(Integer.parseInt(sc.next()));
        }
        
        decompress();
        write_res();
        close_file();

    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        read_file("Original Data.txt"); // compress data
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        read_compressed_data("Compressed Data.txt"); // decompress data
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LZW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LZW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LZW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LZW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LZW().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}

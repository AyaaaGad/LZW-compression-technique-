
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

    Formatter out; //34an yktb el tag be format el string

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
        out.flush(); // 34an yktb 3al file

    }
    
    ArrayList<String> dictionary = new ArrayList<String>();
      
    public void initialize_array ()
    { 
        for( int asci=0 ; asci <= 127 ; asci++) //initialize array bel asci code and its chars
        {
            String character = "";
            character += (char) asci;
            dictionary.add(character);
        }
    }  
        
    public void compress(String data) {
        
        initialize_array();
        
        int indx = 0; // bam4y beh 3al el inputstream .. data

        String cur = "";  // b5zn feeh el klma eli bdwr 3leha fel dic
        int dic_indx;   // indx el klma fel dic
        
        openfile("Compressed Data.txt");

        cur += data.charAt(indx++);  // b7ot awel 7rf fel cur

        while ( indx <= data.length()) // 34an na bzwd el indx b3d ma b7ot el 7arf
        {
            dic_indx = dictionary.indexOf(cur);
            
            while ((dic_indx != -1) && (indx < data.length())) // el klma mwgoda fel dic + lsa mawslt4 le a5r 7arf (34an ma5rog4 bara el boundries we adeef garbage)
            {    
                cur += data.charAt(indx++);  // zawd 7rf fl klma
                dic_indx = dictionary.indexOf(cur); // then search again

            }
             
            if (cur.length() > 1 && dic_indx == -1) // m3naha eno mal2a4 el klma fel dic ... 34an make sure eno lsa m4 a5r 7arf
            {
                write_tag(dictionary.indexOf(cur.substring(0, cur.length() - 1))); // btkon el klma kolha mwgoda ma3da a5r 7arf gded ... bnktb el indx bt3ha
                dictionary.add(cur); // bdef el string kolha el gdeda fel dic
                char c = cur.charAt(cur.length() - 1); // el cur bykon feh a5r 7arf 34an ybd2 ya5od klma gdeda 3leh
                cur = "";
                cur += c;

            }
              
            else 
            {
                write_tag(dictionary.indexOf(cur)); // kda l cur hykon 7rf wa7d we hykon a5r 7rf fa hayktbo we y3ml break 34an myrg34 ydwr 3leeh tany we nd5ol fe infinite loop
                break;

            }
               
        }
        
        closefile();
    
    }

    public void read_file(String FileName) { // read data file

        open_file(FileName);
        String data = "";

        while (sc.hasNext()) // while it's not the end of file 
        {
            String cur = sc.next(); // read line then append " ignore white spaces "
            data += cur;
        }

        compress(data);
        close_file();

    }

    String res = "";

    public void decompress() 
    {
         
        initialize_array();  // b7ot el 7rof kolha fel array (asci)
        int prev_dic_pointer ;
        int cur_dic_pointer = pointers.get(0) ; 
        
        res += dictionary.get(cur_dic_pointer); // awel 7arf ha7oto fel res 34an aked haykon mwgod
        
        
        for (int i=1 ; i<pointers.size() ; i++) // haym4y 3al pointers eli 2araha kolha we haybd2 mn tany wa7d
        {
            cur_dic_pointer = pointers.get(i) ; // el position aw el asci eli wa2f 3leeh nw according lel dic
            prev_dic_pointer = pointers.get(i-1) ; // el position aw el asci eli 2ably
            
            if ((cur_dic_pointer >= 0) && (cur_dic_pointer < dictionary.size())) // m3anaha eno el indx da mawgod fel dic
            {
                res += dictionary.get(cur_dic_pointer); // bktb el klma ely leha el asci fe res 34an yro7 yktbha 3al file
                dictionary.add( dictionary.get(prev_dic_pointer)+dictionary.get(cur_dic_pointer).charAt(0)); // b3ml add lel klma eli 2able + first char mn eli na feha
            }
            
            else // m3naha eno el indx OR asci da m4 mwgod fel dic nw ...  handling " ???? " case
            {   
                String unknown_word =  dictionary.get(prev_dic_pointer) + dictionary.get(prev_dic_pointer).charAt(0); // daymn el ??? btb2a el klma eli 2abli + awel 7arf mnha
                res += unknown_word ;
                dictionary.add (unknown_word); // b3ml add b2a lel klma d
            }
            
        }
       
    }

    public void write_res() {

        openfile("Original Data.txt"); // 34an aktb el data b3d ma fakenaha
        out.format("%s", res);
        out.flush();
        closefile();

    }
    
    ArrayList<Integer> pointers = new ArrayList<Integer>();
  
    public void read_compressed_data(String path) // 34an 2a2ra el tags from file
    {
        open_file(path);
      
        while (sc.hasNext()) // while it's not the end of file 
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

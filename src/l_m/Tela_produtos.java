/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package l_m;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.IIOException;
    
/**
 *
 * @author Pedro53722376
 */
public class Tela_produtos extends javax.swing.JInternalFrame {
    String capaCaminho;
    public File livroArq = null;

    public String getCapaCaminho() {
        return capaCaminho;
    }

    public void setCapaCaminho(String capaCaminho) {
        this.capaCaminho = capaCaminho;
    }
    
    
    public static String getExt(String ext){
        int lastDot = ext.lastIndexOf(".");
        if(lastDot != -1){
            ext = ext.substring(lastDot + 1).toLowerCase();
        }        
        
        return ext;
    };
    
    public File recupera(int id){
        
        try{
        try{
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "SELECT id_livro, titulo, arquivo_path FROM livros WHERE id_livro = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            File file = null;
            
            if(rs.next()){
                byte[] bytes = rs.getBytes("arquivo_path");
                String titulo = rs.getString("titulo");
                
                file = new File("C:\\Livros\\Livro\\" + titulo + ".pdf");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();
            }
            JOptionPane.showMessageDialog(null, "sucesso" + rs.getString("titulo"));
            rs.close();
            stmt.close();
            con.close();
            return file;
        }catch(IOException io){
            JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
        }
        }catch(SQLException ex){
            Logger.getLogger(Tela_produtos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public  boolean livroPDF(File livroPdf){
         try {
            
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "INSERT INTO livros(titulo, categoria, arquivo_path, capa_path) VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            try{
                InputStream is = new FileInputStream(livroPdf);
                byte[] bytes = new byte[(int)livroPdf.length()];
                int offSet = 0;
                int numRead = 0;
                while(offSet < bytes.length && (numRead = is.read(bytes, offSet, bytes.length-offSet)) >= 0){
                    offSet += numRead;
                }
                stmt.setString(1, tituloTxt.getText());           
                stmt.setString(2, categoria.getSelectedItem().toString());
                stmt.setBytes(3, bytes);
                stmt.setString(4, getCapaCaminho());
                stmt.execute();

                JOptionPane.showMessageDialog(null, "Livro adicionado co sucesso!");
                con.close();
                stmt.close();
            }catch(IOException io){
                JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
            }
        }catch (SQLException e) {
            Logger.getLogger(Tela_gerenciar_funcionarios.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }
    /**
     * Creates new form Tela_produtos
     */
    public Tela_produtos() {
        initComponents();
        tableLivros.getTableHeader().setReorderingAllowed(false);
        DefaultTableModel modelo = (DefaultTableModel) tableLivros.getModel();
        try{
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "SELECT id_livro, titulo, categoria FROM livros;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Object [] dados = {rs.getString("id_livro"), rs.getString("titulo"), rs.getString("categoria")};
                modelo.addRow(dados);
            }
            
            rs.close();
            stmt.close();
            con.close();
        }catch(SQLException ex){
            Logger.getLogger(Tela_produtos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagemFundo1 = new imagemfundo.ImagemFundo();
        tituloTxt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tituloAdd = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        categoria = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        addBtn = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        caminhoPath = new javax.swing.JTextField();
        selecionarArqBt = new javax.swing.JButton();
        capaFile = new javax.swing.JTextField();
        selecionarCapaBt = new javax.swing.JButton();
        recuperaArq = new javax.swing.JLabel();
        refreshBtn = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableLivros = new javax.swing.JTable();
        searchBt = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        imagemFundo1.setPreferredSize(new java.awt.Dimension(1200, 720));

        tituloTxt.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        tituloTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tituloTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tituloTxtActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/Logo.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 0, 24)); // NOI18N
        jLabel2.setText("Produtos");

        tituloAdd.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        tituloAdd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tituloAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tituloAddActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        jLabel7.setText("Titulo:");

        categoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Romance", "Fantasia", "Ficção", "Mistério / Suspense", "Terror / Horror", "Aventura", "Drama", "Distopia", " " }));
        categoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        jLabel9.setText("Categoria:");

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        jLabel12.setText("Selecionar arquivo:");

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/addIcon.png"))); // NOI18N
        addBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/edit.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/delete.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel13.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        jLabel13.setText("Selecionar capa:");

        caminhoPath.setEditable(false);
        caminhoPath.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        selecionarArqBt.setText("Selecionar");
        selecionarArqBt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selecionarArqBt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selecionarArqBtMouseClicked(evt);
            }
        });
        selecionarArqBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionarArqBtActionPerformed(evt);
            }
        });

        capaFile.setEditable(false);
        capaFile.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        capaFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capaFileActionPerformed(evt);
            }
        });

        selecionarCapaBt.setText("Selecionar");
        selecionarCapaBt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selecionarCapaBt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selecionarCapaBtMouseClicked(evt);
            }
        });

        recuperaArq.setText("recuperar Arquivo");
        recuperaArq.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        recuperaArq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recuperaArqMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jLabel13))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(caminhoPath)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(selecionarArqBt))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(130, 130, 130)
                                    .addComponent(jLabel7))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(105, 105, 105)
                                    .addComponent(jLabel9))
                                .addComponent(tituloAdd)
                                .addComponent(categoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(capaFile, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(selecionarCapaBt)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel12))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(13, 13, 13))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(addBtn)
                                .addGap(77, 77, 77)
                                .addComponent(jLabel6)
                                .addGap(82, 82, 82)
                                .addComponent(jLabel10)
                                .addGap(55, 55, 55))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(recuperaArq)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(44, 44, 44)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tituloAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caminhoPath, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selecionarArqBt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capaFile, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selecionarCapaBt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBtn)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(28, 28, 28)
                .addComponent(recuperaArq)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        refreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/refresh.png"))); // NOI18N
        refreshBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refreshBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshBtnMouseClicked(evt);
            }
        });

        tableLivros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tableLivros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", " Titulo", "Categoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableLivros);

        searchBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/searchIcon.png"))); // NOI18N
        searchBt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBtMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout imagemFundo1Layout = new javax.swing.GroupLayout(imagemFundo1);
        imagemFundo1.setLayout(imagemFundo1Layout);
        imagemFundo1Layout.setHorizontalGroup(
            imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagemFundo1Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 297, Short.MAX_VALUE)
                .addGroup(imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(imagemFundo1Layout.createSequentialGroup()
                        .addComponent(tituloTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchBt)
                        .addGap(27, 27, 27)
                        .addComponent(refreshBtn)
                        .addGap(22, 22, 22))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        imagemFundo1Layout.setVerticalGroup(
            imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagemFundo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(imagemFundo1Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(imagemFundo1Layout.createSequentialGroup()
                        .addGroup(imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tituloTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(refreshBtn)
                            .addComponent(searchBt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagemFundo1, javax.swing.GroupLayout.PREFERRED_SIZE, 1201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 50, Short.MAX_VALUE)
                .addComponent(imagemFundo1, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tituloTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tituloTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tituloTxtActionPerformed

    private void tituloAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tituloAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tituloAddActionPerformed

    private void searchBtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBtMouseClicked
          try {
            DefaultTableModel modelo = (DefaultTableModel) tableLivros.getModel();
            modelo.setNumRows(0);
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "SELECT id_livro, titulo, categoria FROM livros WHERE titulo LIKE ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+tituloTxt.getText()+"%");
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Object[] dados = {rs.getString("id_livro"), rs.getString("titulo"), rs.getString("categoria")};
                modelo.addRow(dados);
            }
 
            rs.close();
            con.close();
            stmt.close();
            
        }catch (SQLException e) {
            Logger.getLogger(Tela_gerenciar_funcionarios.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }//GEN-LAST:event_searchBtMouseClicked

    private void refreshBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshBtnMouseClicked
         DefaultTableModel modelo = (DefaultTableModel) tableLivros.getModel();
        try{
            modelo.setNumRows(0);
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "SELECT id_livro, titulo, categoria FROM livros;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Object [] dados = {rs.getString("id_livro"), rs.getString("titulo"), rs.getString("categoria")};
                modelo.addRow(dados);
            }
            
            rs.close();
            stmt.close();
            con.close();
        }catch(SQLException ex){
            Logger.getLogger(Tela_produtos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_refreshBtnMouseClicked
/*    */
    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        if(tituloAdd.getText().isBlank() || caminhoPath.getText().isBlank() || capaFile.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Nenhum campo pode estar vazio");
            return;
        }
        livroPDF(livroArq.getAbsoluteFile());
         
    }//GEN-LAST:event_addBtnMouseClicked

    private void capaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capaFileActionPerformed
      

    }//GEN-LAST:event_capaFileActionPerformed

    private void selecionarArqBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionarArqBtActionPerformed
     
    }//GEN-LAST:event_selecionarArqBtActionPerformed

    private void selecionarArqBtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selecionarArqBtMouseClicked
        try{
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.showOpenDialog(this);
            livroArq = fc.getSelectedFile();
            
            if(!getExt(livroArq.getPath()).equals("pdf")){
                JOptionPane.showMessageDialog(null, "somente arquivos pdf");
                return;
            }
            
            caminhoPath.setText(livroArq.getPath());
        }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado!");
        }
    }//GEN-LAST:event_selecionarArqBtMouseClicked

    private void selecionarCapaBtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selecionarCapaBtMouseClicked
        try{           
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.showOpenDialog(this);
            File f = fc.getSelectedFile();
            
            if(!getExt(f.getPath()).equals("png") && !getExt(f.getPath()).equals("jpg")){
                JOptionPane.showMessageDialog(null, "somente arquivos png ou jpg!");
                return;
            }
            
            capaFile.setText(f.getPath());
            setCapaCaminho(f.getPath());
            }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado!");
        }
    }//GEN-LAST:event_selecionarCapaBtMouseClicked

    private void recuperaArqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recuperaArqMouseClicked
        recupera(1);
    }//GEN-LAST:event_recuperaArqMouseClicked

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addBtn;
    private javax.swing.JTextField caminhoPath;
    private javax.swing.JTextField capaFile;
    private javax.swing.JComboBox<String> categoria;
    private imagemfundo.ImagemFundo imagemFundo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel recuperaArq;
    private javax.swing.JLabel refreshBtn;
    private javax.swing.JLabel searchBt;
    private javax.swing.JButton selecionarArqBt;
    private javax.swing.JButton selecionarCapaBt;
    private javax.swing.JTable tableLivros;
    private javax.swing.JTextField tituloAdd;
    private javax.swing.JTextField tituloTxt;
    // End of variables declaration//GEN-END:variables
}

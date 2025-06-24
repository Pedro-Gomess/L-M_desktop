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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
    
/**
 *
 * @author Pedro53722376
 */
public class Tela_produtos extends javax.swing.JInternalFrame {
    public Byte livroFile;
    public String livroImg;
    public File capaArq = null;
    public File livroArq = null;

    public Byte getLivroFile() {
        return livroFile;
    }

    public void setLivroFile(Byte livroFile) {
        this.livroFile = livroFile;
    }

    public String getLivroImg() {
        return livroImg;
    }

    public void setLivroImg(String livroImg) {
        this.livroImg = livroImg;
    }

  

    
    public static String getExt(String ext){
        int lastDot = ext.lastIndexOf(".");
        if(lastDot != -1){
            ext = ext.substring(lastDot + 1).toLowerCase();
        }        
        
        return ext;
    };
    
    public boolean updateSituacao(String id){
        try{
           Connection con = DataBaseConnection.conexaoBanco();
           String sql = "UPDATE livros_enviados SET situacao = 'Ativo' WHERE id_livro_enviado = ?";
           PreparedStatement stmt = con.prepareStatement(sql);
           stmt.setString(1, id);
           stmt.execute();
        }catch(SQLException ex){
            Logger.getLogger(Tela_produtos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    };
    
    public File recuperaLivro(int id, String situacao){
        
        try{
            try{
                Connection con = DataBaseConnection.conexaoBanco();
                String sql;
                if(situacao.toLowerCase().equals("pendente")){
                    sql = "SELECT id_livro_enviado, titulo, livro_file, capa_img  FROM livros_enviados WHERE id_livro_enviado = ?;";
                }else{
                    sql = "SELECT id_livro, titulo, livro_file, capa_img  FROM livros WHERE id_livro = ?;";
                }
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                File file = null;

                if(rs.next()){
                    byte[] bytesLivro = rs.getBytes("livro_file");
                    String titulo = rs.getString("titulo");
                    file = new File("C:\\Livros\\Livro\\" + titulo + ".pdf");
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytesLivro);

                    byte[] bytesCapa = rs.getBytes("capa_img");
                    file = new File("C:\\Livros\\capaLivro\\" + titulo + ".png");
                    fos = new FileOutputStream(file);
                    fos.write(bytesCapa);
                    fos.close();
                }
                JOptionPane.showMessageDialog(null, "Download relizado com sucesso!");
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
    
    public  boolean livroPDF(File livroPdf, File capaFile){
         try {
            
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "INSERT INTO livros(titulo, autor, visualizacao, categoria, livro_file, capa_img, situacao) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            
            try{
                InputStream is = new FileInputStream(livroPdf);
                byte[] bytesLivro = new byte[(int)livroPdf.length()];
                int offSet = 0;
                int numRead = 0;
                while(offSet < bytesLivro.length && (numRead = is.read(bytesLivro, offSet, bytesLivro.length-offSet)) >= 0){
                    offSet += numRead;
                }
                
                is = new FileInputStream(capaFile);
                byte[] bytesCapa = new byte[(int)capaFile.length()];
                offSet = 0;
                numRead = 0;
                while(offSet < bytesCapa.length && (numRead = is.read(bytesCapa, offSet, bytesCapa.length-offSet)) >= 0){
                    offSet += numRead;
                }
                stmt.setString(1, tituloAdd.getText());                 
                stmt.setString(2, autorTxt1.getText()); 
                stmt.setString(3, "0");
                stmt.setString(4, categoriaTxt.getText());
                stmt.setBytes(5, bytesLivro);
                stmt.setBytes(6, bytesCapa);
                stmt.setString(7, "Ativo");
                stmt.execute();

                JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso!");
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
            String sql = "SELECT id_livro, titulo, situacao FROM livros;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Object [] dados = {rs.getString("id_livro"), rs.getString("titulo"), rs.getString("situacao")};
                modelo.addRow(dados);
            }
            
            
            sql = "SELECT id_livro_enviado, titulo, situacao FROM livros_enviados WHERE situacao = 'pendente';";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Object [] dados = {rs.getString("id_livro_enviado"), rs.getString("titulo"), rs.getString("situacao")};
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
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        addBtn = new javax.swing.JLabel();
        deleteBt = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        caminhoPath = new javax.swing.JTextField();
        selecionarArqBt = new javax.swing.JButton();
        capaFile = new javax.swing.JTextField();
        selecionarCapaBt = new javax.swing.JButton();
        categoriaTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        autorTxt1 = new javax.swing.JTextField();
        dowloadBt = new javax.swing.JLabel();
        refreshBtn = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableLivros = new javax.swing.JTable();
        searchBt = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setMaximumSize(new java.awt.Dimension(1288, 736));
        setMinimumSize(new java.awt.Dimension(1288, 736));

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

        deleteBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/delete.png"))); // NOI18N
        deleteBt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteBtMouseClicked(evt);
            }
        });

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

        categoriaTxt.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        categoriaTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        categoriaTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaTxtActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 0, 18)); // NOI18N
        jLabel8.setText("Autor:");

        autorTxt1.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        autorTxt1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        autorTxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autorTxt1ActionPerformed(evt);
            }
        });

        dowloadBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/download_icon.png"))); // NOI18N
        dowloadBt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dowloadBtMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(121, 121, 121)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(13, 13, 13))
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tituloAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(130, 130, 130)
                                    .addComponent(jLabel7))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(85, 85, 85)
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(caminhoPath)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selecionarArqBt))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(jLabel12))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(capaFile, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(64, 64, 64)
                                                .addComponent(addBtn)
                                                .addGap(36, 36, 36)
                                                .addComponent(dowloadBt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(deleteBt)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selecionarCapaBt)))
                                .addComponent(categoriaTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(autorTxt1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jLabel8)))
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
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autorTxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoriaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
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
                    .addComponent(deleteBt)
                    .addComponent(addBtn)
                    .addComponent(dowloadBt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
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
                "ID", " Titulo", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableLivros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tableLivros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableLivrosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableLivros);

        searchBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/searchIcon.png"))); // NOI18N
        searchBt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                .addGroup(imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, imagemFundo1Layout.createSequentialGroup()
                        .addComponent(tituloTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(searchBt)
                        .addGap(27, 27, 27)
                        .addComponent(refreshBtn)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, imagemFundo1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        imagemFundo1Layout.setVerticalGroup(
            imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagemFundo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagemFundo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(imagemFundo1, javax.swing.GroupLayout.DEFAULT_SIZE, 1223, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imagemFundo1, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
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
            String sql = "SELECT id_livro, titulo, situacao FROM livros;";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Object [] dados = {rs.getString("id_livro"), rs.getString("titulo"), rs.getString("situacao")};
                modelo.addRow(dados);
            }
            
            sql = "SELECT id_livro_enviado, titulo, situacao FROM livros_enviados WHERE situacao = 'pendente';";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Object [] dados = {rs.getString("id_livro_enviado"), rs.getString("titulo"), rs.getString("situacao")};
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
        if(tableLivros.isRowSelected(tableLivros.getSelectedRow())){
            String idLivro = String.valueOf(tableLivros.getValueAt(tableLivros.getSelectedRow(), 0));
            updateSituacao(idLivro);
        };
        livroPDF(livroArq.getAbsoluteFile(), capaArq.getAbsoluteFile());
         
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
            capaArq = fc.getSelectedFile();
            if(!getExt(f.getPath()).equals("png") && !getExt(f.getPath()).equals("jpg")){
                JOptionPane.showMessageDialog(null, "somente arquivos png ou jpg!");
                return;
            }
            
            capaFile.setText(f.getPath());
            }catch(NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado!");
        }
    }//GEN-LAST:event_selecionarCapaBtMouseClicked

    private void categoriaTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriaTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoriaTxtActionPerformed

    private void autorTxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autorTxt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_autorTxt1ActionPerformed

    private void tableLivrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableLivrosMouseClicked
        DefaultTableModel modelo = (DefaultTableModel) tableLivros.getModel();
        String idLivro = String.valueOf(tableLivros.getValueAt(tableLivros.getSelectedRow(), 0));
        String situacaoLivro = String.valueOf(tableLivros.getValueAt(tableLivros.getSelectedRow(), 2));
       
        try{
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "SELECT id_livro, titulo, autor, categoria FROM livros WHERE id_livro = '"+idLivro+"' AND situacao = '"+situacaoLivro+"';";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
               tituloAdd.setText(rs.getString("titulo"));
               autorTxt1.setText(rs.getString("autor"));
               categoriaTxt.setText(rs.getString("categoria"));
               caminhoPath.setText(rs.getString("titulo") + ".pdf");
               capaFile.setText(rs.getString("titulo") + ".png");
            }
            
            sql = "SELECT id_livro_enviado, titulo, autor, categoria, livro_file, capa_img FROM livros_enviados WHERE id_livro_enviado = '"+idLivro+"' AND situacao = '"+situacaoLivro+"';";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
               tituloAdd.setText(rs.getString("titulo"));
               autorTxt1.setText(rs.getString("autor"));
               categoriaTxt.setText(rs.getString("categoria"));
               caminhoPath.setText(rs.getString("titulo") + ".pdf");
               capaFile.setText(rs.getString("titulo") + ".png");
            }
            rs.close();
            stmt.close();
            con.close();
        }catch(SQLException ex){
            Logger.getLogger(Tela_produtos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tableLivrosMouseClicked

    private void dowloadBtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dowloadBtMouseClicked
        String id =  String.valueOf(tableLivros.getValueAt(tableLivros.getSelectedRow(), 0));
        String situacao = String.valueOf(tableLivros.getValueAt(tableLivros.getSelectedRow(), 2));
        recuperaLivro(Integer.parseInt(id), situacao);
    }//GEN-LAST:event_dowloadBtMouseClicked

    private void deleteBtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtMouseClicked
          DefaultTableModel modelo = (DefaultTableModel) tableLivros.getModel();
        try{
            String idLivro = String.valueOf(tableLivros.getValueAt(tableLivros.getSelectedRow(), 0));
            Connection con = DataBaseConnection.conexaoBanco();
            String sql = "SELECT id_livro, titulo, situacao FROM livros WHERE id_livro = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, idLivro);
            stmt.execute();
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                              
                sql = "DELETE FROM livros WHERE id_livro = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, idLivro);
                stmt.execute(); 
            }
            
            sql = "SELECT id_livro_enviado, titulo, situacao FROM livros_enviados WHERE situacao = 'pendente';";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                    
                sql = "DELETE FROM livros_enviados WHERE id_livro_enviado = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, rs.getString("id_livro_enviado"));
                
            }
            stmt.execute(); 
            
            JOptionPane.showMessageDialog(null, "Livro excluido com sucesso!");
            rs.close();
            stmt.close();
            con.close();
        }catch(SQLException ex){
            Logger.getLogger(Tela_produtos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deleteBtMouseClicked

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addBtn;
    private javax.swing.JTextField autorTxt1;
    private javax.swing.JTextField caminhoPath;
    private javax.swing.JTextField capaFile;
    private javax.swing.JTextField categoriaTxt;
    private javax.swing.JLabel deleteBt;
    private javax.swing.JLabel dowloadBt;
    private imagemfundo.ImagemFundo imagemFundo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel refreshBtn;
    private javax.swing.JLabel searchBt;
    private javax.swing.JButton selecionarArqBt;
    private javax.swing.JButton selecionarCapaBt;
    private javax.swing.JTable tableLivros;
    private javax.swing.JTextField tituloAdd;
    private javax.swing.JTextField tituloTxt;
    // End of variables declaration//GEN-END:variables
}

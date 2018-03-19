/**************************************************************************************
                                                                                       
                      LA CLASSE SUIVANTE CORRESPOND A L'INTERFACES                     
                           DE DETAILS D'UN PATIENT DES URGENCES                        
                                              
 *************************************************************************************/

package Interface;

import fonctionalCore.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author camille
 */
public class URGENCE_Detail_Patient extends javax.swing.JFrame {
    String nom_fichier_image = "Background02.png";
     
    
/****************************************************************
                            CONSTRUCTEUR                         
****************************************************************/ 
    public URGENCE_Detail_Patient(Personne leGarsConnecter,Patient lePatient) {
        
        super("Connexion");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(URGENCE_Detail_Patient.MAXIMIZED_BOTH); 
        initComponents();
        Dimension sizeBckgrd = this.getSize();
        Label_bckgrd.setSize(sizeBckgrd);       
        pack();
        this.setVisible(true);
        URL resource = SA_Accueil.class.getResource("/Images/AccueilOn.png");
        Icon warnIcon = new ImageIcon(resource);
        Button_Accueil.setIcon(warnIcon);
        quiSuisJe =new PH(((PH)leGarsConnecter).getId_PH(),((PH)leGarsConnecter).getSpecialite(),((PH)leGarsConnecter).getNom(),((PH)leGarsConnecter).getPrenom(),((PH)leGarsConnecter).getnTel());
        monPatient = new Patient(lePatient.getIpp(), lePatient.getNom(),lePatient.getPrenom(),lePatient.getnTel(),lePatient.getNomDeNaissance(),lePatient.getLieuDeNaissance(),lePatient.getMedecin(),lePatient.getAdresse(),lePatient.getDateDeNaissance(),lePatient.getSexe(),lePatient.getId_confiance(),lePatient.getCode_postal(),lePatient.getVille());
        
            // clear tout les champ de text
            jTextArea1.setText("");
            PatientPHNom.setText("");
            PatientPHPrenom.setText("");
            PatientPHIPP.setText("");
            PatientPHDateNaissance.setText("");
            PatientPHAdresse.setText("");
            PatientPHCP.setText("");
            PatientPHVille.setText("");
            PatientPHTelephone.setText("");
            PrenomContact.setText("");
            NomContact.setText("");
            adresseContact.setText("");
            CPContact.setText("");
            villeContact.setText("");
            telContact.setText("");
            relationContact.setText("");
            spe.setText("");
            secteur.setText("");
            chambre.setText("");
            lit.setText("");
            
           
            Label_Titre.setText("Information Patient : "+monPatient.getPrenom()+" "+monPatient.getNom());
            PatientPHNom.setText(monPatient.getNom());
            PatientPHPrenom.setText(monPatient.getPrenom());
            PatientPHIPP.setText(monPatient.getIpp());
            PatientPHDateNaissance.setText(monPatient.getDateDeNaissance());
            PatientPHAdresse.setText(monPatient.getAdresse());
            PatientPHCP.setText(monPatient.getCode_postal());
            PatientPHVille.setText(monPatient.getVille());
            PatientPHTelephone.setText(monPatient.getnTel());
            String idDeLaPersDeConf = monPatient.getId_confiance();
            
            if(!idDeLaPersDeConf.equals("")){
                maPersConf = PersonneDeConfiance.AfficherInfoPersonneConfiance(idDeLaPersDeConf);
                PrenomContact.setText(maPersConf.getPrenom());
                NomContact.setText(maPersConf.getNom());
                adresseContact.setText(maPersConf.getAdresse());
                CPContact.setText(maPersConf.getCode_postal());
                villeContact.setText(maPersConf.getVille());
                telContact.setText(maPersConf.getnTel());
                relationContact.setText(maPersConf.getRelation());
            }  
            
            Localisation locaDuPatient = Patient.afficherLocalisationPatient(monPatient.getIpp());
            spe.setText(locaDuPatient.getService());
            secteur.setText(locaDuPatient.getSecteur());
            chambre.setText(locaDuPatient.getChambre());
            lit.setText(locaDuPatient.getLit());
            
    
                    
                    
            ArrayList<Prescription> listePrescription = Prescription.afficherPrescription(monPatient.getIpp());
      
            DefaultTableModel model = new DefaultTableModel();
            model.setRowCount(0);
            Tableau_prescription.setModel(model);
            model.addColumn("Numéro de Prescription");
            model.addColumn("Préscription");
            model.addColumn("Date");
            model.addColumn("Prescripteur");

            for (Prescription p : listePrescription) {
                model.addRow(new Object[]{p.getId_prescription(), p.getPrescri(), p.getDate(), "Dr "+p.getPh()});
            }
            
            
            ArrayList<Acte_Infirmier> listActeInf = Acte_Infirmier.afficherActe(monPatient.getIpp());
            
            DefaultTableModel model2 = new DefaultTableModel();
            model2.setRowCount(0);
            model2 = new DefaultTableModel();
            Tableau_ActeInf.setModel(model2);
            model2.addColumn("Acte");
            model2.addColumn("Résultat");
            model2.addColumn("Date");
            model2.addColumn("Soigant");

            for (Acte_Infirmier ac : listActeInf) {
                model2.addRow(new Object[]{ac.getActe(), ac.getResultat(), ac.getDate(), ac.getInfirmier()});
            }
            
            
            
            ArrayList<Obersvation> listObservation = Obersvation.afficherObservation(monPatient.getIpp());
            
            DefaultTableModel model3 = new DefaultTableModel();
            model3.setRowCount(0);
            model3 = new DefaultTableModel();
            Tableau_Observation.setModel(model3);
            model3.addColumn("Observation");
            model3.addColumn("Date");
            for (Obersvation obs : listObservation) {
                model3.addRow(new Object[]{obs.getObs(), obs.getDate()});
            }

      
            ArrayList<Sejour> listHospi = Sejour.ListDesHospitalisation(monPatient.getIpp());
            
            DefaultTableModel model4 = new DefaultTableModel();
            model4.setRowCount(0);
            model4 = new DefaultTableModel();
            Tableau_Hospitalisation.setModel(model4);
            model4.addColumn("Motif");
            model4.addColumn("Service");
            model4.addColumn("Date Entrée");
            model4.addColumn("Date Sortie");
            model4.addColumn("Medecin");
            
            for (Sejour s : listHospi) {
                PH p = PH.AfficherPH(s.getPhRef());
                model4.addRow(new Object[]{s.getMotif(), p.getSpecialite(),s.getDateEntree(),s.getDateSortie(),"Dr "+p.getNom()});
            }
            
            
            ArrayList<Sejour> listConsult = Sejour.ListDesConsultation(monPatient.getIpp());
            
            DefaultTableModel model5 = new DefaultTableModel();
            model5.setRowCount(0);
            model5 = new DefaultTableModel();
            Tableau_Consultation.setModel(model5);
            model5.addColumn("Motif");
            model5.addColumn("Service"); 
            model5.addColumn("Date Entrée");
            model5.addColumn("Medecin");
            
            for (Sejour s : listConsult) {
                PH p = PH.AfficherPH(s.getPhRef());
                String service = Service.AfficherServices(p.getSpecialite());
                model5.addRow(new Object[]{s.getMotif(), service,s.getDateEntree(),"Dr "+p.getNom()});
            }
            
            
            
            
            ArrayList<Prestation> listPresta = Prestation.ListDesPrestation(monPatient.getIpp());
            
            DefaultTableModel model6 = new DefaultTableModel();
            model6.setRowCount(0);
            model6 = new DefaultTableModel();
            Tableau_Prestation.setModel(model6);
            model6.addColumn("Date");
            model6.addColumn("Prelevement / Acte"); 
            model6.addColumn("Resultat");
            
            for (Prestation p : listPresta) {
                model6.addRow(new Object[]{p.getDate(),p.getType(),p.getResultat()});
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

        Button_Deconexion = new javax.swing.JButton();
        Button_Accueil = new javax.swing.JButton();
        Panel_Accueil = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        spe = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        secteur = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        chambre = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        lit = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        PatientPHNom = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        PatientPHPrenom = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        PatientPHIPP = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        PatientPHDateNaissance = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        PatientPHAdresse = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        PatientPHVille = new javax.swing.JLabel();
        PatientPHCP = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        PatientPHTelephone = new javax.swing.JLabel();
        PatientPHNom1 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        PrenomContact = new javax.swing.JLabel();
        NomContact = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        adresseContact = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        CPContact = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        villeContact = new javax.swing.JLabel();
        telContact = new javax.swing.JLabel();
        relationContact = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jpanel150 = new javax.swing.JPanel();
        AjouterPrescription = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tableau_prescription = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tableau_ActeInf = new javax.swing.JTable();
        Res = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Tableau_Prestation = new javax.swing.JTable();
        Bouton_DemanderAnalyse = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Tableau_Observation = new javax.swing.JTable();
        AjouterObservation = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ValiderLettreSortie = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        Tableau_Hospitalisation = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        Tableau_Consultation = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        Label_Titre = new javax.swing.JLabel();
        Label_bckgrd = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Accueil");
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setSize(new java.awt.Dimension(1366, 768));
        getContentPane().setLayout(null);

        Button_Deconexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Deconnexion.png"))); // NOI18N
        Button_Deconexion.setMaximumSize(new java.awt.Dimension(213, 213));
        Button_Deconexion.setMinimumSize(new java.awt.Dimension(213, 213));
        Button_Deconexion.setPreferredSize(new java.awt.Dimension(213, 213));
        Button_Deconexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_DeconexionActionPerformed(evt);
            }
        });
        getContentPane().add(Button_Deconexion);
        Button_Deconexion.setBounds(180, 30, 60, 50);

        Button_Accueil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/AccueilOff.png"))); // NOI18N
        Button_Accueil.setMaximumSize(new java.awt.Dimension(213, 213));
        Button_Accueil.setMinimumSize(new java.awt.Dimension(213, 213));
        Button_Accueil.setPreferredSize(new java.awt.Dimension(213, 213));
        Button_Accueil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_AccueilActionPerformed(evt);
            }
        });
        getContentPane().add(Button_Accueil);
        Button_Accueil.setBounds(250, 30, 60, 50);

        Panel_Accueil.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Localisation", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arvo", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        spe.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        spe.setForeground(new java.awt.Color(102, 102, 102));
        spe.setText("spe");

        jLabel87.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(102, 102, 102));
        jLabel87.setText("Specialite");

        secteur.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        secteur.setForeground(new java.awt.Color(102, 102, 102));
        secteur.setText("secteur");

        jLabel88.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(102, 102, 102));
        jLabel88.setText("Secteur");

        jLabel89.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(102, 102, 102));
        jLabel89.setText("Chambre");

        chambre.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        chambre.setForeground(new java.awt.Color(102, 102, 102));
        chambre.setText("chambre");

        jLabel90.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(102, 102, 102));
        jLabel90.setText("Lit");

        lit.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        lit.setForeground(new java.awt.Color(102, 102, 102));
        lit.setText("lit");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(secteur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel88)
                            .addComponent(jLabel89)
                            .addComponent(jLabel90)
                            .addComponent(jLabel87, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addComponent(spe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chambre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(secteur)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel89)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chambre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lit)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informations Patient", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arvo", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(102, 102, 102));
        jLabel31.setText("NOM :");

        PatientPHNom.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHNom.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHNom.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHNom.setText("PatientPHNom");

        jLabel51.setBackground(new java.awt.Color(255, 255, 255));
        jLabel51.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Prenom :");

        PatientPHPrenom.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHPrenom.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHPrenom.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHPrenom.setText("PatientPHPrenom");

        jLabel53.setBackground(new java.awt.Color(255, 255, 255));
        jLabel53.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(102, 102, 102));
        jLabel53.setText("IPP :");

        PatientPHIPP.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHIPP.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHIPP.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHIPP.setText("PatientPHIPP");

        jLabel57.setBackground(new java.awt.Color(255, 255, 255));
        jLabel57.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(102, 102, 102));
        jLabel57.setText("Sexe :");

        jLabel59.setBackground(new java.awt.Color(255, 255, 255));
        jLabel59.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(102, 102, 102));
        jLabel59.setText("Date de naissance :");

        PatientPHDateNaissance.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHDateNaissance.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHDateNaissance.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHDateNaissance.setText("PatientPHDateNaissance");

        jLabel61.setBackground(new java.awt.Color(255, 255, 255));
        jLabel61.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(102, 102, 102));
        jLabel61.setText("Adresse postale :");

        PatientPHAdresse.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHAdresse.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHAdresse.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHAdresse.setText("PatientPHAdresse");

        jLabel63.setBackground(new java.awt.Color(255, 255, 255));
        jLabel63.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(102, 102, 102));
        jLabel63.setText("Ville :");

        PatientPHVille.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHVille.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHVille.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHVille.setText("PatientPHVille");

        PatientPHCP.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHCP.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHCP.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHCP.setText("PatientPHCP");

        jLabel66.setBackground(new java.awt.Color(255, 255, 255));
        jLabel66.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(102, 102, 102));
        jLabel66.setText("Code Postal :");

        jLabel68.setBackground(new java.awt.Color(255, 255, 255));
        jLabel68.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(102, 102, 102));
        jLabel68.setText("Telephone :");

        PatientPHTelephone.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHTelephone.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHTelephone.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHTelephone.setText("PatientPHTelephone");

        PatientPHNom1.setBackground(new java.awt.Color(255, 255, 255));
        PatientPHNom1.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PatientPHNom1.setForeground(new java.awt.Color(102, 102, 102));
        PatientPHNom1.setText("PatientPHNom1");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PatientPHAdresse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PatientPHNom, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PatientPHPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jLabel53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PatientPHIPP, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PatientPHNom1)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel59)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PatientPHDateNaissance))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PatientPHTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel66)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PatientPHCP, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel63)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PatientPHVille, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(PatientPHNom)
                    .addComponent(jLabel53)
                    .addComponent(PatientPHIPP)
                    .addComponent(jLabel51)
                    .addComponent(PatientPHPrenom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(PatientPHDateNaissance)
                    .addComponent(jLabel59)
                    .addComponent(PatientPHNom1))
                .addGap(33, 33, 33)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61)
                    .addComponent(PatientPHAdresse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(PatientPHCP)
                    .addComponent(jLabel63)
                    .addComponent(PatientPHVille))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel68)
                    .addComponent(PatientPHTelephone))
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personne a Contacter", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arvo", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel71.setBackground(new java.awt.Color(255, 255, 255));
        jLabel71.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(102, 102, 102));
        jLabel71.setText("NOM :");

        jLabel72.setBackground(new java.awt.Color(255, 255, 255));
        jLabel72.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(102, 102, 102));
        jLabel72.setText("Prenom :");

        PrenomContact.setBackground(new java.awt.Color(255, 255, 255));
        PrenomContact.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        PrenomContact.setForeground(new java.awt.Color(102, 102, 102));
        PrenomContact.setText("PrenomContact");

        NomContact.setBackground(new java.awt.Color(255, 255, 255));
        NomContact.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        NomContact.setForeground(new java.awt.Color(102, 102, 102));
        NomContact.setText("NomContact");

        jLabel75.setBackground(new java.awt.Color(255, 255, 255));
        jLabel75.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(102, 102, 102));
        jLabel75.setText("Adresse postale :");

        adresseContact.setBackground(new java.awt.Color(255, 255, 255));
        adresseContact.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        adresseContact.setForeground(new java.awt.Color(102, 102, 102));
        adresseContact.setText("adresseContact");

        jLabel77.setBackground(new java.awt.Color(255, 255, 255));
        jLabel77.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(102, 102, 102));
        jLabel77.setText("Code Postal :");

        CPContact.setBackground(new java.awt.Color(255, 255, 255));
        CPContact.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        CPContact.setForeground(new java.awt.Color(102, 102, 102));
        CPContact.setText("CPContact");

        jLabel79.setBackground(new java.awt.Color(255, 255, 255));
        jLabel79.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(102, 102, 102));
        jLabel79.setText("Ville :");

        villeContact.setBackground(new java.awt.Color(255, 255, 255));
        villeContact.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        villeContact.setForeground(new java.awt.Color(102, 102, 102));
        villeContact.setText("villeContact");

        telContact.setBackground(new java.awt.Color(255, 255, 255));
        telContact.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        telContact.setForeground(new java.awt.Color(102, 102, 102));
        telContact.setText("telContact");

        relationContact.setBackground(new java.awt.Color(255, 255, 255));
        relationContact.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        relationContact.setForeground(new java.awt.Color(102, 102, 102));
        relationContact.setText("relationContact");

        jLabel84.setBackground(new java.awt.Color(255, 255, 255));
        jLabel84.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(102, 102, 102));
        jLabel84.setText("Relation :");

        jLabel85.setBackground(new java.awt.Color(255, 255, 255));
        jLabel85.setFont(new java.awt.Font("Arvo", 1, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(102, 102, 102));
        jLabel85.setText("Telephone :");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NomContact, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PrenomContact, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel84)
                        .addGap(11, 11, 11)
                        .addComponent(relationContact)
                        .addContainerGap())
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel77)
                        .addGap(7, 7, 7)
                        .addComponent(CPContact, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel79)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(villeContact, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel75)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adresseContact, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel85)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(telContact, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(NomContact)
                    .addComponent(jLabel72)
                    .addComponent(PrenomContact)
                    .addComponent(jLabel84)
                    .addComponent(relationContact))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(adresseContact, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel77)
                    .addComponent(CPContact)
                    .addComponent(jLabel79)
                    .addComponent(villeContact))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(telContact))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("PATIENT", jPanel2);

        jpanel150.setBackground(new java.awt.Color(255, 255, 255));

        AjouterPrescription.setBackground(new java.awt.Color(255, 255, 255));
        AjouterPrescription.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        AjouterPrescription.setForeground(new java.awt.Color(102, 102, 102));
        AjouterPrescription.setText("NouvelPrescription");
        AjouterPrescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterPrescriptionActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));
        jPanel9.setPreferredSize(new java.awt.Dimension(678, 214));

        jLabel34.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText("Prescriptions");

        Tableau_prescription.setFont(new java.awt.Font("Arvo", 0, 11)); // NOI18N
        Tableau_prescription.setForeground(new java.awt.Color(102, 102, 102));
        Tableau_prescription.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numéro Prescription", "Prescription", "Date", "Prescripteur"
            }
        ));
        jScrollPane2.setViewportView(Tableau_prescription);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));
        jPanel10.setPreferredSize(new java.awt.Dimension(678, 214));

        jLabel35.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 102));
        jLabel35.setText("Actes Infirmiers");

        Tableau_ActeInf.setFont(new java.awt.Font("Arvo", 0, 11)); // NOI18N
        Tableau_ActeInf.setForeground(new java.awt.Color(102, 102, 102));
        Tableau_ActeInf.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Prescription", "Etat", "Date", "Soignant", "Résultats"
            }
        ));
        jScrollPane3.setViewportView(Tableau_ActeInf);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpanel150Layout = new javax.swing.GroupLayout(jpanel150);
        jpanel150.setLayout(jpanel150Layout);
        jpanel150Layout.setHorizontalGroup(
            jpanel150Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel150Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel150Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(AjouterPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpanel150Layout.setVerticalGroup(
            jpanel150Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel150Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanel150Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AjouterPrescription, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("SOINS", jpanel150);

        Res.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));
        jPanel11.setPreferredSize(new java.awt.Dimension(678, 214));

        jLabel36.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(102, 102, 102));
        jLabel36.setText("Résultats");

        Tableau_Prestation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Date", "Prescripteur", "Prélèvement/ Acte réalisé"
            }
        ));
        jScrollPane5.setViewportView(Tableau_Prestation);

        Bouton_DemanderAnalyse.setBackground(new java.awt.Color(255, 255, 255));
        Bouton_DemanderAnalyse.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        Bouton_DemanderAnalyse.setForeground(new java.awt.Color(102, 102, 102));
        Bouton_DemanderAnalyse.setText("Demander Analyse");
        Bouton_DemanderAnalyse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bouton_DemanderAnalyseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 573, Short.MAX_VALUE)
                .addComponent(Bouton_DemanderAnalyse)
                .addContainerGap())
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(Bouton_DemanderAnalyse, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(388, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                    .addContainerGap(59, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout ResLayout = new javax.swing.GroupLayout(Res);
        Res.setLayout(ResLayout);
        ResLayout.setHorizontalGroup(
            ResLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
                .addContainerGap())
        );
        ResLayout.setVerticalGroup(
            ResLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("RESULTATS", Res);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));
        jPanel18.setPreferredSize(new java.awt.Dimension(678, 214));

        jLabel39.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText("Observations");

        Tableau_Observation.setFont(new java.awt.Font("Arvo", 0, 11)); // NOI18N
        Tableau_Observation.setForeground(new java.awt.Color(102, 102, 102));
        Tableau_Observation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Personnel", "Observation"
            }
        ));
        jScrollPane6.setViewportView(Tableau_Observation);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                .addContainerGap())
        );

        AjouterObservation.setBackground(new java.awt.Color(255, 255, 255));
        AjouterObservation.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        AjouterObservation.setForeground(new java.awt.Color(102, 102, 102));
        AjouterObservation.setText("Ajouter Observation");
        AjouterObservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AjouterObservationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(AjouterObservation, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(AjouterObservation, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("OBSERVATIONS", jPanel5);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));

        jLabel38.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("Lettre de Sortie");

        jLabel7.setFont(new java.awt.Font("Arvo", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Tapez la lettre de sortie ci-dessous :");

        ValiderLettreSortie.setBackground(new java.awt.Color(255, 255, 255));
        ValiderLettreSortie.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        ValiderLettreSortie.setForeground(new java.awt.Color(102, 102, 102));
        ValiderLettreSortie.setText("Valider");
        ValiderLettreSortie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValiderLettreSortieActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addComponent(jLabel7))
                        .addGap(0, 662, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ValiderLettreSortie, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(ValiderLettreSortie, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("LETTRE DE SORTIE", jPanel16);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102)));

        jLabel43.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 102, 102));
        jLabel43.setText("Liste des Consultations");

        Tableau_Hospitalisation.setFont(new java.awt.Font("Arvo", 0, 11)); // NOI18N
        Tableau_Hospitalisation.setForeground(new java.awt.Color(102, 102, 102));
        Tableau_Hospitalisation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Service"
            }
        ));
        jScrollPane9.setViewportView(Tableau_Hospitalisation);

        Tableau_Consultation.setFont(new java.awt.Font("Arvo", 0, 11)); // NOI18N
        Tableau_Consultation.setForeground(new java.awt.Color(102, 102, 102));
        Tableau_Consultation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "PH Responsable"
            }
        ));
        jScrollPane10.setViewportView(Tableau_Consultation);

        jLabel44.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText("Liste des Hospitalisations");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                    .addComponent(jScrollPane9)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel43))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("HISTORIQUE", jPanel7);

        Label_Titre.setBackground(new java.awt.Color(255, 255, 255));
        Label_Titre.setFont(new java.awt.Font("Arvo", 1, 18)); // NOI18N
        Label_Titre.setForeground(new java.awt.Color(102, 102, 102));
        Label_Titre.setText("Information Patient : + Nom Prenom patient");

        javax.swing.GroupLayout Panel_AccueilLayout = new javax.swing.GroupLayout(Panel_Accueil);
        Panel_Accueil.setLayout(Panel_AccueilLayout);
        Panel_AccueilLayout.setHorizontalGroup(
            Panel_AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_AccueilLayout.createSequentialGroup()
                .addContainerGap(338, Short.MAX_VALUE)
                .addComponent(Label_Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(260, 260, 260))
            .addGroup(Panel_AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Panel_AccueilLayout.createSequentialGroup()
                    .addGap(121, 121, 121)
                    .addComponent(jTabbedPane1)
                    .addGap(122, 122, 122)))
        );
        Panel_AccueilLayout.setVerticalGroup(
            Panel_AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_AccueilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Label_Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(581, Short.MAX_VALUE))
            .addGroup(Panel_AccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Panel_AccueilLayout.createSequentialGroup()
                    .addGap(68, 68, 68)
                    .addComponent(jTabbedPane1)
                    .addGap(68, 68, 68)))
        );

        getContentPane().add(Panel_Accueil);
        Panel_Accueil.setBounds(220, 120, 1120, 630);

        Label_bckgrd.setFont(new java.awt.Font("Arvo", 0, 14)); // NOI18N
        Label_bckgrd.setForeground(new java.awt.Color(102, 102, 102));
        Label_bckgrd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Background03.png"))); // NOI18N
        Label_bckgrd.setName("Label_bckgrd"); // NOI18N
        getContentPane().add(Label_bckgrd);
        Label_bckgrd.setBounds(0, -20, 1370, 810);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
  
    
/******************************************************
                        RETOUR ACCUEIL                 
 /****************************************************/ 
    private void Button_AccueilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_AccueilActionPerformed
    URGENCE_Accueil ur_accP =new URGENCE_Accueil(quiSuisJe);
    ur_accP.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_Button_AccueilActionPerformed


 /*****************************************************
                       DECONNEXION                     
 *****************************************************/  
    private void Button_DeconexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_DeconexionActionPerformed
        InterfaceConnexion connexion;
        try {
            connexion = new InterfaceConnexion();
            connexion.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(URGENCE_Detail_Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_Button_DeconexionActionPerformed

    
    
/*****************************************************
            VALIDER LA LETTRE DE SORTIE               
*****************************************************/  
    private void ValiderLettreSortieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValiderLettreSortieActionPerformed

        String lettreDeSortie = jTextArea1.getText();
        Date dateAndTime = Calendar.getInstance().getTime();
        String date =  dateAndTime.getYear()+"-"+dateAndTime.getMonth()+"-"+dateAndTime.getDate()+" "+dateAndTime.getHours()+":"+dateAndTime.getMinutes()+":"+dateAndTime.getSeconds();
        Sejour s = Sejour.RecupererSejour(monPatient.getIpp());
        String message = Sejour.ajouterLettreDeSortie(date,lettreDeSortie,s.getId_Sejour());
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame, message);
        frame.dispose();
    }//GEN-LAST:event_ValiderLettreSortieActionPerformed

    
/*****************************************************
                AJOUTER UNE OBSERVATION               
*****************************************************/  
    private void AjouterObservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterObservationActionPerformed
SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDialog dialog3 = new JDialog();
                dialog3.setSize(500, 250);//On lui donne une taille
                dialog3.setTitle("Ajouter Une Observation"); //On lui donne un titre
                dialog3.setLocationRelativeTo(null);

                dialog3.setLayout(new BorderLayout());
                dialog3.setVisible(true);//On la rend visible

                dialog3.setLayout(new BorderLayout());
                JButton valider = new JButton("Valider");
                JPanel boutons = new JPanel();
                boutons.setLayout(new FlowLayout());
                dialog3.add(boutons, BorderLayout.SOUTH);
                boutons.add(valider);

                JPanel infosPrescription = new JPanel();
                infosPrescription.setLayout(new GridLayout(6, 0));
                dialog3.add(infosPrescription, BorderLayout.CENTER);

                JLabel titre = new JLabel("AJOUT D'UNE OBSERVATION", SwingConstants.CENTER);
                titre.setFont(new Font("Arvo", Font.PLAIN, 22));
                infosPrescription.add(titre);

                Date dateAndTime = Calendar.getInstance().getTime();
                int mois = dateAndTime.getMonth()+1;
                int annee = 1900 + dateAndTime.getYear();
                String date = annee +"-"+mois+"-"+dateAndTime.getDate();
                JLabel dateTime = new JLabel(dateAndTime.toString());
                infosPrescription.add(dateTime);

                infosPrescription.add(new JLabel("<html> <br> Observation : </html>"));

                JTextField resultat2 = new JTextField();
                infosPrescription.add(resultat2);

                dialog3.setVisible(true);//On la rend visible
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix

                valider.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
      
                        String message = Obersvation.AjouterObservation(resultat2.getText(),date, monPatient.getIpp());
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, message);
                         
                        ArrayList<Obersvation> listObservation = Obersvation.afficherObservation(monPatient.getIpp());
            
                        DefaultTableModel model3 = new DefaultTableModel();
                        model3.setRowCount(0);
                        model3 = new DefaultTableModel();
                        Tableau_Observation.setModel(model3);
                        model3.addColumn("Observation");
                        model3.addColumn("Date");
                        for (Obersvation obs : listObservation) {
                            model3.addRow(new Object[]{obs.getObs(), obs.getDate()});
                        }
                        dialog3.dispose();
                    }
                    
                });
            }
        });
    }//GEN-LAST:event_AjouterObservationActionPerformed

    
    
/*****************************************************
               AJOUTER UNE PRESCRIPTION               
*****************************************************/  
    private void AjouterPrescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AjouterPrescriptionActionPerformed
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDialog dialog2 = new JDialog();
                dialog2.setSize(500, 300);//On lui donne une taille
                dialog2.setTitle("Ajouter Une Prescription"); //On lui donne un titre
                dialog2.setLocationRelativeTo(null);

                dialog2.setLayout(new BorderLayout());
                dialog2.setVisible(true);//On la rend visible

                dialog2.setLayout(new BorderLayout());
                JButton valider = new JButton("Valider");
                JPanel boutons = new JPanel();
                boutons.setLayout(new FlowLayout());
                dialog2.add(boutons, BorderLayout.SOUTH);
                boutons.add(valider);

                JPanel infosPrescription = new JPanel();
                infosPrescription.setLayout(new GridLayout(7, 0));
                dialog2.add(infosPrescription, BorderLayout.CENTER);

                JLabel titre = new JLabel("NOUVELLE PRESCRIPTION", SwingConstants.CENTER);
                titre.setFont(new Font("Arvo", Font.PLAIN, 22));
                infosPrescription.add(titre);

                JLabel patient1;

                Date dateAndTime = Calendar.getInstance().getTime();
                JLabel dateTime = new JLabel(dateAndTime.toString());
                int mois = dateAndTime.getMonth()+1;
                int annee = 1900 + dateAndTime.getYear();
                String date = annee +"-"+mois+"-"+dateAndTime.getDate();
                infosPrescription.add(dateTime);

                infosPrescription.add(new JLabel("<html> <br> Prescripteur : Dr "+quiSuisJe.getNom()+" </html>"));

                infosPrescription.add(new JLabel("<html> <br> Prescription : </html>"));
                JTextField prescription = new JTextField();
                infosPrescription.add(prescription);

                dialog2.setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                
                valider.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        String message = Prescription.AjouterUnePrescription(monPatient.getIpp(),prescription.getText(),quiSuisJe,date);           
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, message);
                        dialog2.dispose();
                        
                        ArrayList<Prescription> listePrescription = Prescription.afficherPrescription(monPatient.getIpp());
      
                        DefaultTableModel model = new DefaultTableModel();
                        model.setRowCount(0);
                        Tableau_prescription.setModel(model);
                        model.addColumn("Numéro de Prescription");
                        model.addColumn("Préscription");
                        model.addColumn("Date");
                        model.addColumn("Prescripteur");

                        for (Prescription p : listePrescription) {
                            model.addRow(new Object[]{p.getId_prescription(), p.getPrescri(), p.getDate(), "Dr "+p.getPh()});
                        }
                    }
                });
            }
        });
    }//GEN-LAST:event_AjouterPrescriptionActionPerformed

    private void Bouton_DemanderAnalyseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bouton_DemanderAnalyseActionPerformed
         String s = Sejour.checkSejour(monPatient.getIpp());
        if(s.equals("NoSej")){
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Ce patient n'est pas hospitalisé ou en consultation : demande d'analyse impossible");
        }else{
        
            SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        JDialog dialog3 = new JDialog();
                        dialog3.setSize(500, 250);//On lui donne une taille
                        dialog3.setTitle("Demande d'Analyse"); //On lui donne un titre
                        dialog3.setLocationRelativeTo(null);

                        dialog3.setLayout(new BorderLayout());
                        dialog3.setVisible(true);//On la rend visible

                        dialog3.setLayout(new BorderLayout());
                        JButton valider = new JButton("Valider");
                        JPanel boutons = new JPanel();
                        boutons.setLayout(new FlowLayout());
                        dialog3.add(boutons, BorderLayout.SOUTH);
                        boutons.add(valider);

                        JPanel infosPrescription = new JPanel();
                        infosPrescription.setLayout(new GridLayout(6, 0));
                        dialog3.add(infosPrescription, BorderLayout.CENTER);

                        JLabel titre = new JLabel("Demande d'Analyse", SwingConstants.CENTER);
                        titre.setFont(new Font("Arvo", Font.PLAIN, 22));
                        infosPrescription.add(titre);

                        Date dateAndTime = Calendar.getInstance().getTime();
                        int mois = dateAndTime.getMonth()+1;
                        int annee = 1900 + dateAndTime.getYear();
                        String date = annee +"-"+mois+"-"+dateAndTime.getDate();
                        JLabel dateTime = new JLabel(dateAndTime.toString());
                        infosPrescription.add(dateTime);

                        infosPrescription.add(new JLabel("<html> <br> Type d'analyse demandée : </html>"));

                        JTextField resultat2 = new JTextField();
                        infosPrescription.add(resultat2);

                        dialog3.setVisible(true);//On la rend visible
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix

                        valider.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {

                                String message = Prestation.DemandeDePrestation(monPatient.getIpp(), date,resultat2.getText(),quiSuisJe);
                                JFrame frame = new JFrame();
                                JOptionPane.showMessageDialog(frame, message);
                                dialog3.dispose();

                                 ArrayList<Prestation> listPresta = Prestation.ListDesPrestation(monPatient.getIpp());

                                DefaultTableModel model6 = new DefaultTableModel();
                                model6.setRowCount(0);
                                model6 = new DefaultTableModel();
                                Tableau_Prestation.setModel(model6);
                                model6.addColumn("Date");
                                model6.addColumn("Prelevement / Acte"); 
                                model6.addColumn("Resultat");

                                for (Prestation p : listPresta) {
                                    model6.addRow(new Object[]{p.getDate(),p.getType(),p.getResultat()});
                                }

                            }

                        });
                    }
            });
        }
    }//GEN-LAST:event_Bouton_DemanderAnalyseActionPerformed

     JDialog dialog2;
    JDialog dialog3;
    javax.swing.JComboBox prescriptions;
    javax.swing.JComboBox prescriptionsType;
    JTextField resultat;
    JTextField resultat2;
    PH quiSuisJe;
    Patient monPatient;
    PersonneDeConfiance maPersConf = new PersonneDeConfiance();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AjouterObservation;
    private javax.swing.JButton AjouterPrescription;
    private javax.swing.JButton Bouton_DemanderAnalyse;
    private javax.swing.JButton Button_Accueil;
    private javax.swing.JButton Button_Deconexion;
    private javax.swing.JLabel CPContact;
    private javax.swing.JLabel Label_Titre;
    private javax.swing.JLabel Label_bckgrd;
    private javax.swing.JLabel NomContact;
    private javax.swing.JPanel Panel_Accueil;
    private javax.swing.JLabel PatientPHAdresse;
    private javax.swing.JLabel PatientPHCP;
    private javax.swing.JLabel PatientPHDateNaissance;
    private javax.swing.JLabel PatientPHIPP;
    private javax.swing.JLabel PatientPHNom;
    private javax.swing.JLabel PatientPHNom1;
    private javax.swing.JLabel PatientPHPrenom;
    private javax.swing.JLabel PatientPHTelephone;
    private javax.swing.JLabel PatientPHVille;
    private javax.swing.JLabel PrenomContact;
    private javax.swing.JPanel Res;
    private javax.swing.JTable Tableau_ActeInf;
    private javax.swing.JTable Tableau_Consultation;
    private javax.swing.JTable Tableau_Hospitalisation;
    private javax.swing.JTable Tableau_Observation;
    private javax.swing.JTable Tableau_Prestation;
    private javax.swing.JTable Tableau_prescription;
    private javax.swing.JButton ValiderLettreSortie;
    private javax.swing.JLabel adresseContact;
    private javax.swing.JLabel chambre;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jpanel150;
    private javax.swing.JLabel lit;
    private javax.swing.JLabel relationContact;
    private javax.swing.JLabel secteur;
    private javax.swing.JLabel spe;
    private javax.swing.JLabel telContact;
    private javax.swing.JLabel villeContact;
    // End of variables declaration//GEN-END:variables
}

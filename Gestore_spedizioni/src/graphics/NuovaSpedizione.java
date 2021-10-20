package graphics;

import java.awt.BorderLayout;

import java.text.DateFormat;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Archivio.Archivio;
import dialogGraphics.ErrorDialog;
import dialogGraphics.MessageDialog;
import spedizione.Spedizione;
import spedizione.SpedizioneAssicurata;
import utenti.Cliente;

import java.util.Date;
import java.util.Locale;
/**
 * Classe per l'implementazione del frame che permette la registrazione di una nuova spedizione
 * Svolge la funzione di ascoltatore dei pulsanti e delle caselle di testo
 * @author filipporeggiani
 *
 */
public class NuovaSpedizione extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ButtonGroup contenente i due JradioButton per la scelta del tipo di  spedizione
	 */
	private ButtonGroup group;
	/**
	 * Scelta per il tipo di spedizione
	 */
	private JRadioButton spedNormale,spedAssicurata;
	/**
	 * Casella di testo contenente il codice della spedizione (Generato automaticamente)
	 */
	private JTextField codice;
	/**
	 * Casella di testo contenente la destinazione della spedizione
	 */
	private JTextField destinazione;
	/**
	 * Casella di testo contenente il peso della spedizione
	 */
	private JTextField peso;
	/**
	 * Casella di testo contente la data di creazione della spedizione (fornita dal sistema)
	 */
	private JTextField data;
	/**
	 * Casella di testo contenente lo stato della spedizione (appena creata "IN PREPARAZIONE")
	 */
	private JTextField stato;
	/**
	 * Casella di testo per l'acquisizione del valore assicurato per la spedizione (fornito dall'utente)
	 */
	private JTextField vAssicurato;
	/**
	 * Pulsante per effettuare il logout del cliente
	 */
	private JButton logoutButt;
	/**
	 * Pulsante per la creazione di una nuova spedizione
	 */
	private JButton newspedButt;
	/**
	 * Cliente che ha effettuato l'autenticazione per creare una nuova spedizione
	 */
	private Cliente cliente;
	/**
	 * Archivio contenente tutte le spedizioni di tutti i clienti registrati
	 */
	private Archivio<Spedizione> spedizioni;
	/**
	 * Archivio contenente tutti i clienti registrati nel sistema
	 */
	private Archivio<Cliente> clienti;
	
	/**
	 * Costruttore che crea il frame per la registrazione della nuova spedizione
	 * Acquisisce il cliente che si è autenticato e i due archivi per la ricerca del cliente in archivio e l'aggiunta della nuova spedizione
	 * @param cliente cliente che ha effettuato il login
	 * @param spedizioni archivio delle spedizioni
	 * @param clienti archivio dei clienti
	 */
	public NuovaSpedizione(Cliente cliente, Archivio<Spedizione> spedizioni, Archivio<Cliente> clienti) {
		super("Nuova Spedizione");
		this.cliente=cliente;
		this.spedizioni=spedizioni;
		this.clienti=clienti;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pannelloNord = new JPanel();
		JPanel pannelloSud= new JPanel();
		
		pannelloNord.setLayout(new BorderLayout());
		pannelloSud.setLayout(new GridLayout(8,2));
		
		//componenti pannello nord
		JLabel l= new JLabel("Benvenuto "+cliente.getUsername()+".");
		JLabel s= new JLabel("Selezionare la tipologia di spedizione: ");
		JLabel nota= new JLabel("Il Codice della spedizione viene generato in automatico con l'immissione dei dati.");
		pannelloNord.add(l,BorderLayout.NORTH);
		pannelloNord.add(nota,BorderLayout.CENTER);
		pannelloNord.add(s,BorderLayout.SOUTH);
		
		//componenti pannello sud
		spedNormale= new JRadioButton("Normale");
		spedAssicurata= new JRadioButton("Assicurata");
		group= new ButtonGroup();
		group.add(spedNormale);
		group.add(spedAssicurata);
		JLabel cod= new JLabel("Codice:");
		codice= new JTextField();
		codice.setEditable(false);
		codice.setEnabled(false);
		codice.setText("-");
		JLabel dest= new JLabel("Destinazione:");
		destinazione= new JTextField();
		destinazione.setEditable(true);
		destinazione.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("destinazione inserita: "+destinazione.getText());
			}
		});
		
		JLabel p= new JLabel("Peso (in Kg):");
		peso= new JTextField();
		peso.setEditable(true);
		peso.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Peso inserito: "+peso.getText());
				codice.setText((String) cliente.getUsername()+(cliente.getSpedizioni().getDim()+1)+destinazione.getText().subSequence(0, 2)+peso.getText());
			}
			
		});
		//data
		JLabel ldata= new JLabel("Data: ");
		Date d = new Date();
		DateFormat formatoData = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
		String sData = formatoData.format(d);
		data = new JTextField();
		data.setText(sData);
		data.setEnabled(false);
		data.setEditable(false);
		JLabel stat= new JLabel("Stato: ");
		stato= new JTextField();
		stato.setText("IN PREPARAZIONE");
		stato.setEnabled(false);
		stato.setEditable(false);  //SE CLIENTE
		JLabel ass= new JLabel("Valore Assicurato:");
		vAssicurato= new JTextField();
		spedNormale.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vAssicurato.setText("-");
				vAssicurato.setEditable(false);
				vAssicurato.setEnabled(false);
				System.out.println("Scelta spedizione Normale");
			}
			
		}); 
		spedAssicurata.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				vAssicurato.setEditable(true);
				vAssicurato.setText("");
				vAssicurato.setEnabled(true);
				System.out.println("Scelta spedizione Assicurata");
			}
			
		});
		vAssicurato.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Valore Assicurato: "+vAssicurato.getText());
			}
			
		});
		newspedButt= new JButton("CREA SPEDIZIONE");
		newspedButt.setEnabled(true);
		newspedButt.addActionListener(this);
		logoutButt= new JButton("LOGOUT");
		logoutButt.setEnabled(true);
		logoutButt.addActionListener(this);
		
		pannelloSud.add(spedNormale);
		pannelloSud.add(spedAssicurata);
		pannelloSud.add(cod);
		pannelloSud.add(codice);
		pannelloSud.add(dest);
		pannelloSud.add(destinazione);
		pannelloSud.add(p);
		pannelloSud.add(peso);
		pannelloSud.add(ldata);
		pannelloSud.add(data);
		pannelloSud.add(stat);
		pannelloSud.add(stato);
		pannelloSud.add(ass);
		pannelloSud.add(vAssicurato);
		pannelloSud.add(newspedButt);
		pannelloSud.add(logoutButt);
		
		this.add((pannelloNord),BorderLayout.NORTH);
		this.add((pannelloSud),BorderLayout.SOUTH);
	}
	
	/**
	 * Gestione dei pulsanti del frame
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String scelta= (String) e.getActionCommand();
		if(scelta.equals("CREA SPEDIZIONE")) {
			if((!destinazione.getText().equals("") && !peso.getText().equals("") && !vAssicurato.getText().equals(""))) {
				Spedizione sped;
				String pesoS=peso.getText();
				int pesoInt = Integer.parseInt(pesoS);
			
				//Se scelta spedizione normale
				if(spedNormale.isSelected()) {
					sped= new Spedizione(cliente.getUsername(),codice.getText(),destinazione.getText(),pesoInt,data.getText(),stato.getText());
					spedizioni.add(sped);
					Cliente c= clienti.CercaCliente(cliente.getUsername());
					c.getSpedizioni().add(sped);
				}
				//se scelta spedizione assicurata
				else {
						int vAss= Integer.parseInt(vAssicurato.getText());
						sped= new SpedizioneAssicurata(cliente.getUsername(),codice.getText(),destinazione.getText(),pesoInt,data.getText(),stato.getText(),vAss);
						spedizioni.add(sped);
						Cliente c= clienti.CercaCliente(cliente.getUsername());
						c.getSpedizioni().add(sped);
						vAssicurato.setText("");
					
				}
			
				new MessageDialog("Nuova spedizione creata con successo.");
				group.clearSelection();
				codice.setText("");
				destinazione.setText("");
				peso.setText("");
			
				clienti.StampaClienti();
				System.out.println();
				spedizioni.StampaSpedizioni();
				System.out.println();
			}
			else
				new ErrorDialog("Mancata compilazione di tutti i campi");
		}
		
		if(scelta.equals("LOGOUT")) {
			new MessageDialog("Effettuo il logout..");
			this.setVisible(false);
			this.dispose();	
		}
		
	}


}

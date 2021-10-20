package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Archivio.Archivio;
import dialogGraphics.ErrorDialog;
import dialogGraphics.MessageDialog;
import spedizione.Spedizione;
import utenti.Cliente;

/**
 * Classe che implementa il frame per la registrazione di un nuovo cliente nell'archivio dei clienti
 * La classe svolge anche la funzione di ascoltatore dei pulsanti del frame
 * @author filipporeggiani
 *
 */
public class Registrazione extends JFrame implements ActionListener{
	/**
	 * Casella di testo che acquisisce lo username immesso per la registrazione
	 */
	private JTextField username;
	/**
	 * Casella di testo che acquisisce la password immessa per la registrazione
	 */
	private JTextField password;
	/**
	 * Casella di testo che acquisisce l'indirizzo immesso per la registrazione
	 */
	private JTextField indirizzo;
	/**
	 * Pulsante per la registrazione del nuovo cliente
	 */
	private JButton regButton;
	/**
	 * Contiene i dati del cliente appena registrato
	 */
	private Cliente cliente;
	/**
	 * Archivio contenente tutti i clienti registrati nel sistema
	 */
	private Archivio<Cliente> clienti;
	/**
	 * Collegamento al frame principale per l'impostazione dei pulsanti in seguito alla registrazione
	 */
	private FrameStart startFrame;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore che implementa il frame e acquisisce il frame principale e l'archivio contenente tutti i clienti
	 * Il nuovo cliente viene aggiunto all'archivio 
	 * @param start frame di start
	 * @param clienti archivio dei clienti
	 */
	public Registrazione(FrameStart start,Archivio<Cliente> clienti) {
		super("Registrazione");
		this.clienti=clienti;
		this.startFrame=start;
		
		JPanel pannelloNord= new JPanel();
		JPanel pannelloCentro= new JPanel();
		JPanel pannelloSud= new JPanel();
		
		pannelloNord.setLayout(new BorderLayout());
		pannelloCentro.setLayout(new GridLayout(3,2)); 
		
		//Pannello nord
		JLabel text= new JLabel("Immettere i dati per la registrazione.");
		JLabel text2 = new JLabel("Lo username deve contenere almeno 3 caratteri.");
		pannelloNord.add(text,BorderLayout.NORTH);
		pannelloNord.add(text2,BorderLayout.WEST);
		//Pannello centrale
		JLabel user= new JLabel("Username:");
		pannelloCentro.add(user);
		
		username= new JTextField();
		username.setEditable(true);
		username.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Username immesso: "+username.getText());
				
			}
		});
		pannelloCentro.add(username);
		
		JLabel passwd= new JLabel("Password:");
		pannelloCentro.add(passwd);
		
		password= new JTextField();
		password.setEditable(true);
		password.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("password immessa: "+password.getText());
				
			}
		});
		pannelloCentro.add(password);
		
		JLabel ind= new JLabel("Indirizzo:");
		pannelloCentro.add(ind);
		
		indirizzo= new JTextField();
		indirizzo.setEditable(true);
		indirizzo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Indirizzo immesso: "+indirizzo.getText());
				
			}	
		});
		pannelloCentro.add(indirizzo);
		
		//pannello sud
		regButton= new JButton("REGISTRATI");
		regButton.addActionListener(this);
		pannelloSud.add(regButton);
		
		this.add(pannelloNord, BorderLayout.NORTH);
		this.add(pannelloCentro,BorderLayout.CENTER);
		this.add(pannelloSud, BorderLayout.SOUTH);
		
		
		
		
	}
	/**
	 * Implementa azioni da eseguire con la pressione dei pulsanti del frame
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String scelta2= (String) regButton.getActionCommand();
		if(scelta2.equals("REGISTRATI")) { 
			if(username.getText().length()>2 && !username.getText().equals("") && !password.getText().equals("") && !indirizzo.getText().equals("")  ) {
			cliente= new Cliente(username.getText(),password.getText(),indirizzo.getText(),new Archivio<Spedizione>());
			if(clienti.getDim()==0) {
				clienti.add(cliente);
				this.setVisible(false);
				this.dispose();
				 new MessageDialog("Registrazione avvenuta con successo.");
			}
			//se ho dei clienti
			else {
				//se il cliente presente
				if(clienti.ControllaCliente(username.getText())==false) {
					System.out.println("Cliente presente");
					new ErrorDialog("Username già utilizzato.");
				}
				else //se cliente non presente
				{ clienti.add(cliente);
				  this.setVisible(false);
				  this.dispose();
				  new MessageDialog("Registrazione avvenuta con successo.");
				}
			}
			clienti.StampaClienti();
			System.out.println();
			startFrame.getLogButt().setEnabled(true);
			startFrame.getCreaSpedButt().setEnabled(true);
			}
			else
				new ErrorDialog("Mancata compilazione o compilazione non corretta dei campi.");
		}
		
	}
	
}

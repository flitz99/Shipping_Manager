package graphics;

import javax.swing.*;

import Archivio.Archivio;
import dialogGraphics.ErrorDialog;
import dialogGraphics.MessageDialog;
import spedizione.Spedizione;
import utenti.Amministratore;
import utenti.Cliente;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che realizza il frame principale dell'applicazione e fa sia da ascoltatore delle azioni sui pulsanti sia da ascoltatore degli eventi di finestra
 * @author filipporeggiani
 *
 */
public class FrameStart extends JFrame implements ActionListener, WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * JComboBox che acquisisce la scelta se cliente o amministratore
	 */
	private JComboBox<String> utente;
	/**
	 * Casella di testo con valore inserito nel campo 'Username'
	 */
	private JTextField user;
	/**
	 * Casella di testo con valore inserito nel campo 'Password'
	 */
	private JPasswordField password;
	/**
	 * Pulsante per effettuare il login (cliente/ammministratore)
	 */
	private JButton logButt;
	/**
	 * Pulsante per effettuare la registrazione dei clienti
	 */
	private JButton regButt;
	/**
	 * Pulsante per registrare una nuova spedizione relativa al cliente
	 */
	private JButton CreaSpedButt;
	/**
	 * Archivio dei clienti contenente tutti i clienti registrati
	 */
	private Archivio<Cliente> clienti;
	/**
	 * Archivio delle spedizioni contenente tutte le spedizioni registrate
	 */
	private Archivio<Spedizione> spedizioni;
	
	/**
	 * Costruttore  che imposta il frame e i componenti e acquisisce l'archivio dei clienti e delle spedizioni
	 * @param clienti archivio dei clienti
	 * @param spedizioni archivio delle spedizioni
	 */
	public FrameStart(Archivio<Cliente> clienti,Archivio<Spedizione> spedizioni) {	
		super("Login/Registrazione");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.clienti=clienti;
		this.spedizioni=spedizioni;
		this.addWindowListener(this); //ascoltatore degli eventi di finestra
		
		JPanel pannelloNord= new JPanel();
		JPanel pannelloCentro= new JPanel();
		JPanel pannelloSud = new JPanel();
		
		pannelloNord.setLayout(new BorderLayout());
		pannelloCentro.setLayout(new GridLayout(3,2)); 
		pannelloSud.setLayout(new BorderLayout());
		
		//Componenti pannello nord
		JLabel l= new JLabel("Benvenuto! Eseguire il login o registrarsi.");
		JLabel s= new JLabel("Selezionare tipo di Utente: ");
		pannelloNord.add(l,BorderLayout.NORTH);
		pannelloNord.add(s,BorderLayout.CENTER);

		utente= new JComboBox<String>();
		utente.setEditable(false);
		utente.addItem("Cliente");
		utente.addItem("Amministratore");
		logButt= new JButton("LOGIN");
		regButt= new JButton("REGISTRATI");
		logButt.setEnabled(true);
		regButt.setEnabled(true);
		logButt.addActionListener(this);
		regButt.addActionListener(this); 
			
		
		CreaSpedButt = new JButton("NUOVA SPEDIZIONE");
		CreaSpedButt.setEnabled(true);
		CreaSpedButt.addActionListener(this);
		utente.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//GESTIONE COMBOBOX
				String scelta= (String) utente.getSelectedItem();
				//Se viene selezionato il Cliente nella checkbox
				if(scelta.equals(utente.getItemAt(0))) {  //cliente
					System.out.println("Scelto accesso come cliente");
					regButt.setEnabled(true);
					logButt.setEnabled(true);
					CreaSpedButt.setEnabled(true);
					user.setText("");
					password.setText("");
					if(clienti.getDim()==0) {
						logButt.setEnabled(false);
						CreaSpedButt.setEnabled(false);
					}
				}
				//Se viene selezionato l'amministratore nella checkbox
				if(scelta.equals(utente.getItemAt(1))) { //amministratore
					System.out.println("Scelto accesso come amministratore");
					user.setText("");
					password.setText("");
					logButt.setEnabled(true);
					regButt.setEnabled(false);
					CreaSpedButt.setEnabled(false);
				}
			}
			
		}));
	
		pannelloNord.add(utente,BorderLayout.SOUTH);
		
		//Componenti pannello sud
		JLabel u= new JLabel("Username:");
		user= new JTextField();
		user.setEditable(true);
		user.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Username immesso: "+user.getText());
			}	
		});
		JLabel p= new JLabel("Password:");
		password= new JPasswordField();
		password.setEditable(true);
		password.setEchoChar('*');
		password.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String passwd= password.getPassword().toString();
				System.out.println("Password immessa: "+passwd.toString()); //non visionabile
			}	
		});
		pannelloCentro.add(u);
		pannelloCentro.add(user);
		pannelloCentro.add(p);
		pannelloCentro.add(password);
		pannelloCentro.add(logButt);
		pannelloCentro.add(getRegButt());
		
		pannelloSud.add(CreaSpedButt, BorderLayout.NORTH);
		
		//aggiunta dei due pannelli al frame
		this.add(pannelloNord, BorderLayout.PAGE_START);
		this.add(pannelloCentro,BorderLayout.CENTER);
		this.add(pannelloSud,BorderLayout.SOUTH);
}
	/**
	 * Ascoltatore dei tre pulsanti e della scelta fatta nella JComboBox
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String scelta= (String) e.getActionCommand();
		//se premuto pulsante REGISTRATI
		if(scelta.equals("REGISTRATI")) {
			user.setText("");
			password.setText("");
			Registrazione regFrame= new Registrazione(this,clienti);
			regFrame.pack(); 	//si adatta ai componenti messi (layout standard)
			regFrame.setVisible(true);
			regFrame.setBounds(0, 0, 400, 180);
			regFrame.setLocationRelativeTo(null);
			regFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}	
		//se premuto pulsante LOGIN
		if(scelta.equals("LOGIN")){
			
			//login amministratore
			if(utente.getSelectedItem().equals("Amministratore")) {
				
				String passwd= new String(password.getPassword()); //converto char[] in String
				Amministratore admin = new Amministratore(user.getText(),passwd);
				if(admin.CheckLogin()) {
					if(spedizioni.getDim()>0) {
						LoginAdmin logFrame= new LoginAdmin(clienti,spedizioni);
						logFrame.pack();
						logFrame.setVisible(true);
						logFrame.setBounds(0,0,1400,500);
						logFrame.setLocationRelativeTo(null);
						logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						user.setText("");
						password.setText("");
					}
					else
						new ErrorDialog("Nessuna spedizione visionabile.");
				}
				else
					new ErrorDialog("Username o Password errati.");
				}
				//Login cliente
				if(utente.getSelectedItem().equals("Cliente")){
					String passwd= new String(password.getPassword());
					//se utente con queste credenziali registrato
						Cliente client= (Cliente) clienti.LogCheck(user.getText(), passwd);
					if(client !=null) {
						if(client.getSpedizioni().getDim()>0) {
							LoginCliente logFrame= new LoginCliente(client,spedizioni);
							logFrame.pack();
							logFrame.setVisible(true);
							logFrame.setBounds(0,0,1400,500);
							logFrame.setLocationRelativeTo(null);
							logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						}
						else
							new ErrorDialog("Nessuna spedizione visionabile.");
					}
					else
						new ErrorDialog("Username o Password errati.");
					
					
				}
		}
		if(scelta.equals("NUOVA SPEDIZIONE")) {
			String passwd= new String(password.getPassword());
			Cliente client= (Cliente) clienti.LogCheck(user.getText(), passwd);
			if(client !=null) {
			user.setText("");
			password.setText("");
			NuovaSpedizione spedFrame = new NuovaSpedizione(client,spedizioni,clienti);
			spedFrame.pack();
			spedFrame.setVisible(true);
			spedFrame.setBounds(0,0,600,310);
			spedFrame.setLocationRelativeTo(null);
			spedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			}
			else
				new ErrorDialog("Username o Password errati.");
			
		}
	}


	/**
	 * Permette di acquisire il pulsante relativo al Login
	 * @return restituisce il pulsante di login
	 */
	public JButton getLogButt() {
		return logButt;
	}
	/**
	 * Permette di acquisire il pulsante relativo alla registrazione
	 * @return restituisce il pulsante per la registrazione
	 */
	public JButton getRegButt() {
		return regButt;
	}
	/**
	 * Permette di acquisire pulsante relativo alla creazione di una nuova spedizione
	 * @return restituisce il pulsante per la nuova spedizione
	 */
	public JButton getCreaSpedButt() {
		return CreaSpedButt;
	}

	/**
	 * Permette la gestione degli eventi relativi all'apertura di questa finestra
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		int scelta = JOptionPane.showConfirmDialog( new JFrame(),"Caricare dall' ultima sessione di lavoro?",  "Carica ", JOptionPane.YES_NO_OPTION);
		if(scelta==0) {
			clienti.CaricaDaFileClienti();
			
			if(clienti.getDim()==0) {
				System.out.println("Nessun cliente presente su file");
				new ErrorDialog("Nessun cliente presente su file.");
			}
			else
				clienti.StampaClienti();
				
			spedizioni.CaricaDaFileSpedizioni(clienti);
			
			if(spedizioni.getDim()==0) {
				System.out.println("Nessuna spedizione presente su file");
				new ErrorDialog("Nessuna spedizione presente su file.");
			}
			else {
				spedizioni.StampaSpedizioni();	
				clienti.StampaClienti();
			}
			if(clienti.getDim()==0)
				new MessageDialog("Nessun Cliente presente, effettuare prima la registrazione.");
		}
		else //se parto da una nuova sessione
			new MessageDialog("Nessun Cliente presente, effettuare prima la registrazione.");
			
	}
	/**
	 * Permette la gestione degli eventi relativi alla chiusura di questa finestra
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		int scelta = JOptionPane.showConfirmDialog( new JFrame(),"Sovrascrivere i dati dell'ultima sessione?",  "Uscita", JOptionPane.YES_NO_OPTION);
		//n=0 se scelto si, n=1 se scelto no
		if(scelta==0) {
			clienti.SalvaSuFileClienti();
			spedizioni.SalvaSuFileSpedizioni();
			System.out.println("Salvato su file");
		}
		
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}



}

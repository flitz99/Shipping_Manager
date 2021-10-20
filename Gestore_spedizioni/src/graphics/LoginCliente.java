package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import Archivio.Archivio;
import dialogGraphics.MessageDialog;
import spedizione.Spedizione;
import spedizione.SpedizioneAssicurata;
import utenti.Cliente;
/**
 * Classe che implementa la visualizzazione della tabella per la visione di tutte le spedizioni relative al singolo cliente
 * La classe fa da ascoltatore per le azioni sui pulsanti e sui menu a tendina
 * @author filipporeggiani
 *
 */
public class LoginCliente extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Contiene il cliente che ha effettuato l'accesso
	 */
	private Cliente cliente;
	/**
	 * Archivio contenente tutte le spedizioni (di tutti i clienti)
	 */
	private Archivio<Spedizione> spedizioni;
	/**
	 * Menu a tendina per richiedere il rimborso in caso di spedizione assicurata in stato fallita
	 */
	private JPopupMenu popupMenu;
	/**
	 * Tabella per la visualizzazione di tutte le spedizioni registrate dell'utente
	 */
	private JTable table;
	/**
	 * Pulsante per effettuare il logout del cliente
	 */
	private JButton logout;
	
	/**
	 * Costruttore che riceve il cliente che ha effettuato l'accesso e l'archivio contenente tutte le spedizioni
	 * Viene creata e impostata la tabella per la visualizzazione delle spedizioni
	 * @param c cliente che ha effettuato il login
	 * @param spedizioni archivio delle spedizioni
	 */
	public LoginCliente(Cliente c, Archivio<Spedizione> spedizioni) {
		super("Spedizioni");
		this.cliente=c;
		this.spedizioni= spedizioni;
	
		popupMenu = new JPopupMenu();
		JMenuItem rimborsoR = new JMenuItem("RICHIEDI RIMBORSO");
		rimborsoR.addActionListener(this);
		popupMenu.add(rimborsoR);
		
	TableModel model= new AbstractTableModel() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount() {
			return cliente.getSpedizioni().getDim();
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Spedizione s= (Spedizione) cliente.getSpedizioni().getSpedizione(rowIndex);
			switch(columnIndex) {
				case 0:{	if (s instanceof SpedizioneAssicurata) 
									return "SPEDIZIONE ASSICURATA";
							else
									return "SPEDIZIONE NORMALE";					
				}
				case 1: return s.getCodice();
				case 2: return s.getDestinazione();
				case 3: return s.getPeso();
				case 4: return s.getData();
				case 5: return s.getStato();
				case 6:{	if(s instanceof SpedizioneAssicurata)
								return ((SpedizioneAssicurata) s).getValAssicurato();
							else
								return "-";
						
				}
			}
			return "";
		}
		
		@Override
		public String getColumnName(int col){
			switch (col){
				case 0: return "Tipo Spedizione"; 
				case 1: return "Codice";
				case 2: return "Destinazione";
				case 3: return "Peso";
				case 4: return "Data";
				case 5: return "Stato";
				case 6: return "Valore Assicurato";
		
				default: return  "";
			}
		} 
		
	};

	table= new JTable(model); //creo la tabella sul modello creato
	table.addMouseListener(new MouseAdapter() {
		
		@Override
	    public void mousePressed(MouseEvent event) {
	        int currentRow = table.getSelectedRow();	
	        table.setRowSelectionInterval(currentRow, currentRow); 
	        Spedizione s= (Spedizione) cliente.getSpedizioni().getSpedizione(currentRow);
	        
	        if(s instanceof SpedizioneAssicurata && s.getStato().equals("FALLITA")){
	        	table.setComponentPopupMenu(popupMenu);
	        }
	        else {	table.setComponentPopupMenu(null);
	        
	        }
	    }
	});
	//imposto dimensione preferita colonne
	for(int i=0;i<7;i++) {
	table.getColumnModel().getColumn(i).setPreferredWidth(150);
	}
	
	JScrollPane scroll = new JScrollPane();
	scroll.setViewportView(table);
	
	JTableHeader header= table.getTableHeader();
	header.setBackground(Color.WHITE);
	//gestione pulsante logout
	logout= new JButton("LOGOUT");
	JPanel pannelloButt= new JPanel();
	logout.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String scelta= (String) logout.getActionCommand();
			if(scelta.equals("LOGOUT")) {
				new MessageDialog("Effettuo il logout..");
				setVisible(false);
				dispose();
			}
		}
				
	});
	pannelloButt.add(logout);
			
	this.add(scroll,BorderLayout.NORTH);
	this.add(pannelloButt,BorderLayout.SOUTH);
	}
	/**
	 * Imposta operazioni da eseguire nel caso di pressione del pulsante logout o del menu a tendina
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(table.getComponentPopupMenu()!=null) {
		int selectedRow = table.getSelectedRow();
		  String Scelta= e.getActionCommand();
		  Spedizione s= (Spedizione) cliente.getSpedizioni().getSpedizione(selectedRow);
		  
		  if(s instanceof SpedizioneAssicurata && Scelta=="RICHIEDI RIMBORSO") {
			  s.setStato("RIMBORSO RICHIESTO");
			  spedizioni.CercaSpedizione(s).setStato("RIMBORSO RICHIESTO");
		  }
		}
	}
	
}

package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import Archivio.Archivio;
import dialogGraphics.MessageDialog;
import spedizione.Spedizione;
import spedizione.SpedizioneAssicurata;
import utenti.Cliente;

/**
 * Classe che implementa la visione della JTable con tutte le spedizioni di tutti i clienti
 * La classe fa da ascoltatore per le azioni sui pulsanti e sui menu a tendina
 * @author filipporeggiani
 *
 */
public class LoginAdmin extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Archivio contenente tutte le spedizioni registrate
	 */
	private Archivio<Spedizione> spedizioni;
	/**
	 * Archivio contenente tutti i clienti registrati
	 */
	private Archivio<Cliente> clienti;
	/**
	 * JTable per la visualizzazione di tutte le spedizioni di tutti i clienti
	 */
	private JTable table;
	/**
	 * Menu a tendina per la modifica dello stato di una spedizione normale
	 */
	private JPopupMenu popupMenuNorm;
	/**
	 * Menu a tendina per la cancellazione di una spedizione in stato finale
	 */
	private JPopupMenu popupMenuCanc;
	/**
	 * Menu a tendina per la modifica dello stato di una spedizione assicurata
	 */
	private JPopupMenu popupMenuAss;
	/**
	 * Pulsante per il logout dell'admin
	 */
	private JButton logout;
	/**
	 * Costruttore che acquisisce tutte le spedizioni e tutti i clienti registrati e crea la tabella con tutte le spedizioni
	 * @param clienti archivio dei clienti
	 * @param Spedizioni archivio delle spedizioni
	 */
	public LoginAdmin(Archivio<Cliente> clienti, Archivio<Spedizione> Spedizioni) {
		super("Gestione Spedizioni");
		this.setClienti(clienti);
		this.spedizioni=Spedizioni;
		
		//menu Spedizione normale 
		popupMenuNorm = new JPopupMenu();
		JMenuItem preparazione = new JMenuItem("IN PREPARAZIONE");
		JMenuItem transito = new JMenuItem("IN TRANSITO");
		JMenuItem ricevuta= new JMenuItem("RICEVUTA");
		JMenuItem fallita = new JMenuItem("FALLITA");
		preparazione.addActionListener(this);
		transito.addActionListener(this);
		ricevuta.addActionListener(this);
		fallita.addActionListener(this);
		
		popupMenuNorm.add(preparazione);
		popupMenuNorm.add(transito);
		popupMenuNorm.add(ricevuta);
		popupMenuNorm.add(fallita);
		
		//menu Spedizione assicurata
		popupMenuAss= new JPopupMenu();
		JMenuItem preparazioneAss = new JMenuItem("IN PREPARAZIONE");
		JMenuItem transitoAss = new JMenuItem("IN TRANSITO");
		JMenuItem ricevutaAss= new JMenuItem("RICEVUTA");
		JMenuItem fallitaAss = new JMenuItem("FALLITA");
		JMenuItem rimborsoR = new JMenuItem("RIMBORSO RICHIESTO");
		JMenuItem rimborsoE = new JMenuItem("RIMBORSO EROGATO");
		preparazioneAss.addActionListener(this);
		transitoAss.addActionListener(this);
		ricevutaAss.addActionListener(this);
		fallitaAss.addActionListener(this);
		rimborsoR.addActionListener(this);
		rimborsoE.addActionListener(this);

		popupMenuAss.add(preparazioneAss);
		popupMenuAss.add(transitoAss);
		popupMenuAss.add(ricevutaAss);
		popupMenuAss.add(fallitaAss);
		popupMenuAss.add(rimborsoR);
		popupMenuAss.add(rimborsoE);
		
		//menu per la cancellazione spedizioni in stato finale
		popupMenuCanc= new JPopupMenu();
		JMenuItem cancella= new JMenuItem("CANCELLA");
		cancella.addActionListener(this);
		popupMenuCanc.add(cancella);
		
		TableModel tableModel = new AbstractTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;


			@Override
			public int getRowCount() {
				return spedizioni.getDim();
			}

			@Override
			public int getColumnCount() {
				return 8; //fisso
			}
			
			@Override
			public String getColumnName(int col){
			switch (col){
			 case 0: return "Cliente";
			 case 1: return "Tipo Spedizione"; 
			 case 2: return "Codice";
			 case 3: return "Destinazione";
			 case 4: return "Peso";
			 case 5: return "Data";
			 case 6: return "Stato";
			 case 7: return "Valore Assicurato";
			
			default: return  "";
			}
			} 
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Spedizione s= (Spedizione) spedizioni.getSpedizione(rowIndex);
				switch(columnIndex) {
					case 0: return s.getCliente();
					case 1:{	if (s instanceof SpedizioneAssicurata) 
										return "SPEDIZIONE ASSICURATA";
								else
										return "SPEDIZIONE NORMALE";					
					}
					case 2: return s.getCodice();
					case 3: return s.getDestinazione();
					case 4: return s.getPeso();
					case 5: return s.getData();
					case 6: return s.getStato();
					case 7:{	if(s instanceof SpedizioneAssicurata)
									return ((SpedizioneAssicurata) s).getValAssicurato();
								else
									return "-";
							
					}
				}
				return "";
			}
		
			
		};
		table= new JTable(tableModel); //creo la tabella sul modello creato
		table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
			{
			Component cell = super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
			Spedizione s= (Spedizione) spedizioni.getSpedizione(row);
			switch(s.getStato()) {
				case "FALLITA":{	cell.setBackground( Color.red );
									return cell;
				}
				case "RICEVUTA":{ cell.setBackground( Color.green );
								 	return cell;
				}
				case "RIMBORSO RICHIESTO":{ cell.setBackground(Color.orange);
										return cell;
				}
				case "RIMBORSO EROGATO":{ cell.setBackground( Color.pink );
										return cell;
				}
				case "IN TRANSITO":{	cell.setBackground( Color.blue );
										return cell;
				}
				case "IN PREPARAZIONE":{ cell.setBackground(Color.gray);
											return cell;				
				}
			 }
			return cell;
			}
			});
		table.addMouseListener(new MouseAdapter() {
			
			@Override
		    public void mousePressed(MouseEvent event) {
		        int currentRow = table.getSelectedRow();	
		        table.setRowSelectionInterval(currentRow, currentRow); 
		        Spedizione s= (Spedizione) spedizioni.getSpedizione(currentRow);
		        if(s instanceof SpedizioneAssicurata) {
		        	if(s.getStato().equals("RICEVUTA") || s.getStato().equals("RIMBORSO EROGATO"))
		        		table.setComponentPopupMenu(popupMenuCanc);
		        	else 
		        		table.setComponentPopupMenu(popupMenuAss);
		        }
		        else {
		        	
		        	if(s.getStato().equals("RICEVUTA") || s.getStato().equals("RIMBORSO EROGATO"))
		        		table.setComponentPopupMenu(popupMenuCanc);
		        	else 
		        	table.setComponentPopupMenu(popupMenuNorm);
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
	 * Permette di acuisire l'archivio dei clienti registrati
	 * @return restituisce l'archivio dei clienti
	 */
	public Archivio<Cliente> getClienti() {
		return clienti;
	}
	/**
	 * Permette di impostare i clienti registrati
	 * @param clienti archivio dei clienti
	 */
	public void setClienti(Archivio<Cliente> clienti) {
		this.clienti = clienti;
	}
	
	/**
	 * Ascoltatore degli eventi relativi ai menu a tendina
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		  int selectedRow = table.getSelectedRow();
		  String Scelta= e.getActionCommand();
		  Spedizione s= (Spedizione) spedizioni.getSpedizione(selectedRow);
		  //se spedizione in stato ricevuta o rimborso erogato non cambia stato
		  
		  if(Scelta=="CANCELLA") {
			 spedizioni.CancellaSpedizione(s);
			 clienti.RimuoviSpedizioni(s);
		  }
		  if(Scelta=="IN PREPARAZIONE") { 
			  s.setStato("IN PREPARAZIONE");
		  }
		  if(Scelta=="IN TRANSITO") {
			  s.setStato("IN TRANSITO");
		  }
		  if(Scelta=="RICEVUTA") {
			  s.setStato("RICEVUTA");
		  }
		  if(Scelta=="FALLITA") {
			  s.setStato("FALLITA");
		  }
		  if(Scelta=="RIMBORSO RICHIESTO") {
			  s.setStato("RIMBORSO RICHIESTO");
		  }
		  if(Scelta=="RIMBORSO EROGATO") {
			  s.setStato("RIMBORSO EROGATO");
		  }
	}
	/**
	 * Permette acquisizione del pulsante di logout
	 * @return restituisce il pulsante di logout
	 */
	public JButton getLogout() {
		return logout;
	}



}

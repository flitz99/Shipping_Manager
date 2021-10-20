package Archivio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import spedizione.Spedizione;
import spedizione.SpedizioneAssicurata;
import utenti.Cliente;
/**
 * 
 * @author filipporeggiani
 *Classe che gestisce l'archivio dei clienti e l'archivio delle spedizioni mediante l'uso di un Vector
 *Ogni elemento del vector viene deciso all'invocazione attraverso l'uso dei generics.
 * @param <E> contiene tipo di elemento dell'arraylist
 */
public class Archivio<E>{
	/**
	 * vector della tipologia di oggetto passato.
	 */
	private Vector<E> archivio;
	/**
	 * Dimensione del vector.
	 */
	private int dim;
	
	//costruttore
	/**
	 * Costruttore della classe.
	 * Crea un nuovo vector dell'oggetto scelto e imposta parametro della dimensione iniziale a zero
	 */
	public Archivio() {
			archivio= new Vector<E>();
			dim=0;
 
	}
	/**
	 * Permette di ottenere l'archivio degli ogetti.
	 * @return restituisce l'archivio Vector
	 */
	public Vector<E> getArchivio() {
		return archivio;
	}
	/**
	/**
	 * Metodo per il caricamento da file dei clienti nel Vector relativo ai clienti.
	 * Vengono letti i campi di tutti i clienti registrati su file e caricati nel vector dei clienti
	 */
	@SuppressWarnings("unchecked")
	public void CaricaDaFileClienti() {
		try{
			File file = new File ("");
			if(!file.getAbsolutePath().contains("src")) {
				file= new File(file.getAbsolutePath()+File.separator+"src"+File.separator+"DatiClienti.txt");
			}
			else {
				 file= new File("DatiClienti.txt");
			}
			System.out.println(file.getAbsolutePath());
			FileReader fin= new FileReader(file);
			
		 BufferedReader br =new BufferedReader(fin);
				String dimS =br.readLine();
				int dimensione = Integer.parseInt(dimS); //converto a intero
				for(int i=0;i<dimensione;i++) {
					String username= br.readLine();
					String password= br.readLine();
					String indirizzo= br.readLine();
					Cliente c= new Cliente(username,password,indirizzo,new Archivio<Spedizione>());
					c.StampaCliente();
					archivio.add((E) c);
				}
				this.setDim(dimensione);
				br.close();
				
		}catch(IOException e) {
			System.out.println("Apertura file fallita");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	/**
	/**
	 * Metodo per il caricamento da file delle spedizioni nel Vector relativo alle spedizioni e nell'archivio di spedizioni relativo a ciascun cliente.
	 * Vengono letti i campi di tutte le spedizioni registrate su file e caricate nei vector delle spedizioni (dell'admin e del singolo cliente)
	 */
	@SuppressWarnings("unchecked")
	public void CaricaDaFileSpedizioni(Archivio<Cliente> clienti) {
		try{
			File file = new File ("");
			if(!file.getAbsolutePath().contains("src")) {
				file= new File(file.getAbsolutePath()+File.separator+"src"+File.separator+"DatiSpedizioni.txt");
			}
			else {
				 file= new File("DatiSpedizioni.txt");
			}
			System.out.println(file.getAbsolutePath());
			FileReader fin= new FileReader(file);
			
		 BufferedReader br =new BufferedReader(fin);
				String dimS =br.readLine();
				
				int dimensione = Integer.parseInt(dimS); //converto a intero
				for(int j=0;j<dimensione;j++) {
					String tipo = br.readLine(); //leggo tipo di spedizione
					String cliente;
					 String codice;
					 String destinazione;
					 String pesoS;
					 String data;
					 String stato;
					Spedizione s;
					if(tipo.equals("NORMALE")) {
							 cliente= br.readLine();
							  codice= br.readLine();
							 destinazione= br.readLine();
							  pesoS= br.readLine();
							int peso =Integer.parseInt(pesoS);
							 data= br.readLine();
							 stato=br.readLine();
							s= new Spedizione(cliente,codice,destinazione,peso,data,stato);	
							Cliente client= clienti.CercaCliente(cliente);
							client.getSpedizioni().add(s); //aggiungo spedizione al cliente
							archivio.add((E) s);
							dim++;
							
					}
					if(tipo.equals("ASSICURATA")) {
							cliente= br.readLine();
						    codice= br.readLine();
							 destinazione= br.readLine();
							 pesoS= br.readLine();
							int peso =Integer.parseInt(pesoS);
							 data= br.readLine();
							 stato=br.readLine();
							String vAssS=br.readLine();
							int vAss=Integer.parseInt(vAssS);
							s= new SpedizioneAssicurata(cliente,codice,destinazione,peso,data,stato,vAss);
							Cliente client= clienti.CercaCliente(cliente);
							client.getSpedizioni().add(s);
							archivio.add((E) s);
							dim++;
						
					}
				}
				br.close();
				
		}catch(IOException e) {
			System.out.println("Apertura file fallita");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	/**
	 * Salva i clienti registrati durante l'esecuzione del programma su file
	 */
	public void SalvaSuFileClienti() {
		try {
			File file = new File ("");
			if(!file.getAbsolutePath().contains("src")) {
				file= new File(file.getAbsolutePath()+File.separator+"src"+File.separator+"DatiClienti.txt");
			}
			else {
				 file= new File("DatiClienti.txt");
			}
			System.out.println(file.getAbsolutePath());
			
			FileWriter fout= new FileWriter(file);
				String s = Integer.toString (dim);
				
				fout.write(s+"\n");
				for(int i=0;i<dim;i++) {
				Cliente cliente= (Cliente) archivio.get(i);
				fout.write(cliente.getUsername()+"\n");
				fout.write(cliente.getPassword()+"\n");
				fout.write(cliente.getIndirizzo()+"\n");
				}
			
			fout.close();
			
		}catch(IOException e) {
			System.out.println("Apertura file fallita");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	/**
	 * Salva le spedizioni registrate durante l'esecuzione del programma su file.
	 */
	public void SalvaSuFileSpedizioni() {
		try {
			File file = new File ("");
			if(!file.getAbsolutePath().contains("src")) {
				file= new File(file.getAbsolutePath()+File.separator+"src"+File.separator+"DatiSpedizioni.txt");
			}
			else {
				 file= new File("DatiSpedizioni.txt");
			}
			System.out.println(file.getAbsolutePath());
			FileWriter fout= new FileWriter(file);
			
			String s = Integer. toString (dim);	
			fout.write(s+"\n");
				for(int i=0;i<dim;i++) {
				Spedizione spedizione= (Spedizione) archivio.get(i);
				spedizione.SalvaSuFile(fout);
				}
			fout.close();
			
		}catch(IOException e) {
			System.out.println("Apertura file fallita");
			e.printStackTrace();
			System.exit(-1);;
		}
	}
	/**
	 * Cerca il cliente nel vector attraverso lo username(uniivoco) passato
	 * @param Username contiene username del cliente
	 * @return ritorna cliente o null se non trovato
	 */
	public Cliente CercaCliente(String Username) {
		for(int i=0;i<dim;i++) {
			if(Username.equals(((Cliente) archivio.get(i)).getUsername())) {
				return (Cliente) archivio.get(i);
			}
		}
		return null;	
	}
	/**
	 * Rimuove la spedizione dal vector relativo alle spedizioni del singolo cliente
	 * @param sped spedizione da rimuovere
	 */
	public void RimuoviSpedizioni(Spedizione sped) {
		for(int i=0;i<dim;i++) {
			Cliente client= (Cliente) archivio.get(i);
			for(int j=0;j<client.getSpedizioni().getDim();j++) {
				if(client.getSpedizioni().getSpedizione(j).equals(sped)) {
					client.getSpedizioni().CancellaSpedizione(sped);
				}
			}
		}
	}
	/**
	 * Controlla che il cliente con tale username non sia già registrato
	 * @param Username contiene username del cliente
	 * @return ritorna false se cliente presente o true se non presente
	 */
	public boolean ControllaCliente(String Username) {
		for(int i=0;i<dim;i++){ //ciclo for per ciascun cliente dell'archivio
	           if(Username.equals(((Cliente) archivio.get(i)).getUsername())){ //controllo username clienti presenti con quello appena inserito
	        	   return false; //cliente presente
	           }		  
	      }
		return true; //cliente non presente
	}
	/**
	 * @return restituisce la dimensione del vector
	 */
	public int getDim() {
		return dim;
	}
	/**
	 * Permette di impostare la dimensione del vector
	 * @param dim nuova dimensione da impostare
	 */
	public void setDim(int dim) {
		this.dim = dim;
	}
	/**
	 * Permette l'aggiunta di un nuovo elemento al vector
	 * @param e elemento da aggiungere al vector
	 */
	public void add(E e) {
		archivio.add(e);
		dim++;
	}
	/**
	 * Effettua la stampa dei clienti registrati nel vector
	 */
	public void StampaClienti() {
		System.out.println("CLIENTI:");
		for(int i=0;i<dim;i++) {
			E c= archivio.get(i);
			System.out.println("------"+(i+1)+"------");
			((Cliente) c).StampaCliente();
		}
	}
	/**
	 * Effettua la stampa delle spedizioni registrate nel vector
	 */
	public void StampaSpedizioni() {
		System.out.println("SPEDIZIONI:");
		for(int i=0;i<dim;i++) {
			E s= (E) archivio.get(i);
			System.out.println("------"+(i+1)+"------");
			((Spedizione) s).StampaSpedizione();
		}
	}
	/**
	 * Permette la cancellazione di tutti i clienti registrati nel vector
	 */
	public void Cancella() {
		archivio.clear();
		dim=0;
	}
	/**
	 * Permette la cancellazione della spedizione passata dal vector
	 * @param sped spedizione da rimuovere dall'archivio
	 */
	public void CancellaSpedizione(E sped) {
		archivio.remove(sped);
		dim--;
		
	}
	/**
	 * Cerca una spedizione nell'archivio 
	 * @param sped spedizione da cercare
	 * @return restituisce spedizione trovata o null 
	 */
	public Spedizione CercaSpedizione(E sped) {
		for(int i=0;i<dim;i++) {
			E s=archivio.get(i);
			if(s.equals(sped)) {
				return (Spedizione) s;
			}
		}
		return null;
	}
	/**
	 * Restituisce il cliente registrato nel rispettivo indice del vector
	 * @param index indice elemento da restituire
	 * @return ritorna elemento all'indice passato
	 */
	public E GetCliente(int index) {
		E c= archivio.get(index);
		return (E) c;
	}
	/**
	 * Restituisce spedizione registrata nel resipettivo indice del vector
	 * @param index indice elemento da restituire 
	 * @return ritorna elemento all'indice passato
	 */
	public E getSpedizione(int index) {
		E s= archivio.get(index);
		return (E) s;
		
	}
	/**
	 * Effettua il controllo nel login dei clienti
	 * @param Username contiene username da controllare
	 * @param Password contiene password da controllare
	 * @return ritorna il cliente se presente o null se non presente
	 */
	public E LogCheck(String Username,String Password) {
		for(int i=0;i<dim;i++) {
			E c=archivio.get(i);
			if(((Cliente) c).getUsername().equals(Username)==true && ((Cliente) c).getPassword().equals(Password)==true)
				return c;
		}
		return null;
	}
}

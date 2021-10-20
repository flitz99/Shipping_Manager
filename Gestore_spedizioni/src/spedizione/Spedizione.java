package spedizione;

import java.io.FileWriter;
import java.io.IOException;
/**
 * Classe che rappresenta una spedizione inserita nel sistema
 * @author filipporeggiani
 *
 */
public class Spedizione {
	/**
	 * Stringa contenente lo username del cliente registrato che ha creato la spedizione
	 */
	protected String cliente;
	/**
	 * Stringa contenente il codice della spedizione
	 */
	protected String codice;
	/**
	 * Stringa contenente la destinazione della spedizione
	 */
	protected String Destinazione;
	/**
	 * Intero contenente il peso della spedizione in kg
	 */
	protected int peso;
	/**
	 * Data di creazione della spedizione 
	 */
	protected String data;
	/**
	 * Stringa contenente lo stato della spedizione
	 */
	protected String stato;
	
	/**
	 * Costruttore per la creazione di una nuova spedizione Normale
	 * @param cliente contiente username cliente
	 * @param codice contiene codice della spedizione
	 * @param destinazione contiente destinazione della spedizione
	 * @param peso contiene il peso della spedizione
	 * @param data contiene data di creazione della spedizione
	 * @param stato contiene stato della spedizione
	 */
	public Spedizione(String cliente,String codice, String destinazione, int peso,String data,String stato) {
		this.setCliente(cliente);
		this.codice = codice;
		this.Destinazione = destinazione;
		this.peso = peso;
		this.data=data;
		this.stato=stato;
	}
	/**
	 * Permette la stampa dei dati relativi alla spedizione
	 */
	public void StampaSpedizione() {
		System.out.println("Cliente: "+cliente);
		System.out.println("Codice: "+codice);
		System.out.println("Destinazione: "+Destinazione);
		System.out.println("Peso: "+peso+" Kg");
		System.out.println("Data: "+data);
		System.out.println("Stato: "+stato);
	}
	
	/**
	 * Permette il salvataggio su file della spedizione
	 * @param fout Filewriter su cui scrivere i dati
	 */
	public void SalvaSuFile(FileWriter fout) {
		try{
			fout.write("NORMALE\n");
			fout.write(cliente+"\n");
			fout.write(codice+"\n");
			fout.write(Destinazione+"\n");
			fout.write(peso+"\n");
			fout.write(data+"\n");
			fout.write(stato+"\n");
			
		}catch(IOException e) {
			System.out.println("Apertura file fallita");
			e.printStackTrace();
			System.exit(-1);;
		}
	}
	/**
	 * Permette acquisizione della stringa contenente il codice della spedizione
	 * @return restituisce codice spedizione
	 */
	public String getCodice() {
		return codice;
	}
	/**
	 * Permette di impostare il codice della spedizione
	 * @param codice codice da impostare
	 */
	public void setCodice(String codice) {
		this.codice = codice;
	}
	/**
	 * Permette di ottenere la destinazione della spedizione
	 * @return restituisce la destinazione
	 */
	public String getDestinazione() {
		return Destinazione;
	}
	/**
	 * Permette di impostare la destinazione della spedizione
	 * @param destinazione nuova destinazione da impostare
	 */
	public void setDestinazione(String destinazione) {
		Destinazione = destinazione;
	}
	/**
	 * Permette di ottenere il peso della destinazione
	 * @return restituisce il peso della spedizione
	 */
	public int getPeso() {
		return peso;
	}
	/**
	 * Permette di impostare il peso della spedizione
	 * @param peso nuovo peso da impostare
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}
	/**
	 * Permette di ottenere la data relativa alla spedizione
	 * @return restituisce la data della spedizione
	 */
	public String getData() {
		return data;
	}
	/**
	 * Permette di modificare la data della spedizione
	 * @param data nuova data da impostare
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * Permette di ottenere lo stato della spedizione
	 * @return restituisce lo stato della spedizione
	 */
	public String getStato() {
		return stato;
	}
	/**
	 * Permette di impostare lo stato della spedizione
	 * @param stato nuovo stato da impostare
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}
	/**
	 * Permette di ottenere il cliente che ha creato la spedizione
	 * @return restituisce il cliente 
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * Permette di impostare il cliente che ha creato la spedizione
	 * @param cliente nuovo cliente da impostare
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

}

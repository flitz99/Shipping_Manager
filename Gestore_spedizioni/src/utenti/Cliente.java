package utenti;

import Archivio.Archivio;
import spedizione.Spedizione;

/**
 * Classe che rappresenta un cliente registrato nel sistema
 * @author filipporeggiani
 *
 */
public class Cliente{
	/**
	 * Stringa contenente lo username del cliente
	 */
	private String username;
	/**
	 * Stringa contenente la password del cliente
	 */
	private String password;
	/**
	 * Stringa contenente l'indirizzo del cliente
	 */
	private String indirizzo;
	/**
	 * Archivio contenente tutte le spedizioni del sistema
	 */
	private Archivio<Spedizione> spedizioni;
	
	/**
	 * Crea cliente coi valori passati
	 * @param Username rappresenta username del cliente 
	 * @param password rappresenta password del cliente
	 * @param indirizzo rappresenta indirizzo del cliente
	 * @param spedizioni rappresenta tutte le spedizioni registrate
	 */
	public Cliente(String Username, String password, String indirizzo,Archivio<Spedizione> spedizioni) {
			
		this.username=Username;
		this.password=password;
		this.indirizzo=indirizzo;
		this.spedizioni=spedizioni;
		
	}
	/**
	 * Permette di ottenere lo username del cliente
	 * @return restituisce lo username del cliente
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Permette di impostare lo username del cliente
	 * @param username nuovo username da impostare
	 */
	public void setUsername(String username) {
		this.username = username;	
	}
	/**
	 * Permette di ottenere la password del cliente
	 * @return restituisce la password del cliente
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Permette di impostare la password del cliente
	 * @param password nuova password da impostare
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Permette di ottenere l'indirizzo del cliente
	 * @return restituisce l'indirizzo del cliente
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	/**
	 * Permette di impostare l'indirizzo del cliente
	 * @param indirizzo nuovo indirizzo da impostare
	 */
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	/**
	 * Permette di stampare i dati relativi al cliente
	 */
	public void StampaCliente() {
		  System.out.println("Username: "+this.username);
		  System.out.println("Password: "+this.password);
		  System.out.println("Indirizzo: "+this.indirizzo);
		  System.out.println("Num. Spedizioni: "+this.spedizioni.getDim());
	}
	/**
	 * Permette di ottenere l'archivio delle spedizioni
	 * @return restituisce l'archivio delle spedizioni
	 */
	public Archivio<Spedizione> getSpedizioni() {
		return spedizioni;
	}
	/**
	 * Permette di impostare le spedizioni dell'archivio
	 * @param spedizioni nuovo archivio di spedizioni da impostare
	 */
	public void setSpedizioni(Archivio<Spedizione> spedizioni) {
		this.spedizioni = spedizioni;
	}
}

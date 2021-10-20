package utenti;
/**
 * Classe che rappresenta un amministratore del sistema
 * @author filipporeggiani
 *
 */
public class Amministratore {
	/**
	 * Stringa con lo username di sistema (predefinito)
	 */
	private final String username= "admin";
	/**
	 * Stringa con la password di sistema (predefinita)
	 */
	private final String password="password";
	/**
	 * Stringa contenente lo username inserito al login
	 */
	private String usernameIns;
	/**
	 * Stringa contenente la password inserita al login
	 */
	private String passwordIns;
	/**
	 * Acquisisco i valori inseriti al login 
	 * @param username username inserito
	 * @param password password inserita
	 */
	public Amministratore(String username,String password) {
		this.usernameIns=username;
		this.passwordIns=password;
	}
	
	/**
	 * Effettuo controllo dei valori inseriti con quelli di sistema per l'accesso
	 * @return restituisce l'esito del controllo
	 */
	public boolean CheckLogin() {
		if(usernameIns.equals(username) && passwordIns.equals(password))
			return true;
		else
			return false;
			
	}
	
}

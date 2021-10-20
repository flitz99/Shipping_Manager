package dialogGraphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * Classe che permette l'uso di frame con messaggi di errore aventi testo specifico
 * @author filipporeggiani
 *
 */
public class ErrorDialog {
	/**
	 * Messaggio presente all'interno del frame 
	 */
	private String message; 
	/**
	 * Costruttore che imposta il messaggio di errore passato 
	 * @param message messaggio da impostare
	 */
	public ErrorDialog(String message) {
		this.setMessage(message);	    
		JOptionPane.showMessageDialog(new JFrame(), message, "Errore",
			        JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * Permette di acquisire il messaggio di errore scritto nel frame
	 * @return ritorna messaggio 
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Permette di impostare il messaggio di errore che verrà visualizzato nel frame di errore
	 * @param message nuovo messaggio da impostare
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}

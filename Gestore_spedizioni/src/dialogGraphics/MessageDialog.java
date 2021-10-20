package dialogGraphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * Classe che permette l'uso di frame aventi messaggi di informazione personalizzati
 * @author filipporeggiani
 *
 */
public class MessageDialog {
	/**
	 * Messaggio che viene scritto all'intero del frame
	 */
	private String message; 
	/**
	 * Costruttore che imposta il testo passato come messaggio del frame
	 * @param message messaggio da visualizzare nel frame
	 */
	public MessageDialog(String message) {
		this.setMessage(message);	    
		JOptionPane.showMessageDialog(new JFrame(), message, "Message",
			        JOptionPane.PLAIN_MESSAGE);
	}
	/**
	 * Permette di acquisire il testo scritto nel frame
	 * @return restituisce messaggio 
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Permette di impostare il messaggio che verrà visualizzato all'interno del frame
	 * @param message messaggio da impostare
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}

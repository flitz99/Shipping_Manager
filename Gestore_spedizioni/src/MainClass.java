
import Archivio.Archivio;


import graphics.FrameStart;
import spedizione.Spedizione;
import utenti.Cliente;
/**
 * 
 * @author filipporeggiani
 *	Classe Main
 */
public class MainClass {
	
	public static void main(String[] args){
		
		Archivio<Cliente> clienti= new Archivio<Cliente>(); //creazione archivio clienti
		Archivio<Spedizione> spedizioni= new Archivio<Spedizione>(); //creazione archivio spedizioni
		
		//FRAME PRINCIPALE
		FrameStart frame = new FrameStart(clienti,spedizioni); //istanza frame principale
		frame.pack(); 	//si adatta ai componenti messi (layout standard)
		frame.setVisible(true);
		frame.setBounds(0, 0, 600, 180);
		frame.setLocationRelativeTo(null);

		frame.getCreaSpedButt().setEnabled(false); //rendo pulsante per nuova spedizione non utilizzabile all'inizio
		frame.getLogButt().setEnabled(false); //rendo pulsante per login non utilizzabile all'inizio dai clienti

		
			
	}
}
	

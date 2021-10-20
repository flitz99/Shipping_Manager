package spedizione;

import java.io.FileWriter;
import java.io.IOException;
/**
 * Classe che estende dalla classe Spedizione implementando una spedizione di tipo assicurato
 * @author filipporeggiani
 *
 */
public class SpedizioneAssicurata extends Spedizione{
	
	/**
	 * Valore assicurato della spedizione
	 */
	protected int valAssicurato;
	
	/**
	 * Costruttore che crea una spedizione di tipo assicurato
	 * @param cliente contiene username del cliente creatore della spedizione
	 * @param codice contiene codice della spedizione
	 * @param destinazione contiene destinazione della spedizione
	 * @param peso contiene peso in kg della spedizione
	 * @param data contiene data di creazione della spedizione
	 * @param stato contiene stato attuale della spedizione
	 * @param ValAssicurato contiene valore fino al quale la spedizione è assicurata
	 */
	public SpedizioneAssicurata(String cliente,String codice, String destinazione, int peso, String data, String stato,int ValAssicurato) {
		super(cliente,codice, destinazione, peso, data, stato);
		this.valAssicurato=ValAssicurato;
	}
	/**
	 * Permette la stampa della spedizione assicurata
	 * A differenza della classe padre stampa anche il valore assicurato
	 */
	@Override
	public void StampaSpedizione() {
		super.StampaSpedizione();
		System.out.println("Valore assicurato: "+valAssicurato);
	}
	/**
	 * Permette salvataggio su file della spedizione assicurata
	 * A differenza della classe padre salva anche il valore assicurato
	 */
	@Override
	public void SalvaSuFile(FileWriter fout) {
		try{
			fout.write("ASSICURATA\n");
			fout.write(super.cliente+"\n");
			fout.write(super.codice+"\n");
			fout.write(super.Destinazione+"\n");
			fout.write(super.peso+"\n");
			fout.write(super.data+"\n");
			fout.write(super.stato+"\n");
			fout.write(valAssicurato+"\n");
			
		}catch(IOException e) {
			System.out.println("Apertura file fallita");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	/**
	 * Permette di ottenere il valore assicurato relativo alla spedizione assicurata
	 * @return restituisce valore assicurato
	 */
	public int getValAssicurato() {
		return valAssicurato;
	}
	/**
	 * Permette di impostare il valore assicurato della spedizione assicurata
	 * @param valAssicurato nuovo valore assicurato da impostare
	 */
	public void setValAssicurato(int valAssicurato) {
		this.valAssicurato = valAssicurato;
	}

	
}

/**
 * 
 */
package console;

import java.util.HashMap;
import java.util.List;

import desenvolvimento.SequenciaDNA;

/**
 * @author JN
 *
 */
public class Dados {
	
	private HashMap<String, List<SequenciaDNA>> sequenciasDNA;
	private int[][] quantidadeGC;
	
	private HashMap<String, List<String>> sequenciasAminoacidos;
	
	public Dados(HashMap<String, List<SequenciaDNA>> sequenciasDNA){
		this.setSequenciasDNA(sequenciasDNA);
	}

	public HashMap<String, List<SequenciaDNA>> getSequenciasDNA() {
		return sequenciasDNA;
	}

	public void setSequenciasDNA(HashMap<String, List<SequenciaDNA>> sequenciasDNA) {
		this.sequenciasDNA = sequenciasDNA;
	}

	public int[][] getQuantidadeGC() {
		return quantidadeGC;
	}

	public void setQuantidadeGC(int[][] quantidadeGC) {
		this.quantidadeGC = quantidadeGC;
	}

	public HashMap<String, List<String>> getSequenciasAminoacidos() {
		return sequenciasAminoacidos;
	}

	public void setSequenciasAminoacidos(HashMap<String, List<String>> sequenciasAminoacidos) {
		this.sequenciasAminoacidos = sequenciasAminoacidos;
	}
}

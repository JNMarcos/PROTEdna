/**
 * 
 */
package console;

import java.util.HashMap;
import java.util.List;

import desenvolvimento.AlinhamentoAminoacido;
import desenvolvimento.SequenciaDNA;

/**
 * @author JN
 *
 */
public class Dados {
	
	// cada arquivo tem uma lista de sequências de DNA
	private HashMap<String, List<SequenciaDNA>> sequenciasDNA;
	
	// cada arquivo tem uma lista de alinhamentos
	private HashMap<String, List<AlinhamentoAminoacido>> alinhamentosAminoacidos;
	
	public Dados(HashMap<String, List<SequenciaDNA>> sequenciasDNA){
		this.setSequenciasDNA(sequenciasDNA);
	}

	public HashMap<String, List<SequenciaDNA>> getSequenciasDNA() {
		return sequenciasDNA;
	}

	public void setSequenciasDNA(HashMap<String, List<SequenciaDNA>> sequenciasDNA) {
		this.sequenciasDNA = sequenciasDNA;
	}

	public HashMap<String, List<AlinhamentoAminoacido>> getAlinhamentosAminoacidos() {
		return alinhamentosAminoacidos;
	}

	public void setAlinhamentosAminoacidos(HashMap<String, List<AlinhamentoAminoacido>> alinhamentosAminoacidos) {
		this.alinhamentosAminoacidos = alinhamentosAminoacidos;
	}
}

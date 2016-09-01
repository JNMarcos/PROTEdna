/**
 * 
 */
package desenvolvimento;

import java.util.Hashtable;
import java.util.List;

/**
 * @author JN
 *
 */
public class Dados {
	
	// cada arquivo tem uma lista de sequências de DNA
	private Hashtable<String, List<SequenciaDNA>> sequenciasDNA;
	
	// cada arquivo tem uma lista de alinhamentos
	private Hashtable<String, List<AlinhamentoAminoacido>> alinhamentosAminoacidos;
	
	//segue essa tabela aqui 
		//https://www.bing.com/images/search?q=amino%c3%a1cidos&view=detailv2&&id=1F2C6BB1A1059BFE6FB0E9E50597D793EFD0DEE8&selectedIndex=86&ccid=842hFpWT&simid=607997134848001937&thid=OIP.Mf38da11695932dcb876352bed5471ca4o0&ajaxhist=0
	private char[] tiposAminoacidos = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L',
				'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'Z'};
		
	private int tamanhoMinimoRegiaoBaixaSimilaridade;
	private int tamanhoMaximoPareamento;
	private int tamanhoMinimoPareamento;
		
	public Dados(Hashtable<String, List<SequenciaDNA>> sequenciasDNA){
		this.setSequenciasDNA(sequenciasDNA);
		this.alinhamentosAminoacidos = new Hashtable<String, List<AlinhamentoAminoacido>>();
	}

	public Hashtable<String, List<SequenciaDNA>> getSequenciasDNA() {
		return sequenciasDNA;
	}

	public void setSequenciasDNA(Hashtable<String, List<SequenciaDNA>> sequenciasDNA) {
		this.sequenciasDNA = sequenciasDNA;
	}

	public Hashtable<String, List<AlinhamentoAminoacido>> getAlinhamentosAminoacidos() {
		return alinhamentosAminoacidos;
	}

	public void setAlinhamentosAminoacidos(Hashtable<String, List<AlinhamentoAminoacido>> alinhamentosAminoacidos) {
		this.alinhamentosAminoacidos = alinhamentosAminoacidos;
	}
	
	public char[] getTiposAminoacidos() {
		return tiposAminoacidos;
	}

	public void setTamanhoMinimoRegiaoBaixaSimilaridade(int tamanhoMinimoRegiaoBaixaSimilaridade) {
		this.tamanhoMinimoRegiaoBaixaSimilaridade = tamanhoMinimoRegiaoBaixaSimilaridade;
	}
	
	public int getTamanhoMinimoRegiaoBaixaSimilaridade() {
		return tamanhoMinimoRegiaoBaixaSimilaridade;
	}

	public void setTamanhoMaximoPareamento(int tamanhoMaximoPareamento) {
		this.tamanhoMaximoPareamento = tamanhoMaximoPareamento;
	}
	
	public int getTamanhoMaximoPareamento() {
		return tamanhoMaximoPareamento;
	}

	public void setTamanhoMinimoPareamento(int tamanhoMinimoPareamento) {
		this.tamanhoMinimoPareamento = tamanhoMinimoPareamento;
	}

	public int getTamanhoMinimoPareamento() {
		return tamanhoMinimoPareamento;
	}
}

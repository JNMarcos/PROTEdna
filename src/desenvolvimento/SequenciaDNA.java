/**
 * 
 */
package desenvolvimento;

/**
 * @author JN
 *
 */
public class SequenciaDNA{
	
	private String sequencia;
	private String descricao;
	private int[] quantidadeGC;
	private int[] nBasesEmCadaPosicaoDeTamanhoCodon;
	
	public SequenciaDNA(String sequencia, String descricao){
		this.setSequencia(sequencia);
		this.setDescricao(descricao);
	}
	public String getSequencia() {
		return sequencia;
	}
	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int[] getQuantidadeGC() {
		return quantidadeGC;
	}
	public void setQuantidadeGC(int[] quantidadeGC) {
		this.quantidadeGC = quantidadeGC;
	}
	public int[] getnBasesEmCadaPosicaoDeTamanhoCodon() {
		return nBasesEmCadaPosicaoDeTamanhoCodon;
	}
	public void setnBasesEmCadaPosicaoDeTamanhoCodon(int[] nBasesEmCadaPosicaoDeTamanhoCodon) {
		this.nBasesEmCadaPosicaoDeTamanhoCodon = nBasesEmCadaPosicaoDeTamanhoCodon;
	}
}

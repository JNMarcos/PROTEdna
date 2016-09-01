/**
 * 
 */
package desenvolvimento;

import java.util.Hashtable;

/**
 * @author JN
 *
 */
public class RegiaoBaixaSimilaridade {
	//guarda a região
	private String regiao;
	
	// ver esse link para entender qual valor do índice representa determinado aminoácido
	// segue o mesmo índice que no array tipoAminoacidos em AnalisarAminoacido
	private int[] contadorAminoacidosRegiao;
	
	// valor indica qual o aminoácido mais presente na vizinhança do aminoácido com 
	// maior ocorrência, usa o mesmo esquema que contadorAminoacidosRegiao
	private int indiceMaiorFrequenciaNaRegiao;
	
	private char aminoMaisFrequente;
	
	private int[] contadorVizinhosADireitaAminoacidoDeMaiorFrequencia;
	private int[] contadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia;
	
	// usada para procurar regiões de semelhança entre as regiões
	private	Hashtable<String, Integer> pareamentoTamanho3Inferior;
	private	Hashtable<String, Integer> pareamentoTamanho4;
	private	Hashtable<String, Integer> pareamentoTamanho5;
	private	Hashtable<String, Integer> pareamentoTamanho6Superior;
	
	public RegiaoBaixaSimilaridade(String regiao){
		this.regiao = regiao;
	}
	
	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public int[] getContadorAminoacidosRegiao() {
		return contadorAminoacidosRegiao;
	}

	public void setContadorAminoacidosRegiao(int[] contadorAminoacidosRegiao) {
		this.contadorAminoacidosRegiao = contadorAminoacidosRegiao;
	}

	public int getIndiceMaiorFrequenciaNaRegiao() {
		return indiceMaiorFrequenciaNaRegiao;
	}

	public void setIndiceMaiorFrequenciaNaRegiao(int indiceMaiorFrequenciaNaRegiao) {
		this.indiceMaiorFrequenciaNaRegiao = indiceMaiorFrequenciaNaRegiao;
	}

	public char getAminoMaisFrequente() {
		return aminoMaisFrequente;
	}

	public void setAminoMaisFrequente(char aminoMaisFrequente) {
		this.aminoMaisFrequente = aminoMaisFrequente;
	}

	public int[] getContadorVizinhosADireitaAminoacidoDeMaiorFrequencia() {
		return contadorVizinhosADireitaAminoacidoDeMaiorFrequencia;
	}

	public void setContadorVizinhosADireitaAminoacidoDeMaiorFrequencia(
			int[] contadorVizinhosADireitaAminoacidoDeMaiorFrequencia) {
		this.contadorVizinhosADireitaAminoacidoDeMaiorFrequencia = contadorVizinhosADireitaAminoacidoDeMaiorFrequencia;
	}

	public int[] getContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia() {
		return contadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia;
	}

	public void setContadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia(
			int[] contadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia) {
		this.contadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia = contadorVizinhosAEsquerdaAminoacidoDeMaiorFrequencia;
	}
	
	public Hashtable<String, Integer> getPareamentoTamanho3Inferior() {
		return pareamentoTamanho3Inferior;
	}

	public void setPareamentoTamanho3Inferior(Hashtable<String, Integer> pareamentoTamanho3Inferior) {
		this.pareamentoTamanho3Inferior = pareamentoTamanho3Inferior;
	}

	public Hashtable<String, Integer> getPareamentoTamanho4() {
		return pareamentoTamanho4;
	}

	public void setPareamentoTamanho4(Hashtable<String, Integer> pareamentoTamanho4) {
		this.pareamentoTamanho4 = pareamentoTamanho4;
	}

	public Hashtable<String, Integer> getPareamentoTamanho5() {
		return pareamentoTamanho5;
	}

	public void setPareamentoTamanho5(Hashtable<String, Integer> pareamentoTamanho5) {
		this.pareamentoTamanho5 = pareamentoTamanho5;
	}

	public Hashtable<String, Integer> getPareamentoTamanho6Superior() {
		return pareamentoTamanho6Superior;
	}

	public void setPareamentoTamanho6Superior(Hashtable<String, Integer> pareamentoTamanho6Superior) {
		this.pareamentoTamanho6Superior = pareamentoTamanho6Superior;
	}
}

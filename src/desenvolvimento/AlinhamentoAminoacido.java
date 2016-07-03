/**
 * 
 */
package desenvolvimento;

/**
 * @author JN
 *
 */
public class AlinhamentoAminoacido {
	
		//strings usadas no alinhamento
		// s�o sequ�ncias de amino�cidos
		private String sequenciaA;
		private String sequenciaB;
		
		private String sequenciaAAlinhada;
		private String sequenciaBAlinhada;
		
		// pega o �ndice que � delimitado aleatoriamente a regi�o de baixa similaridade
		// do alinhamento
		private int indiceMenorRegiaoBaixaSimilaridade;
		private int indiceMaiorRegiaoBaixaSimilaridade;
		
		//ver esse link para entender qual valor do �ndice representa determinado amino�cido
		// https://tse3.mm.bing.net/th?id=OIP.Ma47917932878fc328ad6f617d92c2bf4o0&pid=15.1
		private int[] contadorAminoacidosRegiao;
		
		// guarda o �ndice do array de contadorAminoacidosRegiao que possui maior valor
		// sabendo assim qual o amino�ciodo que possui maior frequ�ncia
		private int indiceMaiorFrequenciaNaRegiao;
		
		//indica qual o amino�cido mais presente na vizinhan�a do amino�cido com maior ocorr�ncia
		// usa o mesmo esquema que contadorAminoacidosRegiao
		private int[] contadorVizinhosAminoacidoMaiorFrequencia;
		
		public AlinhamentoAminoacido(String sequenciaA, String sequenciaB){
			this.sequenciaA = sequenciaA;
			this.sequenciaB = sequenciaB;
		}

		public String getSequenciaA() {
			return sequenciaA;
		}

		public void setSequenciaA(String sequenciaA) {
			this.sequenciaA = sequenciaA;
		}

		public String getSequenciaB() {
			return sequenciaB;
		}

		public void setSequenciaB(String sequenciaB) {
			this.sequenciaB = sequenciaB;
		}

		public String getSequenciaAAlinhada() {
			return sequenciaAAlinhada;
		}

		public void setSequenciaAAlinhada(String sequenciaAAlinhada) {
			this.sequenciaAAlinhada = sequenciaAAlinhada;
		}

		public String getSequenciaBAlinhada() {
			return sequenciaBAlinhada;
		}

		public void setSequenciaBAlinhada(String sequenciaBAlinhada) {
			this.sequenciaBAlinhada = sequenciaBAlinhada;
		}

		public int getIndiceMenorRegiaoBaixaSimilaridade() {
			return indiceMenorRegiaoBaixaSimilaridade;
		}

		public void setIndiceMenorRegiaoBaixaSimilaridade(int indiceMenorRegiaoBaixaSimilaridade) {
			this.indiceMenorRegiaoBaixaSimilaridade = indiceMenorRegiaoBaixaSimilaridade;
		}

		public int getIndiceMaiorRegiaoBaixaSimilaridade() {
			return indiceMaiorRegiaoBaixaSimilaridade;
		}

		public void setIndiceMaiorRegiaoBaixaSimilaridade(int indiceMaiorRegiaoBaixaSimilaridade) {
			this.indiceMaiorRegiaoBaixaSimilaridade = indiceMaiorRegiaoBaixaSimilaridade;
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

		public int[] getContadorVizinhosAminoacidoMaiorFrequencia() {
			return contadorVizinhosAminoacidoMaiorFrequencia;
		}

		public void setContadorVizinhosAminoacidoMaiorFrequencia(
				int[] contadorVizinhosAminoacidoMaiorFrequencia) {
			this.contadorVizinhosAminoacidoMaiorFrequencia = contadorVizinhosAminoacidoMaiorFrequencia;
		}
}

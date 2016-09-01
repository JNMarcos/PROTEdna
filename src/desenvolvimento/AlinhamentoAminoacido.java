/**
 * 
 */
package desenvolvimento;

import java.util.List;

/**
 * @author JN
 *
 */
public class AlinhamentoAminoacido {
	
		//strings usadas no alinhamento
		// são sequências de aminoácidos
		private String sequenciaA;
		private String sequenciaB;
		
		private String sequenciaAAlinhada;
		private String sequenciaBAlinhada;
		
		private List<RegiaoBaixaSimilaridade> regioesBaixaSimilaridadeA;
		private List<RegiaoBaixaSimilaridade> regioesBaixaSimilaridadeB;
		
		private String vetorAlinhamento;
				
		public AlinhamentoAminoacido(String sequenciaA, String sequenciaB){
			this.setSequenciaA(sequenciaA);
			this.setSequenciaB(sequenciaB);
			
			this.setSequenciaAAlinhada("");
			this.setSequenciaBAlinhada("");
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

		public List<RegiaoBaixaSimilaridade> getRegioesBaixaSimilaridadeA() {
			return regioesBaixaSimilaridadeA;
		}

		public void setRegioesBaixaSimilaridadeA(List<RegiaoBaixaSimilaridade> regioesBaixaSimilaridade) {
			this.regioesBaixaSimilaridadeA = regioesBaixaSimilaridade;
		}

		public List<RegiaoBaixaSimilaridade> getRegioesBaixaSimilaridadeB() {
			return regioesBaixaSimilaridadeB;
		}

		public void setRegioesBaixaSimilaridadeB(List<RegiaoBaixaSimilaridade> regioesBaixaSimilaridadeB) {
			this.regioesBaixaSimilaridadeB = regioesBaixaSimilaridadeB;
		}

		public String getVetorAlinhamento() {
			return vetorAlinhamento;
		}

		public void setVetorAlinhamento(String vetorAlinhamento) {
			this.vetorAlinhamento = vetorAlinhamento;
		}
}

package lista;

import escalonador.Processo;

public class Lista {

	private Node primeiro, ultimo;
	private int size, ultIndice;

	public Lista() {
		primeiro = ultimo = null;
		size = 0;
		ultIndice = 0;
	}

        /**Insere o processo na Lista
         * 
         * 
         */
	public void insere(Processo p) {
		Node novo = new Node(p);
		if (primeiro == null) {
			ultimo = novo;
		} else {
			novo.Next = primeiro;
		}
		primeiro = novo;
		size++;
	}

	public void imprime() {
		Node aux = primeiro;
		if (aux == null) {
			System.out.println("Lista Vazia");
		} else {
			while (aux != null) {
				System.out.print(aux.processo + "\n");
				aux = aux.Next;
			}
		}
	}

	public Processo localiza(int indice) {
		if (indice < 0 || indice > size) {
			System.out.println("indice invalido");
			return null;
		} else {
			Node aux = primeiro;
			for (int i = 0; i < indice; i++) {
				aux = aux.Next;
			}
			return aux.processo;
		}
	}

	public int getSize() {
		return size;
	}

	public Processo verificaInicio(Lista list, int atual) {
		Node aux = primeiro;

		while (aux != null) {
			if (aux.processo.getComeco() == atual) {
				
				return aux.processo;
			}
			aux = aux.Next;
		}
		return null;
	}

	public int verificaDuracao() {
		Node aux = primeiro;
		int duracaoTotal = 0;
		while (aux != null) {
			duracaoTotal += aux.processo.getDuracao();
			aux = aux.Next;
		}
		
		return duracaoTotal;
	}

	public void remove(int indice) {
		Processo aux;
		if (indice < 0 || indice > size) {
			System.out.println("indice invalido");
		} else {
			Node node = primeiro;
			for (int i = indice; i < this.size; i++) {
				aux = node.processo;
				node.Next = node;
			}

		}

	}
}

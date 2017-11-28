package fila;

import escalonador.Processo;

public class Node {

public Processo processo;
public Node next;

public Node(Processo p) {
	processo = p;
	next = null;
	}
/**
 * 
 * Exibe o node
 */
public String displayNo() {
	return processo.getsNome() + "(" + processo.getDuracaoAT()+ ")";
	}

}

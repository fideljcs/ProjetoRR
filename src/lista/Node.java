package lista;

import escalonador.Processo;

public class Node {

    public Processo processo;
    public Node Next;

    /**
     * O node da lista
     * 
     */
    public Node(Processo p) {
        processo = p;
        Next = null;
    }
}


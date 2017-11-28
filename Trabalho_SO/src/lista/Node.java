package lista;

import escalonador.Processo;

public class Node {

    public Processo processo;
    public Node Next;

    public Node(Processo p) {
        processo = p;
        Next = null;
    }
}


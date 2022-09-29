package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen
    public DobbeltLenketListe() {
        //throw new UnsupportedOperationException();
        this.hode = null;
        this.hale = null;
        this.antall = this.endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        //throw new UnsupportedOperationException();
        if(a == null){
            throw new NullPointerException("Tabellen a er null!");
        }

        for(int i = 0; i < a.length; i++){
            if(a[i] != null){
                Node tmp = new Node(a[i]);
                if(hode == null){
                    hode = hale = tmp;
                }
                hale.neste = tmp;
                tmp.forrige = hale;
                hale = tmp;
                hale.neste = null;
            }
        }

    }

    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int antall() {
        //throw new UnsupportedOperationException();
        if(hode != null){
            int antall = 1;
            return antall(hode.neste, antall);
        }
        return 0;
    }

    private int antall(Node node, int antall){
        if(node == null) return antall;
        antall++;
        return antall(node.neste, antall);
    }

    @Override
    public boolean tom() {
        //throw new UnsupportedOperationException();
        if(hode == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean leggInn(T verdi) {
        //throw new UnsupportedOperationException();
        Objects.requireNonNull(verdi);
        if(hode == null){
            hode = hale = new Node<>(verdi);
        }
        else{
            Node tmp = new Node(verdi);
            hale.neste = tmp;
            tmp.forrige = hale;
            hale = tmp;
            hale.neste = null;
        }
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    private Node<T> finnNode(int indeks){
        if (indeks <= (antall / 2)) {
            Node<T> temp = hode;
            int count = 0;
            while (temp != null) {
                if (count == indeks) {
                    return temp;
                }
                count++;
                temp = temp.neste;
            }
        } else {
            Node<T> temp = hale;
            int count = antall-1;
            while (temp != null) {
                if (count == indeks) {
                    return temp;
                }
                count--;
                temp = temp.forrige;
            }
        }
        return null;
    }

    @Override
    public T hent(int indeks) {
        //throw new UnsupportedOperationException();
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        //throw new UnsupportedOperationException();
        StringBuilder sb = new StringBuilder("[");
        Node temp = hode;

        while (temp != null){
            sb.append(temp.verdi);
            if(temp.neste != null){
                sb.append(", ");
            }
            temp = temp.neste;
        }

        sb.append("]");
        return sb.toString();
    }

    public String omvendtString() {
        //throw new UnsupportedOperationException();
        StringBuilder sb = new StringBuilder("[");
        Node<T> temp = hale;

        while(temp != null){
            sb.append(temp.verdi);
            if(temp.forrige != null){
                sb.append(", ");
            }
            temp = temp.forrige;
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe



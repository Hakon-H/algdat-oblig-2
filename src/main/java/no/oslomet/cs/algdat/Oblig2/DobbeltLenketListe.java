package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


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

    private static void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    public Liste<T> subliste(int fra, int til) {
        //throw new UnsupportedOperationException();
        fratilKontroll(antall, fra, til);

        DobbeltLenketListe output = new DobbeltLenketListe();
        DobbeltLenketListe oops = new DobbeltLenketListe();

        for(int i = fra; i < til; i++){
            output.leggInn(hent(i));
        }

        return output;
    }

    @Override
    public int antall() {
        //throw new UnsupportedOperationException();

        Node tmp = hode;
        antall = 0;

        while(tmp != null){
            antall++;
            tmp = tmp.neste;
        }

        return antall;
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
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        //throw new UnsupportedOperationException();
        if(0 <= indeks && indeks <= antall) {
            Objects.requireNonNull(verdi);
            Node tmp = new Node(verdi);

            //hvis tabellen er tom
            if (hode == null) {
                hode = hale = tmp;
            }
            //hvis man skal sette inn i hode noden
            else if (indeks == 0) {
                tmp.neste = hode;
                hode.forrige = tmp;
                hode = tmp;
            }
            //hvis man skal sette inn i hale noden
            else if (indeks == antall) {
                tmp.forrige = hale;
                hale.neste = tmp;
                hale = tmp;
            }
            //hvis man skal sette inn ett annet sted i listen
            else {
                Node prev = hode;
                for (int i = 1; i < indeks; i++) {
                    prev = prev.neste;
                }
                tmp.neste = prev.neste;
                prev.neste = tmp;
                tmp.forrige = prev;
                tmp.forrige.neste = tmp;
                tmp.neste.forrige = tmp;
            }
            endringer++;
            antall();
        }
        else{throw new IndexOutOfBoundsException(indeks + " " + antall);}
    }

    @Override
    public boolean inneholder(T verdi) {
        //throw new UnsupportedOperationException();
        if(indeksTil(verdi) != -1) return true;
        return false;
    }

    private Node<T> finnNode(int indeks){
        //throw new UnsupportedOperationException();
        if(indeks >= 0 && indeks < antall){
            if(indeks <= antall/2){
                int pos = 0;
                Node tmp = hode;
                while(pos != indeks){
                    pos++;
                    tmp = tmp.neste;
                }
                return tmp;
            }
            else{
                int pos = antall-1;
                Node tmp = hale;
                while(pos != indeks){
                    pos--;
                    tmp = tmp.forrige;
                }
                return tmp;
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
        //throw new UnsupportedOperationException();
        Node tmp = hode;
        int pos = 0;
        while(tmp != null){
            if(tmp.verdi.equals(verdi)) return pos;
            pos++;
            tmp = tmp.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        //throw new UnsupportedOperationException();
        indeksKontroll(indeks, false);
        if(nyverdi == null) throw new NullPointerException();
        T gammelVerdi = hent(indeks);
        finnNode(indeks).verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        //throw new UnsupportedOperationException();
        int indeks = indeksTil(verdi);
        if(indeks == -1){
            return false;
        }
        indeksKontroll(indeks, false);
        Node<T> temp = finnNode(indeks);

        if(indeks == 0){
            if(hode.neste == null){
                hale = null;
            } else{
                hode.neste.forrige = null;
            }
            hode = hode.neste;
        } else if(indeks == antall-1){
            if(hode.neste == null){
                hode = null;
            } else{
                hale.forrige.neste = null;
            }
            hale = hale.forrige;
        } else{
            temp.forrige.neste = temp.neste;
            temp.neste.forrige = temp.forrige;
        }
        endringer++;
        antall();
        return true;
    }

    @Override
    public T fjern(int indeks) {
        //throw new UnsupportedOperationException();
        indeksKontroll(indeks, false);
        Node<T> temp = finnNode(indeks);
        T ut = temp.verdi;

        if(indeks == 0){
            if(hode.neste == null){
                hale = null;
            } else{
                hode.neste.forrige = null;
            }
            hode = hode.neste;
        } else if(indeks == antall-1){
            if(hode.neste == null){
                hode = null;
            } else{
                hale.forrige.neste = null;
            }
            hale = hale.forrige;
        } else{
            temp.forrige.neste = temp.neste;
            temp.neste.forrige = temp.forrige;
        }
        endringer++;
        antall();
        return ut;
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
        //throw new UnsupportedOperationException();

        Iterator iterator = new DobbeltLenketListeIterator();

        return iterator;
    }

    public Iterator<T> iterator(int indeks) {
        //throw new UnsupportedOperationException();

        Iterator iterator = new DobbeltLenketListeIterator(indeks);

        return iterator;
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
            //throw new UnsupportedOperationException();
            indeksKontroll(indeks, false);
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            //throw new UnsupportedOperationException();
            if(iteratorendringer != endringer){
                throw new ConcurrentModificationException();
            }
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            fjernOK = true;
            T verdi = denne.verdi;
            denne = denne.neste;
            return verdi;
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



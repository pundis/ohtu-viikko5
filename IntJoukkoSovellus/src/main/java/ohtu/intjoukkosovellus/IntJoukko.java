
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }   
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) throws IllegalArgumentException {
        if (kapasiteetti < 0)
            throw new IllegalArgumentException("Kapasiteetin oltava positiivinen");//heitin vaan jotain :D
        if (kasvatuskoko < 0) 
            throw new IllegalArgumentException("Kasvatuskoon oltava positiivinen");//heitin vaan jotain :D
        ljono = new int[kapasiteetti];
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (kuuluu(luku)) return false;
        if (alkioidenLkm == ljono.length) laajennaLukujonoa();
        
        ljono[alkioidenLkm] = luku;
        alkioidenLkm++;
        return true;
    }
    
    private void laajennaLukujonoa() {
        int apuri[] = ljono;
        ljono = new int[alkioidenLkm + kasvatuskoko];
        
        kopioiTaulukko(apuri, ljono);
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++)
        if (ljono[i] == luku) return true;

        return false;
    }
    
    public int luvunIndeksi(int luku) {
        for (int i = 0; i < alkioidenLkm; i++)
        if (ljono[i] == luku) return i;

        return -1;

    }
    

    public boolean poista(int luku) {
        int indeksi = luvunIndeksi(luku);
        if (indeksi != -1) poistaLuku(indeksi);
        
        return indeksi != -1;
    }
    
    private void poistaLuku(int indeksi) {
        ljono[indeksi] = 0;
        for (int i = indeksi; i < alkioidenLkm - 1; i++) {
            int apuri = ljono[i];
            ljono[i] = ljono[i + 1];
            ljono[i + 1] = apuri;
        }
        alkioidenLkm--;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int koko() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + ljono[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += ljono[i];
                tuotos += ", ";
            }
            tuotos += ljono[alkioidenLkm - 1];
            tuotos += "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdiste = new IntJoukko();
        for (int luku : a.toIntArray()) {
            yhdiste.lisaa(luku);
        }
        for (int luku : b.toIntArray()) {
            yhdiste.lisaa(luku);
        }
        return yhdiste;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();       
        for (int luku : a.toIntArray()) {
            if (b.kuuluu(luku)) {
                leikkaus.lisaa(luku);
            }
        }
        return leikkaus;
    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko erotus = a;
        int[] bLuvut = b.toIntArray();
        for (int luku : bLuvut) {
            erotus.poista(luku);
        }
        return erotus;
    }
        
}
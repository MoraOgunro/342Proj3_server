public class Card {
    String suite;
    int value;

    Card(String theSuite, int theValue){
        this.suite = theSuite;
        this.value = theValue;
    }
    @Override
    public String toString() {
        return suite + " " + value;
    }
}

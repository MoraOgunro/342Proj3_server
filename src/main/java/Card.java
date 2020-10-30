import java.io.Serializable;

public class Card implements Serializable {
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

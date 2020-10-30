public class ConnectionInfo {
    private static int port;

    ConnectionInfo(){
        port = 0;
    }

    static int getPORT(){
        return port;
    }

    public static void setPORT(int a){
        port = a;
    }
}

import HTTPDemo.HTTPGenerator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        HTTPGenerator hg = new HTTPGenerator("http://127.0.0.1:8000/api/demo-java-call");
        try {
            hg.PostNOFluentAPI();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

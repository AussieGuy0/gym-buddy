package au.com.anthonybruno.gymbuddy;

import io.javalin.Javalin;

public class StartServer {

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8000);
    }
}

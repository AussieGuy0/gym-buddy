package dev.anthonybruno.gymbuddy;

public class StartServer {

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8000);
    }
}

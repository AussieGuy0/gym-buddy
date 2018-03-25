package au.com.anthonybruno.gymbuddy;

import io.javalin.Javalin;

public class Server {

    private final Javalin app = Javalin.create();

    public void start(int portNum) {
        app.port(portNum);
        app.enableStaticFiles("webapp");
        app.start();
        Urls urls = new Urls(app);
        urls.setupEndpoints();
    }

    public void stop() {
        app.stop();
    }
}

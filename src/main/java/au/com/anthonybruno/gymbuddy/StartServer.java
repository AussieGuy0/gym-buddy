package au.com.anthonybruno.gymbuddy;

import io.javalin.Javalin;

public class StartServer {

    public static void main(String[] args) {
        Javalin app = Javalin.start(8000);
        Urls urls = new Urls(app);
        urls.setupEndpoints();
    }
}

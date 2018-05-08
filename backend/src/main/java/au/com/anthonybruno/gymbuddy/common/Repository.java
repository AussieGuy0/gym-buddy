package au.com.anthonybruno.gymbuddy.common;

import au.com.anthonybruno.gymbuddy.Server;
import au.com.anthonybruno.gymbuddy.db.Database;

public abstract class Repository {

    protected static final Database db = Server.DATABASE;
    protected final String tableName;

    public Repository(String tableName) {
        this.tableName = tableName;
    }
}

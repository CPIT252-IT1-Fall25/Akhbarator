package sa.edu.kau.fcit.cpit252.project.viewer;

public interface Database {
    void connect();
    void execute();
    void commit();
}


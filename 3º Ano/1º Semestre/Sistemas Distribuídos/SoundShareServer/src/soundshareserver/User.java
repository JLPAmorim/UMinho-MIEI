package soundshareserver;

public class User {
    private final String name;
    private final String pass;
    private boolean author;

    public User(String name, String pass, boolean author) {
        this.name = name;
        this.pass = pass;
        this.author=author;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public boolean isAuthor() {
        return author;
    }
    
    
}

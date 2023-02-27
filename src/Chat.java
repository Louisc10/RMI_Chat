public class Chat {
    private String author;
    private String message;
    
    public Chat(String author, String message){
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getChat(){
        return author + "#" + message + "\n";
    }
}

/**
 * This class generates a Book object
 * a book object holds a ISBN, Title of book, Author, and Genre
 * All these attributes are held as strings
 * */
public class Book {

    private String isbn;
    private String title;
    private String author;
    private String genre;

    public Book(String id, String title, String author, String genre) {
        this.isbn = id;
        this.title = title;
        this.author = author;
        this.genre = genre;

    }

    public void setIsbn (String id){
        this.isbn = id;
    }

    public String getIsbn () {
        return this.isbn;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public String getTitle () {
        return this.title;
    }

    public void setAuthor (String author){
        this.author = author;
    }

    public String getAuthor () {
        return this.author;
    }

    public void setGenre (String genre){
        this.genre = genre;
    }

    public String getGenre () {
        return this.genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + isbn + '\'' +
                ", Title='" + title + '\'' +
                ", Author='" + author + '\'' +
                ", Genre='" + genre + '\'' +
                "}\n";
    }
}

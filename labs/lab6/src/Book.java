public class Book {

    private String title;
    private String author;
    private String genre;
    private int numPages;


    public Book(String title, String author, String genre, int numPages) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.numPages = numPages;

    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getNumPages() {
        return numPages;
    }

    /*
    public T get(String sortBy) {
        if (sortBy == "title") {
            return getTitle();
        } else if (sortBy == "author") {
            return getAuthor();
        } else if (sortBy == "genre") {
            return getGenre();
        } else if (sortBy == "numPages") {
            return getNumPages();
        } else {
            return null;
        }
    }
    */

    public Integer compareTo(Book other, String sortBy) {
        if (sortBy == "title") {
            return this.title.compareTo(other.getTitle());
        } else if (sortBy == "author") {
            return this.author.compareTo(other.getAuthor());
        } else if (sortBy == "genre") {
            return this.genre.compareTo(other.getGenre());
        } else if (sortBy == "numPages") {
            return (this.numPages - other.getNumPages());
        } else {
            return null;
        }
    }

    public boolean equals(Book other) {
        boolean authorMatches = author.equals(other.getAuthor());
        boolean titleMatches = title.equals(other.getTitle());
        return authorMatches && titleMatches;
    }

    /*
    public static void main(String[] args) {
        Book book1 = new Book("Alice's Adventures in Wonderland", "Carroll",
                "Fantasy", 272);
        Book book2 = new Book("Harry Potter : The Philosopher's Stone", "Rowling",
                "Fantasy", 256);
        System.out.println(book1.compareTo(book2, "title"));
        System.out.println(book1.compareTo(book2, "author"));
        System.out.println(book1.compareTo(book2, "genre"));
        System.out.println(book1.compareTo(book2, "numPages"));
        System.out.println(book1.compareTo(book1, "title"));
        System.out.println(book1.compareTo(book2, "titles"));
    }
    */

}
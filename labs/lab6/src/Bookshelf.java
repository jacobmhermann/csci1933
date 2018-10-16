import java.util.Arrays;
import java.util.Comparator;

public class Bookshelf {

    private Book[] books;

    public Bookshelf(Book[] books) {
        this.books = books;
    }

    public Book[] getBooks() {
        return books;
    }


    public Bookshelf mergeBookshelves(Bookshelf bookshelf, String sortBy){
        Book[] thisBooks = this.books;
        Book[] bookshelfBooks = bookshelf.books;
        Book[] mergedBooks = new Book[thisBooks.length + bookshelfBooks.length];

        int ptrThis = 0;
        int ptrBookshelf = 0;

        while(ptrThis < thisBooks.length && ptrBookshelf < bookshelfBooks.length){
            if(thisBooks[ptrThis].compareTo(bookshelfBooks[ptrBookshelf], sortBy) < 0){
                mergedBooks[ptrBookshelf + ptrThis] = thisBooks[ptrThis];
                ptrThis++;
            }
            else{
                mergedBooks[ptrBookshelf + ptrThis] = bookshelfBooks[ptrBookshelf];
                ptrBookshelf++;
            }
        }

        if(ptrThis < thisBooks.length){
            for(int i = ptrThis; i < thisBooks.length; i++){
                mergedBooks[i + ptrBookshelf] = thisBooks[i];
            }
        }
        else {
            for(int i = ptrBookshelf; i < bookshelfBooks.length; i++){
                mergedBooks[i + ptrThis] = bookshelfBooks[i];
            }
        }

        return new Bookshelf(mergedBooks);
    }

    public Bookshelf mergeSortBookshelf(String sortBy){
        if (books.length > 1){
            Book[] books1 = Bookshelf.cloneBookArray(this.books, 0, books.length/2);
            Book[] books2 = Bookshelf.cloneBookArray(this.books, books.length/2, books.length);

            Bookshelf bookshelf1 = new Bookshelf(books1);
            Bookshelf bookshelf2 = new Bookshelf(books2);

            bookshelf1 = bookshelf1.mergeSortBookshelf(sortBy);
            bookshelf2 = bookshelf2.mergeSortBookshelf(sortBy);
            return bookshelf1.mergeBookshelves(bookshelf2, sortBy);
        }
        else{
            return this;
        }
    }

    public static void printBookTitles(Book[] books){
        System.out.println("");
        for (int i = 0; i < books.length; i++){
            System.out.println("title: " + books[i].getTitle());
        }
    }

    public static Book[] cloneBookArray(Book[] books, int startIndex, int endIndex){
        int diff = endIndex - startIndex;
        Book[] returnBooks = new Book[diff];
        for (int i = 0; i < returnBooks.length; i++){
            returnBooks[i] = books[startIndex + i];
        }
        return returnBooks;
    }

    public Bookshelf bubbleSortBookshelf(String sortBy){
        for(int i = books.length; i > 0; i--){
            for(int j = 0; j < i-1; j++){
                if(books[j].compareTo(books[j+1], sortBy) > 0){
                    Book temp = books[j+1];
                    books[j+1] = books[j];
                    books[j] = temp;
                }
            }
        }
        return this;
    }

    public static void main(String[] args) {

        Book[] books = {new Book("1984", "Orwell", "Fiction", 528),
                new Book("A Brief History Of Time", "Hawking", "Astronomy", 212),
                new Book("Alice's Adventures in Wonderland", "Carroll", "Fantasy", 272),
                new Book("Harry Potter : The Philosopher's Stone", "Rowling", "Fantasy", 256),
                new Book("Harry Potter : The Chamber of Secrets", "Rowling", "Fantasy", 368),
                new Book("Harry Potter : The Prisoner of Azkaban", "Rowling", "Fantasy", 464),
                new Book("JK Rowling : Autobiography", "Rowling", "Non-Fiction", 500),
                new Book("The Dark Tower: The Gunslinger", "King", "Horror", 224),
                new Book("The Dark Tower: The Drawing of the Three", "King", "Horror", 400),
                new Book("It", "King", "Horror", 1138),
                new Book("Amazing Spider-Man #1", "Ditko", "Comic", 25)};

        Bookshelf bookshelf = new Bookshelf(books);


    }
}
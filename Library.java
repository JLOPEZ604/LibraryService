import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class Library {
    static final String connectionURL = "jdbc:derby:memory:library;create=true";


    public static Collection<Book> getBooks() {
        return Arrays.asList(
                new Book("0-7475-3269-9", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy"),
                new Book("0-545-01022-5", "Harry Potter and the Deathly Hallows", "J.K. Rowling", "Fantasy"),
                new Book("978-0553213119", "Moby Dick", "Herman Melville", "Fiction"),
                new Book("978-0446310789", "To Kill a Mockingbird", "Harper Lee", "Fiction"),
                new Book("978-0486280615", "The Adventures of Huckleberry Finn", "Mark Twain", "Fiction"),
                new Book("978-0743273565", "The Great Gatsby", "F. Scott Fitzgerald", "Fiction"),
                new Book("978-1604596342", "The Education of Henry Adams", "Henry Adams", "Non-Fiction"));
    }

    public static Collection<Member> getMembers() throws SQLException {
        return Arrays.asList(
                new Member(UUID.randomUUID().toString(), "John", "Doe", "123 Maple Street", "987-654-3210"),
                new Member(UUID.randomUUID().toString(), "Jane", "Doe", "123 Maple Street", "987-654-3211"),
                new Member(UUID.randomUUID().toString(), "Dr.", "Evil", "47 Diabolical Way", "547-842-5468"),
                new Member(UUID.randomUUID().toString(), "Sir", "Lancelot", "1372 Joyous Gard Road", "834-879-4973"));
    }


    public static void main(String[] args) {
        try {
            Connection publicLibConnect = DriverManager.getConnection(connectionURL);
            ArrayList<Member> members = new ArrayList<>();
            BookService bookService = new BookService(publicLibConnect);
            MemberService memberService = new MemberService(publicLibConnect);
            LibraryService libraryService = new LibraryService(publicLibConnect);

            Collection<Book> bookList = getBooks();
            Collection<Member> memberList = getMembers();

            for (Book book : bookList) {
                bookService.createBook(book);
            }

            for (Member member : memberList) {
                memberService.createMember(member);
                members.add(member);
            }

            Collection<Book> sameAuthor = bookService.getByAuthor("J.K. Rowling"); //Get by author test
            System.out.println("Books from J.K. Rowling: ");
            System.out.println(sameAuthor);

            Collection<Book> fictionBooks = bookService.getByGenre("Fiction"); //get by genre test
            System.out.println("Books under Fiction: ");
            System.out.println(fictionBooks);

            Book henryAdams = bookService.getByIsbn("978-1604596342"); //Update test
            System.out.println(henryAdams);
            henryAdams.setGenre("Autobiography");
            bookService.updateBook(henryAdams);
            henryAdams = bookService.getByIsbn("978-1604596342");
            System.out.println(henryAdams);

            Book mobyDick = bookService.getByIsbn("978-0553213119");//delete test
            System.out.println("Book Moby Dick has been deleted");
            bookService.deleteBook(mobyDick);

            //Member testing
            Member drEvil = memberService.getById(members.get(2).getUuid()); //get by id test
            System.out.println("Selected member: " + drEvil);

            System.out.println("Member selected by phone number: ");
            Member otherMember = memberService.getByPhoneNumber(members.get(3).getPhoneNumber()); //get by phone test
            System.out.println(otherMember);


            Collection<Member> memberCollection = memberService.getByLastName("Doe"); //get by last name test
            System.out.println("Members found under Doe: ");
            System.out.println(memberCollection);

            drEvil.setPhoneNumber("123-456-7890");
            System.out.println("Phone Number for Dr. Evil has been updated");
            memberService.updateMember(drEvil);
            System.out.println(drEvil);

            memberService.deleteMember(otherMember);
            System.out.println("Member Sir Lancelot Deleted");

            //Library Service Testing
            if (libraryService.borrowBook(drEvil, bookService.getByIsbn("978-0743273565"))) { //check out book test
                System.out.println("Book Borrowed Successfully");
            } else {
                System.out.println("Book not borrowed: Too many books checked out");
            }

            if (libraryService.borrowBook(drEvil,bookService.getByIsbn("0-545-01022-5"))) { //get books test
               Collection<String> checkedOut = libraryService.getBooks(drEvil, bookService);
               System.out.println(checkedOut);
            } else {
                System.out.println("Book not borrowed: Too many books checked out");
            }

            libraryService.returnBook(drEvil, bookService.getByIsbn("0-545-01022-5")); //return book
            System.out.println("Book Returned Successfully");




        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unknown Exception");
            e.printStackTrace();
        }


    }

}

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
/**
 * This class handles the Checking Out and Returning of books,
 * It also establishes a connection with the database to hold a table of
 * members and books checked out by the members
 */


public class LibraryService {
    private final Connection serviceConnection;
    private final PreparedStatement insertStatement;
    private final PreparedStatement deleteStatement;
    private final PreparedStatement selectStatement;

    public LibraryService(Connection connection) throws SQLException {
        this.serviceConnection = connection;
        String executeString = "CREATE TABLE Library (uuid varchar(255) not null, isbn varchar(255) not null, First_name varchar(255), Last_Name varchar(255)," +
                "primary key(uuid, isbn), constraint FK_uuid foreign key (uuid) references Member(uuid), " +
                "constraint FK_isbn foreign key(isbn) references Book(isbn) )";
        Statement statement = serviceConnection.createStatement();
        statement.execute(executeString);

        insertStatement = serviceConnection.prepareStatement("insert into Library (uuid, isbn, First_name, Last_name) values (?,?,?,?) ");
        deleteStatement = serviceConnection.prepareStatement("delete from Library where uuid = ? and isbn = ?");
        selectStatement = serviceConnection.prepareStatement("select * from Library where uuid = ? ");

    }



    public boolean borrowBook(Member member, Book book) throws SQLException { // returns true if member was able to borrow book - false otherwise
        selectStatement.setString(1, member.getUuid());


        int borrowCount = 0;
        ResultSet rset = selectStatement.executeQuery();

        while (rset.next()) {
            borrowCount++;
        }
        if (borrowCount < 3) {
            insertStatement.setString(1, member.getUuid());
            insertStatement.setString(2,book.getIsbn());
            insertStatement.setString(3,member.getFirstName());
            insertStatement.setString(4,member.getLastName());
            insertStatement.executeUpdate();

            return true;
        }

        return false;

    }

    public void returnBook(Member member, Book book) throws SQLException {
        deleteStatement.setString(1,member.getUuid());
        deleteStatement.setString(2,book.getIsbn());

        deleteStatement.executeUpdate();


    }

    public Collection<String> getBooks(Member member, BookService bookService) throws SQLException {
        selectStatement.setString(1, member.getUuid());
        ResultSet libRset = selectStatement.executeQuery();
        Collection<String> bookCollection = new ArrayList<>();

        if (libRset.next()) {
            System.out.println("Name: " + libRset.getString(3) + " " + libRset.getString(4));
            System.out.println("Books Checked out:");
            do {
                bookCollection.add( bookService.getByIsbn(libRset.getString(2)).toString() );
            } while (libRset.next());

            return bookCollection;
        }

        return null;
    }
}

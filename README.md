# LibraryService
A Java assignment designed to utilize the JDBC class and mimic operations using in a library for books

Library: This is the main entry point of the application where the data for books and members is generated. Books are primarily found by their ISBN, and Members are found by their respective UUIDs, which for this scenario are generated randomly on every run of the application. It is also important to know that the database interacted with only exists in the memory of the device and all data is lost the moment the application exits

LibraryService: This is the class designed to handle the operations of Checking Out, Borrowing and Returning books is Generates its own table using both the ISBN of a book and the UUID of a member as primary keys,

Book: This class generates the book object. The book object holds the ISBN, title, author, and genre of a book, as well as its various getters and setters

BookService: The BookService class generates a table holding the Book objects created, using their ISBNs as primary keys. The then handle the various CRUD operations including getting by Author, or Genre and returning a list of results.

Member: This class generates the Member object. This object holds a UUID, First and Last name, Street address, and a phone number, as well as its varous getters and setters

MemberService: This class generates a table based on Members created, and handles those respective CRUD operations.

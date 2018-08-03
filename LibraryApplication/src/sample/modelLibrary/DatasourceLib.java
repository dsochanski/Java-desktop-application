package sample.modelLibrary;

import org.omg.CORBA.PUBLIC_MEMBER;
import sample.modelLibrary.views.BookAuthorCategory;
import sample.modelLibrary.views.BorrowedBooks;

import javax.swing.plaf.PanelUI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatasourceLib {

    public static final String DB_NAME = "library.db";


    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Panstwo Sochanscy\\Music\\LibraryApplication\\" + DB_NAME;

    public static final String TABLE_AUTHOR = "author";
    public static final String COLUMN_AUTHOR_ID = "id_author";
    public static final String COLUMN_AUTHOR_NAME = "name";
    public static final String COLUMN_AUTHOR_LAST_NAME = "last_name";


    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_NAME = "name";
    public static final int INDEX_CATEGORY_ID = 1;
    public static final int INDEX_CATEGORY_NAME = 2;

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_BORROWER_ID = "id_borrower";
    public static final String COLUMN_ORDER_BOOK_ID = "id_book";
    public static final String COLUMN_ORDER_DATE = "order_date";

    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_BOOK_TITLE = "title";
    public static final String COLUMN_BOOK_DESCRIPTION = "description";
    public static final String COLUMN_BOOK_CATEGORY_ID = "id_category";
    public static final String COLUMN_BOOK_AUTHOR_ID = "id_author";

    public static final String TABLE_BORROWER = "borrower";
    public static final String COLUMN_BORROWER_NAME = "name";
    public static final String COLUMN_BORROWER_LAST_NAME = "last_name";
    public static final String COLUMN_BORROWER_PHONE = "phone";

    public static final String QUERY_BOOKS_WITH_AUTHORS_AND_CATEGORIES = "SELECT book.title," +
            " author.name, author.last_name, category.name FROM book " +
            "inner join author on book.id_author = author.id_author " +
            "inner join category on book.id_category = category.id_category";

    public static final String INSERT_CATEGORY = "INSERT INTO " + TABLE_CATEGORY + '(' + COLUMN_CATEGORY_NAME + ") VALUES(?)";

    public static final String INSERT_AUTHOR ="INSERT INTO " + TABLE_AUTHOR +
            '(' + COLUMN_AUTHOR_NAME + ", " + COLUMN_AUTHOR_LAST_NAME + ") VALUES(?, ?)";

    public static final String INSERT_INTO_BOOK = "INSERT INTO " + TABLE_BOOK +
            '(' + COLUMN_BOOK_TITLE + ", " + COLUMN_BOOK_DESCRIPTION + ", " + COLUMN_BOOK_CATEGORY_ID +
            ", " + COLUMN_BOOK_AUTHOR_ID + ") VALUES(?, ?, ?, ?)";

    public static final String FIND_BOOK = "SELECT book.title," +
            " author.name, author.last_name, category.name FROM book " +
            "inner join author on book.id_author = author.id_author " +
            "inner join category on book.id_category = category.id_category " +
            "WHERE book.title LIKE";

    private static final String QUERY_BORROWED_BOOKS_TITLE_NAME_DATE ="SELECT book.title," +
            " borrower.name, borrower.last_name, orders.order_date FROM orders " +
            "INNER JOIN book on orders.id_book = book.id_book INNER JOIN " +
            "borrower on orders.id_borrower = borrower.id_borrower";

    private static final String INSERT_INTO_ORDERS = "INSERT INTO " + TABLE_ORDERS + '(' + COLUMN_ORDER_BORROWER_ID +
            ", " + COLUMN_ORDER_BOOK_ID + ", " + COLUMN_ORDER_DATE + ") VALUES(?, ?, ?) ";

    private static final String INSERT_INTO_BORROWERS = "INSERT INTO " + TABLE_BORROWER + '(' + COLUMN_BORROWER_NAME +
            ", " + COLUMN_BORROWER_LAST_NAME + ", " + COLUMN_BORROWER_PHONE + ") VALUES(?, ?, ?)";


    private Connection conn;


    private PreparedStatement insertIntoCategory;
    private PreparedStatement insertIntoAuthor;
    private PreparedStatement insertIntoBook;
    private PreparedStatement insertIntoOrders;
    private PreparedStatement insertIntoBorrowers;

    private static DatasourceLib instance = new DatasourceLib();

    public DatasourceLib(){}

    public static  DatasourceLib getInstance(){
        return instance;
        //DatasourceLib.getInstance().methodName();
    }



    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertIntoCategory = conn.prepareStatement(INSERT_CATEGORY , Statement.RETURN_GENERATED_KEYS);
            //"Insert into category (name) values (?)"
            insertIntoAuthor = conn.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS);
            insertIntoBook = conn.prepareStatement(INSERT_INTO_BOOK, Statement.RETURN_GENERATED_KEYS);
            insertIntoOrders = conn.prepareStatement(INSERT_INTO_ORDERS, Statement.RETURN_GENERATED_KEYS);
            insertIntoBorrowers = conn.prepareStatement(INSERT_INTO_BORROWERS, Statement.RETURN_GENERATED_KEYS);

            return true;
        } catch(SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {

            if(insertIntoCategory != null){
                insertIntoCategory.close();
            }

            if(insertIntoAuthor != null){
                insertIntoAuthor.close();
            }

            if(insertIntoBook != null){
                insertIntoBook.close();
            }

            if(insertIntoOrders != null){
                insertIntoOrders.close();
            }

            if(insertIntoBorrowers != null){
                insertIntoBorrowers.close();
            }

            if(conn != null) {
                conn.close();
            }
        } catch(SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public List<Author> queryAuthorList(){

        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_AUTHOR)){

            List<Author> authors = new ArrayList<>();
            while (result.next()) {
                Author author = new Author();
                author.setId(result.getInt(COLUMN_AUTHOR_ID));
                author.setName(result.getString(COLUMN_AUTHOR_NAME));
                author.setLastName(result.getString(COLUMN_AUTHOR_LAST_NAME));
                authors.add(author);
            }

            return authors;

        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Category> queryCategoryList(){

        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_CATEGORY)){

            List<Category> categories = new ArrayList<>();
            while (result.next()) {
                Category category = new Category();
                category.setId(result.getInt(INDEX_CATEGORY_ID));
                category.setName(result.getString(INDEX_CATEGORY_NAME));
                categories.add(category);
            }

            return categories;

        }catch (SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<BookAuthorCategory> queryBooksForAuthorsAndCategories(){
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(QUERY_BOOKS_WITH_AUTHORS_AND_CATEGORIES)){

            List<BookAuthorCategory> bookAuthorCategories = new ArrayList<>();
            while(result.next()){
                BookAuthorCategory bookAuthorCategory = new BookAuthorCategory();
                bookAuthorCategory.setTitle(result.getString(1));
                bookAuthorCategory.setAuthorName(result.getString(2));
                bookAuthorCategory.setAuthorLastName(result.getString(3));
                bookAuthorCategory.setCategoryName(result.getString(4));
                bookAuthorCategories.add(bookAuthorCategory);
            }
            return bookAuthorCategories;
        }catch (SQLException e){
            System.out.println("Query for books with category and authors failed: " + e.getMessage());
            return null;
        }
    }

    public List<BorrowedBooks> queryBorrowedBooksTitleNameDate(){
        try(Statement statement = conn.createStatement();
            ResultSet result =statement.executeQuery(QUERY_BORROWED_BOOKS_TITLE_NAME_DATE)){

            List<BorrowedBooks> borrowedBooksName = new ArrayList<>();
            while(result.next()){
                BorrowedBooks borrowedBooks = new BorrowedBooks();
                borrowedBooks.setBookTitle(result.getString(1));
                borrowedBooks.setBorrowerName(result.getString(2));
                borrowedBooks.setBorrowerLastName(result.getString(3));
                borrowedBooks.setOrderDate(result.getString(4));
                borrowedBooksName.add(borrowedBooks);
            }
            return borrowedBooksName;
        }catch (SQLException e){
            System.out.println("Query for borrowed books failed: " + e.getMessage() );
            return null;
        }
    }

    public void insertCategory(String name){

        try{
            conn.setAutoCommit(false);

            insertIntoCategory.setString(1, name);

            int affectedRows = insertIntoCategory.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
            }else {
                throw new SQLException("the category insert failed !!");
            }
        }catch (Exception e){
            System.out.println("Insert category exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch(SQLException e2) {
                System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior / category");
                conn.setAutoCommit(true);
            } catch(SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    public void insertAuthor(String name, String lastName){
        try{
            conn.setAutoCommit(false);

            insertIntoAuthor.setString(1, name);
            insertIntoAuthor.setString(2, lastName);

            int affectedRows = insertIntoAuthor.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
            }else {
                throw new SQLException("the author insert failed !!");
            }
        }catch (Exception e){
            System.out.println("Insert author exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch(SQLException e2) {
                System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior / author");
                conn.setAutoCommit(true);
            } catch(SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    public void insertOrder(int borrowerId, int bookId, String orderDate){
        try{
            conn.setAutoCommit(false);
            insertIntoOrders.setInt(1, borrowerId);
            insertIntoOrders.setInt(2, bookId);
            insertIntoOrders.setString(3, orderDate);

            int affectedRows = insertIntoOrders.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
            }else {
                throw new SQLException("the orders insert failed !!");
            }
        }catch (Exception e){
            System.out.println("Insert order exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch(SQLException e2) {
                System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior / order");
                conn.setAutoCommit(true);
            } catch(SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    public void insertBorrowers(String name, String lastName, String phone){
        try{
            conn.setAutoCommit(false);
            insertIntoBorrowers.setString(1, name);
            insertIntoBorrowers.setString(2, lastName);
            insertIntoBorrowers.setString(3, phone);

            int affectedRows = insertIntoBorrowers.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
            }else {
                throw new SQLException("the borrowers insert failed !!");
            }
        }catch (Exception e){
            System.out.println("Insert borrowers exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch(SQLException e2) {
                System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior / borrower");
                conn.setAutoCommit(true);
            } catch(SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    public void insertBook(String title, String description, int categoryId, int authorId){

        try{
            conn.setAutoCommit(false);

            insertIntoBook.setString(1, title);
            insertIntoBook.setString(2, description);
            insertIntoBook.setInt(3, categoryId);
            insertIntoBook.setInt(4, authorId);

            int affectedRows = insertIntoBook.executeUpdate();
            if(affectedRows == 1) {
                conn.commit();
            }else {
                throw new SQLException("the book insert failed !!");
            }
        }catch (Exception e){
            System.out.println("Insert book exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            } catch(SQLException e2) {
                System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior / book");
                conn.setAutoCommit(true);
            } catch(SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }

        }
    }

    public List<BookAuthorCategory> searchBook(String name){
        try(Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(FIND_BOOK + " '%"+ name + "%'")){

            List<BookAuthorCategory> bookAuthorCategoriesWithSelectedTitle = new ArrayList<>();
            while(result.next()){
                BookAuthorCategory bookAuthorCategory = new BookAuthorCategory();
                bookAuthorCategory.setTitle(result.getString(1));
                bookAuthorCategory.setAuthorName(result.getString(2));
                bookAuthorCategory.setAuthorLastName(result.getString(3));
                bookAuthorCategory.setCategoryName(result.getString(4));
                bookAuthorCategoriesWithSelectedTitle.add(bookAuthorCategory);
            }
            return bookAuthorCategoriesWithSelectedTitle;
        }catch (SQLException e){
            System.out.println("Query for books with category and authors failed: " + e.getMessage());
            return null;
        }

    }



}

import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Book {
  private int id;
  private String title;

  public int getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }


    public Book(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
             this.getId() == newBook.getId();
    }
  }

  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books(title) VALUES (:title)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", title)
        .executeUpdate()
        .getKey();
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books where id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
      .addParameter("book_id",this.getId())
      .addParameter("author_id", author.getId())
      .executeUpdate();
    }
  }

  public List<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()){
    String sql = "SELECT authors.* FROM books JOIN books_authors ON (books.id = books_authors.book_id) JOIN authors ON (books_authors.author_id = authors.id) WHERE books.id = :book_id;";
      return con.createQuery(sql)
      .addParameter("book_id", this.getId())
      .executeAndFetch(Author.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM books WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public static void deleteAllBooks() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM books;";
      con.createQuery(deleteQuery)
      .executeUpdate();
    }
  }
}

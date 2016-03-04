import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Author {
  private int id;
  private String first_name;
  private String last_name;

  public int getId() {
      return id;
    }

    public String getFirstName() {
      return first_name;
    }

    public String getLastName() {
      return last_name;
    }


    public Author(String first_name, String last_name) {
    this.first_name = first_name;
    this.last_name = last_name;
  }

  @Override
  public boolean equals(Object otherAuthor){
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getFirstName().equals(newAuthor.getFirstName()) && this.getLastName().equals(newAuthor.getLastName()) &&
             this.getId() == newAuthor.getId();
    }
  }

  public static List<Author> all() {
    String sql = "SELECT * FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors(first_name, last_name) VALUES (:first_name, :last_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("first_name", first_name)
        .addParameter("last_name", last_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Author find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors where id=:id";
      Author author = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Author.class);
      return author;
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
      con.createQuery(sql)
      .addParameter("author_id",this.getId())
      .addParameter("book_id", book.getId())
      .executeUpdate();
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
    String sql = "SELECT books.* FROM authors JOIN books_authors ON (authors.id = books_authors.author_id) JOIN books ON (books_authors.book_id = books.id) WHERE authors.id = :author_id;";
      return con.createQuery(sql)
      .addParameter("author_id", this.getId())
      .executeAndFetch(Book.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM authors WHERE id = :id;";
      con.createQuery(deleteQuery)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public static void deleteAllAuthors() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM authors;";
      con.createQuery(deleteQuery)
      .executeUpdate();
    }
  }
}

import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Band {
  private int id;
  private String name;

  public int getId() {
      return id;
    }

    public String getName() {
      return name;
    }


    public Band(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
             this.getId() == newBand.getId();
    }
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands where id=:id";
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  // public void addAuthor(Author author) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO books_authors (book_id, author_id) VALUES (:book_id, :author_id)";
  //     con.createQuery(sql)
  //     .addParameter("book_id",this.getId())
  //     .addParameter("author_id", author.getId())
  //     .executeUpdate();
  //   }
  // }
  //
  // public List<Author> getAuthors() {
  //   try(Connection con = DB.sql2o.open()){
  //   String sql = "SELECT authors.* FROM books JOIN books_authors ON (books.id = books_authors.book_id) JOIN authors ON (books_authors.author_id = authors.id) WHERE books.id = :book_id;";
  //     return con.createQuery(sql)
  //     .addParameter("book_id", this.getId())
  //     .executeAndFetch(Author.class);
  //   }
  // }
  //
  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String deleteQuery = "DELETE FROM books WHERE id = :id;";
  //     con.createQuery(deleteQuery)
  //     .addParameter("id", id)
  //     .executeUpdate();
  //   }
  // }
  //
  // public static void deleteAllBooks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String deleteQuery = "DELETE FROM books;";
  //     con.createQuery(deleteQuery)
  //     .executeUpdate();
  //   }
  // }
}

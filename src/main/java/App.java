import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("books", Book.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/authors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("authors", Author.all());
      model.put("template", "templates/authors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/patrons", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("patrons", Patron.all());
      model.put("template", "templates/patrons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Book book = Book.find(id);
      model.put("book", book);
      model.put("author", book.getAuthors());
      model.put("authors", Author.all());
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/authors/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Author author = Author.find(id);
      model.put("author", author);
      model.put("book", author.getBooks());
      model.put("books", Book.all());
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books", (request, response) -> {
      String title = request.queryParams("title");
      title = title.substring(0, 1).toUpperCase() + title.substring(1);
      Book newBook = new Book(title);
      newBook.save();
      response.redirect("/books");
      return null;
    });

    post("/authors", (request, response) -> {
      String firstName = request.queryParams("firstName");
      firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
      String lastName = request.queryParams("lastName");
      lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
      Author newAuthor = new Author(firstName, lastName);
      newAuthor.save();
      response.redirect("/authors");
      return null;
    });

    post("/patrons", (request, response) -> {
      String patronName = request.queryParams("patronName");
      patronName = patronName.substring(0, 1).toUpperCase() + patronName.substring(1);
      Patron newPatron = new Patron(patronName);
      newPatron.save();
      response.redirect("/patrons");
      return null;
    });

    post("/add_books", (request, response) -> {
      int authorId = Integer.parseInt(request.queryParams("author_id"));
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      Book book = Book.find(bookId);
      Author author = Author.find(authorId);
      author.addBook(book);
      response.redirect("/authors/" + authorId);
      return null;
    });


    post("/add_authors", (request, response) -> {
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      int authorId = Integer.parseInt(request.queryParams("author_id"));
      Book book = Book.find(bookId);
      Author author = Author.find(authorId);
      book.addAuthor(author);
      response.redirect("/books/" + bookId);
      return null;
    });


    post("/books/:id/delete", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Book book = Book.find(id);
      book.delete();
      response.redirect("/books");
      return null;
    });


    post("/authors/:id/delete", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Author author = Author.find(id);
      author.delete();
      response.redirect("/authors");
      return null;
    });

    post("/books/delete", (request, response) -> {
      Book.deleteAllBooks();
      response.redirect("/books");
      return null;
    });


    post("/authors/delete", (request, response) -> {
      Author.deleteAllAuthors();
      response.redirect("/authors");
      return null;
    });
  }
}

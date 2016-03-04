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

    get("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Band band = Band.find(id);
      model.put("band", band);
      model.put("venue", band.getVenues());
      model.put("venues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("id"));
      Venue venue = Venue.find(id);
      model.put("venue", venue);
      model.put("band", venue.getBands());
      model.put("bands", Band.all());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands", (request, response) -> {
      String bandName = request.queryParams("bandName");
      Band newBand = new Band(bandName);
      newBand.save();
      response.redirect("/bands");
      return null;
    });

    post("/venues", (request, response) -> {
      String venueName = request.queryParams("venueName");
      Venue newVenue = new Venue(venueName);
      newVenue.save();
      response.redirect("/venues");
      return null;
    });


    //
    // post("/add_books", (request, response) -> {
    //   int authorId = Integer.parseInt(request.queryParams("author_id"));
    //   int bookId = Integer.parseInt(request.queryParams("book_id"));
    //   Book book = Book.find(bookId);
    //   Author author = Author.find(authorId);
    //   author.addBook(book);
    //   response.redirect("/authors/" + authorId);
    //   return null;
    // });
    //
    //
    // post("/add_authors", (request, response) -> {
    //   int bookId = Integer.parseInt(request.queryParams("book_id"));
    //   int authorId = Integer.parseInt(request.queryParams("author_id"));
    //   Book book = Book.find(bookId);
    //   Author author = Author.find(authorId);
    //   book.addAuthor(author);
    //   response.redirect("/books/" + bookId);
    //   return null;
    // });
    //
    //
    // post("/books/:id/delete", (request, response) -> {
    //   int id = Integer.parseInt(request.params("id"));
    //   Book book = Book.find(id);
    //   book.delete();
    //   response.redirect("/books");
    //   return null;
    // });
    //
    //
    // post("/authors/:id/delete", (request, response) -> {
    //   int id = Integer.parseInt(request.params("id"));
    //   Author author = Author.find(id);
    //   author.delete();
    //   response.redirect("/authors");
    //   return null;
    // });
    //
    // post("/books/delete", (request, response) -> {
    //   Book.deleteAllBooks();
    //   response.redirect("/books");
    //   return null;
    // });
    //
    //
    // post("/authors/delete", (request, response) -> {
    //   Author.deleteAllAuthors();
    //   response.redirect("/authors");
    //   return null;
    // });
  }
}

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



    post("/add_bands", (request, response) -> {
      int venueId = Integer.parseInt(request.queryParams("venue_id"));
      int bandId = Integer.parseInt(request.queryParams("band_id"));
      Band band = Band.find(bandId);
      Venue venue = Venue.find(venueId);
      venue.addBand(band);
      response.redirect("/venues/" + venueId);
      return null;
    });


    post("/add_venues", (request, response) -> {
      int bandId = Integer.parseInt(request.queryParams("band_id"));
      int venueId = Integer.parseInt(request.queryParams("venue_id"));
      Band band = Band.find(bandId);
      Venue venue = Venue.find(venueId);
      band.addVenue(venue);
      response.redirect("/bands/" + bandId);
      return null;
    });


    post("/bands/:id/delete", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Band band = Band.find(id);
      band.delete();
      response.redirect("/bands");
      return null;
    });


    post("/venues/:id/delete", (request, response) -> {
      int id = Integer.parseInt(request.params("id"));
      Venue venue = Venue.find(id);
      venue.delete();
      response.redirect("/venues");
      return null;
    });

    post("/bands/delete", (request, response) -> {
      Band.deleteAllBands();
      response.redirect("/bands");
      return null;
    });


    post("/venues/delete", (request, response) -> {
      Venue.deleteAllVenues();
      response.redirect("/venues");
      return null;
    });
  }
}

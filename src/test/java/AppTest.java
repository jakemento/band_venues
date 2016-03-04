import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome to the band-venue page");
  }

  @Test
  public void bandIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or View bands"));
    fill("#bandName").with("the beatles");
    submit(".btn");
    assertThat(pageSource()).contains("the beatles");
  }


  @Test
  public void addVenueToBand() {
    Band newBand = new Band("the beatles");
    newBand.save();
    Venue newVenue = new Venue("my garage");
    newVenue.save();
    String bandPath = String.format("http://localhost:4567/bands/%d", newBand.getId());
    goTo(bandPath);
    assertThat(pageSource()).contains("the beatles");
    assertThat(pageSource()).contains("my garage");
  }

  @Test
  public void VenueIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add or View venues"));
    fill("#venueName").with("my garage");
    submit(".btn");
    assertThat(pageSource()).contains("my garage");
  }

  @Test
  public void addBandToVenue() {
    Band newBand = new Band("the beatles");
    newBand.save();
    Venue newVenue = new Venue("my garage");
    newVenue.save();
    String venuePath = String.format("http://localhost:4567/venues/%d", newVenue.getId());
    goTo(venuePath);
    assertThat(pageSource()).contains("the beatles");
    assertThat(pageSource()).contains("my garage");
  }


  @Test
 public void allBandsNameOnVenuePage() {
   Venue myVenue = new Venue("my garage");
   myVenue.save();
   Band firstBand = new Band("the beatles");
   firstBand.save();
   Band secondBand = new Band("the smiths");
   secondBand.save();
   String venuePath = String.format("http://localhost:4567/venues/%d", myVenue.getId());
   goTo(venuePath);
   assertThat(pageSource()).contains("the beatles");
   assertThat(pageSource()).contains("the smiths");
 }


 @Test
public void allVenusNameOnBandPage() {
  Band myBand = new Band("the beatles");
  myBand.save();
  Venue firstVenue = new Venue("my garage");
  firstVenue.save();
  Venue secondVenue = new Venue("your garage");
  secondVenue.save();
  String bandPath = String.format("http://localhost:4567/bands/%d", myBand.getId());
  goTo(bandPath);
  assertThat(pageSource()).contains("my garage");
  assertThat(pageSource()).contains("your garage");
}

}

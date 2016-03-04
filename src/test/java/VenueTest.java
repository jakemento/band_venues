import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Venue firstVenue = new Venue("my garage");
    Venue secondVenue = new Venue("my garage");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Venue firstVenue = new Venue("my garage");
    firstVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertTrue(savedVenue.equals(firstVenue));
  }

  @Test
  public void save_assignsIdToObject() {
    Venue firstVenue = new Venue("my garage");
    firstVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(firstVenue.getId(), savedVenue.getId());
  }
  @Test
  public void find_findVenueInDatabase_true() {
    Venue firstVenue = new Venue("my garage");
    firstVenue.save();
    Venue savedVenue = Venue.find(firstVenue.getId());
    assertTrue(firstVenue.equals(savedVenue));
  }

  @Test
  public void addBand_addsBandToVenue() {
    Venue myVenue = new Venue("my garage");
    myVenue.save();

    Band myBand = new Band("the beatles");
    myBand.save();

    myVenue.addBand(myBand);
    Band savedBand = myVenue.getBands().get(0);
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void getBands_returnsAllBands_List() {
    Venue myVenue = new Venue("my garage");
    myVenue.save();

    Band myBand = new Band("the beatles");
    myBand.save();

    myVenue.addBand(myBand);
    List savedBands = myVenue.getBands();
    assertEquals(savedBands.size(), 1);
  }

  // @Test
  // public void delete_deletesAllBandFromVenue() {
  //   Venue myVenue = new Venue("C.S.", "Lewis");
  //   myVenue.save();
  //
  //   Band myBand = new Band("Chronicals of Narnia");
  //   myBand.save();
  //
  //   myVenue.addBand(myBand);
  //   myVenue.delete();
  //   assertEquals(myVenue.getBands().size(), 0);
  // }
}

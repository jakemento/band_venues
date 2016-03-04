import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Band firstBand = new Band("the beatles");
    Band secondBand = new Band("the beatles");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Band firstBand = new Band("the beatles");
    firstBand.save();
    Band savedBand = Band.all().get(0);
    assertTrue(savedBand.equals(firstBand));
  }

  @Test
  public void save_assignsIdToObject() {
    Band firstBand = new Band("the beatles");
    firstBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(firstBand.getId(), savedBand.getId());
  }
  @Test
  public void find_findBandInDatabase_true() {
    Band firstBand = new Band("the beatles");
    firstBand.save();
    Band savedBand = Band.find(firstBand.getId());
    assertTrue(firstBand.equals(savedBand));
  }

  @Test
  public void addVenue_addsVenueToBand() {
    Venue myVenue = new Venue("my garage");
    myVenue.save();

    Band myBand = new Band("the beatles");
    myBand.save();

    myBand.addVenue(myVenue);
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(myVenue.equals(savedVenue));
}

  @Test
  public void getVenues_returnsAllVenues_List() {
    Venue myVenue = new Venue("my garage");
    myVenue.save();

    Band myBand = new Band("the beatles");
    myBand.save();
    
    myBand.addVenue(myVenue);
    List savedVenues = myBand.getVenues();
    assertEquals(savedVenues.size(), 1);
  }
//   @Test
//   public void delete_deletesAllAuthorsFromBook() {
//     Author myAuthor = new Author("C.S.", "Lewis");
//     myAuthor.save();
//
//     Book myBook = new Book("Chronicals of Narnia");
//     myBook.save();
//
//     myBook.addAuthor(myAuthor);
//     myBook.delete();
//     assertEquals(myAuthor.getBooks().size(), 0);
//   }
}

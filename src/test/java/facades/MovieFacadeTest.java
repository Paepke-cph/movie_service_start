package facades;

import utils.EMF_Creator;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {
    private static  EntityManagerFactory emf;
    private static  MovieFacade facade;
    private Movie m1 = new Movie(1933, "Hamlet", new String[]{"Jesper Nielsen","Henrik Poulsen","Freddy Fræk"});
    private Movie m2 = new Movie(1933, "Bambie", new String[]{"Ulla Tørnæse","Pia Køl","Freddy Fræk"});
    
    public MovieFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
          emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode",
                "dev",
                "ax2",
                EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = MovieFacade.getMovieFacade(emf);
    }
    
    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }
    
    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(m1);
            em.persist(m2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testMovieCount() {
        assertEquals(2,facade.getMovieCount(),"Expects two rows in the database");
    }
    
    @Test
    public void testGetAllMovies(){
        
        List<Movie> movies = facade.getAllMovies();
        movies.forEach(m->{System.out.println(m);});
        Movie movie = facade.getMovieById(1);
//        assertThat(movies, CoreMatchers.hasItems(movie));
        assertThat(movies, everyItem(hasProperty("name")));
    }

    @Test
    public void testGetMovieById(){
        Movie movie = facade.getMovieById(m2.getId());
//        System.out.println(movie.getName());
        assertThat(movie.getActors()[0], containsString("Tørnæse"));
    }
    
    @Test
    public void testMovieHasActors(){
        Movie movie = facade.getMovieById(m1.getId());
        assertThat(movie.getActors(), arrayContaining("Jesper Nielsen","Henrik Poulsen","Freddy Fræk"));
    }

//    //TODO Remove/Change this before use
//    @Test
//    public void testGetMovieCount(){}
//
//    @Test
//    public void testGetMoviesByName(String name){}
//
//    @Test
//    public void testPopulateMovies(){}
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Movie;
import java.util.List;

/**
 *
 * @author thomas
 */
public interface ImovieFacade {

    List<Movie> getAllMovies();

    Movie getMovieById(long id);

    //TODO Remove/Change this before use
    long getMovieCount();

    List<Movie> getMoviesByName(String name);

    void populateMovies();
    
}

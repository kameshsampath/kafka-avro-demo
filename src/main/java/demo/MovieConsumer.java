package demo;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;
import dev.kameshs.kafka.quarkus.Movie;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/consumed-movies")
public class MovieConsumer {
  @Channel("movies-from-kafka")
  Multi<Movie> movies;

  @GET
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @RestStreamElementType(MediaType.TEXT_PLAIN)
  public Multi<String> stream() {
    return movies.map(movie -> String.format("'%s' from %s", movie.getTitle(), movie.getYear()));
  }
}

package hello.repositories;

import hello.entities.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long>{

    Game findByStatusOrderByIdDesc(Integer status);
}

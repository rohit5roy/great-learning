package hello.repositories;

import hello.entities.Move;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovesRepository extends CrudRepository<Move, Integer>{


    List<Move> findByGameIdAndParticipantId(Long gameId, Long participantId);
}

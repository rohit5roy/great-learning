package hello;

import hello.entities.Game;
import hello.entities.Move;
import hello.entities.Participant;
import hello.repositories.GameRepository;
import hello.repositories.MovesRepository;
import hello.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class GameController {


    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private MovesRepository movesRepository;


    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody
    String addNewGame (@RequestParam(value="p_id") Long participantId
            , @RequestParam(value="j_id") Long joiningParticipantId) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if(participantId == null || joiningParticipantId== null)  return  "not saved";
        Game game = new Game();
        game.setParticipantId(participantId);
        game.setJoiningParticipantId(joiningParticipantId);
        game.setStatus(Game.STATUS.NEW.getStatusId());
        gameRepository.save(game);
        return "Saved";
    }

    @GetMapping(path="/create_game") // Map ONLY GET Requests
    public @ResponseBody
    Game createNewGame (@RequestParam(value="email") String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if(email == null || Objects.equals(email, ""))  return  null;


        Participant participant = new Participant();
        participant.setEmail(email);
        participant = participantRepository.save(participant);

        Game game = new Game();
        game.setParticipantId(participant.getId());
        game.setStatus(Game.STATUS.NEW.getStatusId());
        game = gameRepository.save(game);

        game.setStatus(Game.STATUS.WAITING_FOR_PLAYER.getStatusId());
        game = gameRepository.save(game);

        return game;
    }

    @RequestMapping(path="/join_game") // Map ONLY GET Requests
    public @ResponseBody
    Game joinNewGame (@RequestParam(value="email") String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if(email == null || Objects.equals(email, ""))  return  null;

        Participant participant = new Participant();
        participant.setEmail(email);
        participant = participantRepository.save(participant);

        Game game = gameRepository.findByStatusOrderByIdDesc(Game.STATUS.WAITING_FOR_PLAYER.getStatusId());

        if(game == null) return null;

        game.setJoiningParticipantId(participant.getId());
        game.setStatus(Game.STATUS.IN_PROGRESS.getStatusId());
        game = gameRepository.save(game);

        return game;
    }


    @RequestMapping(path="/move_game") // Map ONLY GET Requests
    public @ResponseBody
    Move gameMove (@RequestParam(value="game_id") Long gameId, @RequestParam(value="player_id") Long playerId, @RequestParam(value="move_data") Integer moveData) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if(gameId == null || playerId == null || moveData == null)  return  null;

        List<Move> prevMoves = movesRepository.findByGameIdAndParticipantId(gameId, playerId);

        StringBuilder movePattern = new StringBuilder();

        for(Move move : prevMoves){
            movePattern.append(move.getMoveData().toString());
        }
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if(!gameOptional.isPresent()) return null;
        Game game = gameOptional.get();

        if(Move.isPatternWinCondition(String.valueOf(movePattern.append(moveData)))){
            game.setStatus(Game.STATUS.COMPLETE.getStatusId());
            game.setOutcome(game.getParticipantId() == playerId ? Game.OUTCOME.WIN_PLAYER_1.getOutcomeId() : Game.OUTCOME.WIN_PLAYER_2.getOutcomeId());
            game = gameRepository.save(game);
        }

        Move move = new Move();
        move.setGameId(gameId);
        move.setParticipantId(playerId);
        move.setMoveData(moveData);


        return movesRepository.save(move);
    }


    @GetMapping(path="/game_status") // Map ONLY GET Requests
    public @ResponseBody
    String getGameStatus (@RequestParam(value="game_id") Long gameId) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if(gameId == null)  return  null;

        Optional<Game> gameOptional = gameRepository.findById(gameId);

        if(!gameOptional.isPresent()) return "Game Id not valid";

        Game game = gameOptional.get();

        return "Game id : " + game.getId() + "  Status : " + game.getStatus() + " Outcome : " + game.getOutcome();
    }



    @GetMapping(path="/all")
    public @ResponseBody Iterable<Game> getAllGames() {
        // This returns a JSON or XML with the users
        return gameRepository.findAll();
    }
}

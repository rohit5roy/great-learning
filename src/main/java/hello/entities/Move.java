package hello.entities;

import javax.persistence.*;

@Entity
public class Move {

//    CREATE TABLE `move` (
//          `id` bigint(20) NOT NULL AUTO_INCREMENT,
//          `participant_id` bigint(20) NOT NULL,
//          `move_data` int(3) NOT NULL,
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=latin1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Basic
    @Column(name = "game_id", nullable = false)
    private Long gameId;

    @Basic
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @Basic
    @Column(name = "move_data", columnDefinition = "INT(3)")
    private Integer moveData;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Integer getMoveData() {
        return moveData;
    }

    public void setMoveData(Integer moveData) {
        this.moveData = moveData;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public static boolean isPatternWinCondition(String pattern){
        String[] patterns = { "123", "456", "789", "147", "258", "369", "159", "357"};

        for(String s : patterns){
            if(pattern.equals(s)) return  true;
        }

        return  false;
    }
}

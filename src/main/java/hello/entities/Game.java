package hello.entities;

import javax.persistence.*;

@Entity
public class Game {

//    CREATE TABLE `game` (
//      `id` bigint(20) NOT NULL AUTO_INCREMENT,
//      `participant_id` bigint(20) NOT NULL,
//      `joining_participant_id` bigint(20) DEFAULT 0,
//      `status` int(3)  NOT NULL,
//      `outcome` int(3) DEFAULT '0',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=latin1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Basic
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @Basic
    @Column(name = "joining_participant_id")
    private Long joiningParticipantId;

    @Basic
    @Column(name = "status", nullable = false, columnDefinition = "INT(3)")
    private Integer status;

    @Basic
    @Column(name = "outcome", columnDefinition = "INT(3)")
    private Integer outcome;


    public enum STATUS {
        NEW(1),
        COMPLETE(2),
        INCOMPLETE(3),
        WAITING_FOR_PLAYER(4),
        IN_PROGRESS(5);

        private int statusId;
        STATUS(int _statusId){
            statusId = _statusId;
        }
        public int getStatusId() {
            return statusId;
        }
    }


    public enum OUTCOME {
        WIN_PLAYER_1(1),
        WIN_PLAYER_2(2),
        DRAW(3),
        TIMED_OUT(4);

        private int outcomeId;
        OUTCOME(int _outcomeId){
            outcomeId = _outcomeId;
        }
        public int getOutcomeId() {
            return outcomeId;
        }
    }

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

    public Long getJoiningParticipantId() {
        return joiningParticipantId;
    }

    public void setJoiningParticipantId(Long joiningParticipantId) {
        this.joiningParticipantId = joiningParticipantId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOutcome() {
        return outcome;
    }

    public void setOutcome(Integer outcome) {
        this.outcome = outcome;
    }
}

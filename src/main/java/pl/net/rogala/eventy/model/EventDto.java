package pl.net.rogala.eventy.model;

import pl.net.rogala.eventy.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventDto {
    private Long id;
    private String name;
    private String decription;
    private LocalDate startDate;
    private LocalDate stopDate;
    private User owner;

    public EventDto(Long id, String name, String decription, LocalDate startDate, LocalDate stopDate, User owner) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.owner = owner;
    }
    private List<CommentDto> comments;

    public EventDto(Long id, String name, String decription, LocalDate startDate, LocalDate stopDate, List<CommentDto> comments) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.comments = comments;
    }

    public User getOwner() {
        return owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDecription() {
        return decription;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }
}

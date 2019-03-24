package pl.net.rogala.eventy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventDto {
    private Long id;
    private String name;
    private String decription;
    private LocalDate startDate;
    private LocalDate stopDate;

    public EventDto(Long id, String name, String decription, LocalDate startDate, LocalDate stopDate) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.startDate = startDate;
        this.stopDate = stopDate;
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
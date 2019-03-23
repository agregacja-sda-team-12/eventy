package pl.net.rogala.eventy.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.net.rogala.eventy.entity.Comment;
import pl.net.rogala.eventy.entity.Event;
import pl.net.rogala.eventy.entity.QEvent;
import pl.net.rogala.eventy.model.EventDto;
import pl.net.rogala.eventy.model.EventType;
import pl.net.rogala.eventy.model.FindEventDto;
import pl.net.rogala.eventy.repository.CommentRepository;
import pl.net.rogala.eventy.entity.User;
import pl.net.rogala.eventy.form.NewEventForm;
import pl.net.rogala.eventy.repository.EventRepository;
import pl.net.rogala.eventy.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;
    private UserService userService;
    private CommentRepository commentRepository;

    private final QEvent event = QEvent.event;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository, UserService userService, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;

    }

    public List<Event> showEventList() {
        return eventRepository.findAll(Sort.by("startDate"));
    }

    public List<EventDto> getEvents(FindEventDto findEventDto) {

        System.out.println("Name: " + findEventDto.getName() + "     Event Type:" + findEventDto.getEventType());
        BooleanExpression booleanExpression = Expressions.asBoolean(true).isTrue();
        if (findEventDto.getName() != null) {
            booleanExpression = booleanExpression.and(event.name.contains(findEventDto.getName()));
        }
        if (findEventDto.getEventType() != null) {
            if (findEventDto.getEventType().equals(EventType.CURRENT)) {

                booleanExpression = booleanExpression.and(event.startDate.after(LocalDate.now()).and(event.startDate.before(LocalDate.now())));
            }
            if (findEventDto.getEventType().equals(EventType.FUTURE)) {
                booleanExpression = booleanExpression.and(event.startDate.after(LocalDate.now()));
            }
        }

        List<Event> all = eventRepository.findAll(booleanExpression);
        return all.stream().map(Event::toEventDto).collect(Collectors.toList());
    }

    public Optional<Event> getSingleEvent(Long eventId) {
        return eventRepository.findById(eventId);
    }


    public List<Comment> getAllCommentsToEvent(Long eventId)
    {return  commentRepository.findAllByEvent_Id(eventId);
    }

    public void addNewComment(Long eventId, String userEmail, String body) {
        Comment comment = new Comment();
        comment.setEvent(eventRepository.findById(eventId).get());
        comment.setAdded(LocalDateTime.now());
        comment.setBody(body);
        comment.setCommentator(userRepository.findByEmail(userEmail).get());
        commentRepository.save(comment);

    }

    /**
     * adding new event to database; setting logged user as owner of added event
     * @param authentication gives logged user's e-mail
     * @param eventForm form to adding new event
     */
    public void addNewEvent(NewEventForm eventForm, Authentication authentication){
        Event event = new Event();
        event.setName(eventForm.getName());
        event.setDecription(eventForm.getDescription());
        event.setStartDate(eventForm.getStartDate());
        event.setStopDate(eventForm.getStopDate());
        User owner = userRepository.findByEmail(authentication.getName()).get();
        event.setOwner(owner);
//        userRepository.addOrganizerRoleForEventOwner(owner.getId());
        userService.addOrganizerRole(owner);
        eventRepository.save(event);
    }
}

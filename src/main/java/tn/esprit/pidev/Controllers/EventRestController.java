package tn.esprit.pidev.Controllers;


import java.util.Optional;

import tn.esprit.pidev.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import tn.esprit.pidev.Entities.Event;
import tn.esprit.pidev.Services.EventService;


@CrossOrigin("*")
@RestController
@RequestMapping("/events")
public class EventRestController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    @GetMapping(value = "/getEvents",produces = MediaType.APPLICATION_JSON_VALUE)
            public ResponseEntity<Object> getEvents()
    {
        return new ResponseEntity<>(eventService.getEvents(), HttpStatus.OK) ;
    }

    @GetMapping(value = "/isUserParticipant/{eventid}/{userid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> isUserParticipant(@PathVariable int eventid,@PathVariable long userid)
{
return new ResponseEntity<>(eventService.isUserParticipant(eventid, userid), HttpStatus.OK) ;
}

    @GetMapping(value = "/getEventsByUser/{userid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getEventsByUser(@PathVariable int userid)
{
return new ResponseEntity<>(eventService.getEventsByUser(userid), HttpStatus.OK) ;
}
    
    @PostMapping("/addEvent")
    public ResponseEntity<Event> ajouterEvent(@RequestParam("event") String eventJson,@RequestParam("file") MultipartFile file) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Event event = objectMapper.readValue(eventJson, Event.class);
        Event nouvelEvent = eventService.ajouterEvent(event,file);
        return ResponseEntity.ok(nouvelEvent);
    }
    
    @PutMapping("/updateEvent")
    public ResponseEntity<Event> modifierEvent(@RequestParam("event") String eventJson,@RequestParam(name = "file",required = false) MultipartFile file) throws JsonMappingException, JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Event event = objectMapper.readValue(eventJson, Event.class);

        Event updatedEvent = eventService.modifierEvent(event,file);
        return ResponseEntity.ok(updatedEvent);
    }
    
    @DeleteMapping("/deleteEvent")
    public ResponseEntity<String> supprimerEvent(@RequestBody Event event)
    {
        boolean result = eventService.supprimerEvent(event);
        if(result)
            return ResponseEntity.ok("{}");
        else
            return ResponseEntity.ok("{}");
    }
    
    @GetMapping("/searchEvent/{id}")
    public ResponseEntity<Event> searchEvent(@PathVariable int id)
    {
        Optional<Event> updatedEvent = eventService.searchEvent(id);
        if(updatedEvent.isPresent())
            return ResponseEntity.ok(updatedEvent.get());
        else
            return ResponseEntity.ok(null);
    }
    
    @GetMapping("/searchEventByName")
    public ResponseEntity<Event> searchEventByName(@RequestParam String eventName)
    {
        Optional<Event> event = eventRepository.findByTitre(eventName);
        if(event.isPresent())
            return ResponseEntity.ok(event.get());
        else
            return ResponseEntity.ok(null);
    }
    
    @PostMapping("/participateEvent/{eventId}/{user_id}")
    public ResponseEntity<Boolean> participerEvent(@PathVariable int eventId,@PathVariable int user_id)
    {
        Optional<Event> search = eventService.searchEvent(eventId);
        if(search.isPresent())
        {
            return ResponseEntity.ok(eventService.participateEvent(eventId, user_id));
        }else
        {
            return ResponseEntity.ok(false);
        }
    }
    
    @PostMapping("/revokeParticipateEvent/{eventId}/{user_id}")
    public ResponseEntity<Boolean> revokeParticipateEvent(@PathVariable int eventId,@PathVariable int user_id)
    {
        Optional<Event> search = eventService.searchEvent(eventId);
        
        if(search.isPresent())
        {
            return ResponseEntity.ok(eventService.revokeParticipateEvent(eventId, user_id));
        }else
        {
            return ResponseEntity.ok(false);
        }
    }
    
    @GetMapping("/revenueEvent/{eventId}")
    public ResponseEntity<String> revenueEvent(@PathVariable int eventId)
    {
        Optional<Event> search = eventService.searchEvent(eventId);
        if(search.isPresent())
        {
            return ResponseEntity.ok(eventService.revenueEvent(eventId)+"");
        }else
        {
            return ResponseEntity.ok("Event Not Found");
        }
    }
    
    @GetMapping("/revenueEvents")
    public ResponseEntity<String> revenueEvents()
    {


        return ResponseEntity.ok(eventService.revenueEvents()+"");

    }
    
    @GetMapping("/numberOfParticipantsEvent/{event_id}")
    public ResponseEntity<String> numberOfParticipantsEvent(@PathVariable int event_id)
    {


        return ResponseEntity.ok(eventService.numberOfParticipantsEvent(event_id)+"");

    }
    
    @GetMapping("/numberOfParticipantsEvents")
    public ResponseEntity<String> numberOfParticipantsEvents()
    {


        return ResponseEntity.ok(eventService.numberOfParticipantsEvents()+"");

    }
    
    @GetMapping("/nombreDePlacesDisponible/{event_id}")
    public ResponseEntity<Integer> nombreDePlacesDisponible(@PathVariable int event_id)
    {
        return ResponseEntity.ok(eventService.nombreDePlacesDisponible(event_id));
    }

}

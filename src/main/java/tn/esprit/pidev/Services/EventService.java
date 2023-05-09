package tn.esprit.pidev.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.pidev.Entities.Event;
import tn.esprit.pidev.Entities.User;
import tn.esprit.pidev.Repositories.EventRepository;
import tn.esprit.pidev.Repositories.UserRepository;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    String path = new ClassPathResource("/src/main/resources/static").getPath();
    private final Path root = Paths.get(path);
    
    
    public Event ajouterEvent(Event event,MultipartFile file) {

        System.out.println(root.toString());
      
        
        try {
            Files.createDirectories(this.root);
          } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
          }

          try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            event.setImage(file.getOriginalFilename());
          } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
              throw new RuntimeException("A file of that name already exists.");
            }
      
            throw new RuntimeException(e.getMessage());
          }



        return eventRepository.save(event);
    }

    public List<Event> getEvents()
    {
        return eventRepository.findAll();
    }
    public List<Event> getEventsByUser(int userid)
    {
        return eventRepository.findByUserid(userid);
    }

    public boolean supprimerEvent(Event event)
    {
        if(eventRepository.findById((int)event.getID()).isPresent())
        {
            eventRepository.delete(event);
            return true;
        }else
        {
            return false;
        }

    }

    public Event modifierEvent(Event event,MultipartFile file)
    {
        if(eventRepository.findById((int)event.getID()).isPresent())
        {
            return eventRepository.save(event);
        }else
        {
            if(file != null)
            {
                try {
                    Files.createDirectories(this.root);
                  } catch (IOException e) {
                    throw new RuntimeException("Could not initialize folder for upload!");
                  }
        
                  try {
                    Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
                    event.setImage(file.getOriginalFilename());
                  } catch (Exception e) {
                    if (e instanceof FileAlreadyExistsException) {
                      throw new RuntimeException("A file of that name already exists.");
                    }
              
                    throw new RuntimeException(e.getMessage());
                  }
        
            }
            
            return ajouterEvent(event,file);
        }
    }

    public Optional<Event> searchEvent(int eventId)
    {
        return Optional.ofNullable(eventRepository.findById(eventId).get());
    }

    public boolean isUserParticipant(int eventid,long userid)
    {
        Optional<Event> e = eventRepository.findById(eventid);
        if(e.isPresent())
        {
            for (User u : e.get().getUsers()) {

                if(u.getId()==userid)
                {
                    return true;
                }
                
            }
            return false;
        }
        return false;
    }

    public boolean participateEvent(int eventId,long user_id)
    {
        Optional<Event> e = eventRepository.findById(eventId);
        if(e.isPresent())
        {

            if(e.get().getNbPlaces()-e.get().getUsers().size()>0)
            {
                Optional<User> u = userRepository.findById(user_id);
                if(u.isPresent())
                {
                    e.get().getUsers().add(u.get());
                    eventRepository.save(e.get());
                    userRepository.save(u.get());
                    return true;

                }else
                {

                    return false;
                }
            }else {
                return false;
            }

        }else
        {
            return false;
        }
    }

    public boolean revokeParticipateEvent(int eventId,long user_id)
    {
        Optional<Event> e = eventRepository.findById(eventId);
        if(e.isPresent())
        {
            Optional<User> u = userRepository.findById(user_id);
            if(u.isPresent())
            {
                e.get().getUsers().remove(u.get());
                eventRepository.save(e.get());
                userRepository.save(u.get());
                return true;

            }else
            {
                return false;
            }
        }else
        {
            return false;
        }
    }

// Fonctionnalités avancées :
    public float revenueEvent(int eventId)
    {
        Optional<Event> e = eventRepository.findById(eventId);
        if(e.isPresent())
        {
            return e.get().getPrix()*e.get().getUsers().size();
        }else
        {
            return 0;
        }

    }

    public int nombreDePlacesDisponible(int event_id)
    {
        Optional<Event> e = eventRepository.findById(event_id);
        if(e.isPresent())
        {
            if(e.get().getNbPlaces()-e.get().getUsers().size()<0)
            {
                return 0;
            }else {
                return e.get().getNbPlaces()-e.get().getUsers().size();
            }
        }else
        {
            return 0;
        }

    }

    public int numberOfParticipantsEvent(int event_id)
    {
        Optional<Event> e = eventRepository.findById(event_id);
        if(e.isPresent())
        {
            return e.get().getUsers().size();
        }else
        {
            return 0;
        }
    }

    public int numberOfParticipantsEvents()
    {
        List<Event> list = eventRepository.findAll();

        int participants = 0;

        for (Event event : list) {
           
                participants+=event.getUsers().size();
            

        }
        return participants;
    }

    public float revenueEvents()
    {
        List<Event> list = eventRepository.findAll();
        float sum = 0;
        for (Event event : list) {
            
                sum+=(event.getPrix()*event.getUsers().size());
            
        }

        return sum;
    }


}

package tn.esprit.pidev.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pidev.Entities.Composition;
import tn.esprit.pidev.Entities.Product;
import tn.esprit.pidev.Services.ICompositionService;

@RestController
@RequestMapping("/api/compositions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CompositionController {

    @Autowired
    private ICompositionService compositionService;

    @GetMapping
    public List<Composition> getAllCompositions() {
        return compositionService.getAllCompositions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Composition> getCompositionById(@PathVariable long id) {
        Composition composition = compositionService.getCompositionById(id);

        if (composition != null) {
            return ResponseEntity.ok(composition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Composition createComposition(@RequestBody Composition composition) {
        return compositionService.saveComposition(composition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Composition> updateComposition(@PathVariable long id, @RequestBody Composition composition) {
        Composition updatedComposition = compositionService.updateComposition(id, composition);

        if (updatedComposition != null) {
            return ResponseEntity.ok(updatedComposition);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompositionById(@PathVariable long id,@RequestBody Product product) {
        compositionService.deleteCompositionById(id,product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

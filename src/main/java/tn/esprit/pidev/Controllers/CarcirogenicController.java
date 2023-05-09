package tn.esprit.pidev.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.pidev.Entities.Carcirogenic;
import tn.esprit.pidev.Services.CarcirogenicService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carcirogenics")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarcirogenicController {
    @Autowired
    private CarcirogenicService carcirogenicService;

    @PostMapping("/")
    public ResponseEntity<Carcirogenic> createCarcirogenic(@RequestBody Carcirogenic carcirogenic) {
        Carcirogenic createdCarcirogenic = carcirogenicService.createCarcirogenic(carcirogenic);
        return new ResponseEntity<>(createdCarcirogenic, HttpStatus.CREATED);
    }
    @PostMapping("/all")
    public ResponseEntity<List<Carcirogenic>> createCarcirogenics(@RequestBody List<Carcirogenic> carcirogenic) {
        List<Carcirogenic> createdCarcirogenic = carcirogenicService.createCarcirogenics(carcirogenic);
        return new ResponseEntity<>(createdCarcirogenic, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Carcirogenic>> getAllCarcirogenics() {
        List<Carcirogenic> carcirogenics = carcirogenicService.getAllCarcirogenics();
        return new ResponseEntity<>(carcirogenics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carcirogenic> getCarcirogenicById(@PathVariable("id") long id) {
        Optional<Carcirogenic> carcirogenic = carcirogenicService.getCarcirogenicById(id);
        return carcirogenic.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCarcirogenic(@PathVariable("id") long id) {
        carcirogenicService.deleteCarcirogenic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carcirogenic> updateCarcirogenic(@PathVariable("id") long id, @RequestBody Carcirogenic carcirogenic) {
        Optional<Carcirogenic> carcirogenicData = carcirogenicService.getCarcirogenicById(id);

        if (carcirogenicData.isPresent()) {
            Carcirogenic updatedCarcirogenic = carcirogenicData.get();
            updatedCarcirogenic.setName(carcirogenic.getName());
            updatedCarcirogenic.setToxicityScore(carcirogenic.getToxicityScore());
            return new ResponseEntity<>(carcirogenicService.createCarcirogenic(updatedCarcirogenic), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

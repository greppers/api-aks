package tn.esprit.pidev.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.pidev.Entities.Carcirogenic;
import tn.esprit.pidev.Repositories.CarcirogenicRepository;

@Service
public class CarcirogenicService {
    @Autowired
    CarcirogenicRepository carcirogenicRepository;
    public Carcirogenic createCarcirogenic(Carcirogenic carcirogenic) {
        return carcirogenicRepository.save(carcirogenic);
    }

    public List<Carcirogenic> getAllCarcirogenics() {
        return carcirogenicRepository.findAll();
    }

    public Optional<Carcirogenic> getCarcirogenicById(long id) {
        return carcirogenicRepository.findById(id);
    }

    // public Optional<Carcirogenic> getCarcirogenicByName(String name) {
    //     return carcirogenicRepository.findByName(name);
    // }
    public List<Carcirogenic> createCarcirogenics(List<Carcirogenic> carcirogenics){
        return carcirogenicRepository.saveAll(carcirogenics);
    }

    public void deleteCarcirogenic(long id) {
        carcirogenicRepository.deleteById(id);
    }

    public void updateCarcirogenic(Carcirogenic carcirogenic) {
        carcirogenicRepository.save(carcirogenic);
    }
}
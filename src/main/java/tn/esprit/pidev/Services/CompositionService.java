package tn.esprit.pidev.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.pidev.Entities.Composition;
import tn.esprit.pidev.Entities.Product;
import tn.esprit.pidev.Repositories.CompositionRepository;

@Service
public class CompositionService implements ICompositionService {
    @Autowired
    private CompositionRepository compositionRepository;

    @Override
    public Composition saveComposition(Composition composition) {
        return compositionRepository.save(composition);
    }

    @Override
    public List<Composition> getAllCompositions() {
        return compositionRepository.findAll();
    }

    @Override
    public Composition getCompositionById(long id) {
        return compositionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCompositionById(long id, Product product) {
        compositionRepository.deleteById(id);
    }

    @Override
    public Composition updateComposition(long id, Composition composition) {
        Composition existingComposition = compositionRepository.findById(id).orElse(null);
        if (existingComposition != null) {
            existingComposition.setName(composition.getName());
            existingComposition.setDescription(composition.getDescription());
            existingComposition.setBioScore(composition.getBioScore());
            return compositionRepository.save(existingComposition);
        } else {
            return null;
        }
    }

}

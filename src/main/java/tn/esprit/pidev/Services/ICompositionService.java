package tn.esprit.pidev.Services;

import java.util.List;

import tn.esprit.pidev.Entities.Composition;
import tn.esprit.pidev.Entities.Product;

public interface ICompositionService {

    Composition saveComposition(Composition composition);

    List<Composition> getAllCompositions();

    Composition getCompositionById(long id);

    void deleteCompositionById(long id,Product product);

    Composition updateComposition(long id, Composition composition);

}
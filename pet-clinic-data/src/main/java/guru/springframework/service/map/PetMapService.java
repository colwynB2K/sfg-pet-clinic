package guru.springframework.service.map;

import guru.springframework.model.Pet;
import guru.springframework.service.CrudService;

import java.util.Set;

public class PetMapService extends AbstractMapService<Pet, Long> implements CrudService<Pet, Long> {
    @Override
    public Set<Pet> findAll() {
        return super.findAll();         // Call the method of the AbstractMapService with the correct Type
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pet Pet) {
        super.delete(Pet);
    }

    @Override
    public Pet save(Pet Pet) {
        return super.save(Pet.getId(), Pet);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}

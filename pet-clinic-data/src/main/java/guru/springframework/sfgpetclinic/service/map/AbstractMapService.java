package guru.springframework.sfgpetclinic.service.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AbstractMapService<T, ID> {

    protected Map<ID, T> map = new HashMap<>();

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    T save(ID id, T object) { // Because we are saving the object into a HashMap we need a key to save the value under.
                              // However, because we are passing in a generic object we can just do object.getId() as we don't know if the object will even have this method
                              // So we add the id as a method paramater and let the concrete class provide the id value depending on its actual properties
        map.put(id, object);

        return object;
    }

    void deleteById(ID id) {
        map.remove(id);
    }

    void delete(T object) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(object)); // Removes all of the elements of this collection that satisfy the given predicate, but this requires our entities to implement an equals method!
    }

}

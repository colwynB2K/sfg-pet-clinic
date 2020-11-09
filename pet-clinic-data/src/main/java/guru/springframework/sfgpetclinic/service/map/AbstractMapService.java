package guru.springframework.sfgpetclinic.service.map;

import guru.springframework.sfgpetclinic.dto.BaseDTO;

import java.util.*;

public class AbstractMapService<T extends BaseDTO, ID extends Long> {

    protected Map<Long, T> map = new HashMap<>();

    Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    T save(T object) throws RuntimeException {
        if (object != null) {
            if (object.getId() == null) {
                object.setId(getNextId());
            }

            map.put(object.getId(), object);
        } else {
            throw new RuntimeException("Object can't be null!");
        }

        return object;
    }

    void deleteById(ID id) {
        map.remove(id);
    }

    void delete(T object) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(object)); // Removes all of the elements of this collection that satisfy the given predicate, but this requires our entities to implement an equals method!
    }

    private Long getNextId() {
        return map.isEmpty() ? 1L : Collections.max(map.keySet()) + 1;
    }

}

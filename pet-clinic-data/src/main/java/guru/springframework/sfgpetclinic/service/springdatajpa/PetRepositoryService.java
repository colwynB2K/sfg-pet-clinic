package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.dto.PetDTO;
import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.mapper.PetMapper;
import guru.springframework.sfgpetclinic.repository.PetRepository;
import guru.springframework.sfgpetclinic.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("SpringDataJPA")
@Slf4j
public class PetRepositoryService implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    @Autowired
    public PetRepositoryService(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    @Override
    public Set<PetDTO> findAll() {

        Set<PetDTO> pets = new HashSet<>();
        petRepository.findAll().forEach(pet -> pets.add(petMapper.toDTO(pet)));

        return pets;
    }

    @Override
    public PetDTO findById(Long id) {
        return petMapper.toDTO(petRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No Pet found for id '" + id + "'")));
    }

    @Override
    public PetDTO save(PetDTO pet) {
        return petMapper.toDTO(petRepository.save(petMapper.toEntity(pet)));
    }

    @Override
    public void delete(PetDTO pet) {
        petRepository.delete(petMapper.toEntity(pet));
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}

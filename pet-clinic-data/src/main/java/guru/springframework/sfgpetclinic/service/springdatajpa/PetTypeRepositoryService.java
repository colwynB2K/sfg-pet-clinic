package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.dto.PetTypeDTO;
import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.mapper.PetTypeMapper;
import guru.springframework.sfgpetclinic.repository.PetTypeRepository;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("SpringDataJPA")
@Slf4j
public class PetTypeRepositoryService implements PetTypeService {

    private final PetTypeRepository petTypeRepository;
    private final PetTypeMapper petTypeMapper;

    @Autowired
    public PetTypeRepositoryService(PetTypeRepository petTypeRepository, PetTypeMapper petTypeMapper) {
        this.petTypeRepository = petTypeRepository;
        this.petTypeMapper = petTypeMapper;
    }

    @Override
    public Set<PetTypeDTO> findAll() {
        Set<PetTypeDTO> petTypes = new HashSet<>();
        petTypeRepository.findAll().forEach(petType -> petTypes.add(petTypeMapper.toDTO(petType)));

        return petTypes;
    }

    @Override
    public PetTypeDTO findById(Long id) {
        return petTypeMapper.toDTO(petTypeRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No PetType found for id '"+ id + "'")));
    }

    @Override
    public PetTypeDTO save(PetTypeDTO petType) {
        return petTypeMapper.toDTO(petTypeRepository.save(petTypeMapper.toEntity(petType)));
    }

    @Override
    public void delete(PetTypeDTO petType) {
        petTypeRepository.delete(petTypeMapper.toEntity(petType));
    }

    @Override
    public void deleteById(Long id) {
        petTypeRepository.deleteById(id);
    }
}

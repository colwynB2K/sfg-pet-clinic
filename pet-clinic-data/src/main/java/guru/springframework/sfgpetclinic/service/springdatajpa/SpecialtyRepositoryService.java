package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.dto.SpecialtyDTO;
import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.mapper.SpecialtyMapper;
import guru.springframework.sfgpetclinic.repository.SpecialtyRepository;
import guru.springframework.sfgpetclinic.service.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("SpringDataJPA")
@Slf4j
public class SpecialtyRepositoryService implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    @Autowired
    public SpecialtyRepositoryService(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    @Override
    public Set<SpecialtyDTO> findAll() {
        Set<SpecialtyDTO> specialties = new HashSet<>();
        specialtyRepository.findAll().forEach(specialty -> specialties.add(specialtyMapper.toDTO(specialty)));

        return specialties;
    }

    @Override
    public SpecialtyDTO findById(Long id) {
        return specialtyMapper.toDTO(specialtyRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No Specialty found for id '" + id +  "'")));
    }

    @Override
    public SpecialtyDTO save(SpecialtyDTO specialty) {
        return specialtyMapper.toDTO(specialtyRepository.save(specialtyMapper.toEntity(specialty)));
    }

    @Override
    public void delete(SpecialtyDTO specialty) {
        specialtyRepository.delete(specialtyMapper.toEntity(specialty));
    }

    @Override
    public void deleteById(Long id) {
        specialtyRepository.deleteById(id);
    }
}

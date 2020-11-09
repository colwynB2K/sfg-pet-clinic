package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.dto.VetDTO;
import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.mapper.VetMapper;
import guru.springframework.sfgpetclinic.repository.VetRepository;
import guru.springframework.sfgpetclinic.service.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("SpringDataJPA")                                                                                       // Avoid conflict with the OwnerMapService implementation bean of OwnerService
@Slf4j
public class VetRepositoryService implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    @Autowired
    public VetRepositoryService(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    @Override
    public Set<VetDTO> findAll() {
        Set<VetDTO> vets = new HashSet<>();
        vetRepository.findAll().forEach(vet -> vets.add(vetMapper.toDTO(vet)));

        return vets;
    }

    @Override
    public VetDTO findById(Long id) {
        return vetMapper.toDTO(vetRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No Vet found for id '" + id + "'")));
    }

    @Override
    public VetDTO save(VetDTO vet) {
        return vetMapper.toDTO(vetRepository.save(vetMapper.toEntity(vet)));
    }

    @Override
    public void delete(VetDTO vet) {
        vetRepository.delete(vetMapper.toEntity(vet));
    }

    @Override
    public void deleteById(Long id) {
        vetRepository.deleteById(id);
    }
}

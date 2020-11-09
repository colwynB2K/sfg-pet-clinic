package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.dto.OwnerDTO;
import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.mapper.OwnerMapper;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repository.OwnerRepository;
import guru.springframework.sfgpetclinic.repository.PetRepository;
import guru.springframework.sfgpetclinic.repository.PetTypeRepository;
import guru.springframework.sfgpetclinic.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Profile("SpringDataJPA")                                                                                       // Avoid conflict with the OwnerMapService implementation bean of OwnerService
@Slf4j
public class OwnerRepositoryService implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final OwnerMapper ownerMapper;

    @Autowired
    public OwnerRepositoryService(OwnerRepository ownerRepository, PetRepository petRepository,
                                  PetTypeRepository petTypeRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public OwnerDTO findByLastName(String lastName) {
        return ownerMapper.toDTO(ownerRepository.findByLastName(lastName));
    }

    @Override
    public Set<OwnerDTO> findAllByLastNameLike(String lastName) {
        return ownerMapper.toDTOSet(ownerRepository.findAllByLastNameLike("%" + lastName + "%"));     // Surround the provided lastName value with SQL wildcards
    }

    @Override
    public Set<OwnerDTO> findAll() {
        Set<OwnerDTO> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owner -> {
                                                OwnerDTO ownerDTO = ownerMapper.toDTO(owner);
                                                owners.add(ownerDTO);
        });

        return owners;
    }

    @Override
    public OwnerDTO findById(Long id) {
        return ownerMapper.toDTO(ownerRepository.findById(id).orElseThrow(()
                -> new ObjectNotFoundException("No owner found for id'" + id + "'")));
    }

    @Override
    @Transactional
    public OwnerDTO save(OwnerDTO owner) {
        Owner ownerToSave = ownerMapper.toEntity(owner);
        return ownerMapper.toDTO(ownerRepository.save(ownerToSave));
    }

    @Override
    public void delete(OwnerDTO owner) {
        ownerRepository.delete(ownerMapper.toEntity(owner));
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }
}

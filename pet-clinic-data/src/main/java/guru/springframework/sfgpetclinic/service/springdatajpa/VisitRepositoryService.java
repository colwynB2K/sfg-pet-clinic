package guru.springframework.sfgpetclinic.service.springdatajpa;

import guru.springframework.sfgpetclinic.dto.VisitDTO;
import guru.springframework.sfgpetclinic.exception.ObjectNotFoundException;
import guru.springframework.sfgpetclinic.mapper.VisitMapper;
import guru.springframework.sfgpetclinic.repository.VisitRepository;
import guru.springframework.sfgpetclinic.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("SpringDataJPA")
@Slf4j
public class VisitRepositoryService implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Autowired
    public VisitRepositoryService(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    @Override
    public Set<VisitDTO> findAll() {
        Set<VisitDTO> visits = new HashSet<>();
        visitRepository.findAll().forEach(visit -> visits.add(visitMapper.toDTO(visit)));

        return visits;
    }

    @Override
    public VisitDTO findById(Long id) {
        return visitMapper.toDTO(visitRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("")));
    }

    @Override
    public VisitDTO save(VisitDTO visit) {
        return visitMapper.toDTO(visitRepository.save(visitMapper.toEntity(visit)));
    }

    @Override
    public void delete(VisitDTO visit) {
        visitRepository.delete(visitMapper.toEntity(visit));
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }
}

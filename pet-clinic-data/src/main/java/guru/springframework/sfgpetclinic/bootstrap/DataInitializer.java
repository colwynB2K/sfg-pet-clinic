package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final OwnerRepository ownerRepository;
    private final PetTypeRepository petTypeRepository;
    private final SpecialtyRepository specialtyRepository;
    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public DataInitializer(OwnerRepository ownerRepository, PetTypeRepository petTypeRepository,
                           SpecialtyRepository specialtyRepository, VetRepository vetRepository,
                           VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.petTypeRepository = petTypeRepository;
        this.specialtyRepository = specialtyRepository;
        this.vetRepository = vetRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!petTypeRepository.findAll().iterator().hasNext()) {                         // Make sure that the data is only initialized in case there is no data
            loadData();
        }
    }

    private void loadData() {
        PetType cat = new PetType();
        cat.setName("cat");

        PetType savedCatPetType = petTypeRepository.save(cat);

        PetType dog = new PetType();
        dog.setName("dog");

        PetType savedDogPetType = petTypeRepository.save(dog);

        log.info("Loaded Pet Types...");

        Owner mike = Owner.builder()
                .firstName("Michael")
                .lastName("Weston")
                .address("123 Brickerel")
                .city("Miami")
                .telephone("1231231234")
                .build();

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setName("Rosco");
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setOwner(mike);

        mike.getPets().add(mikesPet);           // Don't forget the reverse direction!

        ownerRepository.save(mike);

        Owner fiona = Owner.builder()
                .firstName("Fiona")
                .lastName("Glenanne")
                .address("123 Brickerel")
                .city("Miami")
                .telephone("555123456")
                .build();

        Pet fionasPet = new Pet();
        fionasPet.setPetType(savedCatPetType);
        fionasPet.setName("Toulouse");
        fionasPet.setBirthDate(LocalDate.now());
        fionasPet.setOwner(fiona);

        fiona.getPets().add(fionasPet);           // Don't forget the reverse direction!

        ownerRepository.save(fiona);

        log.info("Loaded Owners...");

        Specialty radiology = new Specialty();
        radiology.setName("Radiology");
        Specialty savedRadiologySpecialty = specialtyRepository.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setName("Surgery");
        Specialty savedSurgerySpecialty = specialtyRepository.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setName("Dentistry");
        Specialty savedDentistrySpecialty = specialtyRepository.save(dentistry);

        log.info("Loaded Specialties...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        vet1.getSpecialties().add(savedRadiologySpecialty);

        vetRepository.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vet2.getSpecialties().add(savedSurgerySpecialty);

        vetRepository.save(vet2);

        log.info("Loaded Vets...");

        Visit mikesDogVisit = new Visit();
        mikesDogVisit.setDescription("Vasectomy");
        mikesDogVisit.setDate(LocalDate.now());
        mikesDogVisit.setPet(mikesPet);

        visitRepository.save(mikesDogVisit);

        Visit fionasCatVisit = new Visit();
        fionasCatVisit.setDescription("Sneezy Kitty");
        fionasCatVisit.setDate(LocalDate.now());
        fionasCatVisit.setPet(fionasPet);

        visitRepository.save(fionasCatVisit);

        log.info("Loaded Visits...");
    }
}

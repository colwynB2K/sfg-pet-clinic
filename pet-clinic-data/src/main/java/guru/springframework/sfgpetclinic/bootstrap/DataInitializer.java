package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VetService vetService;
    private final VisitService visitService;

    @Autowired
    public DataInitializer(
            OwnerService ownerService,
            PetTypeService petTypeService,
            SpecialtyService specialtyService,
            VetService vetService,
            VisitService visitService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.vetService = vetService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (petTypeService.findAll().isEmpty()) {                         // Make sure that the data is only initialized in case there is no data
            loadData();
        }
    }

    private void loadData() {
        PetType cat = new PetType();
        cat.setName("cat");

        PetType savedCatPetType = petTypeService.save(cat);

        PetType dog = new PetType();
        dog.setName("dog");

        PetType savedDogPetType = petTypeService.save(dog);

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

        ownerService.save(mike);

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

        ownerService.save(fiona);

        log.info("Loaded Owners...");

        Specialty radiology = new Specialty();
        radiology.setName("Radiology");
        Specialty savedRadiologySpecialty = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setName("Surgery");
        Specialty savedSurgerySpecialty = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setName("Dentistry");
        Specialty savedDentistrySpecialty = specialtyService.save(dentistry);

        log.info("Loaded Specialties...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        vet1.getSpecialties().add(savedRadiologySpecialty);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vet2.getSpecialties().add(savedSurgerySpecialty);

        vetService.save(vet2);

        log.info("Loaded Vets...");

        Visit mikesDogVisit = new Visit();
        mikesDogVisit.setDescription("Vasectomy");
        mikesDogVisit.setDate(LocalDate.now());
        mikesDogVisit.setPet(mikesPet);

        visitService.save(mikesDogVisit);

        Visit fionasCatVisit = new Visit();
        fionasCatVisit.setDescription("Sneezy Kitty");
        fionasCatVisit.setDate(LocalDate.now());
        fionasCatVisit.setPet(fionasPet);

        visitService.save(fionasCatVisit);

        log.info("Loaded Visits...");
    }
}

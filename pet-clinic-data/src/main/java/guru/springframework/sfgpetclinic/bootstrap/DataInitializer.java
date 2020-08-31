package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.service.OwnerService;
import guru.springframework.sfgpetclinic.service.PetTypeService;
import guru.springframework.sfgpetclinic.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final VetService vetService;

    @Autowired
    public DataInitializer(
            OwnerService ownerService,
            PetTypeService petTypeService,
            VetService vetService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType cat = new PetType();
        cat.setName("cat");

        PetType savedCatPetType = petTypeService.save(cat);

        PetType dog = new PetType();
        dog.setName("dog");

        PetType savedDogPetType = petTypeService.save(dog);

        System.out.println("Loaded Pet Types...");

        Owner mike = new Owner();
        mike.setFirstName("Michael");
        mike.setLastName("Weston");
        mike.setAddress("123 Brickerel");
        mike.setCity("Miami");
        mike.setTelephone("1231231234");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setName("Rosco");
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setOwner(mike);

        mike.getPets().add(mikesPet);           // Don't forget the reverse direction!

        ownerService.save(mike);

        Owner fiona = new Owner();
        fiona.setFirstName("Fiona");
        fiona.setLastName("Glenanne");
        fiona.setAddress("123 Brickerel");
        fiona.setCity("Miami");
        fiona.setTelephone("555123456");

        Pet fionasPet = new Pet();
        fionasPet.setPetType(savedCatPetType);
        fionasPet.setName("Toulouse");
        fionasPet.setBirthDate(LocalDate.now());
        fionasPet.setOwner(fiona);

        fiona.getPets().add(fionasPet);           // Don't forget the reverse direction!

        ownerService.save(fiona);

        System.out.println("Loaded Owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }

}

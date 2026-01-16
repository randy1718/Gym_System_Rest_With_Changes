package com.gym.system;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.gym.system.config.AppConfig;
import com.gym.system.model.Trainee;
import com.gym.system.model.Trainer;
import com.gym.system.model.Training;
import com.gym.system.model.TrainingType;
import com.gym.system.service.GymServices;
import com.gym.system.util.PasswordGenerator;

import jakarta.transaction.Transactional;

/**
 * Unit test for simple App.
 */
@SpringJUnitConfig(AppConfig.class)
@Transactional
public class AppTest {

    @Autowired
    private GymServices facade;

    @Test
    @DisplayName("Database should be initialized with the sample data from the JSON file")
    public void DatabaseInitializedCorreclty() {
        var trainees = facade.findAllTrainees();
        var trainers = facade.findAllTrainers();
        var trainings = facade.findAllTrainings();

        assertAll("Database should be initialized with sample data",
                () -> assertFalse(trainees.isEmpty(), "Trainees table is empty"),
                () -> assertFalse(trainers.isEmpty(), "Trainers table is empty"),
                () -> assertFalse(trainings.isEmpty(), "Trainings table is empty"));
    }

    @Test
    @DisplayName("Facade should create Trainee correctly with username and password")
    void shouldCreateTraineeCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Lucas");
        trainee.setLastName("Diaz");

        facade.createTrainee(trainee);

        var savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getFirstName().equals("Lucas"))
                .findFirst();

        assertAll("New trainee should be created correctly",
                () -> assertTrue(savedTrainee.isPresent(), "Trainee was not saved"),
                () -> assertEquals("Lucas.Diaz", savedTrainee.get().getUsername()),
                () -> assertTrue(savedTrainee.get().getIsActive(), "IsActive should be true"),
                () -> assertNotNull(savedTrainee.get().getId(), "Id should be generated"),
                () -> assertNotNull(savedTrainee.get().getPassword(), "Password should be generated"),
                () -> assertEquals(10, savedTrainee.get().getPassword().length(),
                        "Password length must be 10"));
    }

    @Test
    @DisplayName("Facade should create Trainer correctly with username and password")
    void shouldCreateTrainerCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Fernando");
        trainer.setLastName("Rosales");

        facade.createTrainer(trainer);
        // facade.createTrainer(trainer);

        var savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getFirstName().equals("Fernando"))
                .findFirst();

        assertAll("New trainer should be created correctly",
                () -> assertTrue(savedTrainer.isPresent(), "Trainer was not saved"),
                () -> assertEquals("Fernando.Rosales", savedTrainer.get().getUsername()),
                () -> assertTrue(savedTrainer.get().getIsActive(), "IsActive should be true"),
                () -> assertNotNull(savedTrainer.get().getId(), "Id should be generated"),
                () -> assertNotNull(savedTrainer.get().getPassword(), "Password should be generated"),
                () -> assertEquals(10, savedTrainer.get().getPassword().length(),
                        "Password length must be 10"));
    }

    @Test
    @DisplayName("Facade should create Training correctly")
    void shouldCreateTrainingCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Fernando");
        trainer.setLastName("Rosales");
        facade.createTrainer(trainer);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Lucas");
        trainee.setLastName("Diaz");
        facade.createTrainee(trainee);

        TrainingType type = new TrainingType();
        type.setName("Volumen");
        facade.createTrainingType(type);

        var savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getFirstName().equals("Fernando"))
                .findFirst();

        var savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getFirstName().equals("Lucas"))
                .findFirst();

        Training training = new Training();
        training.setTrainer(savedTrainer.get());
        training.setTrainee(savedTrainee.get());
        training.setTrainingName("training cardio - Lucas");
        training.setDate("2025-11-26 10:00:00");
        training.setDuration(60);
        training.setTrainingType(facade.findTrainingTypeByName("Cardio").get());

        System.out.println("Trainer credentials: " +
                savedTrainer.get().getUsername() + " / " + savedTrainer.get().getPassword());
        facade.createTraining(training, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());

        var savedTraining = facade.findAllTrainings()
                .stream()
                .filter(t -> t.getTrainingName().equals("training cardio - Lucas"))
                .findFirst();

        assertAll("New training should be created correctly",
                () -> assertTrue(savedTraining.isPresent(), "Training was not saved"),
                () -> assertEquals(60, savedTraining.get().getDuration()),
                () -> assertEquals("2025-11-26 10:00:00", savedTraining.get().getDate()),
                () -> assertEquals("Cardio", savedTraining.get().getTrainingType().getName()));
    }

    @Test
    @DisplayName("Username generator should append serial number when duplicates exist")
    void shouldGenerateUniqueUsernames() {

        Trainer t1 = new Trainer();
        t1.setFirstName("Maria");
        t1.setLastName("Diaz");

        Trainer t2 = new Trainer();
        t2.setFirstName("Maria");
        t2.setLastName("Diaz");

        Trainer t3 = new Trainer();
        t3.setFirstName("Maria");
        t3.setLastName("Diaz");

        facade.createTrainer(t1);
        facade.createTrainer(t2);
        facade.createTrainer(t3);

        assertAll("Usernames must be unique",
                () -> assertEquals("Maria.Diaz", t1.getUsername()),
                () -> assertEquals("Maria.Diaz1", t2.getUsername()),
                () -> assertEquals("Maria.Diaz2", t3.getUsername()));
    }

    @Test
    @DisplayName("PasswordGenerator should create 10-char random passwords")
    void shouldGenerateValidPassword() {

        String p1 = PasswordGenerator.generate();
        String p2 = PasswordGenerator.generate();

        assertAll("Password properties",
                () -> assertEquals(10, p1.length()),
                () -> assertEquals(10, p2.length()),
                () -> assertNotEquals(p1, p2, "Two generated passwords should be different"));
    }

    @Test
    @DisplayName("Delete a trainee should remove the record from the Trainees table")
    void deleteTrainee_ShouldRemoveExistingTrainee() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Santiago");
        trainer.setLastName("Flores");

        facade.createTrainer(trainer);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Isa");
        trainee.setLastName("Romero");

        facade.createTrainee(trainee);

        TrainingType type = new TrainingType();
        type.setName("Pilates");
        facade.createTrainingType(type);

        var savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Isa.Romero"))
                .findFirst();

        var savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Santiago.Flores"))
                .findFirst();

        Training training = new Training();
        training.setTrainer(savedTrainer.get());
        training.setTrainee(savedTrainee.get());
        training.setDate("2025-10-29 20:00:00");
        training.setDuration(120);
        training.setTrainingType(type);
        training.setTrainingName("training Isa - Pilates");

        facade.createTraining(training, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());

        boolean trainingExistsBefore = facade.findAllTrainings()
                .stream()
                .anyMatch(t -> t.getTrainingName().equals("training Isa - Pilates"));

        assertTrue(trainingExistsBefore, "Training should exist before deleting trainee");

        facade.deleteTrainee(savedTrainee.get().getUsername(), savedTrainee.get().getPassword());

        boolean traineeExistsAfter = facade.findAllTrainees()
                .stream()
                .anyMatch(t -> t.getUsername().equals("Isa.Romero"));

        boolean trainingExistsAfter = facade.findAllTrainings()
                .stream()
                .anyMatch(t -> t.getTrainingName().equals("training Isa - Pilates"));

        assertAll(
                () -> assertFalse(traineeExistsAfter, "Trainee should be deleted"),
                () -> assertFalse(trainingExistsAfter, "Trainings should be deleted via cascade"));
    }

    @Test
    @DisplayName("Facade should retrieve the trainee by using their username.")
    void shouldFindTraineeCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Swift");

        facade.createTrainee(trainee);

        var savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("John.Swift"))
                .findFirst();

        Optional<Trainee> foundTrainee = facade.findTraineeByUsername("John.Swift", savedTrainee.get().getPassword());

        assertAll("Trainee should be found and have correct data",
                () -> assertTrue(foundTrainee.isPresent(), "Trainee was not found"),
                () -> assertEquals("John.Swift", foundTrainee.get().getUsername(), "Username mismatch"),
                () -> assertEquals("John", foundTrainee.get().getFirstName(), "First name mismatch"),
                () -> assertEquals("Swift", foundTrainee.get().getLastName(), "Last name mismatch"));
    }

    @Test
    @DisplayName("Facade should retrieve the trainer by using their username.")
    void shouldFindTrainerCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Alejandro");
        trainer.setLastName("Pereira");

        facade.createTrainer(trainer);

        var savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Alejandro.Pereira"))
                .findFirst();

        Optional<Trainer> foundTrainer = facade.findTrainerByUsername("Alejandro.Pereira",
                savedTrainer.get().getPassword());

        assertAll("Trainer should be found and have correct data",
                () -> assertTrue(foundTrainer.isPresent(), "Trainer was not found"),
                () -> assertEquals("Alejandro.Pereira", foundTrainer.get().getUsername(), "Username mismatch"),
                () -> assertEquals("Alejandro", foundTrainer.get().getFirstName(), "First name mismatch"),
                () -> assertEquals("Pereira", foundTrainer.get().getLastName(), "Last name mismatch"));
    }

    @Test
    @DisplayName("Facade should retrieve the training by using an id.")
    void shouldFindTrainingCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Luis");
        trainer.setLastName("Gomez");

        facade.createTrainer(trainer);

        Trainee trainee = new Trainee();
        trainee.setFirstName("Daniela");
        trainee.setLastName("Suarez");

        facade.createTrainee(trainee);

        TrainingType type = new TrainingType();
        type.setName("Definicion");
        facade.createTrainingType(type);

        var savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Daniela.Suarez"))
                .findFirst();

        var savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Luis.Gomez"))
                .findFirst();

        Training training = new Training();
        training.setTrainer(savedTrainer.get());
        training.setTrainee(savedTrainee.get());
        training.setDate("2025-11-29 20:00:00");
        training.setDuration(120);
        training.setTrainingType(type);
        training.setTrainingName("training Daniela - Definicion");

        facade.createTraining(training, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());

        Optional<Training> foundTraining = facade.findTrainingByTraineeUsernameAndDate(savedTrainee.get().getUsername(),
                savedTrainee.get().getPassword(), "2025-11-29 20:00:00");

        assertAll("Training should be found and have correct data",
                () -> assertTrue(foundTraining.isPresent(), "Training was not found"),
                () -> assertEquals("Daniela.Suarez", foundTraining.get().getTrainee().getUsername(),
                        "trainee ID mismatch"),
                () -> assertEquals("Luis.Gomez", foundTraining.get().getTrainer().getUsername(), "trainer ID mismatch"),
                () -> assertEquals("Definicion", foundTraining.get().getTrainingType().getName(),
                        "training type mismatch"),
                () -> assertEquals("2025-11-29 20:00:00", foundTraining.get().getDate(), "date mismatch"));
    }

    @Test
    @DisplayName("Facade should update Trainee correctly")
    void shouldUpdateTraineeCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Lisa");
        trainee.setLastName("Paz");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Lisa.Paz"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        Trainee updated = savedTrainee.get();
        updated.setAddress("street 8th 5 - 43");

        facade.updateTrainee(savedTrainee.get().getUsername(), savedTrainee.get().getPassword(), updated);

        Optional<Trainee> updatedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Lisa.Paz"))
                .findFirst();

        assertAll("Trainee should be updated correctly",
                () -> assertEquals("street 8th 5 - 43",
                        updatedTrainee.get().getAddress(),
                        "Address was not updated"),
                () -> assertEquals("Lisa",
                        updatedTrainee.get().getFirstName(),
                        "First name should remain unchanged"),
                () -> assertEquals("Paz",
                        updatedTrainee.get().getLastName(),
                        "Last name should remain unchanged"));
    }

    @Test
    @DisplayName("Facade should update Trainer correctly")
    void shouldUpdateTrainerCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Julian");
        trainer.setLastName("Sopo");

        facade.createTrainer(trainer);

        Optional<Trainer> savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Julian.Sopo"))
                .findFirst();

        assertTrue(savedTrainer.isPresent(), "Trainer was not saved");

        Trainer saved = savedTrainer.get();
        saved.setSpecialization("Cross-fit");

        facade.updateTrainer(savedTrainer.get().getUsername(), savedTrainer.get().getPassword(), saved);

        Optional<Trainer> updatedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Julian.Sopo"))
                .findFirst();

        assertAll("Trainer should be updated correctly",
                () -> assertEquals("Cross-fit",
                        updatedTrainer.get().getSpecialization(),
                        "Specialization was not updated"),
                () -> assertEquals("Julian",
                        updatedTrainer.get().getFirstName(),
                        "First name should remain unchanged"),
                () -> assertEquals("Sopo",
                        updatedTrainer.get().getLastName(),
                        "Last name should remain unchanged"));
    }

    @Test
    @DisplayName("Facade should change Trainee password correctly")
    void shouldTraineePasswordChangeCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Sofia");
        trainee.setLastName("Martinez");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Sofia.Martinez"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        String oldPassword = savedTrainee.get().getPassword();
        String newPassword = "NewPass123";

        facade.changeTraineePassword(
                savedTrainee.get().getUsername(),
                oldPassword,
                newPassword);

        Optional<Trainee> updatedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Sofia.Martinez"))
                .findFirst();

        assertAll("Trainee password should be changed correctly",
                () -> assertEquals(newPassword,
                        updatedTrainee.get().getPassword(),
                        "Password was not updated"),
                () -> assertNotEquals(oldPassword,
                        updatedTrainee.get().getPassword(),
                        "Password should be different from the old one"));
    }

    @Test
    @DisplayName("Facade should change Trainer password correctly")
    void shouldTrainerPasswordChangeCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Carlos");
        trainer.setLastName("Lopez");

        facade.createTrainer(trainer);

        Optional<Trainer> savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Carlos.Lopez"))
                .findFirst();

        assertTrue(savedTrainer.isPresent(), "Trainer was not saved");

        String oldPassword = savedTrainer.get().getPassword();
        String newPassword = "NewPass123";

        facade.changeTrainerPassword(
                savedTrainer.get().getUsername(),
                oldPassword,
                newPassword);

        Optional<Trainer> updatedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Carlos.Lopez"))
                .findFirst();

        assertAll("Trainer password should be changed correctly",
                () -> assertEquals(newPassword,
                        updatedTrainer.get().getPassword(),
                        "Password was not updated"),
                () -> assertNotEquals(oldPassword,
                        updatedTrainer.get().getPassword(),
                        "Password should be different from the old one"));
    }

    @Test
    @DisplayName("Facade should toggle trainee status correctly")
    void shouldToggleTraineeStatusCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Sofia");
        trainee.setLastName("Martinez");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Sofia.Martinez"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        facade.toggleTraineeStatus(
                savedTrainee.get().getUsername(),
                savedTrainee.get().getPassword());

        Optional<Trainee> updatedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Sofia.Martinez"))
                .findFirst();

        assertAll("Trainer status should be toggled correctly",
                () -> assertFalse(updatedTrainee.get().getIsActive(), "IsActive should be false"));
    }

    @Test
    @DisplayName("Facade should toggle trainer status correctly")
    void shouldToggleTrainerStatusCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Carlos");
        trainer.setLastName("Lopez");

        facade.createTrainer(trainer);

        Optional<Trainer> savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Carlos.Lopez"))
                .findFirst();

        assertTrue(savedTrainer.isPresent(), "Trainer was not saved");

        facade.toggleTrainerStatus(
                savedTrainer.get().getUsername(),
                savedTrainer.get().getPassword());

        Optional<Trainer> updatedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Carlos.Lopez"))
                .findFirst();

        assertAll("Trainer status should be toggled correctly",
                () -> assertFalse(updatedTrainer.get().getIsActive(), "IsActive should be false"));
    }

    @Test
    @DisplayName("Facade should authenticate Trainee correctly")
    void shouldAuthenticateTraineeCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Liz");
        trainee.setLastName("Vera");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Liz.Vera"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        Boolean isAuthentic = facade.authenticateTrainee(
                savedTrainee.get().getUsername(),
                savedTrainee.get().getPassword());

        assertTrue(isAuthentic, "Authentication should be successful");
    }

    @Test
    @DisplayName("Facade should authenticate Trainer correctly")
    void shouldAuthenticateTrainerCorrectly() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Roco");
        trainer.setLastName("Posada");

        facade.createTrainer(trainer);

        Optional<Trainer> savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Roco.Posada"))
                .findFirst();

        assertTrue(savedTrainer.isPresent(), "Trainer was not saved");

        Boolean isAuthentic = facade.authenticateTrainer(
                savedTrainer.get().getUsername(),
                savedTrainer.get().getPassword());

        assertTrue(isAuthentic, "Authentication should be successful");
    }

    @Test
    @DisplayName("Facade should find Trainings by Trainee username correctly")
    void shouldFindTrainingsByTraineeUsernameCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Lucia");
        trainee.setLastName("Fernandez");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Lucia.Fernandez"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        Trainer trainer = new Trainer();
        trainer.setFirstName("Miguel");
        trainer.setLastName("Alvarez");

        facade.createTrainer(trainer);

        Optional<Trainer> savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Miguel.Alvarez"))
                .findFirst();

        assertTrue(savedTrainer.isPresent(), "Trainer was not saved");

        Optional<TrainingType> type = facade.findTrainingTypeByName("Cardio");

        Training training = new Training();
        training.setTrainer(savedTrainer.get());
        training.setTrainee(savedTrainee.get());
        training.setDate("2026-06-15 10:00:00");
        training.setDuration(240);
        training.setTrainingType(type.get());
        training.setTrainingName("training Lucia - Cardio");

        Training training2 = new Training();
        training2.setTrainer(savedTrainer.get());
        training2.setTrainee(savedTrainee.get());
        training2.setDate("2026-09-17 07:00:00");
        training2.setDuration(240);
        training2.setTrainingType(type.get());
        training2.setTrainingName("training 2 Lucia - Cardio");

        facade.createTraining(training, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());
        facade.createTraining(training2, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());

        List<Training> trainings = facade.findTrainingsByTraineeUsername(
                savedTrainee.get().getUsername(),
                savedTrainee.get().getPassword(),
                "2025-11-29 20:00:00",
                "2026-11-29 20:00:00",
                "Miguel",
                "Cardio");

        assertAll("Trainings should be found correctly",
                () -> assertNotNull(trainings, "Found trainings should not be null"),
                () -> assertEquals("training Lucia - Cardio", trainings.get(0).getTrainingName(),
                        "First training name should match"),
                () -> assertEquals(2, trainings.size(), "There should be 2 trainings found"));
    }

    @Test
    @DisplayName("Facade should find Trainings by Trainer username correctly")
    void shouldFindTrainingsByTrainerUsernameCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Majo");
        trainee.setLastName("Gomez");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Majo.Gomez"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        Trainer trainer = new Trainer();
        trainer.setFirstName("Arturo");
        trainer.setLastName("Calle");

        facade.createTrainer(trainer);

        Optional<Trainer> savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Arturo.Calle"))
                .findFirst();

        assertTrue(savedTrainer.isPresent(), "Trainer was not saved");

        Optional<TrainingType> type = facade.findTrainingTypeByName("CrossFit");

        Training training = new Training();
        training.setTrainer(savedTrainer.get());
        training.setTrainee(savedTrainee.get());
        training.setDate("2025-12-30 11:00:00");
        training.setDuration(240);
        training.setTrainingType(type.get());
        training.setTrainingName("training Majo - CrossFit");

        Training training2 = new Training();
        training2.setTrainer(savedTrainer.get());
        training2.setTrainee(savedTrainee.get());
        training2.setDate("2026-02-08 05:00:00");
        training2.setDuration(240);
        training2.setTrainingType(type.get());
        training2.setTrainingName("training 2 Majo - CrossFit");

        Training training3 = new Training();
        training3.setTrainer(savedTrainer.get());
        training3.setTrainee(savedTrainee.get());
        training3.setDate("2026-04-01 16:00:00");
        training3.setDuration(240);
        training3.setTrainingType(type.get());
        training3.setTrainingName("training 3 Majo - CrossFit");

        facade.createTraining(training, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());
        facade.createTraining(training2, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());
        facade.createTraining(training3, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());

        List<Training> trainings = facade.findTrainingsByTrainerUsername(
                savedTrainer.get().getUsername(),
                savedTrainer.get().getPassword(),
                "2025-12-15 20:00:00",
                "2026-05-15 20:00:00",
                "Majo",
                "CrossFit");

        assertAll("Trainings should be found by using Trainer username correctly",
                () -> assertNotNull(trainings, "Found trainings should not be null"),
                () -> assertEquals("training Majo - CrossFit", trainings.get(0).getTrainingName(),
                        "First training name should match"),
                () -> assertEquals(3, trainings.size(), "There should be 3 trainings found"));
    }

    @Test
    @DisplayName("Facade should find Unassigned Trainers correctly")
    void shouldFindUnassignedTrainersCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Harry");
        trainee.setLastName("Rupert");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Harry.Rupert"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        Trainer trainer = new Trainer();
        trainer.setFirstName("Sandra");
        trainer.setLastName("Diaz");

        facade.createTrainer(trainer);

        Optional<Trainer> savedTrainer = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Sandra.Diaz"))
                .findFirst();

        assertTrue(savedTrainer.isPresent(), "Trainer was not saved");

        Optional<TrainingType> type = facade.findTrainingTypeByName("CrossFit");

        Training training = new Training();
        training.setTrainer(savedTrainer.get());
        training.setTrainee(savedTrainee.get());
        training.setDate("2025-12-31 11:00:00");
        training.setDuration(240);
        training.setTrainingType(type.get());
        training.setTrainingName("training Sandra - CrossFit");

        facade.createTraining(training, savedTrainer.get().getUsername(), savedTrainer.get().getPassword());

        List<Trainer> unassignedTrainers = facade.findUnassignedTrainers(
                savedTrainee.get().getUsername(),
                savedTrainee.get().getPassword());

        assertAll("Unassigned Trainers should be found correctly",
                () -> assertNotEquals(0, unassignedTrainers.size(), "There should not be zero unassigned trainers"),
                () -> assertFalse(
                        unassignedTrainers.stream()
                                .anyMatch(t -> t.getUsername().equals("Sandra.Diaz")),
                        "Sandra Diaz should not be in the unassigned trainers list"));
    }

    @Test
    @DisplayName("Facade should update trainee's trainers listcorrectly")
    void shouldUpdateTraineeTrainersListCorrectly() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("Nina");
        trainee.setLastName("Pereira");

        facade.createTrainee(trainee);

        Optional<Trainee> savedTrainee = facade.findAllTrainees()
                .stream()
                .filter(t -> t.getUsername().equals("Nina.Pereira"))
                .findFirst();

        assertTrue(savedTrainee.isPresent(), "Trainee was not saved");

        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("Ana");
        trainer1.setLastName("Martinez");

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("Bea");
        trainer2.setLastName("Gonzalez");

        facade.createTrainer(trainer1);
        facade.createTrainer(trainer2);

        Optional<Trainer> savedTrainer1 = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Ana.Martinez"))
                .findFirst();

        Optional<Trainer> savedTrainer2 = facade.findAllTrainers()
                .stream()
                .filter(t -> t.getUsername().equals("Bea.Gonzalez"))
                .findFirst();

        assertTrue(savedTrainer1.isPresent(), "Trainer 1 was not saved");
        assertTrue(savedTrainer2.isPresent(), "Trainer 2 was not saved");

        facade.updateTraineeTrainersList(savedTrainer1.get(),
                savedTrainee.get().getUsername(),
                savedTrainee.get().getPassword());

        facade.updateTraineeTrainersList(savedTrainer2.get(),
                savedTrainee.get().getUsername(),
                savedTrainee.get().getPassword());

        System.out.println(facade.findAllTrainees().toString());

        assertAll("Trainee's trainers list should be updated correctly",
                () -> assertEquals(2,
                        savedTrainee.get().getTrainers().size(),
                        "Trainee should have 2 trainers assigned"),
                () -> assertTrue(
                        savedTrainee.get().getTrainers()
                                .stream()
                                .anyMatch(t -> t.getUsername().equals("Ana.Martinez")),
                        "Ana Martinez should be in the trainee's trainers list"));
    }
}
package pl.pjatk.caramba.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pjatk.caramba.exceptions.ValidationException;
import pl.pjatk.caramba.model.Car;
import pl.pjatk.caramba.model.CarClass;
import pl.pjatk.caramba.repository.CarRepository;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RentalServiceTest {
    private static RentalService rentalService;
    private static CarRepository carRepository;

    @BeforeAll
    public static void setup() {
        carRepository = new CarRepository();
        rentalService = new RentalService(carRepository);
    }

    @AfterEach
    public void cleanup() {
        carRepository.removeAll();
    }

    @Test
    public void shouldAddNewCar() {
        Car car = new Car(
                1,
                "Ferrari",
                "Daytona",
                "DJSKAJSLGH12GD",
                CarClass.PREMIUM
        );

        assertDoesNotThrow(() -> rentalService.addCar(car));

    }

    @ParameterizedTest
    @MethodSource("provideCarsWithEmptyVin")
    public void shouldThrowExceptionWhenAddingCarWithEmptyVin(Car car) {
        assertThrows(ValidationException.class, () -> rentalService.addCar(car), "Vin is required");
    }

    @Test
    public void shouldFindCarById() throws Exception {
        Car car = new Car(
                1,
                "Ferrari",
                "Daytona",
                "DJSKAJSLGH12GD",
                CarClass.PREMIUM
        );

        carRepository.addCar(car);

        Optional<Car> foundCar = rentalService.findCarById(1);

        assertTrue(foundCar.isPresent());
        assertEquals(car, foundCar.get());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenCarIsNotPresent() throws Exception {
        Optional<Car> foundCar = rentalService.findCarById(1);

        assertFalse(foundCar.isPresent());
        assertEquals(Optional.empty(), foundCar);

    }

    private static Stream<Arguments> provideCarsWithEmptyVin() {
        return Stream.of(
                Arguments.of(new Car(
                        1,
                        "Ferrari",
                        "Daytona",
                        "",
                        CarClass.PREMIUM
                )),
                Arguments.of(new Car(
                        3,
                        "Ford",
                        "Mustang",
                        "",
                        CarClass.PREMIUM
                ))
        );
    }
}
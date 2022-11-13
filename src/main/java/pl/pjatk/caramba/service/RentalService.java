package pl.pjatk.caramba.service;

import org.springframework.stereotype.Service;
import pl.pjatk.caramba.exceptions.DatabaseException;
import pl.pjatk.caramba.exceptions.ValidationException;
import pl.pjatk.caramba.model.Car;
import pl.pjatk.caramba.repository.CarRepository;

import java.util.Optional;

@Service
public class RentalService {
    private final CarRepository carRepository;

    public RentalService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addCar(Car car) {
        if (isValid(car.getVin())) {
            throw new ValidationException("Vim is required");
        }

        if (isValid(car.getMake())) {
            throw new ValidationException("Make is required");
        }


        try {
            carRepository.addCar(car);
        } catch (Exception e) {
            throw new DatabaseException("Database error", e);
        }
    }

    public Optional<Car> findCarById(int id) {
        return carRepository.findCarById(id);
    }


    private static boolean isValid(String carAttribute) {
        return carAttribute == null || carAttribute.isBlank();
    }

}

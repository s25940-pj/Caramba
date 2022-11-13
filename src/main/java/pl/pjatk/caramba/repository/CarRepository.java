package pl.pjatk.caramba.repository;

import org.springframework.stereotype.Repository;
import pl.pjatk.caramba.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {
    private List<Car> availableCarsList = new ArrayList<>();

    public void addCar(Car car) throws Exception {
        if (availableCarsList.contains(car)) {
            throw new Exception();
        }
        availableCarsList.add(car);
    }

    public Optional<Car> findCarById(int id) {
        return availableCarsList.stream().filter(car -> car.getId() == id).findFirst();
    }

    public void removeAll() {
        availableCarsList = new ArrayList<>();
    }
}

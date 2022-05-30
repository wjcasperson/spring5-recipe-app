package guru.springframework.services;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Set<UnitOfMeasure> getUnitsOfMeasure() {
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        unitOfMeasureRepository.findAll().iterator().forEachRemaining(unitOfMeasureSet::add);
        return unitOfMeasureSet;
    }

    @Override
    public UnitOfMeasure findById(Long l) {
        Optional<UnitOfMeasure>unitOfMeasureOptional = unitOfMeasureRepository.findById(l);
        if(!unitOfMeasureOptional.isPresent()){
            throw new RuntimeException("Error: unit of measure not found!");
        }
        return unitOfMeasureOptional.get();
    }
}

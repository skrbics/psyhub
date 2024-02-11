package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.City;
import si.eclectic.psyhub.repository.CityRepository;
import si.eclectic.psyhub.service.CityService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.City}.
 */
@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City save(City city) {
        log.debug("Request to save City : {}", city);
        return cityRepository.save(city);
    }

    @Override
    public City update(City city) {
        log.debug("Request to update City : {}", city);
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> partialUpdate(City city) {
        log.debug("Request to partially update City : {}", city);

        return cityRepository
            .findById(city.getId())
            .map(existingCity -> {
                if (city.getName() != null) {
                    existingCity.setName(city.getName());
                }
                if (city.getZip() != null) {
                    existingCity.setZip(city.getZip());
                }

                return existingCity;
            })
            .map(cityRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<City> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return cityRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<City> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
    }
}

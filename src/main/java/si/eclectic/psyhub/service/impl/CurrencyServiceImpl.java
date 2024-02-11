package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.Currency;
import si.eclectic.psyhub.repository.CurrencyRepository;
import si.eclectic.psyhub.service.CurrencyService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.Currency}.
 */
@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency save(Currency currency) {
        log.debug("Request to save Currency : {}", currency);
        return currencyRepository.save(currency);
    }

    @Override
    public Currency update(Currency currency) {
        log.debug("Request to update Currency : {}", currency);
        return currencyRepository.save(currency);
    }

    @Override
    public Optional<Currency> partialUpdate(Currency currency) {
        log.debug("Request to partially update Currency : {}", currency);

        return currencyRepository
            .findById(currency.getId())
            .map(existingCurrency -> {
                if (currency.getName() != null) {
                    existingCurrency.setName(currency.getName());
                }
                if (currency.getCode() != null) {
                    existingCurrency.setCode(currency.getCode());
                }

                return existingCurrency;
            })
            .map(currencyRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Currency> findAll(Pageable pageable) {
        log.debug("Request to get all Currencies");
        return currencyRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Currency> findOne(Long id) {
        log.debug("Request to get Currency : {}", id);
        return currencyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Currency : {}", id);
        currencyRepository.deleteById(id);
    }
}

package com.javabeer.adapter.persistance;

import com.javabeer.adapter.persistance.entity.BeerJpaEntity;
import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.usecase.port.persistance.BeerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Repository
@Transactional
public class BeerJpaRepositoryImpl implements BeerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Beer> save(Beer beer) {
        entityManager.persist(BeerEntityMapper.fromDomainModel(beer));
        return Optional.of(beer);
    }

    @Override
    public Optional<Beer> findBeerById(BeerId beerId) {
        return entityManager.createQuery("SELECT b from BeerJpaEntity b where b.id = :id", BeerJpaEntity.class)
                .setParameter("id", beerId.getValue())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .map(BeerEntityMapper::toDomainModel);
    }

    @Override
    public Collection<Beer> findBeersByCategory(BeerCategory beerCategory) {
        return entityManager.createQuery("SELECT b from BeerJpaEntity b where b.category = :category", BeerJpaEntity.class)
                .setParameter("category", beerCategory.name())
                .getResultStream().map(BeerEntityMapper::toDomainModel)
                .toList();
    }
}

package com.javabeer.persistance;

import com.javabeer.domain.Beer;
import com.javabeer.domain.BeerCategory;
import com.javabeer.domain.BeerId;
import com.javabeer.persistance.entity.BeerJpaEntity;
import com.javabeer.usecase.port.persistance.BeerRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        BeerJpaEntity beerJpa = entityManager.createQuery("SELECT b from BeerJpaEntity b where b.id = :id", BeerJpaEntity.class)
                .setParameter("id", beerId.getId()).getSingleResult();
        return Optional.of(beerJpa).map(BeerEntityMapper::toDomainModel);
    }

    @Override
    public Collection<Beer> findBeersByCategory(BeerCategory beerCategory) {
        List<BeerJpaEntity> jpaBeers = entityManager.createQuery("SELECT b from BeerJpaEntity b where b.category = :category", BeerJpaEntity.class)
                .setParameter("category", beerCategory.name()).getResultList();
        return jpaBeers.stream().map(BeerEntityMapper::toDomainModel).collect(Collectors.toList());
    }
}

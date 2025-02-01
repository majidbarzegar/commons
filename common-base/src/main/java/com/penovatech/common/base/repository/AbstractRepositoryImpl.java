package com.penovatech.common.base.repository;

import com.penovatech.common.model.AbstractCriteria;
import com.penovatech.common.model.AbstractPersistable;
import com.penovatech.common.model.AbstractPersistable_;
import com.penovatech.common.base.PredicateBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractRepositoryImpl<M extends AbstractPersistable<I>, C extends AbstractCriteria<I>, I extends Comparable<I>> implements AbstractRepository<M, C, I> {

    @SuppressWarnings("unchecked")
    public AbstractRepositoryImpl() {
        this.entityType = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @PersistenceContext
    protected EntityManager entityManager;
    private final Class<M> entityType;


    @Override
    public Optional<M> get(I id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    @Override
    public List<M> get(List<I> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return List.of();
        }
        String jpql = "SELECT m FROM " + entityType.getSimpleName() + " m WHERE m.id IN (:idList)";
        TypedQuery<M> query = entityManager.createQuery(jpql, entityType);
        query.setParameter("idList", idList);
        return query.getResultList();
    }

    @Override
    public List<M> getAll() {
        String jpql = "SELECT m FROM " + entityType.getSimpleName() + " m";
        TypedQuery<M> query = entityManager.createQuery(jpql, entityType);
        return query.getResultList();
    }

    @Override
    public List<M> get(C criteria) {
        return executeCriteriaQuery(criteria).getResultList();
    }

    @Override
    public Optional<M> getFirst(C criteria) {
        TypedQuery<M> query = executeCriteriaQuery(criteria);
        query.setMaxResults(1);
        List<M> results = query.getResultList();  // Get the result list
        if (results.isEmpty()) {
            return Optional.empty();  // No results
        } else {
            return Optional.of(results.getFirst());  // Return the first result
        }
    }

    @Override
    public Optional<M> getLast(C criteria) {
        long totalCount = getCount(criteria);
        if (totalCount == 0) {
            return Optional.empty();
        }
        TypedQuery<M> query = executeCriteriaQuery(criteria);
        query.setFirstResult((int) (totalCount - 1));
        query.setMaxResults(1);
        List<M> results = query.getResultList();  // Get results from query
        if (results.isEmpty()) {
            return Optional.empty();  // If no results, return empty
        } else {
            return Optional.of(results.get(0));  // Return the first (and only) result
        }
    }

    @Transactional
    @Override
    public long delete(C criteria) {
        List<M> itemsToDelete = executeCriteriaQuery(criteria).getResultList();
        long deletedCount = itemsToDelete.size();
        itemsToDelete.forEach(entityManager::remove);
        return deletedCount;
    }

    @Transactional
    @Override
    public long delete(I id) {
        return delete(List.of(id));
    }

    @Transactional
    @Override
    public long delete(List<I> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return 0;
        }
        String jpql = "DELETE FROM " + entityType.getSimpleName() + " e WHERE e.id IN (:idList)";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("idList", idList);
        return query.executeUpdate();
    }

    @Transactional
    public M save(M model) {
        Objects.requireNonNull(model, "Model cannot be null");
        entityManager.persist(model);
        return model;
    }

    @Transactional
    public List<M> save(List<M> modelList) {
        Objects.requireNonNull(modelList, "Model list cannot be null");
        modelList.forEach(entityManager::persist);
        return modelList;
    }

    @Transactional
    public M update(M model) {
        Objects.requireNonNull(model, "Model cannot be null");
        entityManager.persist(model);
        return model;
    }

    public long getCount(C criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<M> root = query.from(entityType);
        PredicateBuilder<M> predicateBuilder = new PredicateBuilder<>(cb, root);
        addCondition(predicateBuilder, criteria);  // Custom conditions based on the criteria
        predicateBuilder.addCondition(
                (criteriaBuilder, r) -> r.get(AbstractPersistable_.ID).in(criteria.getIdIn()),
                !CollectionUtils.isEmpty(criteria.getIdIn())
        );
        predicateBuilder.addCondition(
                (criteriaBuilder, r) -> criteriaBuilder.not(r.get(AbstractPersistable_.ID).in(criteria.getIdNotIn())),
                !CollectionUtils.isEmpty(criteria.getIdNotIn())
        );
        query.where(predicateBuilder.build());
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    public boolean isExist(C criteria) {
        return getCount(criteria) > 0;
    }

    public boolean isNotExist(C criteria) {
        return !isExist(criteria);
    }

    protected abstract void addCondition(PredicateBuilder<M> predicateBuilder, C criteria);

    private TypedQuery<M> executeCriteriaQuery(C criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<M> query = cb.createQuery(entityType);
        Root<M> root = query.from(entityType);
        PredicateBuilder<M> predicateBuilder = new PredicateBuilder<>(cb, root);
        addCondition(predicateBuilder, criteria);
        predicateBuilder.addCondition(
                (criteriaBuilder, r) -> r.get(AbstractPersistable_.ID).in(criteria.getIdIn()),
                !CollectionUtils.isEmpty(criteria.getIdIn())
        );
        predicateBuilder.addCondition(
                (criteriaBuilder, r) -> criteriaBuilder.not(r.get(AbstractPersistable_.ID).in(criteria.getIdNotIn())),
                !CollectionUtils.isEmpty(criteria.getIdNotIn())
        );
        query.where(predicateBuilder.build());
        TypedQuery<M> typedQuery = entityManager.createQuery(query);
        if (Objects.nonNull(criteria.getPage()) && Objects.nonNull(criteria.getSize())) {
            typedQuery.setFirstResult(criteria.getPage() * criteria.getSize());
            typedQuery.setMaxResults(criteria.getSize());
        }
        return typedQuery;
    }
}

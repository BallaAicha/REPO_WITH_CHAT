package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class CustomListingRepositoryImpl implements CustomListingRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Listing> advancedSearch(String keyword, BigDecimal minPrice, BigDecimal maxPrice, String location, String categoryId, String sortField, boolean isAscending) {
        Query query = new Query();

        if (keyword != null && !keyword.isEmpty()) {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("title").regex(keyword, "i"),
                    Criteria.where("description").regex(keyword, "i")
            ));
        }

        if (minPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice));
        }

        if (maxPrice != null) {
            query.addCriteria(Criteria.where("price").lte(maxPrice));
        }

        if (location != null && !location.isEmpty()) {
            query.addCriteria(Criteria.where("location").is(location));
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            query.addCriteria(Criteria.where("category.id").is(categoryId));
        }

        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = isAscending ? Sort.Direction.ASC : Sort.Direction.DESC;
            query.with(Sort.by(direction, sortField));
        }

        return mongoTemplate.find(query, Listing.class);
    }
}
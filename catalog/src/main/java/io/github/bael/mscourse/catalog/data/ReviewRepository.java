package io.github.bael.mscourse.catalog.data;

import io.github.bael.mscourse.catalog.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository
//        extends CrudRepository<Review, Long>
{
    List<Review> findTop10ByProduct_Id(Long id);
}

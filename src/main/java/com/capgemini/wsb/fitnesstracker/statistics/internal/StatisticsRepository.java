package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    /**
     * Finds statistics for a specific user.
     *
     * @param user the user whose statistics are to be fetched
     * @return An {@link Optional} containing the statistics, or empty if not found
     */
    Optional<Statistics> findByUser(User user);
}

package com.capgemini.wsb.fitnesstracker.statistics.api;

import java.util.List;
import java.util.Optional;

public interface StatisticsProvider {

    /**
     * Retrieves a statistics based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param statisticsId id of the statistics to be searched
     * @return An {@link Optional} containing the located Statistics, or {@link Optional#empty()} if not found
     */
    Optional<Statistics> getStatistics(Long statisticsId);

    /**
     * Retrieves all user statistics.
     *
     * @return A list of all {@link Statistics}
     */
    List<Statistics> getAllStatistics();
}

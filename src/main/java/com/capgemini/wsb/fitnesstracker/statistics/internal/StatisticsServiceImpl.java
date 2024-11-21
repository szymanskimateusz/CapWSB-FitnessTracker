package com.capgemini.wsb.fitnesstracker.statistics.internal;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;
import com.capgemini.wsb.fitnesstracker.statistics.api.StatisticsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsProvider {
    private final StatisticsRepository statisticsRepository;

    @Override
    public Optional<Statistics> getStatistics(Long statisticsId) {
        return statisticsRepository.findById(statisticsId);
    }

    @Override
    public List<Statistics> getAllStatistics() {
        return statisticsRepository.findAll();
    }
}

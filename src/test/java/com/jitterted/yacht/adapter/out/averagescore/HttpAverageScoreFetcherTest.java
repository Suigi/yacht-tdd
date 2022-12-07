package com.jitterted.yacht.adapter.out.averagescore;

import com.jitterted.yacht.domain.ScoreCategory;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class HttpAverageScoreFetcherTest {

    @Test
    void nullFetcherReturnsDefaultValue() {
        HttpAverageScoreFetcher fetcher =
                HttpAverageScoreFetcher.createNull();

        assertThat(fetcher.averageFor(ScoreCategory.FOURS))
                .isEqualTo(42.0);
        assertThat(fetcher.averageFor(ScoreCategory.FULLHOUSE))
                .isEqualTo(42.0);
        assertThat(fetcher.averageFor(ScoreCategory.BIGSTRAIGHT))
                .isEqualTo(42.0);
    }

    @Test
    void nullFetcherReturnsConfiguredValue() {
        HttpAverageScoreFetcher fetcher =
                HttpAverageScoreFetcher.createNull(
                        Map.of(ScoreCategory.FOURS, 1.0,
                               ScoreCategory.FULLHOUSE, 2.0,
                               ScoreCategory.BIGSTRAIGHT, 3.0)
                );

        assertThat(fetcher.averageFor(ScoreCategory.FOURS))
                .isEqualTo(1.0);
    }

    @Test
    void whenConfiguredNullFetcherThrowsExceptionIfCategoryHasNoConfiguredValue() {
        HttpAverageScoreFetcher fetcher =
                HttpAverageScoreFetcher.createNull(
                        Map.of(ScoreCategory.FOURS, 1.0)
                );

        assertThatThrownBy(() -> {
            fetcher.averageFor(ScoreCategory.FULLHOUSE);
        }).isInstanceOf(NoSuchElementException.class)
          .hasMessage("No average configured for FULLHOUSE in Null HttpAverageScoreFetcher.");
    }
}
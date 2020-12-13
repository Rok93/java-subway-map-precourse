package subway.domain.line.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import subway.domain.station.model.Station;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LineTest {

    @DisplayName("Line 객체를 생성하는 기능을 테스트한다")
    @ParameterizedTest()
    @ValueSource(strings = {
            "2호선", "3호선", "4호선"
    })
    void testInitLine(String name) {
        //given
        String stationNames = "시청역,서울역";
        List<Station> stations = Arrays.stream(stationNames.split(","))
                .map(Station::new)
                .collect(Collectors.toList());

        //when
        Line line = new Line(name, stations);

        //then
        assertThat(line).extracting("name").isEqualTo(name);
    }

    @DisplayName("최소 노선이름 길이보다 노선 이름이 짧으면 예외를 던지는 기능을 테스트한다 ")
    @ParameterizedTest
    @ValueSource(strings = {
            "1", "2", "3"
    })
    void testInitLineIfLineNameIsShorterThanMinLineName(String name) {
        //given
        String stationName = "시청역";
        List<Station> stations = Arrays.asList(new Station(stationName));

        //when //then
        assertThatThrownBy(() -> new Line(name, stations))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("노선의 구간이 초기 구간제한 갯수와 다르면 예외를 던지는 기능을 테스트한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "시청역", "시청역,까치산역,서울역"
    })
    void testInitLineIfStationsNumberLessThanInitStationsNumber(String stationNames) {
        //given
        String lineName = "1호선";
        List<Station> stations = Arrays.stream(stationNames.split(","))
                .map(Station::new)
                .collect(Collectors.toList());

        //when //then
        assertThatThrownBy(() -> new Line(lineName, stations))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}

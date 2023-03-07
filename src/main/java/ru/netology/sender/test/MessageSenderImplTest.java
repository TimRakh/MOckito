package ru.netology.sender.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;

public class MessageSenderImplTest {
    @Test
    void sends_text() {

        GeoServiceImpl geoService = Mockito.spy(GeoServiceImpl.class);
        LocalizationServiceImpl localizationService = Mockito.spy(LocalizationServiceImpl.class);
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        HashMap mapRu = new HashMap();
        mapRu.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.0.0");

        HashMap mapENG = new HashMap();
        mapENG.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.0.0");

        String expectedENG = "Welcome";
        String expectedRu = "Добро пожаловать";

        String preferencesENG = messageSender.send(mapENG);
        String preferencesRu = messageSender.send(mapRu);

        Assertions.assertEquals(expectedRu, preferencesRu);
        Assertions.assertEquals(expectedENG, preferencesENG);

    }

    @Test
    void tests_to_verify_location_by_ip() {
        String ip = "96.0.0.0";
        Location expected = new Location("New York", Country.USA, " 10th Avenue", 32);
        GeoService geoService = new GeoServiceImpl();
        Location preferences = geoService.byIp(ip);

        Assertions.assertEquals(expected.getCountry(), preferences.getCountry());
    }
    @Test()
    void testExpectedException() {

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
            GeoServiceImpl exception = new GeoServiceImpl();
            exception.byCoordinates(1, 1);
        });

        Assertions.assertEquals("Not implemented", thrown.getMessage());
    }
}

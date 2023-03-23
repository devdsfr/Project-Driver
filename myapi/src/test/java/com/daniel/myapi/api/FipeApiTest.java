package com.daniel.myapi.api;

import com.daniel.myapi.domain.Marca;
import com.daniel.myapi.domain.Modelo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FipeApiTest {

    private FipeApi fipeApi;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        fipeApi = new FipeApi();
    }

    @Test
    void testGetMarcaPorId() {
        Mono<Marca> marcaMono = fipeApi.getMarcaPorId(1);
        Marca marca = marcaMono.block();
        assertEquals("Acura", marca.getName());
    }

    @Test
    void testGetModeloPorId() {
        Mono<Modelo> modeloMono = fipeApi.getModeloPorId(1, 1);
        Modelo modelo = modeloMono.block();
        assertEquals("Integra GS 1.8", modelo.getName());
    }

    @Test
    void testGetValorFipe() {
        Mono<Double> valorFipeMono = fipeApi.getValorFipe(1, 1, 1995);
        Double valorFipe = valorFipeMono.block();
        assertEquals(2000.0, valorFipe);
    }

    @Test
    void testConsultarPrecoFipe() {
        String json = "{\"preco_fipe\": \"2000.00\"}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(json, HttpStatus.OK);
        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntity);

        Marca marca = new Marca();
        marca.setId("1");
        marca.setName("Acura");

        Modelo modelo = new Modelo();
        modelo.setId("1");
        modelo.setName("Integra GS 1.8");

        BigDecimal precoFipe = fipeApi.consultarPrecoFipe(marca, modelo, 1995);
        assertEquals(new BigDecimal("2000.00"), precoFipe);
    }
}
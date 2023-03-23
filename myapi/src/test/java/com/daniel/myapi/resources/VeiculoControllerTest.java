package com.daniel.myapi.resources;

import com.daniel.myapi.controller.VeiculoController;
import com.daniel.myapi.domain.Veiculo;
import com.daniel.myapi.exception.InvalidMarcaModeloAnoException;
import com.daniel.myapi.exception.VeiculoAlreadyExistsException;
import com.daniel.myapi.exception.VeiculoNotFoundException;
import com.daniel.myapi.services.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VeiculoControllerTest {

    @Mock
    private VeiculoService veiculoService;

    private VeiculoController veiculoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        veiculoController = new VeiculoController();
        veiculoController.veiculoService = veiculoService;
    }

    @Test
    void cadastrarVeiculo() throws InvalidMarcaModeloAnoException, VeiculoAlreadyExistsException {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        when(veiculoService.cadastrarVeiculo(any(Veiculo.class))).thenReturn(veiculo);

        ResponseEntity<Veiculo> response = veiculoController.cadastrarVeiculo(veiculo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(veiculo, response.getBody());
    }

    @Test
    void buscarPorPlaca() throws VeiculoNotFoundException {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        when(veiculoService.buscarPorPlaca("ABC1234")).thenReturn(veiculo);

        ResponseEntity<Veiculo> response = veiculoController.buscarPorPlaca("ABC1234");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculo, response.getBody());
    }

    @Test
    void listarPorMarca() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Veiculo> veiculos = Page.empty();
        when(veiculoService.listarPorMarca("Fiat", pageable)).thenReturn(veiculos);

        ResponseEntity<Page<Veiculo>> response = veiculoController.listarPorMarca("Fiat", 0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(veiculos, response.getBody());
    }
}
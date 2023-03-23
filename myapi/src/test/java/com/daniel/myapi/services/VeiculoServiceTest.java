package com.daniel.myapi.services;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.daniel.myapi.api.FipeApi;
import com.daniel.myapi.domain.Marca;
import com.daniel.myapi.domain.Modelo;
import com.daniel.myapi.domain.Veiculo;
import com.daniel.myapi.exception.InvalidMarcaModeloAnoException;
import com.daniel.myapi.exception.VeiculoAlreadyExistsException;
import com.daniel.myapi.repositories.MarcaRepository;
import com.daniel.myapi.repositories.ModeloRepository;
import com.daniel.myapi.repositories.VeiculoRepository;
import com.daniel.myapi.services.VeiculoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VeiculoServiceTest {

    @Autowired
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private ModeloRepository modeloRepository;

    @Mock
    private FipeApi fipeApi;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        veiculoService = new VeiculoService();
        veiculoService.setVeiculoRepository(veiculoRepository);
        veiculoService.setMarcaRepository(marcaRepository);
        veiculoService.setModeloRepository(modeloRepository);
        veiculoService.setFipeApi(fipeApi);
    }

    @Test
    public void testCadastrarVeiculo() {
        // Cria um veículo para teste
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setAno(2020);

        Marca marca = new Marca();
        marca.setId("1L");
        marca.setName("Marca Teste");

        Modelo modelo = new Modelo();
        modelo.setId("1L");
        modelo.setName("Modelo Teste");
        modelo.setMarca(marca);

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);

        BigDecimal precoFipe = new BigDecimal("50000.00");

        // Configura o mock para retornar a marca e o modelo
        when(marcaRepository.findById(marca.getId())).thenReturn(Optional.of(marca));
        when(modeloRepository.findById(modelo.getId())).thenReturn(Optional.of(modelo));

        // Configura o mock para retornar o preço FIPE
        when(fipeApi.consultarPrecoFipe(marca, modelo, veiculo.getAno())).thenReturn(precoFipe);

        // Configura o mock para retornar que a placa não existe
        when(veiculoRepository.findByPlaca(veiculo.getPlaca())).thenReturn(Optional.empty());

        // Chama o método a ser testado
        Veiculo veiculoCadastrado = veiculoService.cadastrarVeiculo(veiculo);

        // Verifica se o veículo foi salvo corretamente
        assertNotNull(veiculoCadastrado);
        assertEquals(veiculo.getPlaca(), veiculoCadastrado.getPlaca());
        assertEquals(veiculo.getAno(), veiculoCadastrado.getAno());
        assertEquals(marca, veiculoCadastrado.getMarca());
        assertEquals(modelo, veiculoCadastrado.getModelo());
        assertEquals(precoFipe, veiculoCadastrado.getPrecoFipe());
        assertEquals(LocalDate.now(), veiculoCadastrado.getDataCadastro());

        // Verifica se o método findByPlaca foi chamado corretamente
        verify(veiculoRepository, times(1)).findByPlaca(veiculo.getPlaca());

        // Verifica se o método findById foi chamado corretamente para a marca e o modelo
        verify(marcaRepository, times(1)).findById(marca.getId());
        verify(modeloRepository, times(1)).findById(modelo.getId());

        // Verifica se o método consultarPrecoFipe foi chamado corretamente
        verify(fipeApi, times(1)).consultarPrecoFipe(marca, modelo, veiculo.getAno());

        // Verifica se o método save foi chamado corretamente
        verify(veiculoRepository, times(1)).save(veiculo);
    }

    @Test(expected = VeiculoAlreadyExistsException.class)
    public void testCadastrarVeiculoPlacaExistente() {
        // Cria um veículo para teste
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setAno(2020);

        // Configura o mock para retornar que a placa já existe
        when(veiculoRepository.findByPlaca(veiculo.getPlaca())).thenReturn(Optional.of(veiculo));

        // Chama o método a ser testado
        veiculoService.cadastrarVeiculo(veiculo);
    }

    @Test(expected = InvalidMarcaModeloAnoException.class)
    public void testCadastrarVeiculoMarcaInvalida() {
        // Cria um veículo para teste
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setAno(2020);

        Marca marca = new Marca();
        marca.setId("1L");
        marca.setName("Marca Teste");

        Modelo modelo = new Modelo();
        modelo.setId("1L");
        modelo.setName("Modelo Teste");
        modelo.setMarca(marca);

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);

        // Configura o mock para retornar que a marca não existe
        when(marcaRepository.findById(marca.getId())).thenReturn(Optional.empty());

        // Chama o método a ser testado
        veiculoService.cadastrarVeiculo(veiculo);
    }

    @Test(expected = InvalidMarcaModeloAnoException.class)
    public void testCadastrarVeiculoModeloInvalido() {
        // Cria um veículo para teste
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setAno(2020);

        Marca marca = new Marca();
        marca.setId("1L");
        marca.setName("Marca Teste");

        Modelo modelo = new Modelo();
        modelo.setId("1L");
        modelo.setName("Modelo Teste");
        modelo.setMarca(marca);

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);

        // Configura o mock para retornar que o modelo não existe
        when(marcaRepository.findById(marca.getId())).thenReturn(Optional.of(marca));
        when(modeloRepository.findById(modelo.getId())).thenReturn(Optional.empty());

        // Chama o método a ser testado
        veiculoService.cadastrarVeiculo(veiculo);
    }

    @Test(expected = InvalidMarcaModeloAnoException.class)
    public void testCadastrarVeiculoAnoInvalido() {
        // Cria um veículo para teste
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setAno(1899);

        Marca marca = new Marca();
        marca.setId("1L");
        marca.setName("Marca Teste");

        Modelo modelo = new Modelo();
        modelo.setId("1L");
        modelo.setName("Modelo Teste");
        modelo.setMarca(marca);

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);

        // Configura o mock para retornar a marca e o modelo
        when(marcaRepository.findById(marca.getId())).thenReturn(Optional.of(marca));
        when(modeloRepository.findById(modelo.getId())).thenReturn(Optional.of(modelo));

        // Chama o método a ser testado
        veiculoService.cadastrarVeiculo(veiculo);
    }

    @Test
    public void testBuscarPorPlaca() {
        // Cria um veículo para teste
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setAno(2020);

        // Configura o mock para retornar o veículo
        when(veiculoRepository.findByPlaca(veiculo.getPlaca())).thenReturn(Optional.of(veiculo));

        // Chama o método a ser testado
        Veiculo veiculoEncontrado = veiculoService.buscarPorPlaca(veiculo.getPlaca());

        // Verifica se o veículo foi encontrado corretamente
        assertNotNull(veiculoEncontrado);
        assertEquals(veiculo, veiculoEncontrado);

        // Verifica se o método findByPlaca foi chamado corretamente
        verify(veiculoRepository, times(1)).findByPlaca(veiculo.getPlaca());
    }

    @Test(expected = RuntimeException.class)
    public void testBuscarPorPlacaInexistente() {
        // Configura o mock para retornar que o veículo não existe
        when(veiculoRepository.findByPlaca("ABC1234")).thenReturn(Optional.empty());

        // Chama o método a ser testado
        veiculoService.buscarPorPlaca("ABC1234");
    }

    @Test
    public void testListarPorMarca() {
        // Cria uma marca e um veículo para teste
        Marca marca = new Marca();
        marca.setId("1L");
        marca.setName("Marca Teste");

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1234");
        veiculo.setAno(2020);
        veiculo.setMarca(marca);

        // Configura o mock para retornar a marca e o veículo
        when(marcaRepository.findByNomeIgnoreCase(marca.getName())).thenReturn(Optional.of(marca));
        when(veiculoRepository.findByMarca(marca, Pageable.unpaged())).thenReturn(new PageImpl<>(Arrays.asList(veiculo)));

        // Chama o método a ser testado
        Page<Veiculo> veiculosEncontrados = veiculoService.listarPorMarca(marca.getName(), Pageable.unpaged());

        // Verifica se o veículo foi encontrado corretamente
        assertNotNull(veiculosEncontrados);
        assertTrue(veiculosEncontrados.getContent().contains(veiculo));

        // Verifica se o método findByNomeIgnoreCase foi chamado corretamente
        verify(marcaRepository, times(1)).findByNomeIgnoreCase(marca.getName());

        // Verifica se o método findByMarca foi chamado corretamente
        verify(veiculoRepository, times(1)).findByMarca(marca, Pageable.unpaged());
    }

    @Test(expected = RuntimeException.class)
    public void testListarPorMarcaInexistente() {
        // Configura o mock para retornar que a marca não existe
        when(marcaRepository.findByNomeIgnoreCase("Marca Teste")).thenReturn(Optional.empty());

        // Chama o método a ser testado
        veiculoService.listarPorMarca("Marca Teste", Pageable.unpaged());
    }

}
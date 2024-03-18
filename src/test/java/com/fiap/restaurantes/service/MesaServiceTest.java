package com.fiap.restaurantes.service;

import com.fiap.restaurantes.model.Mesa;
import com.fiap.restaurantes.repository.MesaRepository;
import com.fiap.restaurantes.utils.RestauranteHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MesaServiceTest {
    @Mock
    private MesaRepository mesaRepository;

    @InjectMocks
    private MesaServiceImpl mesaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testObterTodasMesas() {
        List<Mesa> mesas = new ArrayList<>();
        mesas.add(new Mesa(1L, 1, 1, RestauranteHelper.gerarRestaurante()));
        mesas.add(new Mesa(2L, 2, 2, RestauranteHelper.gerarRestaurante()));

        when(mesaRepository.obterTodasMesas(Pageable.unpaged())).thenReturn(new PageImpl<>(mesas));

        Page<Mesa> resultado = mesaService.listarMesas(null, Pageable.unpaged());

        assertEquals(mesas.size(), resultado.getContent().size());
    }

    @Test
    public void testObterMesasPorStatus() {
        List<Mesa> mesas = new ArrayList<>();
        mesas.add(new Mesa(1L, 1, 1, RestauranteHelper.gerarRestaurante()));
        mesas.add(new Mesa(2L, 2, 1, RestauranteHelper.gerarRestaurante()));

        when(mesaRepository.getMesasByStatus(1, Pageable.unpaged())).thenReturn(new PageImpl<>(mesas));

        Page<Mesa> resultado = mesaService.listarMesas(1, Pageable.unpaged());

        assertEquals(mesas.size(), resultado.getContent().size());
    }

    @Test
    public void testObterMesaPorId() {
        Mesa mesa = new Mesa(1L, 1, 1, RestauranteHelper.gerarRestaurante());

        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));

        Optional<Mesa> resultado = mesaService.obterMesaPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(mesa, resultado.get());
    }

    @Test
    public void testCriarmesa() {
        Mesa mesa = new Mesa(1L, 1, 1, RestauranteHelper.gerarRestaurante());

        when(mesaRepository.save(mesa)).thenReturn(mesa);

        Mesa novaMesa = mesaService.criarMesa(mesa);

        assertEquals(mesa, novaMesa);
    }

    @Test
    public void testAtualizarMesaExistente() {
        Mesa mesaUpdate = new Mesa(1L, 1, 2, RestauranteHelper.gerarRestaurante());

        when(mesaRepository.existsById(1L)).thenReturn(true);
        when(mesaRepository.save(mesaUpdate)).thenReturn(mesaUpdate);

        Optional<Mesa> mesaUpdateResultado = mesaService.atualizarMesa(1L, mesaUpdate);

        verify(mesaRepository, times(1)).existsById(1L);
        verify(mesaRepository, times(1)).save(mesaUpdate);
        assertTrue(mesaUpdateResultado.isPresent());
        assertEquals(mesaUpdate, mesaUpdateResultado.get());
    }

    @Test
    public void testAtualizarMesaNaoExistente() {
        Mesa mesaUpdate = new Mesa(1L, 1, 2, RestauranteHelper.gerarRestaurante());

        when(mesaRepository.existsById(1L)).thenReturn(false);

        Optional<Mesa> mesaUpdateResultado = mesaService.atualizarMesa(1L, mesaUpdate);

        verify(mesaRepository, never()).save(any());
        assertTrue(mesaUpdateResultado.isEmpty());
    }

    @Test
    public void testDeletarMesaExistente() {
        when(mesaRepository.existsById(1L)).thenReturn(true);

        boolean deletado = mesaService.deletarMesaPorId(1L);

        verify(mesaRepository, times(1)).existsById(1L);
        verify(mesaRepository, times(1)).deleteById(1L);
        assertTrue(deletado);
    }

    @Test
    public void testDeletarMesaNaoExistente() {
        when(mesaRepository.existsById(1L)).thenReturn(false);

        boolean deletado = mesaService.deletarMesaPorId(1L);

        verify(mesaRepository, times(1)).existsById(1L);
        verify(mesaRepository, never()).deleteById(any());
        assertFalse(deletado);
    }

}
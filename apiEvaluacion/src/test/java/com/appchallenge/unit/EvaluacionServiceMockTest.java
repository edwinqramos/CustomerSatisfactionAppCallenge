package com.appchallenge.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.appchallenge.common.util.DateUtil;
import com.appchallenge.model.Evaluacion;
import com.appchallenge.repository.EvaluacionRepository;
import com.appchallenge.service.EvaluacionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluacionServiceMockTest{
	
	@Autowired
	private EvaluacionService evaluacionService;

	@MockBean
	private EvaluacionRepository evaluacionRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		Evaluacion evaluacion1 = new Evaluacion();
		evaluacion1.setIdEvaluacion(1000L);
		evaluacion1.setEmail("edwinqramos@gmail.com");
		evaluacion1.setNombres("Edwin Quispe Ramos");
		evaluacion1.setCalificacion(10);
		evaluacion1.setFechaCreacion(new Date());
		
		Evaluacion evaluacion2 = new Evaluacion();
		evaluacion2.setIdEvaluacion(1000L);
		evaluacion2.setEmail("khuimans@gmail.com");
		evaluacion2.setNombres("Karina Huiman");
		evaluacion2.setCalificacion(8);
		evaluacion2.setFechaCreacion(new Date());
		
		List<Evaluacion> listaMock = new ArrayList<Evaluacion>();
		listaMock.add(evaluacion1);
		listaMock.add(evaluacion2);
		
		when(this.evaluacionRepository.save(ArgumentMatchers.any(Evaluacion.class))).thenReturn(evaluacion1);
		when(this.evaluacionRepository.listarRangoFechas(ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class))).thenReturn(listaMock);
	}
	
	@Test
	public void registrarTest() {
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setEmail("edwinqramos@gmail.com");
		evaluacion.setNombres("Edwin Quispe Ramos...");
		evaluacion.setCalificacion(10);
		
		Evaluacion evaluacionInsert = evaluacionService.registrar(evaluacion);
		
		assertNotNull(evaluacionInsert);
		assertTrue(evaluacionInsert.getIdEvaluacion() > 0);
		assertEquals(evaluacionInsert.getEmail(), evaluacion.getEmail());
	}
	
	@Test
	public void modificarTest() {
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setIdEvaluacion(1000L);
		evaluacion.setEmail("edwinqramos@gmail.com");
		evaluacion.setNombres("Edwin Quispe Ramos...");
		evaluacion.setCalificacion(3);
		
		when(this.evaluacionRepository.save(ArgumentMatchers.any(Evaluacion.class))).thenReturn(evaluacion);
		
		Evaluacion evaluacionUpdate= evaluacionService.modificar(evaluacion);
		
		assertNotNull(evaluacionUpdate);
		assertTrue(evaluacionUpdate.getIdEvaluacion() > 0);
		assertEquals(evaluacionUpdate.getCalificacion(), evaluacion.getCalificacion());
	}
	
	
	@Test
	public void listarRangoFechasTest() throws Exception {
		
		Date fechaInicio = new Date();
		Date fechaFin = new Date();
		
		Calendar calInicio = new GregorianCalendar(); 
		calInicio.setTime(fechaInicio);
		calInicio.set(Calendar.HOUR_OF_DAY, 0);
		calInicio.set(Calendar.MINUTE, 0);
		calInicio.set(Calendar.SECOND, 0);
		
		Calendar calFin = new GregorianCalendar(); 
		calFin.setTime(fechaFin);
		calFin.set(Calendar.HOUR_OF_DAY, 23);
		calFin.set(Calendar.MINUTE, 59);
		calFin.set(Calendar.SECOND, 59);
		
		List<Evaluacion> lista = this.evaluacionService.listarRangoFechas(calInicio.getTime(), calFin.getTime());

		assertNotNull(lista);
		assertTrue(lista.size() > 0);
	}
}

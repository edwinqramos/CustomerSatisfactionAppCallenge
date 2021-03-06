package com.appchallenge.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.appchallenge.model.Evaluacion;
import com.appchallenge.service.EvaluacionService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluacionServiceTest {

	@Autowired
	private EvaluacionService evaluacionService;

	private Long idEvaluacion;

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

		this.idEvaluacion = evaluacionInsert.getIdEvaluacion();
	}

	@Test
	public void modificarTest() {
		// Registramos para luego modificar
		this.registrarTest();

		Evaluacion evaluacionSearch = evaluacionService.buscar(this.idEvaluacion);

		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setEmail("edwinqramos@gmail.com");
		evaluacion.setNombres("Edwin Quispe Ramos...");
		evaluacion.setCalificacion(8);// Valor modificado

		evaluacionSearch.setCalificacion(evaluacion.getCalificacion());

		Evaluacion evaluacionUpdate = evaluacionService.modificar(evaluacion);

		assertNotNull(evaluacionUpdate);
		assertEquals(evaluacionUpdate.getIdEvaluacion(), evaluacion.getIdEvaluacion());
		assertEquals(evaluacionUpdate.getCalificacion(), evaluacion.getCalificacion());
	}

	@Test
	public void listarRangoFechasTest() throws Exception {
		// Registrar un item
		this.registrarTest();

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
		
		List<Evaluacion> lista = evaluacionService.listarRangoFechas(calInicio.getTime(), calFin.getTime());

		assertNotNull(lista);
		assertTrue(lista.size() > 0);
	}
}

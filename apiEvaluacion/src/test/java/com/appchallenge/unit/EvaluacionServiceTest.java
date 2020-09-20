package com.appchallenge.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
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

		// Cambiar por GregorianCalendar
		fechaInicio.setHours(0);
		fechaInicio.setMinutes(0);
		fechaInicio.setSeconds(0);

		fechaFin.setHours(23);
		fechaFin.setMinutes(59);
		fechaFin.setSeconds(59);

		List<Evaluacion> lista = evaluacionService.listarRangoFechas(fechaInicio, fechaFin);

		assertNotNull(lista);
		assertTrue(lista.size() > 0);
	}
}

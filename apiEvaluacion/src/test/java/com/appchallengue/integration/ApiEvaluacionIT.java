package com.appchallengue.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.appchallenge.AppChallengeSpringBootApplication;
import com.appchallenge.common.util.Constantes;
import com.appchallenge.dto.RespuestaApi;
import com.appchallenge.model.Evaluacion;
import com.appchallenge.repository.EvaluacionRepository;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppChallengeSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiEvaluacionIT {

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	private HttpHeaders headers = new HttpHeaders();
	
	private Evaluacion evaluacionTest;
	private List<Evaluacion> listEvaluacionTest;
	
	private Gson gson = new Gson();
	
	@MockBean
	private EvaluacionRepository evaluacionRepository;
	
	@Before
	public void setUp() throws Exception {
		this.evaluacionTest = new Evaluacion();
		this.evaluacionTest.setIdEvaluacion(1000L);
		this.evaluacionTest.setEmail("edwinqramos@gmail.com");
		this.evaluacionTest.setNombres("Edwin Quispe Ramos");
		this.evaluacionTest.setCalificacion(10);
		this.evaluacionTest.setFechaCreacion(new Date());
		
		this.listEvaluacionTest = new ArrayList<Evaluacion>();
		this.listEvaluacionTest.add(this.evaluacionTest);
		
		when(this.evaluacionRepository.save(ArgumentMatchers.any(Evaluacion.class))).thenReturn(this.evaluacionTest);
		when(this.evaluacionRepository.listarRangoFechas(ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class))).thenReturn(this.listEvaluacionTest);
		when(this.evaluacionRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(this.evaluacionTest));
	}

	@Test
	public void registrosExitosoTest() throws JSONException {
		String endPointRegistro = "/api/v1/evaluaciones/registros";
		
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setEmail("edwinqramos@gmail.com");
		evaluacion.setNombres("Edwin Quispe Ramos");
		evaluacion.setCalificacion(10);

		HttpEntity<Evaluacion> entity = new HttpEntity<Evaluacion>(evaluacion, headers);

		ResponseEntity<RespuestaApi> response = restTemplate.exchange(
				createURLWithPort(endPointRegistro),
				HttpMethod.POST, entity, RespuestaApi.class);
		
		RespuestaApi respuestaResponse = response.getBody();

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(respuestaResponse.getStatus(), Constantes.CODIGO_RESPUESTA_GENERAL_EXITO);
		assertNotNull(respuestaResponse.getBody());
		
		//Opcional: Validar que los datos enviados a registrar en la peticion sean devueltos en el Json Body
		/*
		JSONAssert.assertEquals(gson.toJson(this.evaluacionTest), gson.toJson(respuestaResponse.getBody()) ,
	            new CustomComparator(JSONCompareMode.STRICT, new Customization("fechaCreacion", (o1, o2) -> true))
	            );
	    */
	}
	
	@Test
	public void modificacionesExitosaTest() throws JSONException {
		String endPointModificacion = "/api/v1/evaluaciones/modificaciones";
		
		Evaluacion evaluacion = new Evaluacion();
		evaluacion.setIdEvaluacion(this.evaluacionTest.getIdEvaluacion());
		evaluacion.setEmail("edwinqramos@gmail.com");
		evaluacion.setNombres("Edwin Quispe Ramos");
		evaluacion.setCalificacion(2);

		HttpEntity<Evaluacion> entity = new HttpEntity<Evaluacion>(evaluacion, headers);

		ResponseEntity<RespuestaApi> response = restTemplate.exchange(
				createURLWithPort(endPointModificacion),
				HttpMethod.PUT, entity, RespuestaApi.class);
		
		RespuestaApi respuestaResponse = response.getBody();

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(respuestaResponse.getStatus(), Constantes.CODIGO_RESPUESTA_GENERAL_EXITO);
		
		System.out.println("respuestaResponse.getBody()=>"+respuestaResponse.getBody());
		assertNotNull(respuestaResponse.getBody());
		
	}
	
	
	@Test
	public void listadoRangoFechasTest() throws JSONException {
		String endPointListado = "/api/v1/evaluaciones/listadoRangoFechas/20-09-2020/20-09-2020";
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort(endPointListado),
				HttpMethod.GET, entity, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
		
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
}

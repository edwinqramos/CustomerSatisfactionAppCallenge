package com.appchallenge.controller.api;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appchallenge.common.util.Constantes;
import com.appchallenge.common.util.DateUtil;
import com.appchallenge.dto.RespuestaApi;
import com.appchallenge.model.Evaluacion;
import com.appchallenge.service.EvaluacionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping(value = { "/api/v1/evaluaciones" })
public class ApiEvaluacion {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiEvaluacion.class);

	@Autowired
	private EvaluacionService evaluacionService;

	@GetMapping(value = "/listadoRangoFechas/{fechaInicio}/{fechaFin}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Listado de evaluaciones por Rango de fechas")
	@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Error Server")
	public ResponseEntity<List<Evaluacion>> listadoRangoFechas(
			@ApiParam(example = "19-09-2020", required = true) @PathVariable String fechaInicio,
			@ApiParam(example = "19-09-2020", required = true) @PathVariable String fechaFin) {
		try {
			
			Date dateInicio = DateUtil.stringToDate(fechaInicio, DateUtil.FORMATO_DIA_DDMMYYYY);
			Date dateFin = DateUtil.stringToDate(fechaFin, DateUtil.FORMATO_DIA_DDMMYYYY);
			
			Calendar calInicio = new GregorianCalendar(); 
			calInicio.setTime(dateInicio);
			calInicio.set(Calendar.HOUR_OF_DAY, 0);
			calInicio.set(Calendar.MINUTE, 0);
			calInicio.set(Calendar.SECOND, 0);
			
			Calendar calFin = new GregorianCalendar(); 
			calFin.setTime(dateFin);
			calFin.set(Calendar.HOUR_OF_DAY, 23);
			calFin.set(Calendar.MINUTE, 59);
			calFin.set(Calendar.SECOND, 59);
			

			List<Evaluacion> lista = evaluacionService.listarRangoFechas(calInicio.getTime(), calFin.getTime());

			return new ResponseEntity<List<Evaluacion>>(lista, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error: ", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/registros", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Registra una nueva evaluación")
	@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Error Server", response = RespuestaApi.class)
	public ResponseEntity<RespuestaApi> registrar(@RequestBody Evaluacion evaluacion) {
		try {

			Evaluacion evaluacionInsert = evaluacionService.registrar(evaluacion);

			if (evaluacionInsert == null)
				throw new Exception("No se pudo registrar.");

			return new ResponseEntity<RespuestaApi>(new RespuestaApi(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, evaluacionInsert), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error: ", e);
			return new ResponseEntity<>(new RespuestaApi(Constantes.CODIGO_RESPUESTA_GENERAL_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/modificaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Modifica una evaluación")
	@ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Error Server", response = RespuestaApi.class)
	public ResponseEntity<RespuestaApi> modificar(@RequestBody Evaluacion evaluacion) {
		try {

			Evaluacion evaluacionSearch = evaluacionService.buscar(evaluacion.getIdEvaluacion());
			if (evaluacionSearch == null)
				throw new Exception("Error, evaluación no encontrada.");

			evaluacionSearch.setNombres(evaluacion.getNombres());
			evaluacionSearch.setEmail(evaluacion.getEmail());
			evaluacionSearch.setCalificacion(evaluacion.getCalificacion());

			Evaluacion evaluacionUpdate = evaluacionService.modificar(evaluacionSearch);

			if (evaluacionUpdate == null)
				throw new Exception("No se pudo actualizar.");
			
			return new ResponseEntity<RespuestaApi>(new RespuestaApi(Constantes.CODIGO_RESPUESTA_GENERAL_EXITO, evaluacionUpdate), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error: ", e);
			return new ResponseEntity<>(new RespuestaApi(Constantes.CODIGO_RESPUESTA_GENERAL_ERROR, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

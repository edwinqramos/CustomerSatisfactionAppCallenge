package com.appchallenge.service;

import java.util.Date;
import java.util.List;

import com.appchallenge.model.Evaluacion;

public interface EvaluacionService {
	
	public List<Evaluacion> listarRangoFechas(Date fechaInicio, Date fechaFin);
	
	public Evaluacion registrar(Evaluacion evaluacion);
	
	public Evaluacion modificar(Evaluacion evaluacion);
	
	public Evaluacion buscar(Long idEvaluacion);

}

package com.appchallenge.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appchallenge.model.Evaluacion;
import com.appchallenge.repository.EvaluacionRepository;
import com.appchallenge.service.EvaluacionService;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(EvaluacionServiceImpl.class);
	
	@Autowired
	private EvaluacionRepository evaluacionRepository;

	@Override
	public List<Evaluacion> listarRangoFechas(Date fechaInicio, Date fechaFin) {
		try {
			return evaluacionRepository.listarRangoFechas(fechaInicio, fechaFin);
		} catch (Exception e) {
			LOGGER.error("Error EvaluacionServiceImpl#listarRangoFechas: " + e);
			return null;
		}
	}

	@Override
	public Evaluacion registrar(Evaluacion evaluacion) {
		return evaluacionRepository.save(evaluacion);
	}

	@Override
	public Evaluacion modificar(Evaluacion evaluacion) {
		return evaluacionRepository.save(evaluacion);
	}

	@Override
	public Evaluacion buscar(Long idEvaluacion) {
		Optional<Evaluacion> evaluacionSearch = evaluacionRepository.findById(idEvaluacion);
		if(!evaluacionSearch.isPresent()) return null;
		
		return evaluacionSearch.get();
	}

}

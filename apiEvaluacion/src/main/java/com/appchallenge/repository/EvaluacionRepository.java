package com.appchallenge.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.appchallenge.model.Evaluacion;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
	
	@Query("SELECT e FROM Evaluacion e WHERE e.fechaCreacion BETWEEN :fechaDesde AND :fechaHasta")
	public List<Evaluacion> listarRangoFechas(
			@Param("fechaDesde") Date fechaDesde,
			@Param("fechaHasta") Date fechaHasta);
}

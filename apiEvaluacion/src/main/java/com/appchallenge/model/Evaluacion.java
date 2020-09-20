package com.appchallenge.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Evaluacion implements Serializable {

	private static final long serialVersionUID = -7909088622519547624L;

	private Long idEvaluacion;
	private String email;
	private String nombres;
	private int calificacion;
	private Date fechaCreacion;
	
	public Evaluacion() {
		
	}

	public Evaluacion(Long idEvaluacion, String email, String nombres, int calificacion, Date fechaCreacion) {
		super();
		this.idEvaluacion = idEvaluacion;
		this.email = email;
		this.nombres = nombres;
		this.calificacion = calificacion;
		this.fechaCreacion = fechaCreacion;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evaluacion", unique = true, nullable = false)
	public Long getIdEvaluacion() {
		return idEvaluacion;
	}

	public void setIdEvaluacion(Long idEvaluacion) {
		this.idEvaluacion = idEvaluacion;
	}

	@Column(name = "email", nullable = false, length = 120)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "nombres", nullable = false, length = 100)
	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	@Column(name = "calificacion", nullable = false, length = 1)
	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 19)
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}

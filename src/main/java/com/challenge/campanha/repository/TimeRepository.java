package com.challenge.campanha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.challenge.campanha.model.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

	@Query("SELECT time FROM Time time WHERE UPPER(nm_time) = UPPER(:nome)")
	Time buscarPorNome(@Param("nome") String nome);
	
	@Query("SELECT time FROM Time time WHERE cd_time = :id")
	Time buscarPorId(@Param("id") Long id);

}

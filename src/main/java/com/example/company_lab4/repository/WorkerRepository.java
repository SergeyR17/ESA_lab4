package com.example.company_lab4.repository;

import com.example.company_lab4.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    @Query("select t from Worker t where t.personnelNumber = :number")
    Optional<Worker> findByPersonnelNumber(@Param("number") int number);
}
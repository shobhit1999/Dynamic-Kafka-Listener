package com.dynamic.kafkalistener.dao;

import com.dynamic.kafkalistener.entities.Properties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesDao
    extends JpaRepository<Properties, Long>, JpaSpecificationExecutor<Properties> {

}

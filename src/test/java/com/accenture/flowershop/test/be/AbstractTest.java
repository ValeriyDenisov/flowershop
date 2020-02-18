package com.accenture.flowershop.test.be;

import com.accenture.flowershop.be.api.dao.AbstractDAO;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.be.impl.dao.AbstractDAOImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/config/test-application-context.xml")
@Transactional
public abstract class AbstractTest {
}

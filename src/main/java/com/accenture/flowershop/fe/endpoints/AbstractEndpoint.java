package com.accenture.flowershop.fe.endpoints;

import com.accenture.flowershop.be.api.exceptions.EntityException;
import com.accenture.flowershop.be.api.functional.FindAllEntities;
import com.accenture.flowershop.be.api.functional.FindOneEntity;
import com.accenture.flowershop.be.api.functional.logicFromService;
import com.accenture.flowershop.be.entity.common.AbstractEntity;
import com.accenture.flowershop.fe.endpoints.xjc.OperationStatus;
import com.accenture.flowershop.fe.endpoints.xjc.Response;
import com.accenture.flowershop.fe.endpoints.xjc.Status;
import com.accenture.flowershop.fe.endpoints.xjc.XmlAbstractEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractEndpoint<X extends XmlAbstractEntity, E extends AbstractEntity> {
    public static final String SCHEMA = "http://accenture.com/flowershop/schemas";
    public static final String ERROR_SOAP_REQUEST_NULL = "Request is null";
    public static final String ERROR_ENTITY_NOT_FOUND = "Entity not found";
    public static final String ERROR_ENTITIES_NOT_FOUND = "No entity found";

    @Autowired
    DozerBeanMapper dozerMapper;

    protected Response buildResponse(Status status, String message) {
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setMessage(message);
        operationStatus.setStatus(status);

        Response response = new Response();
        response.setStatus(operationStatus);
        return response;
    }

    protected Response getResponseFromEntityService(Object request, logicFromService logicFromService) {
        Response response;

        if (request == null) {
            response = buildResponseIfRequestNull();
        } else {
            try {
                logicFromService.getLogicFromService();
                response = buildResponse();
            } catch (EntityException e) {
                response = buildResponse(Status.ERROR, e.getMessage());
            }
        }
        return response;
    }

    protected EndpointInformation<X> getResponseFromFindingOneEntity(Object request, FindOneEntity<E> getEntity) {
        Response response;
        X xmlEntity = null;

        if (request == null) {
            response = buildResponseIfRequestNull();
        } else {
            E entity = getEntity.findOneEntity();
            if (entity == null) {
                response = buildResponse(Status.ERROR, ERROR_ENTITY_NOT_FOUND);
            } else {
                response = buildResponse();
                xmlEntity = dozerMapper.map(entity, getXmlEntityType());
            }
        }
        return new EndpointInformation<X>(response, xmlEntity);
    }

    protected EndpointInformation<X> getResponseFromFindingAllEntities(Object request, List<X> xmlEntitiesList,
                                                                       FindAllEntities<E> findAllEntities) {
        Response response;

        if (request == null) {
            response = buildResponse(Status.ERROR, ERROR_SOAP_REQUEST_NULL);
        } else {
            List<E> entities = findAllEntities.findAllEntities();
            if (CollectionUtils.isNotEmpty(entities)) {
                for (E entity : entities) {
                    xmlEntitiesList.add(dozerMapper.map(entity, getXmlEntityType()));
                }
                response = buildResponse();
            } else {
                response = buildResponse(Status.ERROR, ERROR_ENTITIES_NOT_FOUND);
            }
        }

        return new EndpointInformation<X>(response, xmlEntitiesList);
    }

    protected Response buildResponseIfRequestNull() {
        return buildResponse(Status.ERROR, ERROR_SOAP_REQUEST_NULL);
    }

    protected Response buildResponse() {
        return buildResponse(Status.SUCCESS, null);
    }

    protected abstract Class<X> getXmlEntityType();

    protected static class EndpointInformation<X extends XmlAbstractEntity> {
        private Response response;
        private X xmlEntity;
        private List<X> xmlEntities;

        public EndpointInformation(Response response) {
            this.response = response;
        }

        public EndpointInformation(Response response, X xmlEntity) {
            this.response = response;
            this.xmlEntity = xmlEntity;
        }

        public EndpointInformation(Response response, List<X> xmlEntities) {
            this.response = response;
            this.xmlEntities = xmlEntities;
        }

        public Response getResponse() {
            return response;
        }

        public X getXmlEntity() {
            return xmlEntity;
        }

        public List<X> getXmlEntities() {
            return xmlEntities;
        }
    }

}

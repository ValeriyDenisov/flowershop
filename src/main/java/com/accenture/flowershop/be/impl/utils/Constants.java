package com.accenture.flowershop.be.impl.utils;

public abstract class Constants {
    public static final String ERROR_ENTITY_FIELD_NULL = "Field {0} is null";
    public static final String ERROR_ENTITY_FIELD_EMPTY = "Field {0} empty";

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    public static final String ENTITY_ID = "id";

    public static final String ENTITY_ADDRESS = "Address";
    public static final String ENTITY_CUSTOMER = "Customer";
    public static final String ENTITY_ORDER = "Order";
    public static final String ENTITY_FLOWER = "Flower";
    public static final String ENTITY_USER = "User";

    public static final String ADDRESS_STREET = "street";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_CODE = "code";
    public static final String ADDRESS_BUILDING = "building";

    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_SECOND_NAME = "secondName";
    public static final String CUSTOMER_FATHER_NAME = "fatherName";
    public static final String CUSTOMER_ADDRESS = "address";
    public static final String CUSTOMER_PHONE = "phone";
    public static final String CUSTOMER_BALANCE = "balance";
    public static final String CUSTOMER_DISCOUNT = "discount";
    public static final String CUSTOMER_EMAIL = "email";


    public static final String ORDER_PRICE = "price";
    public static final String ORDER_OPEN_DATE = "openDate";
    public static final String ORDER_CLOSE_DATE = "closeDate";
    public static final String ORDER_IS_ACTIVE = "isActive";
    public static final String ORDER_CUSTOMER = "customer";

    public static final String FLOWER_NAME = "name";
    public static final String FLOWER_PRICE = "price";
    public static final String FLOWER_QUANTITY_IN_STOCK = "quantityInStock";

    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
}

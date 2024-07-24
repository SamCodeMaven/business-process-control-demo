package uz.xnarx.businessprocesscontroldemo.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectEndpoints {
    public static final String BILLING = "/api/bill/saveBill";//open for both
    public static final String BILL_APPROVE = "/api/bill/approve/{id}";//open for admin
    public static final String BILLINGS = "/api/bill/getAll";//open for admin
    public static final String PRODUCT_SAVE="/api/product/save";//open for both
    public static final String PRODUCTS="/api/product/getAll";//open for both
    public static final String PRODUCT_DETAILS="/api/product/getProduct/{id}";//OPEN FOR BOTH
    public static final String PRODUCT_RESTOCK="/api/product/getProduct/{id}/restock";//open for both
    public static final String PRODUCT_SOLD="/api/product/decreaseQuantities";//open for both
    public static final String CLIENT_REGISTER="/api/client/saveOrEdit";//open for both
    public static final String CLIENTS="/api/client/getClients";//open for admin
    public static final String CLIENT_NAME="/api/client/searchClient";
    public static final String USER_AUTH="/api/auth/authenticate";
    public static final String USER_TOKEN="/api/auth/refresh-token";
    public static final String USER_REGISTER="/api/user/register";
    public static final String USERS="/api/user/getAll";
    public static final String USER_ID="/api/user/getById/{id}";
    public static final String USER_INFO="/api/user/info";
    public static final String USER_ENABLE="/api/user/enable/{userId}";
    public static final String USER_DISABLE="/api/user/disable/{userId}";
    //public static final String CLIENTS_MANAGER="/api/client/getClients/{manager_id}";//open for both
    //public static final String BILL_WORKER = "/api/bill/getAll/{workerId}";//open for both
}

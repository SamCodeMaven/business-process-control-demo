package uz.xnarx.businessprocesscontroldemo.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProjectEndpoints {
    public static final String BILLING = "/api/bill/saveBill";
    public static final String BILLINGS = "/api/bill/getAll";
    public static final String BILL_WORKER = "/api/bill/getAll/{workerId}";
    public static final String PRODUCT_SAVE="/api/product/save";
    public static final String PRODUCTS="/api/product/getAll";
    public static final String PRODUCT_DETAILS="/api/product/getProduct/{id}";
    public static final String PRODUCT_RESTOCK="/api/product/getProduct/{id}/restock";
    public static final String PRODUCT_SOLD="/api/product/decreaseQuantities";
}

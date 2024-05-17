package ca.com.idealimport.common.constants;

public class ErrorConstants {

    public static final String PARTY_NOT_PRESENT = "no record present for this id %s ";
    public static final String PARTY_NAME_NOT_PRESENT = "no name present for this  %s ";
    public static final String RECORD_NOT_PRESENT ="record not present  %s";
    public static final String PURCHASE_ORDER_LINE_NOT_PRESENT = "no purchase line order item present %s ";
    public static final String PURCHASE_ORDERS_NOT_PRESENT = "no purchase orders item present %s ";
    public static final String PRODUCT_NOT_PRESENT = "no product present into inventory for this id %s ";
    public static final String PRODUCT_NOT_DELETE = "You cannot delete this product because" +
            " purchase order already created ";
    public static final String PRODUCT_ITEM_NOT_FOUND = "No product item present for the item %s with color %";

    private ErrorConstants (){

    }
}

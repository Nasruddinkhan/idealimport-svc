package ca.com.idealimport.common.constants;

public class MessageConstants {
    public static final String SALE_ORDER_NAME_NOT_BLANK = "{sale.order.status.name}";
    public static final String DESCRIPTION_NOT_BLANK = "{field.description.not.blank}";
    public static final String FIELD_VLD_SIZE = "{field.validation.error.size}";
    public static final String  TRACKING_NOT_FOUND = "sale.order.not.found.tracking";
    public static final String SALE_ORDER_RES_MSG = "Dear %s,</br> You have create order successfully " +
            "please find the tracking number %s for your future reference";
    public static final String  STATUS_FILE_PATH = "json/sale-order-status.json";


    private MessageConstants() {
    }
}

package ca.com.idealimport.common.constants;

public class MessageConstants {
    public static final String SALE_ORDER_NAME_NOT_BLANK = "{sale.order.status.name}";
    public static final String DESCRIPTION_NOT_BLANK = "{field.description.not.blank}";
    public static final String FIELD_VLD_SIZE = "{field.validation.error.size}";
    public static final String  TRACKING_NOT_FOUND = "sale.order.not.found.tracking";
    public static final String SALE_ORDER_RES_MSG = "Dear %s,</br> You have create order successfully " +
            "please find the tracking number %s for your future reference";
    public static final String STATUS_FILE_PATH = "classpath:templates/json/sale-order-status.json";
    public static final String PERMISSION_PATH = "classpath:templates/json/permission.json";
    public static final String ROLE_PATH = "classpath:templates/json/role.json";
    public static final String USER_PATH = "classpath:templates/json/user.json";
    public static final String CHANGE_PASSWORD_MSG ="change.password.success.msg" ;
    public static final String PRICE_PRESENT = "price.list.present";
    public static final String NO_CUSTOMER_PARTY = "customer.party.not.found";

    public static final String NO_SO_ORDER_FOUND = "sale.order.item.not.found";
    public static final String SO_ORDER_STATUS_UPDATE = "sale.order.status.update";

    public static final String NO_AMT_FOUND = "sale.order.amount.not.found";
    public static final String SO_AMT_UPD_MSG = "sale.order.update.amount.msg";

    private MessageConstants() {
    }
}

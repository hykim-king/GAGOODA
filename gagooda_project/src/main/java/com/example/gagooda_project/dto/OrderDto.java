package com.example.gagooda_project.dto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private String orderId; // order_id PK NN 주문 아이디 AUTO_INCREMENT
    private int userId; // user_id (USER:ORDER)1:N NN 사용자 ID
    private int addressId; // address_id 배송지 아이디
    private Date regDate;  // reg_date 주문일자 NN
    private int totalPrice; // total_price 총 가격 NN
    private String userName; // user_name NN
    private String userEmail; // user_email NN
    private String userPhone; // user_phone NN
    private String postCode; // post_code NN
    private String address; // NN
    private String addressDetail; // address_detail NN
    private String receiverName; // receiver_name NN
    private String receiverPhone; // receiver_phone NN
    private boolean elevator; // NN
    private String imgPath; // img_path NN
    private String oDet; // o_det NN
    private List<OrderDetailDto> orderDetailList;   // 1:N 주문 상세 테이블 연결
    private CommonCodeDto commonCodeDto;

}

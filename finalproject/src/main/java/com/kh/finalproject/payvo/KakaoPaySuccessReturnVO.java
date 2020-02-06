package com.kh.finalproject.payvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPaySuccessReturnVO {

	private String aid, tid, cid, sid;
	private String partner_order, partner_user_id, payment_method_type;
	private KakaoPayAmountVO amount;
	private KakaoPaySuccessCardInfoVO card_info;
	private String item_name, item_code;
	private int quantity;
	private String created_at, approved_at;
	private String payload;
	
	
}
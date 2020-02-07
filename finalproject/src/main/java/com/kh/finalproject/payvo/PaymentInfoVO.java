package com.kh.finalproject.payvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PaymentInfoVO {
	private String partner_user_id, item_name;
	private int total_amount, quantity, tax_free_amount, token;
	
	
	
}

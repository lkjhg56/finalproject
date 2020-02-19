package com.kh.finalproject.payentity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PayListDto {
 
	private int no,  partner_order_id;
	private String partner_user_id, cid, item_name;
	private String created_at;
	private int quantity, total_amount;
	private String status, aid, tid;
}

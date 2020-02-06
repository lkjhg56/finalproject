package com.kh.finalproject.payvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPaySuccessReadyVO {

	private String cid, tid, partner_order_id, partner_user_id, pg_token;
}

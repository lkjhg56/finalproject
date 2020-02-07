package com.kh.finalproject.payvo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPaySuccessCardInfoVO {
	private String purchase_corp, purchase_corp_code,
	issuer_corp, issuer_corp_code, kakaopay_purchase_corp,
	kakaopay_purchase_corp_code, kakaopay_issuer_corp,
	kakaopay_issuer_corp_code, bin, card_type, install_month, approved_id, card_mid, interest_free_install, card_item_code;
}

package com.kh.finalproject.payentity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CashListDto {
	private int cash_no;
	private int price, token;
	private String product;
}

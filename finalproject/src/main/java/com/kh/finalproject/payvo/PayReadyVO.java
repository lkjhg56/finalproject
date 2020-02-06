package com.kh.finalproject.payvo;

public interface PayReadyVO {
	String getItem_name();
	String getPartner_order_id();
	String getPartner_user_id();
	int getQuantity();
	int getTotal_amount();
	int getVat_amount();
	int getTax_free_amount();
}

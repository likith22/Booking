package com.ola.booking.util;

import java.util.UUID;

public class TransactionIdGenerater {
	
	public Integer buildTransactionId() {
		UUID tempTransactionId = UUID.randomUUID();
		String str = ""+tempTransactionId;
		int uid = str.hashCode();
		String filterStr=""+uid;
        str=filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
	}
}

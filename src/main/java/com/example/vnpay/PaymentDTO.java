package com.example.vnpay;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PaymentDTO {
	int amount;
	String description;
	String bankCode;
}

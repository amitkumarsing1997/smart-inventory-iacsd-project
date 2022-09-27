package com.iacsd.demo.util.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Recipient {

	private String recipient;
	private RecipientType type;

	public Recipient(String recipient) {
		this.setRecipient(recipient);
		this.setType(RecipientType.TO);
	}

	public Recipient(String recipient, RecipientType type) {
		this.setRecipient(recipient);
		this.setType(type);
	}
}

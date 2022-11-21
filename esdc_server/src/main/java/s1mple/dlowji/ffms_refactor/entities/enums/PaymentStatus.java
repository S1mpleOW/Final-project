package s1mple.dlowji.ffms_refactor.entities.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
	SUCCEEDED,
	PROCESSING,
	FAILED;

	private String status;

	private PaymentStatus(){}

	private PaymentStatus(String status) {
		this.status = status;
	}
}

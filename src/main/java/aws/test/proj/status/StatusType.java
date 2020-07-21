package aws.test.proj.status;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusType {

	SUCCESS("Success"),
	EMPTY_POST_LIST("Post list is empty"),
	NO_ID("Mandatory field 'id' is missing");

	private final String text;

	StatusType(String text) {

		this.text = text;
	}

	@JsonValue
	public String text() {

		return text;
	}
}

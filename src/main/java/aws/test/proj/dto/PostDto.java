package aws.test.proj.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostDto {

	private final Integer id;
	private final String title;
	private final String body;
	private final Integer userId;

	@JsonCreator
	public PostDto(@JsonProperty("id") Integer id, @JsonProperty("title") String title, @JsonProperty("body") String body, @JsonProperty("userId") Integer userId) {

		this.id = id;
		this.title = title;
		this.body = body;
		this.userId = userId;
	}

	public Integer getId() {

		return id;
	}

	public String getTitle() {

		return title;
	}

	public String getBody() {

		return body;
	}

	public Integer getUserId() {

		return userId;
	}

	@Override
	public String toString() {

		return "PostDto{" + "id=" + id + ", title='" + title + '\'' + ", body='" + body + '\'' + ", userId=" + userId + '}';
	}
}

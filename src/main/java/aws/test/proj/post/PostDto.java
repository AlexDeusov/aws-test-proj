package aws.test.proj.post;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostDto {

	private final Integer userId;
	private final Integer id;
	private final String title;
	private final String body;

	@JsonCreator
	public PostDto(@JsonProperty("userId") Integer userId, @JsonProperty("id") Integer id, @JsonProperty("title") String title, @JsonProperty("body") String body) {

		this.id = id;
		this.title = title;
		this.body = body;
		this.userId = userId;
	}

	public Integer getUserId() {

		return userId;
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
}

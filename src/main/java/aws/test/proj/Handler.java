package aws.test.proj;

import aws.test.proj.exception.ATPException;
import aws.test.proj.exception.CompressionException;
import aws.test.proj.post.PostDto;
import aws.test.proj.request.Request;
import aws.test.proj.status.StatusType;
import aws.test.proj.utils.HttpUtils;
import aws.test.proj.utils.S3Utils;
import aws.test.proj.utils.SystemManagerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.utils.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

public class Handler {

	private final ObjectMapper MAPPER = new ObjectMapper();

	public StatusType handleRequest(Request request) throws ATPException {

		Integer userId = request.getId();
		if (Objects.nonNull(userId)) {
			try {
				String baseUrl = SystemManagerUtils.getRestApiBaseUrlParameter();

				var response = HttpUtils.get(baseUrl + userId);
				var posts = MAPPER.readValue(response.body(), new TypeReference<List<PostDto>>() {});

				if (CollectionUtils.isNullOrEmpty(posts)) {
					return StatusType.EMPTY_POST_LIST;
				}

				ByteBuffer byteBuffer = compress(posts);
				S3Utils.uploadObject(byteBuffer, userId);

				return StatusType.SUCCESS;
			} catch (Exception e) {
				throw new ATPException(e.getMessage());
			}
		}
		return StatusType.NO_ID;
	}

	private ByteBuffer compress(List<PostDto> posts) throws CompressionException {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (GZIPOutputStream gzipStream = new GZIPOutputStream(outputStream)) {
			gzipStream.write(MAPPER.writeValueAsBytes(posts));
		} catch (IOException e) {
			throw new CompressionException(posts);
		}
		return ByteBuffer.wrap(outputStream.toByteArray());
	}
}

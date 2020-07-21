package aws.test.proj;

import aws.test.proj.dto.PostDto;
import aws.test.proj.exception.CompressionException;
import aws.test.proj.exception.SystemManagerException;
import aws.test.proj.utils.HttpUtils;
import aws.test.proj.utils.S3Utils;
import aws.test.proj.utils.SystemManagerUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.zip.DeflaterOutputStream;

public class Handler implements RequestHandler<Map<String, Integer>, String> {

	private final ObjectMapper MAPPER = new ObjectMapper();

	@Override
	public String handleRequest(Map<String, Integer> input, Context context) {

		LambdaLogger logger = context.getLogger();
		System.getenv().keySet().forEach(key -> {
			try {
				String value = SystemManagerUtils.findParameterByKey(key);
				if (Objects.nonNull(value)) {
					logger.log("Getting '" + value + "' value by '" + key + "' key.\n");
				}
			} catch (SystemManagerException e) {
				logger.log(e.getMessage() + "\n");
			}
		});

		Integer id = input.get("id");
		if (Objects.nonNull(id)) {
			try {
				String baseUrl = SystemManagerUtils.getRestApiBaseUrlParameter();

				var response = HttpUtils.get(baseUrl + id);
				PostDto postDto = MAPPER.readValue(response.body(), PostDto.class);

				ByteBuffer byteBuffer = compress(postDto);

				PutObjectResponse uploadResult = S3Utils.uploadObject(byteBuffer, postDto.getUserId());

				return "Operation has completed successfully (upload result: " + uploadResult.toString() + ")";
			} catch (Exception e) {
				return e.getMessage();
			}
		}
		return "Mandatory field 'id' is missing!";
	}

	private ByteBuffer compress(PostDto dto) throws CompressionException {

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try (OutputStream out = new DeflaterOutputStream(outputStream)) {
			out.write(MAPPER.writeValueAsBytes(dto));
		} catch (IOException e) {
			throw new CompressionException(dto);
		}
		return ByteBuffer.wrap(outputStream.toByteArray());
	}
}

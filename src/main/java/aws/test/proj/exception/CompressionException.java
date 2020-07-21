package aws.test.proj.exception;

public class CompressionException extends ATPException {

	public CompressionException(Object object) {

		super("Cannot compress " + object.getClass().getName() + " object");
	}
}

package tk.pratanumandal.expr4j;

public abstract class Token {

	protected String value;

	public Token(String value) {
		super();
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
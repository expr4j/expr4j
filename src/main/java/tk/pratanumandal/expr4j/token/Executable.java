package tk.pratanumandal.expr4j.token;

import java.util.List;

public abstract class Executable<T> implements Token {
	
	public final String label;
	public final Operation<T> operation;

	public Executable(String label, Operation<T> operation) {
		this.label = label;
		this.operation = operation;
	}
	
	public T evaluate(List<T> operands) {
		return this.operation.execute(operands);
	}
	
	@FunctionalInterface
	public interface Operation<T> {
		
		public abstract T execute(List<T> operands);

	}

}

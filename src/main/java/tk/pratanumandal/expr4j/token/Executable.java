package tk.pratanumandal.expr4j.token;

import java.util.List;

public abstract class Executable<T> implements Token {
	
	public final Operation<T> operation;

	public Executable(Operation<T> operation) {
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

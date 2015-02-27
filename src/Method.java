
public enum METHOD {
	DIAGONAL_ONE("DiagonalOne"),
	STRING_ONE("StringOne"),
	COLUMN_ONE("ColumnOne");
	private String value;
	METHOD(String s){
		this.setValue(s);
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
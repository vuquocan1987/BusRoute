package cs.edu.busroute.db.helper;

public enum TableTypeEnum {
	FORWARD("Bus_Station_Fw"), BACKWARD("Bus_Station_Bw");
	private String tableName;

	private TableTypeEnum(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}
}

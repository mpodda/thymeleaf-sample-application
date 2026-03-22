package com.mpodda.thymeleaf_sample.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableDataModel implements Serializable {
	private static final long serialVersionUID = -1508868895110143119L;
	
	private List<TableColumnModel> tableColumns;
	
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<TableColumnModel> getTableColumns() {
		return tableColumns;
	}

	public void setTableColumns(List<TableColumnModel> tableColumns) {
		this.tableColumns = tableColumns;
	}
	
	public void addTableColumn(TableColumnModel tableColumn) {
		if (this.tableColumns == null) {
			this.tableColumns = new ArrayList<TableColumnModel>();
		}
		
		this.tableColumns.add(tableColumn);
	}
}

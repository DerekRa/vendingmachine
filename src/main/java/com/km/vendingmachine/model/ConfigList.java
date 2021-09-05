package com.km.vendingmachine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "configList")
@ApiModel(description="Details about Config List Model")
public class ConfigList implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "config_seq")
    @Column(nullable = false, updatable = false)
	@ApiModelProperty(position = 0, notes = "Unique id and auto generate with hybernate of Config List")
	private int id;
	@ApiModelProperty(position = 1, notes = "Rows will be Letters")
	private int rows;
	@ApiModelProperty(position = 2, notes = "Columns will be Numbers")
	private int columns;
	
	public ConfigList() {}

	public ConfigList(int id, int rows, int columns) {
		super();
		this.id = id;
		this.rows = rows;
		this.columns = columns;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "ConfigList [id=" + id + ", rows=" + rows + ", columns=" + columns + "]";
	}
}

package com.viewkm;

import java.util.List;

import com.viewkm.DataSourceEntity;

import weka.core.FastVector;
import weka.core.Instances;

public interface ImportArrfDataService {
	/**
	 * 其中:m_RelationName是表�?
	 * m_Attributes是属性名
	 * @param m_RelationName
	 * @param m_Attributes
	 */
	public void createArrfTable(String m_RelationName,FastVector m_Attributes);
	
	/**
	 * 插入数据�?
	 * @param m_RelationName 
	 * @param instances
	 */
	public void addInstances(String m_RelationName, Instances instances);
	
    /**
     * 数据源表
     * @param dataSourceEntity
     */
	public void addDataSourceEntity(DataSourceEntity dataSourceEntity);
	/**
	 * 获取数据�?
	 * @return
	 */
	public List<DataSourceEntity> getDataSourceEntityList();
	/**
	 * 获取单个数据�?
	 * @param id
	 * @return
	 */
	public DataSourceEntity getDataSourceEntityByPK(int id);
}
